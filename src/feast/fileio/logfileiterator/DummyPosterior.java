package feast.fileio.logfileiterator;

import beast.core.BEASTInterface;
import beast.core.Distribution;
import beast.core.Input;
import beast.core.State;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A dummy posterior object.  When set as the posterior for a State object,
 * the State will identify inputs to this posterior as objects that must be
 * reinitialized when any of the state nodes change.
 */
public class DummyPosterior extends Distribution {

    public Input<List<BEASTInterface>> inputs = new Input<>("object",
            "Dummy posterior input.",
            new ArrayList<>());

    @Override
    public double calculateLogP() {
        for (BEASTInterface object : inputs.get()) {
            if (object instanceof Distribution)
                ((Distribution) object).calculateLogP();
        }

        return super.calculateLogP();
    }

    @Override
    public List<String> getArguments() {
        return null;
    }

    @Override
    public List<String> getConditions() {
        return null;
    }

    @Override
    public void sample(State state, Random random) {

    }
}
