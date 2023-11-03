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

package feast.simulation;

import beast.core.Description;
import beast.core.Input;
import beast.evolution.alignment.Alignment;
import beast.evolution.alignment.Sequence;
import beast.evolution.alignment.Taxon;
import beast.evolution.alignment.TaxonSet;
import beast.evolution.datatype.DataType;
import beast.evolution.sitemodel.SiteModel;
import beast.evolution.substitutionmodel.JukesCantor;
import beast.evolution.substitutionmodel.SubstitutionModel;
import beast.evolution.tree.Node;
import beast.evolution.tree.Tree;
import beast.util.Randomizer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tim Vaughan
 */
@Description("A sequence alignment which is a randomly shuffled version of" +
        " the input alignment.  Use for ")
public class ShuffledAlignment extends Alignment {

    public Input<Alignment> alignmentInput = new Input<>("alignment",
            "Alignment to be shuffled.",
            Input.Validate.REQUIRED);

    @Override
    public void initAndValidate() {
        List<Sequence> inputSequences = alignmentInput.get().sequenceInput.get();
        List<String> taxaNames = alignmentInput.get().getTaxaNames();
        int[] shuffledIndices = Randomizer.shuffled(taxaNames.size());

        for (int i=0; i<inputSequences.size(); i++) {
            Sequence shuffledSequence = new Sequence(taxaNames.get(i),
                    inputSequences.get(shuffledIndices[i]).getData());

            sequenceInput.setValue(shuffledSequence, this);
        }

        m_dataType = alignmentInput.get().getDataType();
        super.initAndValidate();
    }
}
