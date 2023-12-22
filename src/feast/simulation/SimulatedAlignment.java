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

package feast.simulation;

import beast.base.core.Description;
import beast.base.core.Function;
import beast.base.core.Input;
import beast.base.evolution.alignment.Alignment;
import beast.base.evolution.alignment.Sequence;
import beast.base.evolution.alignment.TaxonSet;
import beast.base.evolution.datatype.DataType;
import beast.base.evolution.sitemodel.SiteModel;
import beast.base.evolution.substitutionmodel.JukesCantor;
import beast.base.evolution.substitutionmodel.SubstitutionModel;
import beast.base.evolution.tree.Node;
import beast.base.evolution.tree.Tree;
import beast.base.util.Randomizer;
import beast.pkgmgmt.BEASTClassLoader;
import beast.pkgmgmt.PackageManager;
import feast.nexus.CharactersBlock;
import feast.nexus.NexusBuilder;
import feast.nexus.TaxaBlock;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tim Vaughan
 */
@Description("A more flexible alignment simulator. Doesn't require " +
        "pre-specification of number of taxa.")
public class SimulatedAlignment extends Alignment {

    public Input<Tree> treeInput = new Input<>(
            "tree",
            "Tree down which to simulate sequence evolution.",
            Input.Validate.REQUIRED);

    public Input<SiteModel> siteModelInput = new Input<>(
            "siteModel",
            "Site model to use in simulation.",
            Input.Validate.REQUIRED);

    public Input<Integer> sequenceLengthInput = new Input<>(
            "sequenceLength",
            "Length of sequence to simulate.",
            Input.Validate.REQUIRED);

    public Input<String> outputFileNameInput = new Input<>(
            "outputFileName",
            "Name of file (if any) simulated alignment should be saved to.");

    public Input<Sequence> startingSequenceInput = new Input<>(
            "startingSequence",
            "Initial sequence to start from.  (Default is random draw from equilibrium distribution.)");

    public Input<Function> startingSequenceAgeInput = new Input<>(
            "startingSequenceAge",
            "When startingSequence is specified, age (relative to the " +
                    "final sample) at which the sequence simulation starts " +
                    "from. (Default is the age of the root of the tree, but " +
                    "use this to make the starting sequence correspond to" +
                    "an earlier time.) Must be greater than the tMRCA of the tree.");

    private Tree tree;
    private SiteModel siteModel;
    private int seqLength;
    private DataType dataType;

    private Sequence startingSequence;

    public SimulatedAlignment() {
        sequenceInput.setRule(Input.Validate.OPTIONAL);
    }

    @Override
    public void initAndValidate() {

        tree = treeInput.get();
        siteModel = siteModelInput.get();
        seqLength = sequenceLengthInput.get();

        startingSequence = startingSequenceInput.get();

        sequences.clear();

        grabDataType();

        if (startingSequence != null && startingSequence.getSequence(dataType).size() != seqLength)
            throw new RuntimeException("Starting sequence length does not match " +
                    "desired length of simulated sequence alignment.");

        simulate();

        super.initAndValidate();

        // Write simulated alignment to disk if required
        if (outputFileNameInput.get() != null) {
            try (PrintStream pstream = new PrintStream(outputFileNameInput.get())) {
                NexusBuilder nb = new NexusBuilder();
                nb.append(new TaxaBlock(new TaxonSet(this)));
                nb.append(new CharactersBlock(this));
                nb.write(pstream);
            } catch (FileNotFoundException ex) {
                throw new RuntimeException("Error writing to file "
                        + outputFileNameInput.get() + ".");
            }
        }
    }

