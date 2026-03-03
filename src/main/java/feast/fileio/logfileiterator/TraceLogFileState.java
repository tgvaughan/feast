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

package feast.fileio.logfileiterator;

import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.inference.StateNode;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Description("A LogFileState representing the mapping from input trace log file " +
        "samples to BEAST 2 Parameters.")
public class TraceLogFileState extends LogFileState {

    public Input<List<LogFileRealParameter>> logFileEntryInput = new Input<>("logFileEntry",
            "Associates column of logfile with BEASTObject",
            new ArrayList<>());

    List<LogFileRealParameter> logFileEntries;
    Map<Integer, LogFileRealParameter> columnEntryMap;

    int colCount;

    @Override
    public void initAndValidate() {
        super.initAndValidate();

        logFileEntries = logFileEntryInput.get();

        // Find and parse header
        String[] logFileVariables = getNextLogLine();
        colCount = logFileVariables.length;

        columnEntryMap = new HashMap<>();
        for (int c=0; c<colCount; c++) {
            for (LogFileRealParameter entry : logFileEntries) {
                if (entry.getFieldName().equals(logFileVariables[c])) {
                    columnEntryMap.put(c, entry);
                    break;
                }
            }
        }
    }

    private String[] getNextLogLine() {
        String line = null;
        try {
            do {
                line = inFile.readLine();
            } while (line != null && (line.isEmpty() || line.startsWith("#")));
        } catch (IOException e) {
            throw new RuntimeException("Error reading log file.");
        }

        if (line == null)
            return null;
        else
            return line.split("\t");
    }

    public long updateToNextEntry() {

        String[] values = getNextLogLine();

        if (values == null) {
            currentSample = -1;
            return currentSample;
        }

        for (int c : columnEntryMap.keySet()) {
            columnEntryMap.get(c).setFieldParameterValue(values[c]);
        }

        currentSample = Long.parseLong(values[0]);
        return currentSample;
    }

    List<StateNode> stateNodes = null;

    @Override
    public List<StateNode> getStateNodes() {
        if (stateNodes == null)
            stateNodes = logFileEntries.stream().map(e -> e.fieldParameter).distinct().collect(Collectors.toList());

        return stateNodes;
    }
}
