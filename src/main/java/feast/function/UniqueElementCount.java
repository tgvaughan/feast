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

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

@Description("Function representing the number of unique elements of another function.")
public class UniqueElementCount extends LoggableFunction {

    public Input<Function> argInput = new Input<>("arg",
            "Number of unique elements of this parameter will be logged.",
            Input.Validate.REQUIRED);

    Set<Double> valueSet = new HashSet<>();

    @Override
    public void initAndValidate() {
    }

    @Override
    public int getDimension() {
        return 1;
    }

    @Override
    public double getArrayValue(int dim) {

        if (dim > 0)
            return 0;

        valueSet.clear();
        for (int i = 0; i< argInput.get().getDimension(); i++) {
            valueSet.add(argInput.get().getArrayValue(i));
        }

        return valueSet.size();
    }

    @Override
    public void init(PrintStream out) {
        out.print(getID() + "\t");
    }

    @Override
    public void log(long sample, PrintStream out) {
        out.print(getArrayValue(0));
    }

    @Override
    public void close(PrintStream out) {
    }
}
