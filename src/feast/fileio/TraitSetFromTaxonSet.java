/*
 * Copyright (c) 2023 Tim Vaughan
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
import beast.base.evolution.tree.TraitSet;

import java.util.regex.Pattern;

@Description("Initialize a trait set from a taxon set. " +
        "Permits similar functionality to the 'autoconfigure' option in BEAUti.")
public class TraitSetFromTaxonSet extends TraitSet {

    public Input<String> delimiterInput = new Input<>("delimiter",
            "Delimiter used to split taxon names.",
            Input.Validate.REQUIRED);

    public Input<Boolean> beforeInput = new Input<>("everythingBeforeFirst",
            "Use everything before this delimiter.", false);

    public Input<Boolean> afterInput = new Input<>("everythingAfterLast",
            "Use everything before this delimiter.", false);

    public Input<Integer> groupInput = new Input<>("takeGroup",
            "Use everthing in this group.", -1);

    String delimiter;
    boolean before, after;
    int group;

    public TraitSetFromTaxonSet() {
        traitsInput.setRule(Input.Validate.OPTIONAL);
    }

    @Override
    public void initAndValidate() {
        delimiter = delimiterInput.get();
        before = beforeInput.get();
        after = afterInput.get();
        group = groupInput.get();

        if (!before  && !after && group < 0) {
            throw new IllegalArgumentException("At least one of everythingBefore, everythingAfter or takeGroup " +
                    "must be specified.");
        }

        StringBuilder traitSB = new StringBuilder();

        for (String taxon : taxaInput.get().asStringList()) {
            if (traitSB.length()>0)
                traitSB.append(",");
            traitSB.append(taxon).append("=");

            if (before) {
                traitSB.append(taxon, 0, taxon.indexOf(delimiter));
            } else if (after) {
                traitSB.append(taxon, taxon.lastIndexOf(delimiter)+1, taxon.length());
            } else {
                traitSB.append(taxon.split(Pattern.quote(delimiter))[group]);
            }
        }

        traitsInput.setValue(traitSB.toString(), this);

        super.initAndValidate();
    }
}
