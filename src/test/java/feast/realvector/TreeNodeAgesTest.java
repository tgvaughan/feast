package feast.realvector;

import beast.base.evolution.tree.TreeParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TreeNodeAgesTest {

    @Test
    public void test() {

        TreeParser tree = new TreeParser(
                "((A:1,B:2):0.5,(C:0.3,D:1.4):2.3):0.0;",
                false);

        TreeNodeAges treeNodeAges = new TreeNodeAges();
        treeNodeAges.initByName("tree", tree);

        assertEquals(7, treeNodeAges.size());
        assertEquals(2.2, treeNodeAges.get(0), 1e-10);
        assertEquals(1.2, treeNodeAges.get(1), 1e-10);
        assertEquals(1.1, treeNodeAges.get(2), 1e-10);
        assertEquals(0.0, treeNodeAges.get(3), 1e-10);
        assertEquals(3.2, treeNodeAges.get(4), 1e-10);
        assertEquals(1.4, treeNodeAges.get(5), 1e-10);
        assertEquals(3.7, treeNodeAges.get(6), 1e-10);
    }
}
