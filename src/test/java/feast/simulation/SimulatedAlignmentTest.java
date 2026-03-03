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

package feast.simulation;

import beast.base.evolution.alignment.Sequence;
import beast.base.evolution.sitemodel.SiteModel;
import beast.base.evolution.substitutionmodel.JukesCantor;
import beast.base.evolution.tree.Node;
import beast.base.evolution.tree.TreeParser;
import beast.base.util.Randomizer;
import org.junit.Assert;
import org.junit.Test;

public class SimulatedAlignmentTest {

    @Test
    public void testSequenceSimulation() {
        Randomizer.setSeed(53);

        TreeParser tree = new TreeParser("((t4:2.369581270289798,(t2:0.7362719592981231,t8:0.7362719592981231):1.6333093109916748):1.4995934976492373,((((t9:0.8936315906157204,(t6:0.8779660314367823,t0:0.8779660314367823):0.015665559178938082):1.2275368715430122,t1:2.1211684621587326):0.359584409136005,t7:2.4807528712947375):1.0357416037243077,(t10:2.225485714051735,(t3:2.1642811859654225,(t11:0.31165890742494096,t5:0.31165890742494096):1.8526222785404816):0.06120452808631249):1.2910087609673102):0.3526802929199899):0.13082523206096486;");

        SiteModel siteModel = new SiteModel();
        siteModel.initByName("substModel", new JukesCantor(),
                "mutationRate", "0.001");

        SimulatedAlignment simulatedAlignment = new SimulatedAlignment();
        simulatedAlignment.initByName("tree" ,tree,
                "siteModel", siteModel,
                "strip", true,
                "sequenceLength", 100000);

        int snpCount = 0;
        for (int i=0; i<simulatedAlignment.getPatternCount(); i++) {
            int startVal = simulatedAlignment.getPattern(0, i);
            for (int t = 1; t < simulatedAlignment.getTaxonCount(); t++) {
                if (simulatedAlignment.getPattern(t, i) != startVal) {
                    snpCount += simulatedAlignment.getPatternWeight(i);
                    break;
                }
            }
        }

        // This should hold for low substitution rates
        Assert.assertEquals(1e-3, snpCount/100000.0/getTreeLength(tree.getRoot()), 1e-4);
    }

    private double getTreeLength(Node root) {
        if (root.isLeaf())
            return 0.0;

        double subtreeLength = 0.0;
        for (Node child : root.getChildren())
            subtreeLength += child.getLength() + getTreeLength(child);

        return subtreeLength;
    }


    @Test
    public void testStartingSequence() {
        TreeParser tree = new TreeParser("((t4:2.369581270289798,(t2:0.7362719592981231,t8:0.7362719592981231):1.6333093109916748):1.4995934976492373,((((t9:0.8936315906157204,(t6:0.8779660314367823,t0:0.8779660314367823):0.015665559178938082):1.2275368715430122,t1:2.1211684621587326):0.359584409136005,t7:2.4807528712947375):1.0357416037243077,(t10:2.225485714051735,(t3:2.1642811859654225,(t11:0.31165890742494096,t5:0.31165890742494096):1.8526222785404816):0.06120452808631249):1.2910087609673102):0.3526802929199899):0.13082523206096486;");


        SiteModel siteModel = new SiteModel();
        siteModel.initByName("substModel", new JukesCantor(),
                "mutationRate", "0.0");

        String startingSequence = "GGGGGCCCCC";

        SimulatedAlignment simulatedAlignment = new SimulatedAlignment();
        simulatedAlignment.initByName("tree" ,tree,
                "siteModel", siteModel,
                "sequenceLength", 10,
                "startingSequence", new Sequence("parent", startingSequence));

        for (Sequence leafSequence : simulatedAlignment.sequenceInput.get())
            Assert.assertEquals(startingSequence, leafSequence.dataInput.get());
    }

    @Test
    public void testStemStartingSequence() {
        TreeParser tree = new TreeParser("((t4:2.369581270289798,(t2:0.7362719592981231,t8:0.7362719592981231):1.6333093109916748):1.4995934976492373,((((t9:0.8936315906157204,(t6:0.8779660314367823,t0:0.8779660314367823):0.015665559178938082):1.2275368715430122,t1:2.1211684621587326):0.359584409136005,t7:2.4807528712947375):1.0357416037243077,(t10:2.225485714051735,(t3:2.1642811859654225,(t11:0.31165890742494096,t5:0.31165890742494096):1.8526222785404816):0.06120452808631249):1.2910087609673102):0.3526802929199899):0.13082523206096486;");

        SiteModel siteModel = new SiteModel();
        siteModel.initByName("substModel", new JukesCantor(),
                "mutationRate", "0.0");

        String startingSequence = "GGGGGCCCCC";

        SimulatedAlignment simulatedAlignment = new SimulatedAlignment();
        simulatedAlignment.initByName("tree" ,tree,
                "siteModel", siteModel,
                "sequenceLength", 10,
                "startingSequence", new Sequence("parent", startingSequence),
                "startingSequenceAge", "4.0");

        for (Sequence leafSequence : simulatedAlignment.sequenceInput.get())
            Assert.assertEquals(startingSequence, leafSequence.dataInput.get());
    }
}
