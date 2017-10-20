package feast.parameter;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class TimeParameterTest {

    @Test
    public void test() {

        TimeParameter param = new TimeParameter();
        param.initByName("time", "05/03/1980 27/02/1980",
                "mostRecentSampleTime", "01/01/1990",
                "timeFormat", "dd/MM/yyyy");

        assertEquals(param.getValue(0), 9.825137, 1e-6);
        assertEquals(param.getValue(1), 9.844262, 1e-6);
    }
}
