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

package feast.modelselect;

import beast.base.core.Description;
import beast.base.core.Function;
import beast.base.core.Input;
import beast.base.inference.Operator;
import beast.base.inference.StateNode;
import beast.base.inference.distribution.ParametricDistribution;
import beast.base.inference.parameter.RealParameter;
import beast.base.util.Randomizer;
import org.apache.commons.math.MathException;

import java.util.*;

@Description("An operator for sampling the elements of a RealParameter (parameter) " +
        "by sampling from a Dirichlet process prior.")
public class DirichletProcessOperator extends Operator {

    public Input<RealParameter> parameterInput = new Input<>(
            "parameter",
            "Parameter to operate on.",
            Input.Validate.REQUIRED);

    public Input<ParametricDistribution> baseDistribInput = new Input<>(
            "baseDistr",
            "Base distribution for Dirichlet process",
            Input.Validate.REQUIRED);

    public Input<Function> scaleParameterInput = new Input<>(
            "scaleParameter",
            "Scale parameter for Dirichlet process",
            Input.Validate.REQUIRED);

    RealParameter param;
    Function scaleParam;
    ParametricDistribution distr;

    @Override
    public void initAndValidate() {
        param = parameterInput.get();
        distr = baseDistribInput.get();
        scaleParam = scaleParameterInput.get();
    }

    Map<Double,Integer> counts = new HashMap<>();

    @Override
    public double proposal() {

        double logHR = 0.0;

        int N = param.getDimension();
        double alpha = scaleParam.getArrayValue();

        int idx = Randomizer.nextInt(N);

        counts.clear();
        for (int i=0; i<N; i++) {
            if (i==idx)
                continue;
            counts.merge(param.getValue(i), 1, Integer::sum);
        }

        if (counts.get(param.getValue(idx)) == null)
            logHR += Math.log(alpha/(alpha+N-1)) + distr.logDensity(param.getValue(idx));
        else
            logHR += Math.log(counts.get(param.getValue(idx))/(alpha+N-1));

        double u = Randomizer.nextDouble()*(alpha+N-1);
        if (u < alpha) {
            logHR -= Math.log(alpha/(alpha+N-1));
            try {
                param.setValue(idx, distr.sample(1)[0][0]);
                logHR -= distr.logDensity(param.getValue(idx));
            } catch (MathException e) {
                throw new RuntimeException("Base distribution does not permit sampling.");
            }
        } else {
            int idxPrime = (int)Math.round(Math.floor(u-alpha));

            if (idxPrime>=idx)
                idxPrime += 1;

            param.setValue(idx, param.getValue(idxPrime));
            logHR -= Math.log(counts.get(param.getValue(idxPrime))/(alpha+N-1));
        }

        return logHR;
    }

    @Override
    public List<StateNode> listStateNodes() {
        List<StateNode> stateNodes = new ArrayList<>();
        stateNodes.add(param);

        return stateNodes;
    }
}
