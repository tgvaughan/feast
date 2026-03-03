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

package feast.function;

import beast.base.core.Description;
import beast.base.core.Function;
import beast.base.core.Input;

import java.util.ArrayList;
import java.util.List;

@Description("A Function whose elements are the elements of an input Function " +
        "scaled by another input Function.")
public class Scale extends LoggableFunction {

    public Input<Function> functionInput = new Input<>("function",
            "Function to scale", Input.Validate.REQUIRED);

    public Input<List<Function>> scalingFactorsInput = new Input<>("scaleBy",
            "Amount to scale by", new ArrayList<>());

    Function function;
    List<Function> scalingFactors;

    @Override
    public void initAndValidate() {
        function = functionInput.get();
        scalingFactors = scalingFactorsInput.get();

        for (Function scalingFactor : scalingFactors)
        if (scalingFactor.getDimension() != 1)
            throw new IllegalArgumentException("Dimension of scaleBy argument to Scale is not 1.");
    }

    @Override
    public int getDimension() {
        return function.getDimension();
    }

    @Override
    public double getArrayValue(int i) {
        double res=function.getArrayValue(i);
        for (Function scalingFactor : scalingFactors)
            res *= scalingFactor.getArrayValue();

        return res;
    }
}
