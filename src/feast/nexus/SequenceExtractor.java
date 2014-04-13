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

package feast.nexus;

import beast.core.BEASTObject;
import beast.core.Input;
import beast.core.Runnable;
import beast.evolution.alignment.Alignment;
import beast.util.XMLParser;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
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
            Alignment alignment = findAlignment(runnable);
            if (alignment != null)
                NexusWriter.write(alignment, null, new PrintStream(args[1]));
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find file '" + args[0] + "'.");
            System.exit(1);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(SequenceExtractor.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
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
    private static Alignment findAlignment(BEASTObject obj) throws IllegalArgumentException, IllegalAccessException {
        for (Input input : obj.listInputs()) {
            if (input.get() != null) {
                if (input.get() instanceof Alignment)
                    return (Alignment)input.get();
                if (input.get() instanceof List) {
                    for (Object child : (List)input.get()) {
                        if (child instanceof BEASTObject) {
                            Alignment alignment = findAlignment((BEASTObject)child);
                            if (alignment != null)
                                return alignment;
                        }
                    }
                }
                if (input.get() instanceof BEASTObject) {
                    Alignment alignment = findAlignment((BEASTObject)(input.get()));
                    if (alignment != null)
                        return alignment;
                }
            }
        }
        return null;
    }
    
}
