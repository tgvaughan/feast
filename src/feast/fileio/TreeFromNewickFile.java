package feast.fileio;

import beast.core.Description;
import beast.core.Input;
import beast.evolution.tree.Tree;
import beast.util.TreeParser;

import java.io.*;

/**
 * @author Tim Vaughan
 */
@Description("Wrapper around TreeParser to enable reading newick " +
        "strings from files.")
public class TreeFromNewickFile extends TreeParser {

    public Input<String> fileNameInput = new Input<>("fileName", "Name of file "
            + "containing tree in Newick format.", Input.Validate.REQUIRED);

    public TreeFromNewickFile() { }

    @Override
    public void initAndValidate() {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(fileNameInput.get()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("Input file not found.");
        }
        String line;
        StringBuilder newickBuilder = new StringBuilder();

        try {
            while ((line = reader.readLine()) != null)
                newickBuilder.append(line.trim());
        } catch (IOException e) {
            throw new RuntimeException("Error reading from input file.");
        }

        newickInput.setValue(newickBuilder.toString(), this);

        super.initAndValidate();
    }
}
