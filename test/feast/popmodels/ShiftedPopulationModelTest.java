package feast.popmodels;

import beast.core.parameter.RealParameter;
import beast.evolution.tree.coalescent.ExponentialGrowth;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShiftedPopulationModelTest {

    @Test
    public void test() {
        ExponentialGrowth egm = new ExponentialGrowth();
        egm.initByName("popSize", new RealParameter("1.0"),
                "growthRate", new RealParameter("1.0"));
        ShiftedPopulationModel spm = new ShiftedPopulationModel();
        spm.initByName("populationModel", egm,
                "offset", new RealParameter("0.5"));

        assertEquals(egm.getPopSize(0.5), spm.getPopSize(0), 1e-10);
        assertEquals(egm.getPopSize(0), spm.getPopSize(-0.5), 1e-10);

        assertEquals(egm.getIntensity(0.5), spm.getIntensity(0), 1e-10);
        assertEquals(egm.getIntensity(0), spm.getIntensity(-0.5), 1e-10);
    }
}
