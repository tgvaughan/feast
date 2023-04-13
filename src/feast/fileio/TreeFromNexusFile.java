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
import beast.base.evolution.tree.TreeParser;
import feast.nexus.BasicNexusParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Tim Vaughan
 */
@Description("Wrapper around TreeParser to enable reading trees from Nexus files.")
public class TreeFromNexusFile extends TreeParser {

    public Input<String> fileNameInput = new Input<>("fileName", "Name of Nexus file "
            + "containing a tree block Nexus format.", Input.Validate.REQUIRED);

    public Input<Integer> treeIndexInput = new Input<>("treeIndex",
            "Index of tree in tree file (default 0).", 0);

    Map<String,String> translateMap;

    public TreeFromNexusFile() { }

    @Override
    public void initAndValidate() {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileNameInput.get()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Input file not found.");
        }

        try {
            BasicNexusParser nexusParser = new BasicNexusParser(reader);

            BasicNexusParser.NexusBlock treesBlock = nexusParser.getNextBlockMatching("trees");

            BasicNexusParser.NexusCommand treeCommand = null;
            BasicNexusParser.NexusCommand translateCommand = null;

            int thisTreeIdx = 0;
            for (BasicNexusParser.NexusCommand cmd : treesBlock.commands) {
                switch(cmd.name) {
                    case "tree":
                        if (thisTreeIdx == treeIndexInput.get())
                            treeCommand = cmd;
                        thisTreeIdx += 1;
                        break;
                    case "translate":
                        translateCommand = cmd;
                        break;
                }
            }

            if (treeCommand != null) {
                int idx = treeCommand.args.indexOf("=");
                String newickString = treeCommand.args.substring(idx+1).trim();
                newickInput.setValue(newickString, this);
            } else {
                throw new RuntimeException("No trees (or no tree with chosen index) found in nexus file.");
            }

            if (translateCommand != null) {

                translateMap = new HashMap<>();

                for (String pairStr : translateCommand.args.split(",")) {
                    String[] pair = pairStr.trim().split("\\s+");
                    if (pair.length != 2)
                        throw new RuntimeException("Error parsing translate command.");

                    translateMap.put(pair[0], pair[1]);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading from input file.");
        }

        super.initAndValidate();

        if (translateMap != null)
            translateLeafIds(translateMap);
    }
}
