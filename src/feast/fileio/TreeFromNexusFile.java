package feast.fileio;

import beast.core.Description;
import beast.core.Input;
import beast.util.TreeParser;
import feast.nexus.BasicNexusParser;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Tim Vaughan
 */
@Description("Wrapper around TreeParser to enable reading newick " +
        "strings from files.")
public class TreeFromNexusFile extends TreeParser {

    public Input<String> fileNameInput = new Input<>("fileName", "Name of Nexus file "
            + "containing a tree block Nexus format.", Input.Validate.REQUIRED);

    public TreeFromNexusFile() { }

    @Override
    public void initAndValidate() {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileNameInput.get()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Input file not found.");
        }

        try {
            BasicNexusParser nexusParser = new BasicNexusParser(reader);

            BasicNexusParser.NexusBlock treesBlock = nexusParser.getNextBlockMatching("trees");

            BasicNexusParser.NexusCommand treeCommand = null;
            for (BasicNexusParser.NexusCommand cmd : treesBlock.commands) {
                if (cmd.name.equals("tree")) {
                    treeCommand = cmd;
                    break;

                }
            }

            if (treeCommand != null) {
                int idx = treeCommand.args.indexOf("=");
                String newickString = treeCommand.args.substring(idx+1).trim();
                newickInput.setValue(newickString, this);
            } else {
                throw new RuntimeException("No trees found in nexus file.");
            }

        } catch (IOException e) {
            throw new RuntimeException("Error reading from input file.");
        }

        super.initAndValidate();
    }
}
