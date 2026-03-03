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
import beast.base.inference.parameter.IntegerParameter;
import beast.base.util.Randomizer;

@Description("Operator which acts on subsets of elements of an IntegerParameter " +
        "by uniformly selecting new values from the allowed range of values, " +
        "similar to the action of IntUniformOperator.")
public class BlockIntUniformOperator extends Operator {

    public Input<IntegerParameter> parameterInput = new Input<>(
            "parameter", "Parameter to operate on", Input.Validate.REQUIRED);

    public Input<BooleanParameter> indicatorInput = new Input<>(
            "indicator",
            "Boolean vector indicating which elements to operate on. " +
                    "(If absent, all elements are operated on.)");

    IntegerParameter parameter;
    BooleanParameter indicator;
    boolean hasIndicator;

    @Override
    public void initAndValidate() {
        parameter = parameterInput.get();
        indicator = indicatorInput.get();
        hasIndicator = indicator != null;

        if (hasIndicator && indicator.getDimension() != parameter.getDimension())
            throw new IllegalArgumentException("Indicator and parameter dimension must match.");
    }

    @Override
    public double proposal() {

        for (int i=0; i<parameter.getDimension(); i++) {
            if (hasIndicator && !indicator.getValue(i))
                continue;

            int newVal = parameter.getLower() + Randomizer.nextInt(parameter.getUpper() - parameter.getLower() + 1);

            if (newVal>parameter.getUpper() || newVal<parameter.getLower())
                return Double.NEGATIVE_INFINITY;

            parameter.setValue(i, newVal);
        }

        return 0.0;
    }
}
