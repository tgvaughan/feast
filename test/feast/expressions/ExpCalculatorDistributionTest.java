/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package feast.expressions;

import beast.base.inference.parameter.RealParameter;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * JUnit test for feast.ExpCalculatorDistribution
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class ExpCalculatorDistributionTest {
    
    @Test
    public void test() throws Exception {
        RealParameter xparam = new RealParameter("10.0 20.0");
        xparam.setID("x");
        
        ExpCalculatorDistribution instance = new ExpCalculatorDistribution();
        instance.initByName(
                "value", "exp(-sum((x-{11,19})^2)/2)",
                "arg", xparam);
        
        assertTrue(Math.abs(instance.calculateLogP()- (-1.0))<1e-15);
    }
}

