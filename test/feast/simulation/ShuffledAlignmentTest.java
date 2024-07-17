/*
 * Copyright (c) 2023 Tim Vaughan
 *
 * This file is part of feast.
 *
 * feast is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or (at
 * your option) any later version.
 *
 * feast is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with feast. If not, see <https://www.gnu.org/licenses/>.
 */

package feast.simulation;

import beast.base.util.Randomizer;
import feast.fileio.AlignmentFromFasta;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShuffledAlignmentTest {

    @Test
    public void test() {
        Randomizer.setSeed(42);

        AlignmentFromFasta alignment = new AlignmentFromFasta();
        alignment.initByName(
                "fileName", "test/feast/fileio/test_alignment.fasta");

        ShuffledAlignment shuffledAlignment = new ShuffledAlignment();
        shuffledAlignment.initByName("alignment", alignment);

        assertEquals(alignment.sequenceInput.get().get(0).getTaxon(),
                shuffledAlignment.sequenceInput.get().get(0).getTaxon());
        assertEquals(alignment.sequenceInput.get().get(1).getTaxon(),
                shuffledAlignment.sequenceInput.get().get(1).getTaxon());
        assertEquals(alignment.sequenceInput.get().get(2).getTaxon(),
                shuffledAlignment.sequenceInput.get().get(2).getTaxon());
        assertEquals(alignment.sequenceInput.get().get(3).getTaxon(),
                shuffledAlignment.sequenceInput.get().get(3).getTaxon());

        // Valid only for seed above
        assertEquals(alignment.sequenceInput.get().get(0).getData(),
                shuffledAlignment.sequenceInput.get().get(2).getData());
        assertEquals(alignment.sequenceInput.get().get(1).getData(),
                shuffledAlignment.sequenceInput.get().get(1).getData());
        assertEquals(alignment.sequenceInput.get().get(2).getData(),
                shuffledAlignment.sequenceInput.get().get(3).getData());
        assertEquals(alignment.sequenceInput.get().get(3).getData(),
                shuffledAlignment.sequenceInput.get().get(0).getData());
    }
}
