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

import beast.core.BEASTObject;
import beast.core.Input;
import beast.core.Runnable;
import beast.evolution.alignment.Alignment;
import beast.evolution.alignment.FilteredAlignment;
import beast.util.XMLParser;
import feast.nexus.NexusWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Simple tool for extracting a sequence alignment from a BEAST 2 input file
 * and writing it out as a Nexus file.  The input file must be a valid
 * BEAST 2 input file, not simply a fragment.  This unfortunately means that
 * we can't handle the XML output of SequenceSimulator.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class SequenceExtractor {
    
    public static void main(String[] args) {

        if (args.length<2) {
            System.out.println("Usage: SequenceExtractor "
                    + "beast_input_file.xml output_file.nexus");
            System.exit(0);
        }
            
        try {
            XMLParser parser = new XMLParser();
            Runnable runnable = parser.parseFile(new File(args[0]));
            Set<Alignment> alignments = findAlignments(runnable);
            if (alignments.isEmpty()) {
                System.out.println("No alignments found!");
                System.exit(1);
            }
            if (alignments.size()>1) {
                String prefix;
                int extIndex = args[0].lastIndexOf('.');
                if (extIndex<0) {
                    // No extension
                    prefix = args[0];
                } else {
                    prefix = args[0].substring(0, extIndex);
                }

                for (Alignment alignment : alignments) {
                    if (alignment instanceof FilteredAlignment)
                        continue;

                    NexusWriter.write(alignment, null,
                            new PrintStream(prefix + "_" + alignment.getID() + ".nexus"));
                }

            } else {
                NexusWriter.write((Alignment)alignments.toArray()[0], null, new PrintStream(args[1]));
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find file '" + args[0] + "'.");
            System.exit(1);
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            Logger.getLogger(SequenceExtractor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(SequenceExtractor.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
    
    /**
     * Return first alignment object found underneath BEASTObject obj.
     * 
     * @param obj
     * @return Alignment object if found, null otherwise.
     * @throws IllegalArgumentException
     * @throws IllegalAccessException 
     */
    private static Set<Alignment> findAlignments(BEASTObject obj) throws IllegalArgumentException, IllegalAccessException {
       Set<Alignment> alignments = new HashSet<>();

        for (Input input : obj.listInputs()) {
            if (input.get() != null) {
                if (input.get() instanceof Alignment)
                    alignments.add((Alignment)input.get());
                if (input.get() instanceof List) {
                    for (Object child : (List)input.get()) {
                        if (child instanceof BEASTObject) {
                            alignments.addAll(findAlignments((BEASTObject)child));
                        }
                    }
                }
                if (input.get() instanceof BEASTObject) {
                    alignments.addAll(findAlignments((BEASTObject)(input.get())));
                }
            }
        }
        return alignments;
    }
    
}
