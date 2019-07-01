/*
 * Copyright (C) 2014 Tim Vaughan <tgvaughan@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package feast.fileio;

import beast.core.Input;
import beast.evolution.alignment.Alignment;
import beast.evolution.alignment.Sequence;

import java.io.*;

/**
 * Read in an alignment from a fasta file.  Sequence labels are assumed
 * to be taxon labels.
 *
 * @author Tim Vaughan
 */
public class AlignmentFromFasta extends Alignment {

    public Input<String> fileNameInput = new Input<>("fileName", "Name of file "
            + "containing sequence alignment in fasta format.", Input.Validate.REQUIRED);

    public Input<String> outFileNameInput = new Input<>("xmlFileName",
            "Name of file to write XML fragment to.");

    public AlignmentFromFasta() { }

    @Override
    public void initAndValidate() {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileNameInput.get()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FASTA file '"
                    + fileNameInput.get() + "' not found.");
        }

        StringBuilder seqBuilder = new StringBuilder();
        String line;
        String header = null;

        try {
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith(">")) {
                    if (header != null) {
                        sequenceInput.setValue(new Sequence(header, seqBuilder.toString()), this);
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

        sequenceInput.setValue(new Sequence(header, seqBuilder.toString()), this);

        super.initAndValidate();

        if (outFileNameInput.get() != null) {
            PrintStream pstream = null;
            try {
                pstream = new PrintStream(outFileNameInput.get());
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Error writing to output file '"
                        + outFileNameInput.get() + "'.");
            }
            pstream.println("<alignment spec='beast.evolution.alignment.Alignment'>");
            for (Sequence seq : sequenceInput.get())
                pstream.format("\t<sequence taxon='%s' value='%s'/>\n",
                        seq.taxonInput.get(), seq.dataInput.get());
            pstream.println("</alignment>");
        }
    }
}
