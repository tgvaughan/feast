package feast.fileio;

import beast.base.evolution.alignment.TaxonSet;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TraitSetFromXSVTest {

    @Test
    public void test() throws Exception {

        AlignmentFromFasta alignment = new AlignmentFromFasta();
        alignment.initByName(
                "fileName", "test/feast/fileio/test_alignment.fasta");

        TraitSetFromXSV traitSet = new TraitSetFromXSV();
        traitSet.initByName("taxa", new TaxonSet(alignment),
                "fileName", "test/feast/fileio/test_traitset.csv",
                "skipFirstRow", true,
                "sep", ",",
                "traitname", "date",
                "dateFormat", "yyyy-M-dd");

        assertEquals(0.6612021857922628,
                traitSet.getValue("fish"), 1e-10);
        assertEquals(0.6420765027321522,
                traitSet.getValue("frog"), 1e-10);
        assertEquals(0.0,
                traitSet.getValue("snake"), 1e-10);
        assertEquals(0.0,
                traitSet.getValue("mouse"), 1e-10);
    }
}
