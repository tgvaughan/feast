/*
 * Copyright (c) 2023 ETH Zurich
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
import beast.base.spec.inference.distribution.Uniform;
import beast.base.spec.inference.parameter.RealVectorParam;
import beast.base.util.DiscreteStatistics;
import beast.base.util.Randomizer;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomRealVectorParamTest {

    @Test
    public void testSelf() {
        Randomizer.setSeed(53);

        Uniform distr = new Uniform();
        distr.initByName("lower", "5", "upper", "10");

        RandomRealVectorParam randomRealVectorParam = new RandomRealVectorParam();
        randomRealVectorParam.initByName("dimension", "100000", "value", "0",
                "distr", distr);

        List<Double> sampList = randomRealVectorParam.getElements();
        double[] sampArray = new double[sampList.size()];
        for (int i=0; i<sampArray.length; i++)
            sampArray[i] = sampList.get(i);

        assertEquals(7.5, DiscreteStatistics.mean(sampArray), 0.01);
        assertEquals(25.0/12.0, DiscreteStatistics.variance(sampArray), 0.01);

    }

    @Test
    public void testOther() {
        Randomizer.setSeed(53);

        Uniform distr = new Uniform();
        distr.initByName("lower", "5", "upper", "10");

        RealVectorParam<Real> paramToInitialise = new RealVectorParam<>();
        paramToInitialise.initByName("dimension", "100000", "value", "1.0");

        RandomRealVectorParam randomRealVectorParam = new RandomRealVectorParam();
        randomRealVectorParam.initByName("initial",  paramToInitialise,
                "distr", distr);

        List<Double> sampList = paramToInitialise.getElements();
        double[] sampArray = new double[sampList.size()];
        for (int i=0; i<sampArray.length; i++)
            sampArray[i] = sampList.get(i);

        assertEquals(7.5, DiscreteStatistics.mean(sampArray), 0.01);
        assertEquals(25.0/12.0, DiscreteStatistics.variance(sampArray), 0.01);

    }
}
