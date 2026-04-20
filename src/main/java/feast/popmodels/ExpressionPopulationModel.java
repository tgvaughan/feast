/*
 * Copyright (c) 2023 ETH Zurich
 *
 * This file is part of feast.
 *
 * feast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * feast is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with feast. If not, see <https://www.gnu.org/licenses/>.
 */

package feast.popmodels;

import beast.base.core.BEASTObject;
import beast.base.core.Description;
import beast.base.core.Function;
import beast.base.core.Input;
import beast.base.evolution.tree.coalescent.PopulationFunction;
import beast.base.inference.parameter.RealParameter;
import beast.base.spec.domain.Real;
import beast.base.spec.type.RealVector;
import feast.expressions.parser.ExpCalculatorVisitor;
import feast.expressions.parser.ExpressionLexer;
import feast.expressions.parser.ExpressionParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.math4.legacy.analysis.integration.TrapezoidIntegrator;
import org.apache.commons.math4.legacy.ode.FirstOrderDifferentialEquations;
import org.apache.commons.math4.legacy.ode.events.EventHandler;
import org.apache.commons.math4.legacy.ode.nonstiff.DormandPrince54Integrator;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Description("A population model where the population size function is given by an" +
        " expression evaluated at runtime.")
public class ExpressionPopulationModel extends PopulationFunction.Abstract {

    public Input<String> expressionInput = new Input<>("value",
            "Expression needed for calculations.", Input.Validate.REQUIRED);

    public Input<Boolean> isLogInput = new Input<>("isLog",
            "True if expression represents log(P), false if it represents P. "
                    + "Default is false.", false);

    public Input<List<RealVector>> realVectorsInput = new Input<>(
            "arg", "Parameters/functions needed for the calculation",
            new ArrayList<>());

    public Input<Integer> maxTimeToConsiderInput = new Input<>(
            "maxTimeToConsider",
            "Maximum time to consider when numerically inverting intensity integral.",
            1000);

    ParseTree parseTree;
    ExpCalculatorVisitor visitor;
    TimeRealVectorParam timeParam;
    boolean isLog;
    double maxTimeToConsider;

    TrapezoidIntegrator intensityIntegrator;

    InvIntensityODE invIntensityODE;
    InvIntensityEventHandler invIntensityEventHandler;
    DormandPrince54Integrator invIntensityIntegrator;

    @Override
    public void initAndValidate() {

        // Parameter to house real value
        timeParam = new TimeRealVectorParam(1.0);

        isLog = isLogInput.get();
        maxTimeToConsider = maxTimeToConsiderInput.get();

        // Assemble name->param map
        Map<String, RealVector> realVectorMap = new HashMap<>();
        realVectorMap.put("t", timeParam);
        for (RealVector realVector : realVectorsInput.get()) {
            BEASTObject obj = (BEASTObject)realVector;
            realVectorMap.put(obj.getID(), realVector);
        }

        // Build AST from expression string
        CharStream input = CharStreams.fromString(expressionInput.get());
        ExpressionLexer lexer = new ExpressionLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExpressionParser parser = new ExpressionParser(tokens);
        parseTree = parser.expression();

        // Create new visitor for calculating expression values:
        visitor = new ExpCalculatorVisitor(realVectorMap);

        if (visitor.visit(parseTree).length != 1)
            throw new IllegalArgumentException("ExpressionPopulationModel "
                    + "expressions must be single-valued.");

        intensityIntegrator = new TrapezoidIntegrator();

        invIntensityIntegrator = new DormandPrince54Integrator(1e-6,
                1, 1e-3, 1e-3);
        invIntensityODE = new InvIntensityODE();
        invIntensityEventHandler = new InvIntensityEventHandler();
    }

    @Override
    public List<String> getParameterIds() {
        return null;
    }

    @Override
    public double getPopSize(double t) {
        timeParam.setValue(t);
        double val = visitor.visit(parseTree)[0];
        if (isLog)
            return Math.exp(val);

        if (val<0)
            throw new IllegalStateException("ExpressionPopulationModel expression " +
                    "evaluated to negative population size at t=" + t);

        return val;
    }

    @Override
    public double getIntensity(double t) {
        if (t == 0.0)
            return 0.0;

        // Compute intensity using a rough numerical approach.
        // TODO figure out a caching scheme to avoid recomputing this intgral so frequently

        return intensityIntegrator.integrate(Integer.MAX_VALUE, v -> 1.0/getPopSize(v), 0, t);
    }

    double[] intensity = new double[1];

    @Override
    public double getInverseIntensity(double x) {
        if (x == 0.0 || x == Double.POSITIVE_INFINITY)
            return x;

        // Computes inverse intensity using a rough numerical approach.
        // TODO figure out a caching scheme to avoid recomputing this integral so frequently.

        invIntensityIntegrator.clearEventHandlers();
        invIntensityIntegrator.addEventHandler(invIntensityEventHandler,
                1, 1e-3, Integer.MAX_VALUE);
        invIntensityEventHandler.setTarget(x);

        intensity[0] = 0.0;
        double result = invIntensityIntegrator.integrate(invIntensityODE,
                0, intensity, maxTimeToConsider, intensity);

        if (result >= maxTimeToConsider)
            throw new IllegalStateException("Numerical error computing inverse " +
                    "intensity. (Maybe population size is changing too quickly?)");

        return result;
    }

    /**
     * Light-weight RealScalarParam-equivalent used to bind the time value
     * to the symbol "t" in the population size expression.
     */
    private static class TimeRealVectorParam implements RealVector<Real> {

        double value;

        public TimeRealVectorParam(double value) {
            this.value = value;
        }

        @Override
        public Real getDomain() {
            return Real.INSTANCE;
        }

        @Override
        public List<Double> getElements() {
            return List.of(value);
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public double get(int i) {
            return value;
        }

        public void setValue(double newValue) {
            value = newValue;
        }
    }

    /**
     * ODE used to numerically invert intensity function
     */
    private class InvIntensityODE implements FirstOrderDifferentialEquations {

        @Override
        public int getDimension() {
            return 1;
        }

        @Override
        public void computeDerivatives(double t, double[] y, double[] ydot) {
            ydot[0] = 1/getPopSize(t);
        }
    }

    /**
     * Event handler used to numerically invert intensity function
     */
    private static class InvIntensityEventHandler implements EventHandler {
        double target = 0.0;

        public void setTarget(double target)  {
            this.target = target;
        }

        @Override
        public void init(double t0, double[] y0, double t) { }

        @Override
        public double g(double t, double[] y) {
            return y[0]-target;
        }

        @Override
        public Action eventOccurred(double t, double[] y, boolean increasing) {
            return Action.STOP;
        }

        @Override
        public void resetState(double t, double[] y) { }
    }

    /**
     * Main method for debugging only.
     *
     * @param args command line arguments (unused)
     */
    public static void main(String[] args) {

        RealParameter arg = new RealParameter("1.0");
        arg.setID("alpha");

        ExpressionPopulationModel epm = new ExpressionPopulationModel();
        epm.initByName(
                "value", "alpha*(cos(t)+1)",
                "isLog", true,
                "arg", arg);

        // intensity(t) = log(T/alpha + 1)

        try (PrintStream ps = new PrintStream("out.txt")) {
            ps.println("t\tN\tintensity\tinvIntensity");
            for (double t = 0; t < 10; t += 0.01) {
                ps.println(t + "\t" + epm.getPopSize(t) + "\t" + epm.getIntensity(t)
                        + "\t" + epm.getInverseIntensity(epm.getIntensity(t)));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
