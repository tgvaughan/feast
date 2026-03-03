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

import beast.base.inference.parameter.RealParameter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RealParameterFromFunctionTest {

    @Test
    public void test() {
        RealParameter originalParameter = new RealParameter("1.2 2.3 3.4");

        RealParameterFromFunction rpff = new RealParameterFromFunction();
        rpff.initByName("function", originalParameter);

        assertEquals(3, rpff.getDimension(), 1e-10);
        assertEquals(originalParameter.getValue(0), rpff.getValue(0), 1e-10);
        assertEquals(originalParameter.getValue(1), rpff.getValue(1), 1e-10);
        assertEquals(originalParameter.getValue(2), rpff.getValue(2), 1e-10);
    }
}
