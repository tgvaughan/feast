package feast.parameter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TimeParameterTest {

    @Test
    public void test() {

        TimeParameter param = new TimeParameter();
        param.initByName("time", "05/03/1980 27/02/1980",
                "mostRecentSampleTime", "01/01/1990",
                "timeFormat", "dd/MM/yyyy");

        assertEquals(9.825137, param.getValue(0), 1e-6);
        assertEquals(9.844262, param.getValue(1), 1e-6);
    }

    @Test
    public void testBounds() {
        TimeParameter param = new TimeParameter();
        param.initByName("time", "05/03/1980 27/02/1980",
                "mostRecentSampleTime", "01/01/1990",
                "timeFormat", "dd/MM/yyyy",
                "timeEarlier", "19/02/1980",
                "timeLater", "01/04/1980");

        assertEquals(9.866120, param.getUpper(), 1e-6);
        assertEquals(9.751366, param.getLower(), 1e-6);
    }
}
