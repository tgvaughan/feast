/*
 * Copyright (c) 2023 Tim Vaughan
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

import beast.base.core.Function;
import beast.base.core.Input;
import beast.base.inference.Distribution;
import beast.base.inference.State;
import beast.base.inference.distribution.ParametricDistribution;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class DirichletProcessPrior extends Distribution {

    public Input<Function> parameterInput = new Input<>("parameter",
            "Array to which to apply DPP.",
            Input.Validate.REQUIRED);

    public Input<ParametricDistribution> baseDistribInput = new Input<>(
            "baseDistr",
            "Base distribution for Dirichlet process",
            Input.Validate.REQUIRED);

    public Input<Function> scaleParameterInput = new Input<>("scaleParameter",
            "Scale parameter of DPP.",
            Input.Validate.REQUIRED);

    Function parameter, scaleParameter;
    ParametricDistribution baseDistr;

    @Override
    public void initAndValidate() {
        parameter = parameterInput.get();
        scaleParameter = scaleParameterInput.get();
        baseDistr = baseDistribInput.get();
    }

    Map<Double,Integer> counts = new HashMap<>();

    @Override
    public double calculateLogP() {
        logP = 0.0;

        double alpha = scaleParameter.getArrayValue();

        counts.clear();
        for (int i=0; i<parameter.getDimension(); i++) {
            double x = parameter.getArrayValue(i);
            if (counts.get(x) != null)
                logP += Math.log(counts.get(x)/(i+alpha));
            else
                logP += Math.log(alpha/(i+alpha)) + baseDistr.logDensity(x);

            counts.merge(x, 1, Integer::sum);
        }

        return logP;
    }

    @Override
    public List<String> getArguments() {
        return null;
    }

    @Override
    public List<String> getConditions() {
        return null;
    }

    @Override
    public void sample(State state, Random random) {
        throw new UnsupportedOperationException();
    }
}
