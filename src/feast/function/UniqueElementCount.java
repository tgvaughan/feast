package feast.function;

import beast.core.*;

import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

@Description("Function representing the number of unique elements of another function.")
public class UniqueElementCount extends LoggableFunction {

    public Input<Function> argInput = new Input<>("arg",
            "Number of unique elements of this parameter will be logged.",
            Input.Validate.REQUIRED);

    Set<Double> valueSet = new HashSet<>();

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

        valueSet.clear();
        for (int i = 0; i< argInput.get().getDimension(); i++) {
            valueSet.add(argInput.get().getArrayValue(i));
        }

        return valueSet.size();
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
