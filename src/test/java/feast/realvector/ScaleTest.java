package feast.realvector;

import beast.base.inference.parameter.RealParameter;
import beast.base.spec.domain.Real;
import beast.base.spec.inference.parameter.RealScalarParam;
import beast.base.spec.inference.parameter.RealVectorParam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScaleTest {

    @Test
    public void test() {
        RealVectorParam<Real> param = new RealVectorParam<>(new double[] {1.0, 2.0, 3.0}, Real.INSTANCE);
        RealScalarParam<Real> factor = new RealScalarParam<>(2.5, Real.INSTANCE);

        Scale<Real> scaledParam = new Scale<>();
        scaledParam.initByName("function", param, "scaleBy", factor);

        assertEquals(3, scaledParam.size());
        assertEquals(2.5, scaledParam.get(0), 1e-10);
        assertEquals(5.0, scaledParam.get(1), 1e-10);
        assertEquals(7.5, scaledParam.get(2), 1e-10);
    }
}
