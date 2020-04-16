package feast.fileio.logfileiterator;

import beast.core.Input;
import beast.evolution.alignment.Taxon;
import beast.evolution.alignment.TaxonSet;
import beast.evolution.tree.Tree;
import beast.util.TreeParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TreeLogFileState extends LogFileState {

    public Input<Tree> treeInput = new Input<>("tree",
            "Tree to read log file state into.", Input.Validate.REQUIRED);

    Tree tree;
    TaxonSet taxonSet;

    @Override
    public void initAndValidate() {
        super.initAndValidate();

        tree = treeInput.get();

        try {
            if (!inFile.readLine().trim().toLowerCase().equals("#nexus")) {
                throw new RuntimeException("Tree log file " + logFileName + " is not a valid NEXUS file.");
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file " + logFileName + ". Aborting.");
        }

        // Skip until trees block start:
        String line;
        do {
            line = readNexusLine();
        } while (!line.isEmpty() && !line.toLowerCase().equals("begin trees"));

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
            String[] pair = pairStr.split("\\s+");
            taxonList.add(new Taxon(pair[0]));
        }

        taxonSet = new TaxonSet(taxonList);
    }

    @Override
    public int updateToNextEntry() {

        String line = readNexusLine();

        if (line.isEmpty() || !line.toLowerCase().startsWith("tree state_")) {
            currentSample = -1;
            return currentSample;
        }

        line = line.substring("tree state_".length()).trim();
        currentSample = Integer.parseInt(line.split(" ")[0]);

        int idx = line.indexOf("=");
        String newickString = line.substring(idx+1);

        Tree thisTree = new TreeParser();
        thisTree.initByName("adjustTipHeights", false,
                "newick", newickString,
                "taxonset", taxonSet);

        tree.assignFromWithoutID(thisTree);

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

}
