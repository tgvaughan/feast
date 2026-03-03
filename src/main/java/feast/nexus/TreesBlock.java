/*
 * Copyright (c) 2023 ETH Zurich
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

import beast.base.evolution.alignment.TaxonSet;
import beast.base.evolution.tree.Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * Nexus block specifying a number of trees.
 *
 * @author Tim Vaughan
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
     * @param trees list of trees
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
     * @param tree tree to add
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
     * @param tree tree to add
     * @param name name of tree
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
     * @param tree tree to represent
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
        int translationOffset = trees.get(0).taxaTranslationOffset;
        for (int i=0; i<taxonSet.getTaxonCount(); i++) {
            translate.append("\n\t\t").append(i + translationOffset).append(" ")
                    .append(taxonSet.getTaxonId(i));
            
            if (i<taxonSet.getTaxonCount()-1)
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
