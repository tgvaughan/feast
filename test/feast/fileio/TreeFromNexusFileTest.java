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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class TreeFromNexusFileTest {

    public TreeFromNexusFileTest() {
    }

    @Test
    public void test() throws Exception {
    
        TreeFromNexusFile tree = new TreeFromNexusFile();
        tree.initByName(
                "fileName", "test/feast/fileio/test_tree.nexus",
                "IsLabelledNewick", true);

        assertEquals(12, tree.getLeafNodeCount());
        assertEquals(23, tree.getNodeCount());

        List<String> taxonNames = Arrays.asList(tree.getTaxaNames());
        assertTrue(taxonNames.contains("pig"));
        assertTrue(taxonNames.contains("cow"));
    }
}
