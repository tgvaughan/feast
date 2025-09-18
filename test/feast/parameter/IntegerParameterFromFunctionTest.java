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

import beast.base.inference.parameter.IntegerParameter;
import org.junit.Assert;
import org.junit.Test;

public class IntegerParameterFromFunctionTest {

    @Test
    public void test() {
        IntegerParameter originalParameter = new IntegerParameter("1 2 3");

        IntegerParameterFromFunction rpff = new IntegerParameterFromFunction();
        rpff.initByName("function", originalParameter);

        Assert.assertEquals(3, rpff.getDimension());
        Assert.assertEquals(originalParameter.getValue(0), rpff.getValue(0), 1e-10);
        Assert.assertEquals(originalParameter.getValue(1), rpff.getValue(1), 1e-10);
        Assert.assertEquals(originalParameter.getValue(2), rpff.getValue(2), 1e-10);
    }
}
