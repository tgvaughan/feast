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

import beast.base.spec.domain.Real;
import beast.base.spec.inference.parameter.RealVectorParam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RealVectorParamFromRealVectorTest {

    @Test
    public void test() {
        RealVectorParam<Real> originalParameter = new RealVectorParam<>(new double[] {1.2,2.3,3.4}, Real.INSTANCE);

        RealVectorParamFromRealVector newParameter = new RealVectorParamFromRealVector();
        newParameter.initByName("realVector", originalParameter);

        assertEquals(3, newParameter.size(), 1e-10);
        assertEquals(originalParameter.get(0), newParameter.get(0), 1e-10);
        assertEquals(originalParameter.get(1), newParameter.get(1), 1e-10);
        assertEquals(originalParameter.get(2), newParameter.get(2), 1e-10);
    }
}
