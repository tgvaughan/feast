package feast.fileio;

import beast.core.Description;
import beast.core.Input;
import beast.evolution.tree.Tree;
import beast.util.TreeParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;

/**
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
@Description("Wrapper around TreeParser to enable reading newick " +
        "strings from files.")
public class TreeFromNewickFile extends TreeParser {

    public Input<String> fileNameInput = new Input<>("fileName", "Name of file "
            + "containing tree in Newick format.", Input.Validate.REQUIRED);

    public TreeFromNewickFile() { }

    @Override
    public void initAndValidate() throws Exception {

        BufferedReader reader = new BufferedReader(new FileReader(fileNameInput.get()));
        String line;
        StringBuilder newickBuilder = new StringBuilder();

        while ((line = reader.readLine()) != null)
            newickBuilder.append(line.trim());

        newickInput.setValue(newickBuilder.toString(), this);

        super.initAndValidate();
    }
}
