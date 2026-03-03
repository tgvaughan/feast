package feast.popmodels;

import beast.base.evolution.tree.coalescent.ConstantPopulation;
import beast.base.evolution.tree.coalescent.ExponentialGrowth;
import beast.base.inference.parameter.RealParameter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExpressionPopulationModelTest {

    @Test
    public void testPopSize() {

        RealParameter arg = new RealParameter("1.0");
        arg.setID("alpha");

        ExpressionPopulationModel epm = new ExpressionPopulationModel();
        epm.initByName(
                "value", "1/(t+alpha)",
                "isLog", false,
                "arg", arg);

        assertEquals(1.0, epm.getPopSize(0), 1e-10);
        assertEquals(0.5, epm.getPopSize(1), 1e-10);
        assertEquals(0.1, epm.getPopSize(9), 1e-10);

        epm.setInputValue("isLog", true);
        epm.initAndValidate();

        assertEquals(1.0, Math.log(epm.getPopSize(0)), 1e-10);
        assertEquals(0.5, Math.log(epm.getPopSize(1)), 1e-10);
        assertEquals(0.1, Math.log(epm.getPopSize(9)), 1e-10);

    }

    @Test
    public void testIntensity() {

        RealParameter arg = new RealParameter("1.0");
        arg.setID("alpha");

        ExpressionPopulationModel epm = new ExpressionPopulationModel();
        epm.initByName(
                "value", "1/(t+alpha)",
                "isLog", false,
                "arg", arg);

        // intensity(t) = 0.5*T^2 + alpha*T

        assertEquals(0.0, epm.getIntensity(0), 1e-10);
        assertEquals(1.5, epm.getIntensity(1), 1e-10);
        assertEquals(49.5, epm.getIntensity(9), 1e-10);
    }
}
