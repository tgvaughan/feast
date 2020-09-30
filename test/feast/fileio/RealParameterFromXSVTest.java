package feast.fileio;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RealParameterFromXSVTest {

    @Test
    public void testReadAll() {

        RealParameterFromXSV rpfXSV = new RealParameterFromXSV();
        rpfXSV.initByName(
                "fileName", "test/feast/fileio/test_csv.csv",
                "startRow", 0,
                "startCol", 0,
                "sep", ","
        );

        assertEquals(20, rpfXSV.getDimension());
        assertEquals(11.0, rpfXSV.getValue(0), 1e-10);
        assertEquals(14.0, rpfXSV.getValue(3), 1e-10);
        assertEquals(54.0, rpfXSV.getValue(19), 1e-10);
    }

    @Test
    public void testReadRow() {

        RealParameterFromXSV rpfXSV = new RealParameterFromXSV();
        rpfXSV.initByName(
                "fileName", "test/feast/fileio/test_csv.csv",
                "startRow", 3,
                "rowCount", 1,
                "startCol", 0,
                "sep", ","
        );

        assertEquals(4, rpfXSV.getDimension());
        assertEquals(41, rpfXSV.getValue(0), 1e-10);
        assertEquals(42, rpfXSV.getValue(1), 1e-10);
        assertEquals(43, rpfXSV.getValue(2), 1e-10);
        assertEquals(44, rpfXSV.getValue(3), 1e-10);
    }

    @Test
    public void testReadCol() {

        RealParameterFromXSV rpfXSV = new RealParameterFromXSV();
        rpfXSV.initByName(
                "fileName", "test/feast/fileio/test_csv.csv",
                "startRow", 0,
                "startCol", 2,
                "colCount", 1,
                "sep", ","
        );

        assertEquals(5, rpfXSV.getDimension());
        assertEquals(13, rpfXSV.getValue(0), 1e-10);
        assertEquals(23, rpfXSV.getValue(1), 1e-10);
        assertEquals(33, rpfXSV.getValue(2), 1e-10);
        assertEquals(43, rpfXSV.getValue(3), 1e-10);
        assertEquals(53, rpfXSV.getValue(4), 1e-10);
    }
}
