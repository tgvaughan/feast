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
import static org.junit.Assert.*;

/**
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class AlignmentFromFastaTest {
    
    public AlignmentFromFastaTest() {
    }

    @Test
    public void test() throws Exception {
    
        AlignmentFromFasta alignment = new AlignmentFromFasta();
        alignment.initByName(
                "fileName", "test/feast/fileio/test_alignment.fasta");

        assertEquals(4, alignment.getTaxonCount());
        assertEquals(20, alignment.getSiteCount());
        assertEquals(13, alignment.getPatternCount());
    }

    @Test
    public void testFiltered() throws Exception {

        AlignmentFromFasta alignment = new AlignmentFromFasta();
        alignment.initByName(
                "fileName", "test/feast/fileio/test_alignment.fasta",
                "endsWith", "e");

        assertEquals(2, alignment.getTaxonCount());
        assertEquals(20, alignment.getSiteCount());
        assertEquals(7, alignment.getPatternCount());
    }
}
