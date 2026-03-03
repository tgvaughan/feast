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

package feast.operators;

import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.inference.parameter.RealParameter;
import beast.base.util.Randomizer;

@Description("Scale operator which scales identical values together.")
public class SmartScaleOperator extends SmartRealOperator {

    public Input<Double> scaleFactorInput = new Input<>("scaleFactor",
            "Scale factor will be chosen between scaleFactor and 1/scaleFactor.", 0.75);

    final public Input<Boolean> optimiseInput = new Input<>("optimise",
            "flag to indicate that the scale factor is automatically changed " +
                    "in order to achieve a good acceptance rate (default true)", true);

    public Input<Double> scaleFactorUpperLimitInput = new Input<>("scaleFactorUpperLimit",
            "Upper Limit of scale factor", 1.0 - 1e-8);
    public Input<Double> scaleFactorLowerLimitInput = new Input<>("scaleFactorLowerLimit",
            "Lower limit of scale factor", 1e-8);

    double scaleFactor, scaleFactorLowerLimit, scaleFactorUpperLimit;

    @Override
    public void initAndValidate() {
        super.initAndValidate();

        scaleFactor = scaleFactorInput.get();

        scaleFactorLowerLimit = scaleFactorLowerLimitInput.get();
        scaleFactorUpperLimit = scaleFactorUpperLimitInput.get();
    }

    @Override
    public double proposal() {

        // Select class at random

        int classIdx = Randomizer.nextInt(nClasses);

        // Choose scale factor

        double minf = Math.min(scaleFactor, 1.0/scaleFactor);
        double f = minf + Randomizer.nextDouble()*(1.0/minf - minf);

        // Scale selected elements:

        for (RealParameter param : parameters) {
            Integer[] group = groups.get(param);

            for (int i=0; i<param.getDimension(); i++) {
                if (group[i] == classIdx) {
                    double newVal = f * param.getValue(i);
                    if (newVal < param.getLower() || newVal > param.getUpper())
                        return Double.NEGATIVE_INFINITY;
                    else
                        param.setValue(i, newVal);
                }
            }
        }

        // Hastings ratio for x -> f*x with f ~ [alpha,1/alpha] is 1/f.

        return -Math.log(f);
    }

    @Override
    public void optimize(double logAlpha) {
        if (optimiseInput.get()) {
            double delta = calcDelta(logAlpha);
            delta += Math.log(1.0/scaleFactor - 1.0);
            setCoercableParameterValue(1.0/(Math.exp(delta) + 1.0));
        }
    }

    @Override
    public double getCoercableParameterValue() {
        return scaleFactor;
    }

    @Override
    public void setCoercableParameterValue(double value) {
        scaleFactor = Math.max(Math.min(value, scaleFactorUpperLimit), scaleFactorLowerLimit);
    }
}
