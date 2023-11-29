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

package feast.fileio.logfileiterator;

import beast.base.core.BEASTObject;
import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.inference.StateNode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Set;

@Description("A container for one or more state variables to be populated on " +
        "the basis of an input log file.")
public abstract class LogFileState extends BEASTObject {
    public Input<String> logFileNameInput = new Input<>("logFileName", "Name of log file",
            Input.Validate.REQUIRED);

    protected String logFileName;
    protected BufferedReader inFile;

    protected int currentSample;

    @Override
    public void initAndValidate() {
        logFileName = logFileNameInput.get();

        try {
            inFile = new BufferedReader(new FileReader(logFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        currentSample = -1;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public int getCurrentSample() {
        return currentSample;
    }

    public abstract int updateToNextEntry();

    public abstract List<StateNode> getStateNodes();

}
