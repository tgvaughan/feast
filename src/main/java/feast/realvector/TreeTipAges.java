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

package feast.realvector;

import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.evolution.tree.Tree;
import beast.base.spec.domain.NonNegativeReal;

@Description("RealVector representing ages of leaf nodes of tree.")
public class TreeTipAges extends CalculatedRealVector<NonNegativeReal> {

    public Input<Tree> treeInput = new Input<>("tree",
            "Tree to extract leaf ages from.",
            Input.Validate.REQUIRED);

    @Override
    public void initAndValidate() {
    }

    @Override
    public NonNegativeReal getDomain() {
        return NonNegativeReal.INSTANCE;
    }

    @Override
    public int size() {
        return treeInput.get().getLeafNodeCount();
    }

    @Override
    public double get(int i) {
        return treeInput.get().getNode(i).getHeight();
    }
}
