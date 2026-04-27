package feast.popmodels;

import beast.base.spec.domain.Real;
import beast.base.spec.evolution.tree.coalescent.ExponentialGrowth;
import beast.base.spec.inference.parameter.RealScalarParam;
import beast.base.spec.inference.parameter.RealVectorParam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShiftedPopulationModelTest {

    @Test
    public void test() {
        ExponentialGrowth egm = new ExponentialGrowth();
        egm.initByName("popSize", new RealScalarParam<>(1.0, Real.INSTANCE),
                "growthRate", new RealScalarParam<>(1.0, Real.INSTANCE));
        ShiftedPopulationModel spm = new ShiftedPopulationModel();
        spm.initByName("populationModel", egm,
                "offset", new RealScalarParam<>(0.5, Real.INSTANCE));

        assertEquals(egm.getPopSize(0.5), spm.getPopSize(0), 1e-10);
        assertEquals(egm.getPopSize(0), spm.getPopSize(-0.5), 1e-10);

        assertEquals(egm.getIntensity(2.5) - egm.getIntensity(0.5), spm.getIntensity(2), 1e-10);
        assertEquals(1, spm.getInverseIntensity(spm.getIntensity(1)), 1e-10);
    }
}
