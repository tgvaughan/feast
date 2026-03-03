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

import beast.base.core.Input;
import beast.base.evolution.alignment.Alignment;
import beast.base.evolution.alignment.Sequence;
import beast.base.evolution.alignment.TaxonSet;

import java.io.*;
import java.net.URL;

/**
 * Alignments initialized from files extend this class.
 */
public class AlignmentFromFile extends Alignment {

    public Input<String> fileNameInput = new Input<>("fileName",
            "Name of file containing sequence alignment.");

    public Input<String> urlInput = new Input<>("url",
            "URL from which to download sequence alignment.",
            Input.Validate.XOR, fileNameInput);

    public Input<String> outFileNameInput = new Input<>("xmlFileName",
            "Name of file to write XML fragment to.");

    public Input<String> endsWithInput = new Input<>("endsWith",
            "If provided, include only those sequences whose header " +
            "strings end with the provided substring.");

    public Input<TaxonSet> includeOnlyInput = new Input<>(
            "includeOnly",
            "Only include the taxa listed in this taxon set.");

    /**
     * Add sequence to the alignment, provided the predicates specified
     * in the inputs are satisfied.
     *
     * @param sequence sequence to add
     */
    protected void addSequence(Sequence sequence) {
        if ((endsWithInput.get() == null
                || sequence.getTaxon().endsWith(endsWithInput.get()))
        && ((includeOnlyInput.get() == null
                || includeOnlyInput.get().getTaxaNames().contains(sequence.getTaxon()))))
            sequenceInput.setValue(sequence, this);
    }

    /**
     * Retrieves a buffered reader for an alignment file or URL.
     * Used by the concrete child classes to access the data contained at the
     * given location.
     *
     * @return buffered reader
     */
    protected BufferedReader getReader() {
        if (fileNameInput.get() != null) {
            try {
                return new BufferedReader(new FileReader(fileNameInput.get()));
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Error reading from file '"
                        + fileNameInput.get() + "': "
                        + e.getMessage());
            }
        }
        else {
            try {
                return new BufferedReader(new InputStreamReader(new URL(urlInput.get()).openStream()));
            } catch (IOException e) {
                throw new RuntimeException("Error reading from URL '"
                + urlInput.get() + "': "
                + e.getMessage());
            }
        }
    }
}
