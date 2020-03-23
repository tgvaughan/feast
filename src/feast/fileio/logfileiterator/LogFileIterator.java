package feast.fileio.logfileiterator;

import beast.core.Input;
import beast.core.Logger;
import beast.core.Runnable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LogFileIterator extends Runnable {

    public Input<List<LogFileState>> logFileStateInput = new Input<>("logFileState",
            "Log file state.",
            new ArrayList<>());

    public Input<List<Logger>> loggersInput = new Input<>("logger",
            "Logger used to produce output.",
            new ArrayList<>());

    List<LogFileState> logFileStates;
    List<Logger> loggers;

    @Override
    public void initAndValidate() {
        logFileStates = logFileStateInput.get();
        loggers = loggersInput.get();

    }

    @Override
    public void run() {

        outputLogsStart();

        iterate();

        outputLogsStop();
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

            // Generate output log files for this state

            for (Logger logger : loggers) {
                logger.log(nextSample);
            }

        }

    }
}
