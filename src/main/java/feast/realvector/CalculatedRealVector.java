package feast.realvector;

import beast.base.spec.domain.Real;

import java.util.ArrayList;
import java.util.List;

public abstract class CalculatedRealVector<D extends Real> extends LoggableRealVector<D> {

    @Override
    public List<Double> getElements() {
        List<Double> elements = new ArrayList<>();
        for (int i=0; i<size(); i++)
            elements.add(get(i));
        return elements;
    }
}
