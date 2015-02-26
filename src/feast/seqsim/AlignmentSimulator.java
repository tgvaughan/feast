/*
 * Copyright (C) 2015 Tim Vaughan (tgvaughan@gmail.com)
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
package feast.seqsim;

import beast.core.Input;
import beast.core.Runnable;
import beast.evolution.alignment.Alignment;
import beast.evolution.alignment.Sequence;
import beast.evolution.branchratemodel.BranchRateModel;
import beast.evolution.datatype.DataType;
import beast.evolution.datatype.Nucleotide;
import beast.evolution.sitemodel.SiteModel;
import beast.evolution.tree.Node;
import beast.evolution.tree.Tree;
import beast.util.Randomizer;
import beast.util.XMLProducer;
import feast.input.In;
import java.io.PrintStream;

/**
 * A repackaging of the beast 2 sequence simulator.
 *
 * @author Tim Vaughan (tgvaughan@gmail.com)
 */
public class AlignmentSimulator extends Runnable {

    public Input<Tree> treeInput = new In<Tree>(
        "tree",
        "Tree down which to simulate alignment.")
        .setRequired();

    public Input<SiteModel> siteModelInput = new In<SiteModel>(
        "siteModel",
        "Site model used to simulate alignment.")
        .setRequired();

    public Input<BranchRateModel> branchRateModelInput = new In<>(
        "branchRateModel", "Branch rate model used to simulate alignment.");

    public Input<Integer> sequenceLengthInput = new In<Integer>(
        "sequenceLength", "Length of sequences to simulate")
        .setRequired();

    public Input<DataType.Base> dataTypeInput = new In<DataType.Base>(
        "dataType", "Type of sequence data.").setDefault(new Nucleotide());

    public Input<String> fileNameInput = new In<>(
        "fileName",
        "Name of file alignment will be written to.");


    int nCategories, nStates;
    double[][] probs;

    @Override
    public void initAndValidate() throws Exception {
        nCategories = siteModelInput.get().getCategoryCount();
        nStates = dataTypeInput.get().getStateCount();
        probs = new double[nCategories][nStates*nStates];
    }

    @Override
    public void run() throws Exception {
        Alignment alignment = simulate();

        PrintStream ps;
        if (fileNameInput.get() != null)
            ps = new PrintStream(fileNameInput.get());
        else
            ps = System.out;

        ps.println(new XMLProducer().toRawXML(alignment));
    }

    
    /**
     * Convert integer representation of sequence into a Sequence
     *
     * @param seq  integer representation of the sequence
     * @param node used to determine taxon for sequence
     * @return Sequence
     * @throws Exception
     */
    Sequence intArray2Sequence(int[] seq, Node node) throws Exception {
        DataType dataType = dataTypeInput.get();
        String seqString = dataType.state2string(seq);
        String taxonName = treeInput.get().getTaxonId(node);
        return new Sequence(taxonName, seqString);
    }

    /**
     * Perform the actual sequence generation.
     *
     * @return alignment containing randomly generated sequences for the nodes in the
     *         leaves of the tree
     * @throws Exception
     */
    public Alignment simulate() throws Exception {
        Node root = treeInput.get().getRoot();


        double[] categoryProbs = siteModelInput.get().getCategoryProportions(root);
        int[] category = new int[sequenceLengthInput.get()];
        for (int i = 0; i < sequenceLengthInput.get(); i++) {
            category[i] = Randomizer.randomChoicePDF(categoryProbs);
        }

        double[] frequencies = siteModelInput.get().getSubstitutionModel().getFrequencies();
        int[] seq = new int[sequenceLengthInput.get()];
        for (int i = 0; i < sequenceLengthInput.get(); i++) {
            seq[i] = Randomizer.randomChoicePDF(frequencies);
        }


        Alignment alignment = new Alignment();
        alignment.userDataTypeInput.setValue(dataTypeInput.get(), alignment);
        alignment.setID("SequenceSimulator");

        traverse(root, seq, category, alignment);


        return alignment;
    } // simulate

    /**
     * Recursively walk through the tree top down, and add sequence to alignment whenever
     * a leave node is reached.
     *
     * @param node           reference to the current node, for which we visit all children
     * @param parentSequence randomly generated sequence of the parent node
     * @param category       array of categories for each of the sites
     * @param alignment
     * @throws Exception
     */
    void traverse(Node node, int[] parentSequence, int[] category, Alignment alignment) throws Exception {
        for (int iChild = 0; iChild < 2; iChild++) {
            Node child = (iChild == 0 ? node.getLeft() : node.getRight());
            for (int i = 0; i < nCategories; i++) {
                getTransitionProbabilities(treeInput.get(), child, i, probs[i]);
            }

            int[] seq = new int[sequenceLengthInput.get()];
            double[] cProb = new double[nStates];
            for (int i = 0; i < sequenceLengthInput.get(); i++) {
                System.arraycopy(probs[category[i]], parentSequence[i] * nStates, cProb, 0, nStates);
                seq[i] = Randomizer.randomChoicePDF(cProb);
            }

            if (child.isLeaf()) {
                alignment.sequenceInput.setValue(intArray2Sequence(seq, child), alignment);
            } else {
                traverse(child, seq, category, alignment);
            }
        }
    }

    /**
     * Get transition probability matrix for particular rate category.
     * 
     * @param tree
     * @param node
     * @param rateCategory
     * @param probs 
     */
    void getTransitionProbabilities(Tree tree, Node node, int rateCategory, double[] probs) {

        Node parent = node.getParent();
        double branchRate = (branchRateModelInput.get() == null
            ? 1.0
            : branchRateModelInput.get().getRateForBranch(node));
        branchRate *= siteModelInput.get().getRateForCategory(rateCategory, node);

         siteModelInput.get().getSubstitutionModel().getTransitionProbabilities(
             node, parent.getHeight(), node.getHeight(), branchRate, probs);

    }


    
}
