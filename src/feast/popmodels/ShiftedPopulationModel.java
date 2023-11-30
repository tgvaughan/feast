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

package feast.popmodels;

import beast.base.core.Description;
import beast.base.core.Function;
import beast.base.core.Input;
import beast.base.evolution.tree.coalescent.ExponentialGrowth;
import beast.base.evolution.tree.coalescent.PopulationFunction;
import beast.base.inference.parameter.RealParameter;

import java.io.PrintStream;
import java.util.List;

@Description("A population model which is time-shifted relative to an input" +
        " population model.")
public class ShiftedPopulationModel extends PopulationFunction.Abstract {

    public Input<PopulationFunction> popFuncInput = new Input<>(
            "populationModel",
            "Population model to shift in time",
            Input.Validate.REQUIRED);

    public Input<Function> offsetInput = new Input<>(
            "offset",
            "Time offset to use.",
            Input.Validate.REQUIRED);

    PopulationFunction popFunc;
    Function offset;

    @Override
    public void initAndValidate() {
        popFunc = popFuncInput.get();
        offset = offsetInput.get();
    }

    @Override
    public List<String> getParameterIds() {
        return null;
    }

    @Override
    public double getPopSize(double t) {
        return popFunc.getPopSize(t+offset.getArrayValue());
    }

    @Override
    public double getIntensity(double t) {
        return popFunc.getIntensity(t+offset.getArrayValue()) - popFunc.getIntensity(offset.getArrayValue());
    }

    @Override
    public double getInverseIntensity(double x) {
        return popFunc.getInverseIntensity(x + popFunc.getIntensity(offset.getArrayValue())) - offset.getArrayValue();
    }

    public static void main(String[] args) {

        ExponentialGrowth epm = new ExponentialGrowth();
        epm.initByName("popSize", new RealParameter("10.0"),
                "growthRate", new RealParameter("1.0"));

        ShiftedPopulationModel spm = new ShiftedPopulationModel();
        spm.initByName("populationModel", epm,
                "offset", new RealParameter("0.5"));

        try (PrintStream out = System.out) {
            out.println("t\tN\tx\ttprime");

            double T = 10.0; int N=101;
            for (int i=0; i<N; i++) {
                double t = i * T / (N - 1);
                out.println(t + "\t" + spm.getPopSize(t)
                        + "\t" + spm.getIntensity(t)
                        + "\t" + spm.getInverseIntensity(spm.getIntensity(t)));
            }
        }
    }
}
