package feast.fileio.logfileiterator;

import beast.core.BEASTInterface;
import beast.core.BEASTObject;
import beast.core.Input;
import beast.core.StateNode;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class TraceLogFileState extends LogFileState {

    public Input<List<LogFileRealParameter>> logFileEntryInput = new Input<>("logFileEntry",
            "Associates column of logfile with BEASTObject",
            new ArrayList<>());

    List<LogFileRealParameter> logFileEntries;
    Map<Integer, LogFileRealParameter> columnEntryMap;

    int colCount;

    @Override
    public void initAndValidate() {
        super.initAndValidate();

        logFileEntries = logFileEntryInput.get();

        // Find and parse header
        String[] logFileVariables = getNextLogLine();
        colCount = logFileVariables.length;

        columnEntryMap = new HashMap<>();
        for (int c=0; c<colCount; c++) {
            for (LogFileRealParameter entry : logFileEntries) {
                if (entry.getFieldName().equals(logFileVariables[c])) {
                    columnEntryMap.put(c, entry);
                    break;
                }
            }
        }
    }

    private String[] getNextLogLine() {
        String line = null;
        try {
            do {
                line = inFile.readLine();
            } while (line != null && (line.isEmpty() || line.startsWith("#")));
        } catch (IOException e) {
            throw new RuntimeException("Error reading log file.");
        }

        if (line == null)
            return null;
        else
            return line.split("\t");
    }

    public int updateToNextEntry() {

        String[] values = getNextLogLine();

        if (values == null) {
            currentSample = -1;
            return currentSample;
        }

        for (int c : columnEntryMap.keySet()) {
            columnEntryMap.get(c).setFieldParameterValue(values[c]);
        }

        currentSample = Integer.parseInt(values[0]);
        return currentSample;
    }

    List<StateNode> stateNodes = null;

    @Override
    public List<StateNode> getStateNodes() {
        if (stateNodes == null)
            stateNodes = logFileEntries.stream().map(e -> e.fieldParameter).collect(Collectors.toList());

        return stateNodes;
    }
}
