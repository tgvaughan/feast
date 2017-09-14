package feast.simulation;

import beast.core.BEASTObject;
import beast.core.Input;
import beast.core.Logger;
import beast.core.Runnable;

import java.util.ArrayList;
import java.util.List;

public class GPSimulator extends Runnable {

    public Input<BEASTObject> beastObjectInput = new Input<>(
            "simulationObject",
            "BEASTObject constituting simulated quantity.",
            Input.Validate.REQUIRED);

    public Input<Integer> nSimsInput = new Input<>(
            "nSims",
            "Number of values to simulate",
            Input.Validate.REQUIRED);

    public Input<List<Logger>> loggersInput = new Input<>(
            "logger",
            "Logger used to write results to screen or disk.",
            new ArrayList<>());

    @Override
    public void initAndValidate() { }

    @Override
    public void run() throws Exception {

        // Initialize loggers
        for (Logger logger : loggersInput.get()) {
            logger.init();
        }

        for (int i=0; i<nSimsInput.get(); i++) {
            if (i>0)
               beastObjectInput.get().initAndValidate();

            // Log state
            for (Logger logger : loggersInput.get())
                logger.log(i);
        }

        // Finalize loggers
        for (Logger logger : loggersInput.get())
            logger.close();
    }
}
