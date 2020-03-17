package feast.modelselect;

import beast.core.Distribution;
import beast.core.Input;
import beast.core.State;
import beast.core.parameter.IntegerParameter;
import beast.core.parameter.RealParameter;

import java.util.*;

public class DirichletIndexPrior extends Distribution {

    public Input<IntegerParameter> selectionIndiciesInput = new Input<>("selectionIndices",
            "Model selection index.", Input.Validate.REQUIRED);

    public Input<RealParameter> scalingParameterInput = new Input<>("scalingParameter",
            "Scaling parameter for Dirichlet process",
            Input.Validate.REQUIRED);

    IntegerParameter selectionIndices;
    RealParameter scalingParameter;
    int[] counts;

    public DirichletIndexPrior() {
        selectionIndices = selectionIndiciesInput.get();
        scalingParameter = scalingParameterInput.get();

        counts = new int[selectionIndices.getDimension()];
    }

    @Override
    public double calculateLogP() {
        logP = 0.0;

        Arrays.fill(counts, 0);

        double alpha = scalingParameterInput.get().getValue();

        for (int i=0; i<selectionIndices.getDimension(); i++) {

            if (counts[selectionIndices.getValue(i)]>0)
                logP += Math.log((i+1)/(alpha + i));
            else
                logP += Math.log(alpha/(alpha+i));

            counts[selectionIndices.getValue(i)] += 1;
        }

        return logP;
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
