package feast.fileio.logfileiterator;

import beast.base.core.BEASTObject;
import beast.base.core.Input;
import beast.base.inference.StateNode;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.Set;

public abstract class LogFileState extends BEASTObject {
    public Input<String> logFileNameInput = new Input<>("logFileName", "Name of log file",
            Input.Validate.REQUIRED);

    protected String logFileName;
    protected BufferedReader inFile;

    protected int currentSample;

    @Override
    public void initAndValidate() {
        logFileName = logFileNameInput.get();

        try {
            inFile = new BufferedReader(new FileReader(logFileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        currentSample = -1;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public int getCurrentSample() {
        return currentSample;
    }

    public abstract int updateToNextEntry();

    public abstract List<StateNode> getStateNodes();

}
