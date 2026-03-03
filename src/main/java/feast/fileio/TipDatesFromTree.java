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

package feast.fileio;

import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.evolution.tree.Node;
import beast.base.evolution.tree.TraitSet;
import beast.base.evolution.tree.Tree;

@Description("Traitset for tip dates obtained from input tree")
public class TipDatesFromTree extends TraitSet{

    public Input<Tree> treeInput = new Input<>("tree", "Tree from which to " +
            "extract tip dates.", Input.Validate.REQUIRED);

    public TipDatesFromTree() {
        traitsInput.setRule(Input.Validate.OPTIONAL);
        traitNameInput.setRule(Input.Validate.OPTIONAL);
    }

    @Override
    public void initAndValidate() {

        traitNameInput.setValue("date-backward", this);

        StringBuilder valueBuilder = new StringBuilder();

        boolean isFirst = true;
        for (Node leaf : treeInput.get().getExternalNodes()) {
            if (isFirst)
                isFirst = false;
            else
                valueBuilder.append(",");
            valueBuilder.append(leaf.getID() + "=" + leaf.getHeight());
        }

        traitsInput.setValue(valueBuilder.toString(), this);

        super.initAndValidate();
    }
}
