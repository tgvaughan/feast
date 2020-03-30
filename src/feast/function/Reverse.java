package feast.function;

import beast.core.*;

import java.io.PrintStream;

@Description("A function whose elements are the elements of the input function but in reverse order.")
public class Reverse extends CalculationNode implements Function, Loggable {

    public Input<Function> functionInput = new Input<>("arg",
            "Argument to reverse elements of.", Input.Validate.REQUIRED);

    @Override
    public void initAndValidate() {

    }

    @Override
    public int getDimension() {
        return functionInput.get().getDimension();
    }

    @Override
    public double getArrayValue(int dim) {
        return functionInput.get().getArrayValue(getDimension()-1-dim);
    }

    @Override
    public void init(PrintStream out) {
        for (int i=0; i<getDimension(); i++)
            out.print(((BEASTObject)functionInput.get()).getID()
                    + "[" + i + "]\t");
    }

    @Override
    public void log(long nSample, PrintStream out) {
        for (int i=0; i<getDimension(); i++)
            out.print(getArrayValue(i) + "\t");
    }

    @Override
    public void close(PrintStream out) {

    }
}
