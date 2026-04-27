package feast.parameter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeParameterTest {

    @Test
    public void test() {

        TimeParameter param = new TimeParameter();
        param.initByName("time", "05/03/1980 27/02/1980",
                "mostRecentSampleTime", "01/01/1990",
                "timeFormat", "dd/MM/yyyy");

        assertEquals(9.825137, param.get(0), 1e-6);
        assertEquals(9.844262, param.get(1), 1e-6);
    }
}
