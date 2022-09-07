package feast.modelselect;

import beast.core.*;
import beast.core.parameter.IntegerParameter;
import beast.core.parameter.RealParameter;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

@Description("Class of Functions useful for BSSVS-style model selection/averaging.")
public class ModelSelectionParameter extends CalculationNode implements Function, Loggable {

    public Input<List<Function>> parametersInput = new Input<>("parameter",
            "Parameter for the selection pool.",
            new ArrayList<>());

    public Input<IntegerParameter> selectionIndicesInput = new Input<>("selectionIndices",
            "Integer parameter containing indicies to which each output parameter is mapped.",
            Input.Validate.REQUIRED);

    public Input<Integer> thisIndexInput = new Input<>("thisIndex",
            "Index of output.", 0);

    @Override
    public void initAndValidate() { }


    /*
     * Function
     */

    @Override
    public int getDimension() {
        return parametersInput.get().get(selectionIndicesInput.get().getValue(thisIndexInput.get())).getDimension();
    }

    @Override
    public double getArrayValue(int dim) {
        return parametersInput.get().get(selectionIndicesInput.get().getValue(thisIndexInput.get())).getArrayValue(dim);
    }


    /*
     *  Loggable
     */

    @Override
    public void init(final PrintStream out) {
        final int valueCount = getDimension();
        if (valueCount == 1) {
            out.print(getID() + "\t");
        } else {
            for (int value = 0; value < valueCount; value++) {
                out.print(getID() + (value + 1) + "\t");
            }
        }
    }

    @Override
    public void log(final long sample, final PrintStream out) {
        for (int i = 0; i < getDimension(); i++) {
            out.print(getArrayValue(i) + "\t");
        }
    }

    @Override
    public void close(PrintStream out) {

    }
}
