package feast.modelselect;

import beast.core.Distribution;
import beast.core.Input;
import beast.core.State;
import beast.core.parameter.IntegerParameter;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class ModelSelectionIndexPrior extends Distribution {

    public Input<IntegerParameter> selectionIndiciesInput = new Input<>("selectionIndices",
            "Model selection index.", Input.Validate.REQUIRED);

    /**
     * Count the number of ways in which L characters from an alphabet of size m can provide
     * n distinct values.
     *
     * @param n number of distinct values
     * @param L length of sequence
     * @param m size of alphabet
     * @return number of sequences fitting these constraints
     */
    private static int count(int n, int L, int m) {
        if (n<=0 || L<=0 || n>m || n>L)
            return 0;

        if (L==1) {
            if (n==1)
                return m;
            else
                return 0;
        } else {
            return n*count(n,L-1,m) + (m-n+1)*count(n-1,L-1,m);
        }
    }

    Set<Integer> indexSet = new HashSet<>();

    @Override
    public double calculateLogP() {
        int L = selectionIndiciesInput.get().getDimension();

        indexSet.clear();
        for (int i=0; i<L; i++)
            indexSet.add(selectionIndiciesInput.get().getValue(i));
        int nUnique = indexSet.size();


        logP = 1.0/(double)(L*count(nUnique, L, L));

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

    public static void main(String[] args) {
        System.out.println(count(4,4,6));
    }
}
