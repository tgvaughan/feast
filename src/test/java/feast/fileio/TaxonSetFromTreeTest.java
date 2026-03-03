package feast.fileio;

import beast.base.evolution.alignment.TaxonSet;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaxonSetFromTreeTest {

    @Test
    public void test() {
        TreeFromNewickFile tree = new TreeFromNewickFile();
        tree.initByName(
                "fileName", "test/feast/fileio/test_trees.newick",
                "IsLabelledNewick", true,
                "adjustTipHeights", false);

        TaxonSet taxonSet = new TaxonSetFromTree();
        taxonSet.initByName("tree", tree);

        assertEquals(12, taxonSet.getTaxonCount());
        assertTrue(taxonSet.getTaxaNames().contains("t0"));
        assertTrue(taxonSet.getTaxaNames().contains("t1"));
        assertTrue(taxonSet.getTaxaNames().contains("t2"));
        assertTrue(taxonSet.getTaxaNames().contains("t3"));
        assertTrue(taxonSet.getTaxaNames().contains("t4"));
        assertTrue(taxonSet.getTaxaNames().contains("t5"));
        assertTrue(taxonSet.getTaxaNames().contains("t6"));
        assertTrue(taxonSet.getTaxaNames().contains("t7"));
        assertTrue(taxonSet.getTaxaNames().contains("t8"));
        assertTrue(taxonSet.getTaxaNames().contains("t9"));
        assertTrue(taxonSet.getTaxaNames().contains("t10"));
        assertTrue(taxonSet.getTaxaNames().contains("t11"));

        assertFalse(taxonSet.getTaxaNames().contains("t12"));
    }
}
