package feast.realvector;

import beast.base.evolution.tree.TreeParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleAgesTest {

    @Test
    public void test() {

        TreeParser tree = new TreeParser(
                "((A:1,B:2):0.5,(C:0.3,D:1.4):2.3):0.0;",
                false);

        SampleAges sampleAges = new SampleAges();
        sampleAges.initByName("tree", tree);

        assertEquals(4, sampleAges.size());
        assertEquals(2.2, sampleAges.get(0), 1e-10);
        assertEquals(1.2, sampleAges.get(1), 1e-10);
        assertEquals(1.1, sampleAges.get(2), 1e-10);
        assertEquals(0.0, sampleAges.get(3), 1e-10);
    }
}
