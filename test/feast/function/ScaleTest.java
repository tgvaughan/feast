package feast.function;

import beast.core.Function;
import beast.core.parameter.RealParameter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScaleTest {

    @Test
    public void test() {
        RealParameter param = new RealParameter("1.0 2.0 3.0");
        RealParameter factor = new RealParameter("2.5");

        LoggableFunction scaledParam = new Scale();
        scaledParam.initByName("function", param, "scaleBy", factor);

        assertEquals(3, scaledParam.getDimension());
        assertEquals(2.5, scaledParam.getArrayValue(0), 1e-10);
        assertEquals(5.0, scaledParam.getArrayValue(1), 1e-10);
        assertEquals(7.5, scaledParam.getArrayValue(2), 1e-10);
    }
}