    /**
     * Perform actual sequence simulation.
     */
    private void simulate() {
        int nTaxa = tree.getLeafNodeCount();

        double[] categoryProbs = siteModel.getCategoryProportions(tree.getRoot());

        int nCategories = siteModel.getCategoryCount();
        SubstitutionModel substModel = siteModel.getSubstitutionModel();
        int nStates = substModel instanceof JukesCantor
                ? 4
                : substModel.getStateCount();
        double[][] transitionProbs = new double[nCategories][nStates * nStates];

        int[][] alignment = new int[nTaxa][seqLength];

        int[] categories = new int[seqLength];
        for (int i = 0; i < seqLength; i++)
            categories[i] = Randomizer.randomChoicePDF(categoryProbs);

        Node root = tree.getRoot();

        int[] parentSequence = new int[seqLength];
        if (startingSequence != null) {

            for (int i=0; i<parentSequence.length; i++)
                parentSequence[i] = startingSequence.getSequence(dataType).get(i);

            if (startingSequenceAgeInput.get() != null) {
                if (startingSequenceAgeInput.get().getArrayValue()<root.getHeight())
                    throw new IllegalArgumentException("Starting sequence age " +
                            "must be older than corresponding tree root.");

                parentSequence = getEvolvedSequence(root, parentSequence,
                        transitionProbs, categories,
                        startingSequenceAgeInput.get().getArrayValue(), root.getHeight());
            }
        }
        else {
            double[] frequencies = siteModel.getSubstitutionModel().getFrequencies();
            for (int i = 0; i < parentSequence.length; i++)
                parentSequence[i] = Randomizer.randomChoicePDF(frequencies);
        }

        traverse(root, parentSequence,
                categories, transitionProbs,
                alignment);

        for (int leafIdx=0; leafIdx<nTaxa; leafIdx++) {
            String seqString = dataType.encodingToString(alignment[leafIdx]);

            String taxonName;
            if (tree.getNode(leafIdx).getID() != null)
                taxonName = tree.getNode(leafIdx).getID();
            else
                taxonName = "t" + leafIdx;

            sequenceInput.setValue(new Sequence(taxonName, seqString), this);
        }
    }

    /**
     * Simulate the evolution of a sequence under a substitution model over
     * a specified time.
     *
     * @param node node below lineage on which evolution is to be simulated.
     * @param startSequence starting sequence at the earlier time.
     * @param transitionProbs working space for computing transition probabilities.
     * @param categories working space for computing categories.
     * @param startTime earlier time
     * @param endTime later time
     * @return reference to sequence (newly allocated) array representing evolved sequence.
     */
    private int[] getEvolvedSequence(Node node, int[] startSequence,
                                double[][] transitionProbs,
                                int[] categories,
                                double startTime, double endTime) {
        // Calculate transition probabilities
        for (int i=0; i<siteModel.getCategoryCount(); i++) {
            siteModel.getSubstitutionModel().getTransitionProbabilities(
                    node, startTime, endTime,
                    siteModel.getRateForCategory(i, node),
                    transitionProbs[i]);
        }

        // Draw characters on child sequence
        int[] endSequence = new int[startSequence.length];
        int nStates = dataType.getStateCount();
        double[] charProb = new double[nStates];
        for (int i=0; i<endSequence.length; i++) {
            int category = categories[i];
            System.arraycopy(transitionProbs[category],
                    startSequence[i]*nStates, charProb, 0, nStates);
            endSequence[i] = Randomizer.randomChoicePDF(charProb);
        }

        return endSequence;
    }

    /**
     * Traverse a tree, simulating a sequence alignment down it.
     *
     * @param node Node of the tree
     * @param parentSequence Sequence at the parent node in the tree
     * @param categories Mapping from sites to categories
     * @param transitionProbs transition probabilities
     * @param regionAlignment alignment for particular region
     */
    private void traverse(Node node,
            int[] parentSequence,
            int[] categories, double[][] transitionProbs,
            int[][] regionAlignment) {

        for (Node child : node.getChildren()) {

            int[] childSequence = getEvolvedSequence(child, parentSequence,
                    transitionProbs, categories,
                    node.getHeight(), child.getHeight());

            if (child.isLeaf()) {
                System.arraycopy(childSequence, 0,
                        regionAlignment[child.getNr()], 0, childSequence.length);
            } else {
                traverse(child, childSequence,
                        categories, transitionProbs,
                        regionAlignment);
            }
        }
    }

    /**
     * HORRIBLE function to identify data type from given description.
     */
    private void grabDataType() {
        if (userDataTypeInput.get() != null) {
            dataType = userDataTypeInput.get();
        } else {

            List<String> dataTypeDescList = new ArrayList<>();
            List<String> classNames = PackageManager.find(DataType.class, "beast.base.evolution.datatype");
            for (String className : classNames) {
                try {
                    DataType thisDataType = (DataType) BEASTClassLoader.forName(className).newInstance();
                    if (dataTypeInput.get().equals(thisDataType.getTypeDescription())) {
                        dataType = thisDataType;
                        break;
                    }
                    dataTypeDescList.add(thisDataType.getTypeDescription());
                } catch (ClassNotFoundException
                    | InstantiationException
                    | IllegalAccessException e) {
                }
            }
            if (dataType == null) {
                throw new IllegalArgumentException("Data type '"
                        + dataTypeInput.get()
                        + "' cannot be found.  Choose one of "
                        + Arrays.toString(dataTypeDescList.toArray(new String[0])));
            }
        }
    }

}
