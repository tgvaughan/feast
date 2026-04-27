package feast.fileio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RealVectorParamFromXSVTest {

    @Test
    public void testReadAll() {

        RealVectorParamFromXSV param = new RealVectorParamFromXSV();
        param.initByName(
                "fileName", "src/test/java/feast/fileio/test_csv.csv",
                "startRow", 0,
                "startCol", 0,
                "sep", ","
        );

        assertEquals(20, param.size());
        assertEquals(11.0, param.get(0), 1e-10);
        assertEquals(14.0, param.get(3), 1e-10);
        assertEquals(54.0, param.get(19), 1e-10);
    }

    @Test
    public void testReadRow() {

        RealVectorParamFromXSV param = new RealVectorParamFromXSV();
        param.initByName(
                "fileName", "src/test/java/feast/fileio/test_csv.csv",
                "startRow", 3,
                "rowCount", 1,
                "startCol", 0,
                "sep", ","
        );

        assertEquals(4, param.size());
        assertEquals(41, param.get(0), 1e-10);
        assertEquals(42, param.get(1), 1e-10);
        assertEquals(43, param.get(2), 1e-10);
        assertEquals(44, param.get(3), 1e-10);
    }

    @Test
    public void testReadCol() {

        RealVectorParamFromXSV param = new RealVectorParamFromXSV();
        param.initByName(
                "fileName", "src/test/java/feast/fileio/test_csv.csv",
                "startRow", 0,
                "startCol", 2,
                "colCount", 1,
                "sep", ","
        );

        assertEquals(5, param.size());
        assertEquals(13, param.get(0), 1e-10);
        assertEquals(23, param.get(1), 1e-10);
        assertEquals(33, param.get(2), 1e-10);
        assertEquals(43, param.get(3), 1e-10);
        assertEquals(53, param.get(4), 1e-10);
    }
}
