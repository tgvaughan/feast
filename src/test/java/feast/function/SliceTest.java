package feast.function;

import beast.base.core.Function;
import beast.base.inference.parameter.RealParameter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SliceTest {

    @Test
    public void test() {
        Function param = new RealParameter("0 1 2 3 4 5");
        Slice slicedParam = new Slice();
        slicedParam.initByName(
                "arg", param,
                "index", 2,
                "count", 3);

        assertEquals(3, slicedParam.getDimension());
        assertEquals(2, slicedParam.getArrayValue(0), 1e-10);
        assertEquals(3, slicedParam.getArrayValue(1), 1e-10);
        assertEquals(4, slicedParam.getArrayValue(2), 1e-10);
        assertEquals(0, slicedParam.getArrayValue(3), 1e-10);
        assertEquals(2, slicedParam.getArrayValue(), 1e-10);
    }

    @Test
    public void testBy() {
        Function param = new RealParameter("0 1 2 3 4 5 6 7 8 9 10");
        Slice slicedParam = new Slice();
        slicedParam.initByName(
                "arg", param,
                "index", 2,
                "count", 4,
                "by", 2);

        assertEquals(4, slicedParam.getDimension());
        assertEquals(2, slicedParam.getArrayValue(0), 1e-10);
        assertEquals(4, slicedParam.getArrayValue(1), 1e-10);
        assertEquals(6, slicedParam.getArrayValue(2), 1e-10);
        assertEquals(8, slicedParam.getArrayValue(3), 1e-10);
        assertEquals(2, slicedParam.getArrayValue(), 1e-10);
    }
}
