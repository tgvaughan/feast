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


import beast.base.inference.distribution.ParametricDistribution;
import beast.base.inference.distribution.Uniform;
import beast.base.inference.parameter.RealParameter;
import beast.base.util.DiscreteStatistics;
import beast.base.util.Randomizer;
import org.junit.Assert;
import org.junit.Test;

public class RandomRealParameterTest {

    @Test
    public void testSelf() {
        Randomizer.setSeed(53);

        ParametricDistribution distr = new Uniform();
        distr.initByName("lower", "5", "upper", "10");

        RandomRealParameter randomRealParameter = new RandomRealParameter();
        randomRealParameter.initByName("dimension", "100000", "value", "0",
                "distr", distr);

        Assert.assertEquals(7.5, DiscreteStatistics.mean(randomRealParameter.getDoubleValues()), 0.01);
        Assert.assertEquals(25.0/12.0, DiscreteStatistics.variance(randomRealParameter.getDoubleValues()), 0.01);

    }

    @Test
    public void testOther() {
        Randomizer.setSeed(53);

        ParametricDistribution distr = new Uniform();
        distr.initByName("lower", "5", "upper", "10");

        RealParameter paramToInitialise = new RealParameter();
        paramToInitialise.initByName("dimension", "100000", "value", "1.0");

        RandomRealParameter randomRealParameter = new RandomRealParameter();
        randomRealParameter.initByName("initial",  paramToInitialise,
                "distr", distr);

        Assert.assertEquals(7.5, DiscreteStatistics.mean(paramToInitialise.getDoubleValues()), 0.01);
        Assert.assertEquals(25.0/12.0, DiscreteStatistics.variance(paramToInitialise.getDoubleValues()), 0.01);

    }
}
