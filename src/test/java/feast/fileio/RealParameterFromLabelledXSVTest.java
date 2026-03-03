package feast.fileio;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RealParameterFromLabelledXSVTest {

    @Test
    public void testReadElement() {

        RealParameterFromLabelledXSV rpflXSV = new RealParameterFromLabelledXSV();
        rpflXSV.initByName(
                "fileName", "test/feast/fileio/test_labelled_csv.csv",
                "rowLabels", "row_1",
                "colLabels", "col_3",
                "sep", ","
        );

        assertEquals(1, rpflXSV.getDimension());
        assertEquals(13, rpflXSV.getValue(0), 1e-10);
    }

    @Test
    public void testReadColumn() {

        RealParameterFromLabelledXSV rpflXSV = new RealParameterFromLabelledXSV();
        rpflXSV.initByName(
                "fileName", "test/feast/fileio/test_labelled_csv2.csv",
                "colLabels", "col_3",
                "sep", ","
        );

        assertEquals(5, rpflXSV.getDimension());
        assertEquals(13, rpflXSV.getValue(0), 1e-10);
        assertEquals(23, rpflXSV.getValue(1), 1e-10);
        assertEquals(33, rpflXSV.getValue(2), 1e-10);
        assertEquals(43, rpflXSV.getValue(3), 1e-10);
        assertEquals(53, rpflXSV.getValue(4), 1e-10);
    }

    @Test
    public void testReadRow() {

        RealParameterFromLabelledXSV rpflXSV = new RealParameterFromLabelledXSV();
        rpflXSV.initByName(
                "fileName", "test/feast/fileio/test_labelled_csv3.csv",
                "rowLabels", "row_2",
                "sep", ","
        );

        assertEquals(4, rpflXSV.getDimension());
        assertEquals(21, rpflXSV.getValue(0), 1e-10);
        assertEquals(22, rpflXSV.getValue(1), 1e-10);
        assertEquals(23, rpflXSV.getValue(2), 1e-10);
        assertEquals(24, rpflXSV.getValue(3), 1e-10);
    }
}
