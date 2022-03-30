package feast.function;

import beast.core.*;

import java.io.PrintStream;

/**
 * @author Tim Vaughan
 */
@Description("A Function representing a number of elements of another Function.")
public class Slice extends LoggableFunction {

    public Input<Function> functionInput = new Input<>("arg",
            "Argument to extract element from.", Input.Validate.REQUIRED);

    public Input<Integer> startIndexInput = new Input<>("index",
            "Index of first element to extract.", Input.Validate.REQUIRED);

    public Input<Integer> countInput = new Input<>("count",
            "Number of elements to extract.", 1);

    public Input<Integer> byInput = new Input<>("by",
            "Interval between elements (default 1).", 1);

    protected int indexStart, indexEnd, count, by;

    @Override
    public void initAndValidate() {
        indexStart = startIndexInput.get();
        count = countInput.get();
        by = byInput.get();
        indexEnd = indexStart + by*(count - 1);

        if (indexEnd >= functionInput.get().getDimension())
            throw new IllegalArgumentException("Index and count arguments to" +
                    " Slice are out of bounds.");
    }

    @Override
    public int getDimension() {
        return count;
    }

    @Override
    public double getArrayValue() {
        return getArrayValue(0);
    }

    @Override
    public double getArrayValue(int iDim) {
        if (iDim < count)
            return functionInput.get().getArrayValue(indexStart + iDim*by);
        else
            return 0;
    }
}
