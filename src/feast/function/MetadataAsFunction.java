/*
 * Copyright (c) 2025 ETH Zürich
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

@Description("Represents tree node metadata as a function with as many elements " +
        "as there are nodes in the tree.  Values are ordered according to node " +
        "number, and missing values are included as NaNs.")
public class MetadataAsFunction extends LoggableFunction {

    public Input<Tree> treeInput = new Input<>("tree",
            "Tree whose metadata to represent as a function.",
            Input.Validate.REQUIRED);

    public Input<String> keyInput = new Input<>("key",
            "Key specifying a particular metadata value.",
            Input.Validate.REQUIRED);

    Tree tree;
    String key;

    @Override
    public void initAndValidate() {
        tree = treeInput.get();
        key = keyInput.get();
    }

    @Override
    public int getDimension() {
        return tree.getNodeCount();
    }

    @Override
    public double getArrayValue(int idx) {
        Object val = tree.getNode(idx).getMetaData(key);
        if (val instanceof Double valDouble)
            return valDouble;
        else
            return Double.NaN;
    }
}
