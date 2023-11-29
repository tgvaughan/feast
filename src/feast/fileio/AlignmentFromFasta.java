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
import beast.base.evolution.alignment.Sequence;

import java.io.*;

/**
 * @author Tim Vaughan
 */

@Description("Objects of this type are regular beast Alignments, but are " +
        "populated using data read in from an external fasta file.  Sequence " +
        "labels used as taxon labels.")
public class AlignmentFromFasta extends AlignmentFromFile {

    public AlignmentFromFasta() { }

    @Override
    public void initAndValidate() {

        // Guard against double-initialization
        if (!sequenceInput.get().isEmpty()) {
            super.initAndValidate();
            return;
        }

        BufferedReader reader = getReader();

        StringBuilder seqBuilder = new StringBuilder();
        String line;
        String header = null;

        try {
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith(">")) {
                    if (header != null) {
                        addSequence(new Sequence(header, seqBuilder.toString()));
                        seqBuilder = new StringBuilder();
                    }
                    header = line.substring(1).trim();
                    continue;
                }
                seqBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from FASTA file '"
                    + fileNameInput.get() + "'.");
        }

        addSequence(new Sequence(header, seqBuilder.toString()));

        super.initAndValidate();

        if (outFileNameInput.get() != null) {
            try (PrintStream pstream = new PrintStream(outFileNameInput.get())){
                pstream.println("<alignment spec='beast.evolution.alignment.Alignment'>");
                for (Sequence seq : sequenceInput.get())
                    pstream.format("\t<sequence taxon='%s' value='%s'/>\n",
                            seq.taxonInput.get(), seq.dataInput.get());
                pstream.println("</alignment>");
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Error writing to output file '"
                        + outFileNameInput.get() + "'.");
            }

        }
    }
}
