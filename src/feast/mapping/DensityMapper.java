package feast.mapping;

import beast.core.*;
import beast.core.parameter.IntegerParameter;
import beast.core.parameter.RealParameter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
@Description("Utility for mapping probability density surfaces.")
public class DensityMapper extends beast.core.Runnable {

    public Input<List<RealParameter>> realParamsInput = new Input<>(
            "realParam",
            "Real parameter to vary.",
            new ArrayList<>());

    public Input<List<IntegerParameter>> stepsInput = new Input<>(
            "steps",
            "Number of steps to take between parameter bounds. 1 means use" +
            "initial value.",
            new ArrayList<>());

    public Input<List<Distribution>> distribsInput = new Input<>(
            "distribution",
            "Distribution to map.",
            new ArrayList<>());


    public Input<List<Logger>> loggersInput = new Input<>(
            "logger",
            "Logger used to store output.",
            new ArrayList<>());

    int nValues;
    int sample;

    State dummyState;

    public DensityMapper() { }

    @Override
    public void initAndValidate() throws Exception {

        if (realParamsInput.get().size() != stepsInput.get().size())
            throw new IllegalArgumentException("Number of step sizes " +
                    "must match number of real params.");

        // Calculate total number of params to vary
        nValues = 0;
        for (int i=0; i<realParamsInput.get().size(); i++) {
            int thisN = realParamsInput.get().get(i).getDimension();
            if (stepsInput.get().get(i).getDimension() != 1
                    && stepsInput.get().get(i).getDimension() != thisN)
                throw new IllegalArgumentException("Dimension of step sizes " +
                        "param must be 1 or the dimension of the associated " +
                        "real param.");

            nValues += thisN;
        }

        // Add RealParameters to dummy state:
        dummyState = new State();
        for (RealParameter param : realParamsInput.get())
            dummyState.stateNodeInput.setValue(param, dummyState);
        dummyState.initAndValidate();
        dummyState.initialise();

        sample = 0;

    }

    @Override
    public void run() throws Exception {
        // Initialise loggers:
        for (Logger logger : loggersInput.get())
            logger.init();

        // Loop over parameter values
        nestedLoop(0);

        // Finalize loggers
        loggersInput.get().forEach(beast.core.Logger::close);
    }

    /**
     * Perform nested loop over combinations of parameter values
     *
     * @param depth initialize to zero,
     */
    void nestedLoop(int depth) {

        if (depth<nValues) {
            int paramIdx;
            int elIdx = 0;
            int count = 0;
            for (paramIdx = 0; paramIdx<realParamsInput.get().size(); paramIdx++) {
                RealParameter thisParam = realParamsInput.get().get(paramIdx);
                if (count + thisParam.getDimension() > depth) {
                    elIdx = depth - count;
                    break;
                }
                count += thisParam.getDimension();
            }

            boolean stepAll = stepsInput.get().get(paramIdx).getDimension() == 1;

            int nSteps;
            if (stepAll)
                nSteps = stepsInput.get().get(paramIdx).getValue();
            else
                nSteps = stepsInput.get().get(paramIdx).getValue(elIdx);

            RealParameter param = realParamsInput.get().get(paramIdx);

            double delta = nSteps > 1
                    ? (param.getUpper()-param.getLower())/(nSteps-1)
                    : 0.0; // Unused in this case.

            if (stepAll) {
                for (int i=0; i<stepsInput.get().get(paramIdx).getValue(); i++) {
                    for (elIdx = 0; elIdx < param.getDimension(); elIdx++)
                        if (delta>0.0)
                            param.setValue(elIdx, param.getLower() + i * delta);

                    nestedLoop(depth + param.getDimension());
                }
            } else {
                for (int i = 0; i < stepsInput.get().get(paramIdx).getValue(elIdx); i++) {
                    if (delta>0.0)
                        param.setValue(elIdx, param.getLower() + i * delta);
                    nestedLoop(depth + 1);
                }

            }

        } else {
            for (Distribution distrib : distribsInput.get()) {
                try {
                    dummyState.setPosterior(distrib);
                    dummyState.checkCalculationNodesDirtiness();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    distrib.calculateLogP();
                } catch (Exception e) {
                    System.out.println("Error computing density.");
                    e.printStackTrace();
                }
            }

            for (Logger logger : loggersInput.get()) {
                logger.log(sample);
            }

            sample += 1;
        }

    }

}
