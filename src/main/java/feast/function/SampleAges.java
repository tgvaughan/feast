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
import beast.base.core.Input;
import beast.base.evolution.tree.Tree;

@Description("Function representing ages of sample nodes of tree.")
public class SampleAges extends LoggableFunction {

    public Input<Tree> treeInput = new Input<>("tree",
            "Tree to extract leaf ages from.",
            Input.Validate.REQUIRED);

    @Override
    public void initAndValidate() {
    }

    @Override
    public int getDimension() {
        return treeInput.get().getLeafNodeCount();
    }

    @Override
    public double getArrayValue(int dim) {
        return treeInput.get().getArrayValue(dim);
    }
}
