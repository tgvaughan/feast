package feast.realvector;

import beast.base.core.Loggable;
import beast.base.inference.CalculationNode;
import beast.base.spec.domain.Int;
import beast.base.spec.type.IntVector;

import java.io.PrintStream;

public abstract class LoggableIntVector<D extends Int> extends CalculationNode implements Loggable, IntVector<D> {
    @Override
    public void init(PrintStream out) {
        if (size() == 1) {
            out.print(getID() + "\t");
        } else {
            for (int i = 0; i < size(); i++)
                out.print(getID() + "[" + i + "]\t");
        }
    }

    @Override
    public void log(long nSample, PrintStream out) {
        for (int i = 0; i < size(); i++)
            out.print(get(i) + "\t");
    }

    @Override
    public void close(PrintStream out) {

    }
}
