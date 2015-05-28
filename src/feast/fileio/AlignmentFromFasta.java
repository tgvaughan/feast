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
import beast.util.NexusParser;
import feast.input.In;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class AlignmentFromFasta extends Alignment {

    public Input<String> fileNameInput = new In<String>("fileName", "Name of file "
            + "containing sequence alignment in format.").setRequired();

    public Input<String> outFileNameInput = new In<>("xmlFileName",
            "Name of file to write XML fragment to.");

    public AlignmentFromFasta() { }

    @Override
    public void initAndValidate() throws Exception {


        List<Sequence> sequenceList = new ArrayList<>();

        BufferedReader reader = new BufferedReader(new FileReader(fileNameInput.get()));
        String line;
        StringBuilder seqBuilder = new StringBuilder();
        String header = null;
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

        super.initAndValidate();

        if (outFileNameInput.get() != null) {
            PrintStream pstream = new PrintStream(outFileNameInput.get());
            pstream.println("<alignment spec='beast.evolution.alignment.Alignment'>");
            for (Sequence seq : sequenceInput.get())
                pstream.format("\t<sequence taxon='%s' value='%s'/>\n",
                        seq.taxonInput.get(), seq.dataInput.get());
            pstream.println("</alignment>");
        }
    }
}
