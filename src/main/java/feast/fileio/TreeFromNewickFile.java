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
import beast.base.evolution.tree.TreeParser;

import java.io.*;

/**
 * @author Tim Vaughan
 */
@Description("Wrapper around TreeParser to enable reading newick " +
        "strings from files.")
public class TreeFromNewickFile extends TreeParser {

    public Input<String> fileNameInput = new Input<>("fileName", "Name of file "
            + "containing tree in Newick format.", Input.Validate.REQUIRED);

    public Input<Integer> treeIndexInput = new Input<>("treeIndex",
            "Index of tree in tree file (default 0).", 0);

    public TreeFromNewickFile() { }

    @Override
    public void initAndValidate() {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileNameInput.get()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Input file not found.");
        }
        String line;
        StringBuilder newickBuilder = new StringBuilder();

        try {
            while ((line = reader.readLine()) != null)
                newickBuilder.append(line.trim());
        } catch (IOException e) {
            throw new RuntimeException("Error reading from input file.");
        }

        String[] treeStrings = newickBuilder.toString().split(";");

        if (treeIndexInput.get() >= treeStrings.length)
            throw new IllegalArgumentException("Tree index exceeds number of trees in input file.");

        newickInput.setValue(newickBuilder.toString().split(";")[treeIndexInput.get()], this);

        super.initAndValidate();
    }
}
