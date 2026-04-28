package feast.realvector;

import beast.base.evolution.tree.TreeParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeTipAgesTest {

    @Test
    public void test() {

        TreeParser tree = new TreeParser(
                "((A:1,B:2):0.5,(C:0.3,D:1.4):2.3):0.0;",
                false);

        TreeTipAges treeTipAges = new TreeTipAges();
        treeTipAges.initByName("tree", tree);

        assertEquals(4, treeTipAges.size());
        assertEquals(2.2, treeTipAges.get(0), 1e-10);
        assertEquals(1.2, treeTipAges.get(1), 1e-10);
        assertEquals(1.1, treeTipAges.get(2), 1e-10);
        assertEquals(0.0, treeTipAges.get(3), 1e-10);
    }
}
