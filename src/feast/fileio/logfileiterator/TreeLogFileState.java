package feast.fileio.logfileiterator;

import beast.core.Input;
import beast.core.StateNode;
import beast.evolution.alignment.Taxon;
import beast.evolution.alignment.TaxonSet;
import beast.evolution.tree.Node;
import beast.evolution.tree.RandomTree;
import beast.evolution.tree.Tree;
import beast.util.TreeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TreeLogFileState extends LogFileState {

    public Input<Tree> treeInput = new Input<>("tree",
            "Tree to read log file state into.", Input.Validate.REQUIRED);

    public Input<Boolean> readTaxonSetInput = new Input<>("readTaxonSet",
            "Read taxon set from tree file and assign to each tree.",
            true);

    Tree tree;
    TaxonSet taxonSet;
    boolean readTaxonSet;

    @Override
    public void initAndValidate() {
        super.initAndValidate();

        tree = treeInput.get();
        readTaxonSet = readTaxonSetInput.get();

        try {
            if (!inFile.readLine().trim().equalsIgnoreCase("#nexus")) {
                throw new RuntimeException("Tree log file " + logFileName + " is not a valid NEXUS file.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file " + logFileName + ". Aborting.");
        }

        // Skip until trees block start:
        String line;
        do {
            line = readNexusLine();
        } while (!line.isEmpty() && !line.equalsIgnoreCase("begin trees"));

        if (line.isEmpty())
            throw new RuntimeException("No trees block found in " + logFileName + ". Aborting.");

        // Assume translate is first (sloppy, I know, but we're processing BEAST output here).
        line = readNexusLine();
        if (!line.toLowerCase().startsWith("translate")) {
            throw new RuntimeException("No translate command found in " + logFileName + ". Aborting.");
        }

        // Convert translate command into TaxonSet
        List<Taxon> taxonList = new ArrayList<>();
        for (String pairStr : line.substring("translate ".length()).split(",")) {
            String[] pair = pairStr.trim().split("\\s+");
            taxonList.add(new Taxon(pair[1]));
        }

        taxonSet = new TaxonSet(taxonList);

        Tree initialTree = new Tree();
        initialTree.initByName("taxonset", taxonSet);
        tree.assignFromWithoutID(initialTree);
    }

    final static Pattern logLinePattern = Pattern.compile(
            "^tree\\s+STATE_(\\d+)\\s*=\\s*(\\[&[^]]*])?\\s*(.*)$");

    @Override
    public int updateToNextEntry() {

        String line = readNexusLine();

        if (line.isEmpty() || !line.toLowerCase().startsWith("tree state_")) {
            currentSample = -1;
            return currentSample;
        }

        Matcher m = logLinePattern.matcher(line);
        if (!m.find())
            throw new IllegalStateException("Error parsing tree log file line:\n" + line);

        currentSample = Integer.parseInt(m.group(1));
        String newickString = m.group(3);

        TreeParser treeParser = new TreeParser();
        if (readTaxonSet) {
            treeParser.initByName("adjustTipHeights", false,
                    "IsLabelledNewick", false,
                    "newick", newickString,
                    "taxonset", taxonSet);
        } else {
            treeParser.initByName("adjustTipHeights", false,
                    "IsLabelledNewick", true,
                    "newick", newickString,
                    "taxonset", null);

            while (treeParser.getNodeCount() > tree.getNodeCount())
                tree.addNode(new Node());

            while (treeParser.getNodeCount() < tree.getNodeCount())
                tree.removeNode(tree.getNodeCount()-1);
        }

        // Apply taxon labels to nodes.
        // Not sure why this doesn't happen in TreeParser.
        if (readTaxonSet && taxonSet != null)
            for (Node node : treeParser.getExternalNodes())
                node.setID(taxonSet.getTaxonId(node.getNr()));

        tree.assignFromWithoutID(treeParser);

        return currentSample;
    }

    private String readNexusLine() {

        StringBuilder line = new StringBuilder();

        try {
            int nextByte;
            char nextChar;
            while (true) {
                nextByte = inFile.read();
                if (nextByte < 0)
                    break;

                nextChar = (char) nextByte;
                if (nextChar == ';')
                    break;

                line.append(nextChar);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading from tree log file " + logFileName + ". Aborting.");
        }

        return line.toString().trim();
    }

    private boolean readTaxaBlock() {

        return true;
    }

    List<StateNode> stateNodes = new ArrayList<>();

    @Override
    public List<StateNode> getStateNodes() {
        if (stateNodes.isEmpty())
            stateNodes.add(tree);

        return stateNodes;
    }
}
