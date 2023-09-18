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

package feast.operators;

import beast.base.core.Input;
import beast.base.inference.Operator;
import beast.base.inference.parameter.BooleanParameter;
import beast.base.inference.parameter.IntegerParameter;
import beast.base.util.Randomizer;

public class BlockIntRandomWalkOperator extends Operator {

    public Input<IntegerParameter> windowSizeInput = new Input<>(
            "windowSize", "The size of the window centred on the " +
            "old value within which the new value can be chosen.", Input.Validate.REQUIRED);

    public Input<IntegerParameter> parameterInput = new Input<>(
            "parameter", "Parameter to operate on", Input.Validate.REQUIRED);

    public Input<BooleanParameter> indicatorInput = new Input<>(
            "indicator",
            "Boolean vector indicating which elements to operate on. " +
                    "(If absent, all elements are operated on.)");

    IntegerParameter parameter;
    BooleanParameter indicator;
    boolean hasIndicator;

    int windowSize;

    @Override
    public void initAndValidate() {
        parameter = parameterInput.get();
        indicator = indicatorInput.get();
        hasIndicator = indicator != null;

        if (hasIndicator && indicator.getDimension() != parameter.getDimension())
            throw new IllegalArgumentException("Indicator and parameter dimension must match.");

        windowSize = windowSizeInput.get().getValue();
    }

    @Override
    public double proposal() {

        for (int i=0; i<parameter.getDimension(); i++) {
            if (hasIndicator && !indicator.getValue(i))
                continue;

            int newVal = parameter.getValue(i) +
                    Randomizer.nextInt(2*windowSize + 1) - windowSize;

            if (newVal>parameter.getUpper() || newVal<parameter.getLower())
                return Double.NEGATIVE_INFINITY;

            parameter.setValue(i, newVal);
        }

        return 0.0;
    }
}
