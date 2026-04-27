/*
 * Copyright (c) 2025 ETH Zürich
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

package feast.parameter;

import beast.base.spec.domain.Int;
import beast.base.spec.inference.parameter.IntVectorParam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntVectorParamFromIntVectorTest {

    @Test
    public void test() {
        IntVectorParam<Int> originalParameter = new IntVectorParam<>(new int[] {1,2,3}, Int.INSTANCE);

        IntVectorParamFromIntVector newParameter = new IntVectorParamFromIntVector();
        newParameter.initByName("intVector", originalParameter);

        assertEquals(3, newParameter.size());
        assertEquals(originalParameter.get(0), newParameter.get(0), 1e-10);
        assertEquals(originalParameter.get(1), newParameter.get(1), 1e-10);
        assertEquals(originalParameter.get(2), newParameter.get(2), 1e-10);
    }
}
