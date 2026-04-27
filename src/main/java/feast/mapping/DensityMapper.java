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

package feast.mapping;

import beast.base.core.BEASTObject;
import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.inference.*;
import beast.base.inference.Runnable;
import beast.base.inference.parameter.BooleanParameter;
import beast.base.inference.parameter.IntegerParameter;
import beast.base.inference.parameter.RealParameter;
import beast.base.spec.domain.Bool;
import beast.base.spec.domain.Int;
import beast.base.spec.domain.Real;
import beast.base.spec.inference.parameter.RealVectorParam;
import beast.base.spec.type.BoolVector;
import beast.base.spec.type.IntVector;

import java.util.*;

/**
 * @author Tim Vaughan
 */
@Description("Utility for mapping probability density surfaces.")
public class DensityMapper extends Runnable {

    public Input<List<RealVectorParam<? extends Real>>> realParamsInput = new Input<>(
            "realParam",
            "Real parameter to vary.",
            new ArrayList<>());

    public Input<List<IntVector<? extends Int>>> stepsInput = new Input<>(
            "steps",
            "Number of steps to take between parameter bounds. 1 means use" +
            "initial value.",
            new ArrayList<>());

    public Input<List<BoolVector>> logScaleInput = new Input<>(
            "logScale",
            "Whether to use log scale when stepping. Default false.",
            new ArrayList<>());

    public Input<List<Distribution>> distribsInput = new Input<>(
            "distribution",
            "Distribution to map.",
            new ArrayList<>());

    public Input<List<Logger>> loggersInput = new Input<>(
            "logger",
            "Logger used to store output.",
            new ArrayList<>());

    public Input<Boolean> acceptAfterDistribCalculationInput = new Input<>(
            "acceptAfterDistribCalculation",
            "If true, call state.acceptCalculationNodes() after distribution " +
                    "has been computed.  Default true.",
            true);

    int nValues;
    int sample;

    State dummyState;

    public DensityMapper() { }

    @Override
    public void initAndValidate() {

        if (realParamsInput.get().size() != stepsInput.get().size())
            throw new IllegalArgumentException("Number of step sizes " +
                    "must match number of real params.");

        if (!logScaleInput.get().isEmpty() && logScaleInput.get().size() != realParamsInput.get().size())
            throw new IllegalArgumentException("Number of logScale elements " +
                    "must match number of real params.");

        // Calculate total number of params to vary
        nValues = 0;
        for (int i=0; i<realParamsInput.get().size(); i++) {
            int thisN = realParamsInput.get().get(i).size();
            if (stepsInput.get().get(i).size() != 1
                    && stepsInput.get().get(i).size() != thisN)
                throw new IllegalArgumentException("Dimension of step sizes " +
                        "param must be 1 or the dimension of the associated " +
                        "real param.");

            nValues += thisN;
        }

        // Add RealParameters to dummy state:
        dummyState = new State();

        Set<StateNode> stateNodes = new HashSet<>();
        for (Distribution distrib : distribsInput.get())
            stateNodes.addAll(collectStateNodes(distrib));

        for (StateNode stateNode : stateNodes)
            dummyState.stateNodeInput.setValue(stateNode, dummyState);

        dummyState.initAndValidate();
        dummyState.initialise();

        sample = 0;

    }

    Set<StateNode> collectStateNodes(BEASTObject rootObject) {
       HashSet<StateNode> stateNodes = new HashSet<>();

        for (Input<?> input : rootObject.getInputs().values()) {
            if (input.get() instanceof  StateNode)
                stateNodes.add((StateNode) input.get());
            else if (input.get() instanceof CalculationNode) {
                stateNodes.addAll(collectStateNodes((CalculationNode)input.get()));
            } else if (input.get() instanceof List<?>) {
                for (Object obj : (List<?>) input.get()) {
                    if (obj instanceof StateNode)
                        stateNodes.add((StateNode) obj);
                    else if (obj instanceof CalculationNode)
                        stateNodes.addAll(collectStateNodes((BEASTObject) obj));
                }
            }
        }

        return stateNodes;
    }

    @Override
    public void run() throws Exception {
        // Initialise loggers:
        for (Logger logger : loggersInput.get())
            logger.init();

        // Loop over parameter values
        nestedLoop(0);

        // Finalize loggers
        loggersInput.get().forEach(Logger::close);
    }

    /**
     * Perform nested loop over combinations of parameter values
     *
     * @param depth initialize to zero,
     */
    void nestedLoop(int depth) {

        if (depth<nValues) {
            int paramIdx;
            int elIdx = 0;
            int count = 0;
            for (paramIdx = 0; paramIdx<realParamsInput.get().size(); paramIdx++) {
                RealVectorParam<? extends Real> thisParam = realParamsInput.get().get(paramIdx);
                if (count + thisParam.size() > depth) {
                    elIdx = depth - count;
                    break;
                }
                count += thisParam.size();
            }

            boolean stepAll = stepsInput.get().get(paramIdx).size() == 1;

            int nSteps;
            if (stepAll)
                nSteps = stepsInput.get().get(paramIdx).get(0);
            else
                nSteps = stepsInput.get().get(paramIdx).get(elIdx);

            RealVectorParam<? extends Real> param = realParamsInput.get().get(paramIdx);

            boolean useLog = logScaleInput.get().isEmpty()
                    ? false
                    : logScaleInput.get().get(paramIdx).get();

            double delta;
            if (nSteps>1) {
                if (useLog) {
                    delta = (Math.log(param.getUpper()) - Math.log(param.getLower()))/(nSteps-1);
                } else {
                    delta = (param.getUpper()-param.getLower())/(nSteps-1);
                }
            } else {
                delta = 0.0;
            }

            if (stepAll) {
                for (int i=0; i<stepsInput.get().get(paramIdx).get(0); i++) {
                    for (elIdx = 0; elIdx < param.size(); elIdx++) {
                        if (delta > 0.0) {
                            if (useLog) {
                                param.set(elIdx, Math.exp(Math.log(param.getLower()) + i * delta));
                            } else {
                                param.set(elIdx, param.getLower() + i * delta);
                            }
                        }
                    }

                    nestedLoop(depth + param.size());
                }
            } else {
                for (int i = 0; i < stepsInput.get().get(paramIdx).get(elIdx); i++) {
                    if (delta>0.0)
                        param.set(elIdx, param.getLower() + i * delta);
                    nestedLoop(depth + 1);
                }

            }

        } else {
            for (Distribution distrib : distribsInput.get()) {
                try {
                    for (StateNode stateNode : dummyState.stateNodeInput.get())
                        stateNode.setEverythingDirty(true);

                    dummyState.setPosterior(distrib);
                    dummyState.checkCalculationNodesDirtiness();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    dummyState.robustlyCalcPosterior(distrib);
                } catch (Exception e) {
                    System.out.println("Error computing density.");
                    e.printStackTrace();
                }
            }

            if (acceptAfterDistribCalculationInput.get())
                dummyState.acceptCalculationNodes();

            for (Logger logger : loggersInput.get()) {
                logger.log(sample);
            }

            sample += 1;
        }
    }
}
