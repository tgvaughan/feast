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

import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.evolution.alignment.Alignment;
import beast.base.evolution.alignment.Sequence;
import beast.base.evolution.alignment.Taxon;
import beast.base.evolution.alignment.TaxonSet;
import beast.base.evolution.datatype.DataType;
import beast.base.evolution.sitemodel.SiteModel;
import beast.base.evolution.substitutionmodel.JukesCantor;
import beast.base.evolution.substitutionmodel.SubstitutionModel;
import beast.base.evolution.tree.Node;
import beast.base.evolution.tree.Tree;
import beast.base.util.Randomizer;
import beast.pkgmgmt.BEASTClassLoader;
import beast.pkgmgmt.PackageManager;
import feast.nexus.CharactersBlock;
import feast.nexus.NexusBuilder;
import feast.nexus.TaxaBlock;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tim Vaughan
 */
@Description("A sequence alignment which is a randomly shuffled version of" +
        " the input alignment.  Can be used for performing date randomization tests.")
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
