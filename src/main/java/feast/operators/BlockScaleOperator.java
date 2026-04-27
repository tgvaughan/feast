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
import beast.base.inference.Operator;
import beast.base.inference.parameter.BooleanParameter;
import beast.base.inference.parameter.RealParameter;
import beast.base.spec.domain.Real;
import beast.base.spec.inference.parameter.BoolVectorParam;
import beast.base.spec.inference.parameter.RealVectorParam;
import beast.base.util.Randomizer;

import java.util.HashSet;
import java.util.Set;

@Description("Operator which acts on subsets of elements of a RealVectorParam.")
public class BlockScaleOperator extends Operator {

    public Input<RealVectorParam<? extends Real>> parameterInput = new Input<>(
            "parameter", "Parameter to be scaled", Input.Validate.REQUIRED);

    public Input<BoolVectorParam> indicatorInput = new Input<>(
            "indicator",
            "Boolean vector indicating which elements to scale. " +
                    "(If absent, all elements are scaled together.)");

    public Input<Double> scaleFactorInput = new Input<>(
            "scaleFactor", "Lower bound of the scale factor.",
            0.8);

    RealVectorParam<? extends Real> parameter;
    BoolVectorParam indicator;
    boolean hasIndicator;

    double scaleFactor;

    @Override
    public void initAndValidate() {
        parameter = parameterInput.get();
        indicator = indicatorInput.get();
        hasIndicator = indicator != null;

        if (hasIndicator && indicator.size() != parameter.size())
            throw new IllegalArgumentException("Indicator and parameter dimension must match.");

        scaleFactor = scaleFactorInput.get();
    }

    /**
     * Set used for computing degrees of freedom.
     */
    private final Set<Double> uniqueValues = new HashSet<>();

    /**
     * Determine the number of degrees of freedom (unique element values)
     * among the indicated elements.
     *
     * @return degrees of freedom
     */
    private int getScaledDegreesOfFreedom() {
        uniqueValues.clear();

        for (int i=0; i<parameter.size(); i++) {
            if (hasIndicator && !indicator.get(i))
                continue;

            if (parameter.get(i) == 0.0)
                continue;

            uniqueValues.add(parameter.get(i));
        }

        return uniqueValues.size();
    }

    /**
     * Select scale factor uniformly at random from [scaleFactor, 1/scaleFactor]
     *
     * @return scale factor
     */
    private double chooseScaleFactor() {
        return scaleFactor + Randomizer.nextDouble()*(1.0/scaleFactor - scaleFactor);
    }

    /**
     * Scale indicated elements of parameter by f.
     *
     * @param f scale factor
     * @return true if scaling succeeds, false if bounds are exceeded.
     */
    private boolean scaleParameter(double f) {

        for (int i=0; i<parameter.size(); i++) {
            if (hasIndicator && !indicator.get(i))
                continue;

            double newVal = parameter.get(i)*f;

            if (newVal>parameter.getUpper() || newVal<parameter.getLower())
                return false;

            parameter.set(i, newVal);
        }

        return true;
    }

    @Override
    public double proposal() {

        double f = chooseScaleFactor();

        if (!scaleParameter(f))
            return Double.NEGATIVE_INFINITY;

        return Math.log(f)*(getScaledDegreesOfFreedom()-2);
    }
}
