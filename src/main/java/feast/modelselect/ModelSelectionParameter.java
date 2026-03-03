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

package feast.modelselect;

import beast.base.core.Description;
import beast.base.core.Function;
import beast.base.core.Input;
import beast.base.core.Loggable;
import beast.base.inference.CalculationNode;
import beast.base.inference.parameter.IntegerParameter;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@Description("Class of Functions useful for BSSVS-style model selection/averaging.")
public class ModelSelectionParameter extends CalculationNode implements Function, Loggable {

    public Input<List<Function>> parametersInput = new Input<>("parameter",
            "Parameter for the selection pool.",
            new ArrayList<>());

    public Input<IntegerParameter> selectionIndicesInput = new Input<>("selectionIndices",
            "Integer parameter containing indicies to which each output parameter is mapped.",
            Input.Validate.REQUIRED);

    public Input<Integer> thisIndexInput = new Input<>("thisIndex",
            "Index of output.", 0);

    @Override
    public void initAndValidate() { }


    /*
     * Function
     */

    @Override
    public int getDimension() {
        return parametersInput.get().get(selectionIndicesInput.get().getValue(thisIndexInput.get())).getDimension();
    }

    @Override
    public double getArrayValue(int dim) {
        return parametersInput.get().get(selectionIndicesInput.get().getValue(thisIndexInput.get())).getArrayValue(dim);
    }


    /*
     *  Loggable
     */

    @Override
    public void init(final PrintStream out) {
        final int valueCount = getDimension();
        if (valueCount == 1) {
            out.print(getID() + "\t");
        } else {
            for (int value = 0; value < valueCount; value++) {
                out.print(getID() + (value + 1) + "\t");
            }
        }
    }

    @Override
    public void log(final long sample, final PrintStream out) {
        for (int i = 0; i < getDimension(); i++) {
            out.print(getArrayValue(i) + "\t");
        }
    }

    @Override
    public void close(PrintStream out) {

    }
}
