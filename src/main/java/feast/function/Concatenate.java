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

@Description("A Function whose elements are the result of concatenating the elements of the input Functions.")
public class Concatenate extends LoggableFunction {

    public Input<List<Function>> functionsInput = new Input<>("arg",
            "One or more functions to concatenate.",
            new ArrayList<>());

    List<Function> functions;
    int totalDim;

    @Override
    public void initAndValidate() {
        functions = functionsInput.get();

        if (functions.isEmpty())
            throw new IllegalArgumentException("Concatenate expects at least one input function.");

        totalDim = 0;
        for (Function func : functionsInput.get())
            totalDim += func.getDimension();
    }

    @Override
    public int getDimension() {
        return totalDim;
    }

    @Override
    public double getArrayValue(int dim) {
        int paramIdx=0;
        while (dim >= functions.get(paramIdx).getDimension()) {
            dim -= functions.get(paramIdx).getDimension();
            paramIdx += 1;
        }

        return functions.get(paramIdx).getArrayValue(dim);
    }
}
