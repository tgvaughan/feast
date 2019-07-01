/*
 * Copyright (C) 2014 Tim Vaughan <tgvaughan@gmail.com>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

package feast.nexus;

import beast.evolution.alignment.Alignment;
import beast.evolution.alignment.TaxonSet;
import beast.evolution.tree.Tree;
import java.io.PrintStream;
import java.util.List;

/**
 * Class for producing NEXUS files.
 * 
 * @author Tim Vaughan
 */
public class NexusWriter {
    
    /**
     * Write an alignment and/or one or more trees to the provided print stream
     * in Nexus format.
     * 
     * @param alignment Alignment to write (may be null)
     * @param trees Zero or more trees with taxa corresponding to alignment. (May be null)
     * @param pstream Print stream where output is sent
     */
    public static void write(Alignment alignment, List<Tree> trees,
            PrintStream pstream) {
        
        TaxonSet taxonSet = null;
        if (alignment != null) {
            taxonSet = new TaxonSet(alignment);
        } else {
            if (trees != null && !trees.isEmpty())
                taxonSet = trees.get(0).getTaxonset();
        }

        NexusBuilder nb = new NexusBuilder();
        
        if (taxonSet != null) {
            nb.append(new TaxaBlock(taxonSet));
        }
        
        if (alignment != null)
            nb.append(new CharactersBlock(alignment));
        
        if (trees != null && !trees.isEmpty())
            nb.append(new TreesBlock(trees));
        
        nb.write(pstream);
    }
    
}
