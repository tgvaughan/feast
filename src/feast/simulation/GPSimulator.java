package feast.simulation;

import beast.base.core.BEASTObject;
import beast.base.core.Input;
import beast.base.inference.Logger;
import beast.base.inference.Runnable;

import java.util.ArrayList;
import java.util.List;

public class GPSimulator extends Runnable {

    public Input<List<BEASTObject>> beastObjectInput = new Input<>(
            "simulationObject",
            "BEASTObject constituting simulated quantity.",
            new ArrayList<>());

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

        if (beastObjectInput.get().isEmpty())
            throw new IllegalArgumentException("Need to specify at least one" +
                    " simulation object.");

        // Initialize loggers
        for (Logger logger : loggersInput.get()) {
            logger.init();
        }

        for (int i=0; i<nSimsInput.get(); i++) {
            if (i>0) {
                for (BEASTObject beastObject : beastObjectInput.get())
                    beastObject.initAndValidate();
            }

            // Log state
            for (Logger logger : loggersInput.get())
                logger.log(i);
        }

        // Finalize loggers
        for (Logger logger : loggersInput.get())
            logger.close();
    }
}
