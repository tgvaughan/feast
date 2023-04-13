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

package feast.function;

import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.evolution.alignment.TaxonSet;
import beast.base.evolution.tree.TraitSet;

@Description("All trait sets have at least some (potentially useless) representation " +
        "as a vector of doubles.  This class just allows you to explicitly treat them " +
        "as Functions.")
public class TraitSetAsFunction extends LoggableFunction {

    public Input<TraitSet> traitSetInput = new Input<>("traitSet",
            "Trait set to represent as a function.",
            Input.Validate.REQUIRED);

    TaxonSet taxonSet;
    TraitSet traitSet;

    @Override
    public void initAndValidate() {
        traitSet = traitSetInput.get();
        taxonSet = traitSet.taxaInput.get();
    }

    @Override
    public int getDimension() {
        return taxonSet.getTaxonCount();
    }

    @Override
    public double getArrayValue(int dim) {
        return traitSet.getValue(taxonSet.asStringList().get(dim));
    }
}
