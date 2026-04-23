package feast.realvector;

import beast.base.spec.domain.Real;
import beast.base.spec.inference.parameter.RealVectorParam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SequenceTest {

    @Test
    public void test() {
        RealVectorParam<Real> param = new RealVectorParam<>(new double[] {1.0, 2.0, 3.0}, Real.INSTANCE);

        Reverse reversedParam = new Reverse();
        reversedParam.initByName("arg", param);

        assertEquals(3, reversedParam.size());
        assertEquals(3.0, reversedParam.get(0), 1e-10);
        assertEquals(2.0, reversedParam.get(1), 1e-10);
        assertEquals(1.0, reversedParam.get(2), 1e-10);
    }
}
