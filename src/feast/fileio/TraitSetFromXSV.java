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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Description("Initialize a trait set from a CSV/TSV file.")
public class TraitSetFromXSV extends TraitSet {

    public Input<String> fileNameInput = new Input<>("fileName", "Name of CSV/TSV file to extract values from.",
            Input.Validate.REQUIRED);

    public Input<String> sepInput = new Input<>("sep",
            "Separator for CSV/TSV file.  Default is TAB.","\t");

    public Input<Integer> taxonNameColInput = new Input<>("taxonNameCol",
            "Index of column containing taxon names (Default 0).", 0);

    public Input<Integer> traitValueColInput = new Input<>("traitValueCol",
            "Index of column containing trait names (Default 1).", 1);


    public Input<Boolean> skipHeaderRowInput = new Input<>("skipFirstRow",
            "If true, skip first row. (Default false.)", false);

    public TraitSetFromXSV() {
        traitsInput.setRule(Input.Validate.OPTIONAL);
    }

    @Override
    public void initAndValidate() {

        StringBuilder traitSB = new StringBuilder();

        String sep = sepInput.get();
        int keyIdx = taxonNameColInput.get();
        int valIdx = traitValueColInput.get();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileNameInput.get()))) {
            if (skipHeaderRowInput.get())
                reader.readLine();

            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(sep);
                String taxonName = elements[keyIdx].trim();
                if (taxaInput.get().getTaxaNames().contains(taxonName)) {
                    if (traitSB.length()>0)
                        traitSB.append(",");
                    String traitValue = elements[valIdx].trim();
                    traitSB.append(taxonName).append("=").append(traitValue);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from file '"
                    + fileNameInput.get() + "': " + e.getMessage());
        }

        traitsInput.setValue(traitSB.toString(), this);

        super.initAndValidate();
    }
}
