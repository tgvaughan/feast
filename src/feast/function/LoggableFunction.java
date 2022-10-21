package feast.function;

import beast.base.core.Function;
import beast.base.core.Loggable;
import beast.base.inference.CalculationNode;

import java.io.PrintStream;

public abstract class LoggableFunction extends CalculationNode implements Loggable, Function {

    @Override
    public void init(PrintStream out) {
        if (getDimension()==1) {
            out.print(getID() + "\t");
        } else {
            for (int i = 0; i < getDimension(); i++)
                out.print(getID() + "[" + i + "]\t");
        }
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
