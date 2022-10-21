package feast.fileio.logfileiterator;

import beast.base.core.BEASTInterface;
import beast.base.core.BEASTObject;
import beast.base.core.Input;
import beast.base.inference.Logger;
import beast.base.inference.Runnable;
import beast.base.inference.State;
import beast.base.inference.StateNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LogFileIterator extends Runnable {

    public Input<List<LogFileState>> logFileStateInput = new Input<>("logFileState",
            "Log file state.",
            new ArrayList<>());

    public Input<List<Logger>> loggersInput = new Input<>("logger",
            "Logger used to produce output.",
            new ArrayList<>());

    List<LogFileState> logFileStates;
    List<Logger> loggers;

    Set<BEASTInterface> updatedObjects = new HashSet<>();

    @Override
    public void initAndValidate() {
        logFileStates = logFileStateInput.get();
        loggers = loggersInput.get();
    }

    @Override
    public void run() {

        buildState();

        outputLogsStart();

        iterate();

        outputLogsStop();
    }

    /**
     * Build state
     */

    State state = new State();
    DummyPosterior dummyPosterior = new DummyPosterior();

    public void buildState() {

        for (LogFileState logFileState : logFileStates) {
            for (StateNode stateNode : logFileState.getStateNodes())
                state.setInputValue("stateNode", stateNode);
        }

        state.initAndValidate();
        state.initialise();

        for (Logger logger : loggersInput.get()) {
            for (BEASTObject loggable : logger.loggersInput.get()) {
                dummyPosterior.inputs.setValue(loggable, dummyPosterior);
            }
        }
        dummyPosterior.initAndValidate();

        state.setPosterior(dummyPosterior);
    }

    /**
     * Initialize output log files.
     */
    public void outputLogsStart() {
        try {
            for (Logger logger : loggers)
                logger.init();
        } catch (IOException e) {
            System.out.println("Error initializing output log files: " + e.getMessage());
        }
    }

    /**
     * Close output log files.
     */
    public void outputLogsStop() {
        for (Logger logger : loggers) {
            logger.close();
        }
    }

    public void iterate() {

        while (true) {

            // Increment each input log file by one state

            int nextSample = 0;

            for (LogFileState logFileState : logFileStates) {

                int thisSample = logFileState.updateToNextEntry();


                if (thisSample < 0) {
                    System.err.println("Reached the end of log file " + logFileState.getLogFileName());
                    return;
                }

                nextSample = Math.max(thisSample, nextSample);
            }

            // Bring all input log files up to the same state

            for (LogFileState logFileState : logFileStates) {

                while (logFileState.getCurrentSample() < nextSample)
                    logFileState.updateToNextEntry();

                if (logFileState.getCurrentSample() > nextSample) {
                    System.err.println("Log file sample frequencies do not seem to divide evenly into one another.");
                    return;
                }
            }

            // Update log file parameter outputs
            state.robustlyCalcPosterior(dummyPosterior);

            // Generate output log files for this state

            for (Logger logger : loggers) {
                logger.log(nextSample);
            }
        }
    }

}
