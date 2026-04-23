package feast.realvector;

import beast.base.spec.domain.Real;
import beast.base.spec.inference.parameter.RealVectorParam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SliceTest {

    @Test
    public void test() {
        RealVectorParam<Real> param = new RealVectorParam<>(new double[] {0,1,2,3,4,5}, Real.INSTANCE);
        Slice slicedParam = new Slice();
        slicedParam.initByName(
                "arg", param,
                "index", 2,
                "count", 3);

        assertEquals(3, slicedParam.size());
        assertEquals(2, slicedParam.get(0), 1e-10);
        assertEquals(3, slicedParam.get(1), 1e-10);
        assertEquals(4, slicedParam.get(2), 1e-10);
        assertEquals(0, slicedParam.get(3), 1e-10);
    }

    @Test
    public void testBy() {
        RealVectorParam<Real> param = new RealVectorParam<>(new double[] {0,1,2,3,4,5,6,7,8,9,10}, Real.INSTANCE);
        Slice slicedParam = new Slice();
        slicedParam.initByName(
                "arg", param,
                "index", 2,
                "count", 4,
                "by", 2);

        assertEquals(4, slicedParam.size());
        assertEquals(2, slicedParam.get(0), 1e-10);
        assertEquals(4, slicedParam.get(1), 1e-10);
        assertEquals(6, slicedParam.get(2), 1e-10);
        assertEquals(8, slicedParam.get(3), 1e-10);
    }
}
