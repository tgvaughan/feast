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

import beast.base.evolution.alignment.Sequence;
import beast.base.parser.NexusParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Reads in an alignment from a NEXUS file.
 *
 * @author Tim Vaughan
 */
public class AlignmentFromNexus extends AlignmentFromFile {
    
    public AlignmentFromNexus() { }

    @Override
    public void initAndValidate() {

        // Guard against double-initialization
        if (!sequenceInput.get().isEmpty()) {
            super.initAndValidate();
            return;
        }

        NexusParser parser = new NexusParser();
        try {
            parser.parseFile(fileNameInput.get(), getReader());
        } catch (IOException e) {
            throw new RuntimeException("Error reading NEXUS file '" + fileNameInput.get() + "'.");
        }
        if (parser.m_alignment == null)
            throw new IllegalArgumentException("Failed to read alignment from"
                    + " file '" + fileNameInput.get() + "'");
        
        for (Sequence seq : parser.m_alignment.sequenceInput.get())
            addSequence(seq);
        
        super.initAndValidate();
        
        if (outFileNameInput.get() != null) {
            PrintStream pstream = null;
            try {
                pstream = new PrintStream(outFileNameInput.get());
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Error writing to NEXUS file '" + outFileNameInput.get() + "'.");
            }
            pstream.println("<alignment spec='beast.evolution.alignment.Alignment'>");
            for (Sequence seq : sequenceInput.get())
                pstream.format("\t<sequence taxon='%s' value='%s'/>\n",
                        seq.taxonInput.get(), seq.dataInput.get());
            pstream.println("</alignment>");
        }
    }
}
