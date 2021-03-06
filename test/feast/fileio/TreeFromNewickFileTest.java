/*
 * Copyright (C) 2015 Tim Vaughan <tgvaughan@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package feast.fileio;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class TreeFromNewickFileTest {

    public TreeFromNewickFileTest() {
    }

    @Test
    public void test() throws Exception {
    
        TreeFromNewickFile tree = new TreeFromNewickFile();
        tree.initByName(
                "fileName", "test/feast/fileio/test_trees.newick",
                "IsLabelledNewick", true,
                "adjustTipHeights", false);

        assertEquals(12, tree.getLeafNodeCount());
        assertEquals(23, tree.getNodeCount());
    }

    @Test
    public void testTreeIndex() {
        TreeFromNewickFile tree = new TreeFromNewickFile();
        tree.initByName(
                "fileName", "test/feast/fileio/test_trees.newick",
                "treeIndex", 1,
                "IsLabelledNewick", true,
                "adjustTipHeights", false);

        assertEquals(3, tree.getLeafNodeCount());
        assertEquals(5, tree.getNodeCount());
    }

    @Test(expected = RuntimeException.class)
    public void testTreeIndexOOB() {
        TreeFromNewickFile tree = new TreeFromNewickFile();
        tree.initByName(
                "fileName", "test/feast/fileio/test_trees.newick",
                "treeIndex", 2,
                "IsLabelledNewick", true,
                "adjustTipHeights", false);
    }
}
