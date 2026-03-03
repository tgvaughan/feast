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
import beast.base.evolution.alignment.Taxon;
import beast.base.evolution.alignment.TaxonSet;
import beast.base.evolution.tree.Tree;

@Description("Represents a TaxonSet object where the taxon names are " +
        "extracted from the given tree.")
public class TaxonSetFromTree extends TaxonSet {

    public Input<Tree> treeInput = new Input<>("tree",
            "Tree from which to take taxon set.",
            Input.Validate.REQUIRED);

    public TaxonSetFromTree() {
        alignmentInput.setRule(Input.Validate.FORBIDDEN);
    }

    @Override
    public void initAndValidate() {
        TaxonSet taxonSet = treeInput.get().getTaxonset();
        if (taxonSet == null)
            throw new IllegalArgumentException("Tree provided to " +
                    "TaxonSetFromTree has no taxon set defined.");

        for (Taxon taxon : taxonSet.getTaxonSet())
            taxonsetInput.get().add(taxon);

        super.initAndValidate();
    }
}
