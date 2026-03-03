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

/**
 * @author Tim Vaughan
 */
@Description("A function produced by interleaving the elements of two " +
        "or more input functions.")
public class Interleave extends LoggableFunction {

    public Input<List<Function>> argsInput = new Input<>("arg",
            "Function to interleave.",
            new ArrayList<>());

    int maxLen, dim;
    List<Function> args;

    @Override
    public void initAndValidate() {
        args = argsInput.get();

        maxLen = 0;
        for (Function arg : args)
            maxLen = Math.max(arg.getDimension(), maxLen);

        dim = maxLen*args.size();
    }

    @Override
    public int getDimension() {
        return dim;
    }

    @Override
    public double getArrayValue(int i) {
        if (i >= dim)
            throw new IllegalArgumentException("Index exceeds length of interleaved function.");

        int col = i / args.size();
        int row = i % args.size();

        Function arg = args.get(row);
        return arg.getArrayValue(col % arg.getDimension());
    }
}
