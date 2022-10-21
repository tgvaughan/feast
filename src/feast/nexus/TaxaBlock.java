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

import beast.base.evolution.alignment.TaxonSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Nexus Taxa block.
 *
 * @author Tim Vaughan
 */
public class TaxaBlock extends NexusBlock {
    
    private final TaxonSet taxonSet;

    public TaxaBlock(TaxonSet taxonSet) {
        this.taxonSet = taxonSet;
    }

    @Override
    public String getBlockName() {
        return "taxa";
    }

    @Override
    public List<String> getBlockLines() {
        List<String> lines = new ArrayList<>();

        int ntax=taxonSet.getTaxonCount();

        lines.add("dimensions ntax=" + ntax);

        StringBuilder taxLabels = new StringBuilder("taxlabels");
        for (int i=0; i<ntax; i++)
            taxLabels.append(" ").append(taxonSet.getTaxonId(i));

        lines.add(taxLabels.toString());
        
        return lines;
    }
    
}
