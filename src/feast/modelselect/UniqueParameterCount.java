package feast.modelselect;

import beast.core.*;
import beast.core.parameter.IntegerParameter;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

public class UniqueParameterCount extends CalculationNode implements Function, Loggable {

    public Input<IntegerParameter> parameterIndicesInput = new Input<>("selectionIndices",
            "Integer parameter controlling parameter indices.", Input.Validate.REQUIRED);

    Set<Integer> indexSet = new HashSet<>();

    @Override
    public void initAndValidate() {
    }

    @Override
    public int getDimension() {
        return 1;
    }

    @Override
    public double getArrayValue(int dim) {

        if (dim > 0)
            return 0;

        indexSet.clear();
        for (int i=0; i<parameterIndicesInput.get().getDimension(); i++) {
            indexSet.add(parameterIndicesInput.get().getValue(i));
        }

        return indexSet.size();
    }

    @Override
    public void init(PrintStream out) {
        out.print(getID() + "\t");
    }

    @Override
    public void log(long sample, PrintStream out) {
        out.print(getArrayValue(0));
    }

    @Override
    public void close(PrintStream out) {
    }
}
