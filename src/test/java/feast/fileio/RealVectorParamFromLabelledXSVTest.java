package feast.fileio;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RealVectorParamFromLabelledXSVTest {

    @Test
    public void testReadElement() {

        RealVectorParamFromLabelledXSV param = new RealVectorParamFromLabelledXSV();
        param.initByName(
                "fileName", "src/test/java/feast/fileio/test_labelled_csv.csv",
                "rowLabels", "row_1",
                "colLabels", "col_3",
                "sep", ","
        );

        assertEquals(1, param.size());
        assertEquals(13, param.get(0), 1e-10);
    }

    @Test
    public void testReadColumn() {

        RealVectorParamFromLabelledXSV param = new RealVectorParamFromLabelledXSV();
        param.initByName(
                "fileName", "src/test/java/feast/fileio/test_labelled_csv2.csv",
                "colLabels", "col_3",
                "sep", ","
        );

        assertEquals(5, param.size());
        assertEquals(13, param.get(0), 1e-10);
        assertEquals(23, param.get(1), 1e-10);
        assertEquals(33, param.get(2), 1e-10);
        assertEquals(43, param.get(3), 1e-10);
        assertEquals(53, param.get(4), 1e-10);
    }

    @Test
    public void testReadRow() {

        RealVectorParamFromLabelledXSV param = new RealVectorParamFromLabelledXSV();
        param.initByName(
                "fileName", "src/test/java/feast/fileio/test_labelled_csv3.csv",
                "rowLabels", "row_2",
                "sep", ","
        );

        assertEquals(4, param.size());
        assertEquals(21, param.get(0), 1e-10);
        assertEquals(22, param.get(1), 1e-10);
        assertEquals(23, param.get(2), 1e-10);
        assertEquals(24, param.get(3), 1e-10);
    }
}
