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

package feast.nexus;

import beast.base.evolution.alignment.Alignment;
import beast.base.evolution.alignment.TaxonSet;
import beast.base.evolution.tree.Tree;

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
