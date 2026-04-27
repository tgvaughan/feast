package feast.popmodels;

import beast.base.spec.domain.Real;
import beast.base.spec.evolution.tree.coalescent.ConstantPopulation;
import beast.base.spec.evolution.tree.coalescent.ExponentialGrowth;
import beast.base.spec.inference.parameter.RealScalarParam;
import beast.base.spec.inference.parameter.RealVectorParam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompoundPopulationModelTest {

    @Test
    public void test() {

        CompoundPopulationModel cpm = new CompoundPopulationModel();
        ConstantPopulation pf1 = new ConstantPopulation();
        pf1.initByName("popSize", new RealScalarParam<>(1.0, Real.INSTANCE));
        ExponentialGrowth pf2 = new ExponentialGrowth();
        pf2.initByName("popSize", new RealScalarParam<>(1.0, Real.INSTANCE),
                "growthRate", new RealScalarParam<>(1.0, Real.INSTANCE));
        ConstantPopulation pf3 = new ConstantPopulation();
        pf3.initByName("popSize", new RealScalarParam<>(2.0, Real.INSTANCE));

        cpm.initByName(
                "populationModel", pf1,
                "populationModel", pf2,
                "populationModel", pf3,
                "changeTimes", new RealVectorParam<>(new double[] {3.0,5.0}, Real.INSTANCE),
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
        pf1.initByName("popSize", new RealScalarParam<>(1.0, Real.INSTANCE));
        ExponentialGrowth pf2 = new ExponentialGrowth();
        pf2.initByName("popSize", new RealScalarParam<>(1.0, Real.INSTANCE),
                "growthRate", new RealScalarParam<>(1.0, Real.INSTANCE));
        ConstantPopulation pf3 = new ConstantPopulation();
        pf3.initByName("popSize", new RealScalarParam<>(2.0, Real.INSTANCE));

        cpm.initByName(
                "populationModel", pf1,
                "populationModel", pf2,
                "populationModel", pf3,
                "changeTimes", new RealVectorParam<>(new double[] {3.0,5.0}, Real.INSTANCE),
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
