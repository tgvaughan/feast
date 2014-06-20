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

import beast.evolution.alignment.TaxonSet;
import beast.evolution.tree.Tree;
import java.util.ArrayList;
import java.util.List;

/**
 * Nexus block specifying a number of trees.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class TreesBlock extends NexusBlock {

    private final List<Tree> trees;
    private final List<String> names;

    /**
     * Construct an empty trees block.
     */
    public TreesBlock() {
        trees = new ArrayList<>();
        names = new ArrayList<>();
    }
    
    /**
     * Construct a trees block containing those trees in the provided list.
     * 
     * @param trees 
     */
    public TreesBlock(List<Tree> trees) {
        this.trees = trees;
        this.names = new ArrayList<>();
        for (int i=0; i<trees.size(); i++)
            this.names.add("TREE_" + i);
    }
    
    /**
     * Add tree to existing trees block.
     * 
     * @param tree
     * @return TreesBlock object for method chaining.
     */
    public TreesBlock addTree(Tree tree) {
        trees.add(tree);
        names.add("TREE_" + names.size());
        
        return this;
    }
    
    /**
     * Add tree (with name) to existing trees block
     * 
     * @param tree
     * @param name
     * @return TreesBlock object for method chaining.
     */
    public TreesBlock addTree(Tree tree, String name) {
        trees.add(tree);
        names.add(name);
        
        return this;
    }
    
    /**
     * Method used to generate string representation of Tree.  Useful
     * for overriding in cases where tree.toString() is not appropriate.
     * 
     * @param tree
     * @return string representation of tree (some variant of Newick).
     */
    public String getTreeString(Tree tree) {
        return tree.toString();
    }
    
    @Override
    public String getBlockName() {
        return "trees";
    }

    @Override
    public List<String> getBlockLines() {
        List<String> lines = new ArrayList<>();
        
        if (trees.isEmpty())
            return lines;
        
        StringBuilder translate = new StringBuilder("translate");
        TaxonSet taxonSet = trees.get(0).getTaxonset();
        List<String> taxonNames = taxonSet.asStringList();
        for (int i=0; i<taxonNames.size(); i++) {
            translate.append("\n\t\t").append(i).append(" ")
                    .append(taxonNames.get(i));
            
            if (i<taxonNames.size()-1)
                translate.append(",");
        }
        lines.add(translate.toString());
        
        for (int i=0; i<trees.size(); i++) {
            
            // Remove trailing ";" if present (as it should be!)
            String newick = getTreeString(trees.get(i));
            if (newick.endsWith(";"))
                newick = newick.substring(0, newick.length()-1);
            
            lines.add(String.format("tree %s = [&R] %s", names.get(i), newick));
        }
        
        return lines;
    }
    
}
