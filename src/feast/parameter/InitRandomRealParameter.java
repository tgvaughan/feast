package feast.parameter;

import beast.base.core.BEASTObject;
import beast.base.core.Description;
import beast.base.core.Input;
import beast.base.inference.StateNode;
import beast.base.inference.StateNodeInitialiser;
import beast.base.inference.distribution.OneOnX;
import beast.base.inference.distribution.ParametricDistribution;
import beast.base.inference.distribution.Uniform;
import beast.base.inference.parameter.RealParameter;
import org.apache.commons.math.MathException;

import java.util.List;

@Description("Helps to randomly initialize a RealParameter from a given distribution")
public class InitRandomRealParameter extends BEASTObject implements StateNodeInitialiser {
    final public Input<RealParameter> paramInput = new Input<>("initial",
            "Initial parameter.", Input.Validate.REQUIRED);
    final public Input<ParametricDistribution> distributionInput = new Input<>("distr",
            "Distribution from which to draw a random value. Usually the prior ditribution for this parameter.",
            Input.Validate.REQUIRED);


    boolean sample_from_tmp = false;
    ParametricDistribution distribution;
    @Override
    public void initAndValidate() {
        initStateNodes();
    }


    @Override
    public void initStateNodes() {
        int dim = paramInput.get().getDimension();
        distribution = distributionInput.get();
        if (distribution instanceof Uniform)
            if (Double.isInfinite(((Uniform) distribution).lowerInput.get()) ||
                Double.isInfinite(((Uniform) distribution).upperInput.get())){
            System.out.println("Cannot sample from improper Uniform distribution." +
                    "One or both bounds are infinite.");
            System.exit(1);
        }
        if (distribution instanceof OneOnX){
            System.out.println("Currently not implemented for OneOnX distribution.");
            System.exit(1);
        }

        for (int i = 0; i < dim; i++){
            Double rnd = null;
            try {
                do {
                    rnd  = distribution.sample(1)[0][0];
                } while (rnd < paramInput.get().getLower() || rnd > paramInput.get().getUpper());
            } catch (MathException e) {
                throw new RuntimeException(e);
            }
            paramInput.get().setValue(i, rnd);
        }
    }

    @Override
    public void getInitialisedStateNodes(List<StateNode> stateNodes) {
        stateNodes.add(paramInput.get());
    }


}
