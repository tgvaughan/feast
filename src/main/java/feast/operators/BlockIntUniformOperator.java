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
import beast.base.spec.domain.Int;
import beast.base.spec.inference.parameter.BoolVectorParam;
import beast.base.spec.inference.parameter.IntVectorParam;
import beast.base.util.Randomizer;

@Description("Operator which acts on subsets of elements of an IntVectorParam " +
        "by uniformly selecting new values from the allowed range of values, " +
        "similar to the action of IntUniformOperator.")
public class BlockIntUniformOperator extends Operator {

    public Input<IntVectorParam<? extends Int>> parameterInput = new Input<>(
            "parameter", "Parameter to operate on", Input.Validate.REQUIRED);

    public Input<BoolVectorParam> indicatorInput = new Input<>(
            "indicator",
            "Boolean vector indicating which elements to operate on. " +
                    "(If absent, all elements are operated on.)");

    IntVectorParam<? extends Int> parameter;
    BoolVectorParam indicator;
    boolean hasIndicator;

    @Override
    public void initAndValidate() {
        parameter = parameterInput.get();
        indicator = indicatorInput.get();
        hasIndicator = indicator != null;

        if (hasIndicator && indicator.size() != parameter.size())
            throw new IllegalArgumentException("Indicator and parameter dimension must match.");
    }

    @Override
    public double proposal() {

        for (int i=0; i<parameter.size(); i++) {
            if (hasIndicator && !indicator.get(i))
                continue;

            int newVal = parameter.getLower() + Randomizer.nextInt(parameter.getUpper() - parameter.getLower() + 1);

            if (newVal>parameter.getUpper() || newVal<parameter.getLower())
                return Double.NEGATIVE_INFINITY;

            parameter.set(i, newVal);
        }

        return 0.0;
    }
}
