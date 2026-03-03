package feast.popmodels;

import beast.base.evolution.tree.coalescent.ConstantPopulation;
import beast.base.evolution.tree.coalescent.ExponentialGrowth;
import beast.base.inference.parameter.RealParameter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompoundPopulationModelTest {

    @Test
    public void test() {

        CompoundPopulationModel cpm = new CompoundPopulationModel();
        ConstantPopulation pf1 = new ConstantPopulation();
        pf1.initByName("popSize", new RealParameter("1.0"));
        ExponentialGrowth pf2 = new ExponentialGrowth();
        pf2.initByName("popSize", new RealParameter("1.0"),
                "growthRate", new RealParameter("1.0"));
        ConstantPopulation pf3 = new ConstantPopulation();
        pf3.initByName("popSize", new RealParameter("2.0"));

        cpm.initByName(
                "populationModel", pf1,
                "populationModel", pf2,
                "populationModel", pf3,
                "changeTimes", new RealParameter("3.0 5.0"),
                "makeContinuous", false);

        assertEquals(1.0, cpm.getPopSize(1), 1e-10);
        assertEquals(Math.exp(-1*1.0), cpm.getPopSize(4.0), 1e-10);
        assertEquals(2, cpm.getPopSize(5.5), 1e-10);

        assertEquals(1.0, cpm.getIntensity(1), 1e-10);
        assertEquals(3.0 + Math.exp(1.0)-1.0, cpm.getIntensity(4), 1e-10);
        assertEquals(3.0 + Math.exp(2.0)-1.0 + 0.5*0.5, cpm.getIntensity(5.5), 1e-10);

        assertEquals(6.0, cpm.getInverseIntensity(cpm.getIntensity(6)), 1e-10);
        assertEquals(4.0, cpm.getInverseIntensity(cpm.getIntensity(4)), 1e-10);
        assertEquals(0.0, cpm.getInverseIntensity(cpm.getIntensity(0)), 1e-10);
        assertEquals(-1.0, cpm.getInverseIntensity(cpm.getIntensity(-1)), 1e-10);
    }

    @Test
    public void testContinuous() {

        CompoundPopulationModel cpm = new CompoundPopulationModel();
        ConstantPopulation pf1 = new ConstantPopulation();
        pf1.initByName("popSize", new RealParameter("1.0"));
        ExponentialGrowth pf2 = new ExponentialGrowth();
        pf2.initByName("popSize", new RealParameter("1.0"),
                "growthRate", new RealParameter("1.0"));
        ConstantPopulation pf3 = new ConstantPopulation();
        pf3.initByName("popSize", new RealParameter("2.0"));

        cpm.initByName(
                "populationModel", pf1,
                "populationModel", pf2,
                "populationModel", pf3,
                "changeTimes", new RealParameter("3.0 5.0"),
                "makeContinuous", true);

        assertEquals(1.0, cpm.getPopSize(1), 1e-10);
        assertEquals(Math.exp(-1*1.0), cpm.getPopSize(4.0), 1e-10);
        assertEquals(Math.exp(-2*1.0), cpm.getPopSize(5.5), 1e-10);

        assertEquals(1.0, cpm.getIntensity(1), 1e-10);
        assertEquals(3.0 + Math.exp(1.0)-1.0, cpm.getIntensity(4), 1e-10);
        assertEquals(3.0 + Math.exp(2.0)-1.0 + (1.0/Math.exp(-2*1.0))*0.5,
                cpm.getIntensity(5.5), 1e-10);

        assertEquals(6.0, cpm.getInverseIntensity(cpm.getIntensity(6)), 1e-10);
        assertEquals(4.0, cpm.getInverseIntensity(cpm.getIntensity(4)), 1e-10);
        assertEquals(0.0, cpm.getInverseIntensity(cpm.getIntensity(0)), 1e-10);
        assertEquals(-1.0, cpm.getInverseIntensity(cpm.getIntensity(-1)), 1e-10);
    }
}
