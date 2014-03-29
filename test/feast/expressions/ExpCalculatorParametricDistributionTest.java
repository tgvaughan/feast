/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package feast.expressions;

import beast.core.parameter.RealParameter;
import beast.math.distributions.Prior;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit test for feast.ExpCalculatorParametricDistribution
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class ExpCalculatorParametricDistributionTest {
    
    @Test
    public void test() throws Exception {
        RealParameter xparam = new RealParameter("10.0");
        xparam.setLower(Double.NEGATIVE_INFINITY);
        xparam.setUpper(Double.POSITIVE_INFINITY);
        xparam.setID("x");
        
        ExpCalculatorParametricDistribution instance = new ExpCalculatorParametricDistribution();
        instance.initByName(
                "expression", "exp(-(x-11)^2/2)");
        
        Prior prior = new Prior();
        prior.initByName("x", xparam, "distr", instance);
     
        double res = prior.calculateLogP();
        assertTrue(Math.abs(res - (-0.5))<1e-15);
    }
}
