package feast.realvector;

import beast.base.spec.domain.Real;
import beast.base.spec.inference.parameter.RealVectorParam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConcatenateTest {
    @Test
    public void test() {
        RealVectorParam<Real> param1 = new RealVectorParam<>(new double[]{1.0, 2.0, 3.0}, Real.INSTANCE);
        RealVectorParam<Real> param2 = new RealVectorParam<>(new double[]{4.0, 5.0}, Real.INSTANCE);

        Concatenate concatenatedParam = new Concatenate();
        concatenatedParam.initByName("arg", param1, "arg", param2);

        assertEquals(5, concatenatedParam.size());
        assertEquals(1.0, concatenatedParam.get(0), 1e-10);
        assertEquals(2.0, concatenatedParam.get(1), 1e-10);
        assertEquals(3.0, concatenatedParam.get(2), 1e-10);
        assertEquals(4.0, concatenatedParam.get(3), 1e-10);
        assertEquals(5.0, concatenatedParam.get(4), 1e-10);
    }
}
