package feast.function;

import beast.core.Input;

public class Sequence extends LoggableFunction {

    public Input<Double> startInput = new Input<>("start",
            "Start value of sequence",
            Input.Validate.REQUIRED);

    public Input<Double> stopInput = new Input<>("stop",
            "Stop value of sequence",
            Input.Validate.REQUIRED);

    public Input<Integer> lengthInput = new Input<>("length",
            "Length of sequence",
            Input.Validate.REQUIRED);

    double[] values;

    @Override
    public void initAndValidate() {
        int length = lengthInput.get();

        if (length<2)
            throw new IllegalArgumentException("Sequence length input " +
                    "must be at least 2.");

        values = new double[length];

        double start = startInput.get();
        double stop = stopInput.get();
        double delta = (stop-start)/(length - 1);

        for (int i=0; i<length; i++)
            values[i] = start + i*delta;
    }

    @Override
    public int getDimension() {
        return values.length;
    }

    @Override
    public double getArrayValue(int dim) {
        return values[dim];
    }
}
