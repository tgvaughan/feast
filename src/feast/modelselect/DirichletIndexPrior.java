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

    int N;

    @Override
    public void initAndValidate() {
        selectionIndices = selectionIndiciesInput.get();
        scalingParameter = scalingParameterInput.get();

        N = selectionIndices.getDimension();
        counts = new int[N];
    }

    @Override
    public double calculateLogP() {
        logP = 0.0;

        Arrays.fill(counts, 0);
        int nUnique = 0;

        double alpha = scalingParameterInput.get().getValue();

        for (int i=1; i<=N; i++) {

            int k = selectionIndices.getValue(i-1);

            if (counts[k]>0)
                logP += Math.log(counts[k]/(alpha+i-1));
            else {
                logP += Math.log(alpha / (alpha+i-1) / (double)(N - nUnique));
                nUnique += 1;
            }

            counts[k] += 1;
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
