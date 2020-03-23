package feast.fileio.logfileiterator;

import beast.core.BEASTObject;
import beast.core.Input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public abstract class LogFileState extends BEASTObject {
    public Input<String> logFileNameInput = new Input<>("logFileName", "Name of log file",
            Input.Validate.REQUIRED);

    String logFileName;
    BufferedReader inFile;

    int currentSample;

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
}
