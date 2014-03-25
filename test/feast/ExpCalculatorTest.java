/*
 * Copyright (C) 2014 Tim Vaughan <tgvaughan@gmail.com>.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301  USA
 */

package feast;

import beast.core.parameter.RealParameter;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class ExpCalculatorTest {
    
    @Test
    public void test() throws Exception {
        RealParameter iparam = new RealParameter("1.0 2.0 3.0");
        iparam.setID("I");

        RealParameter jparam = new RealParameter("27.0 13.5");
        jparam.setID("J");

        
        ExpCalculator instance = new ExpCalculator();
        instance.initByName(
                "expression", "-(J[0]/J + log(exp(I))*-3 - 1.5 + 1.5 + 1 + -3)",
                "parameter", iparam,
                "parameter", jparam);
        
        assertEquals(instance.getDimension(), 3);
        assertTrue(Math.abs(instance.getArrayValue(0)-4.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(1)-6.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(2)-10.0)<1e-15);
        
        instance.initByName(
                "expression", "sum(I*2)",
                "parameter", iparam);
        assertEquals(instance.getDimension(), 1);
        assertTrue(Math.abs(instance.getArrayValue(0)-12.0)<1e-15);
        
        instance.initByName(
                "expression", "sqrt(sum(I*2)+4) + J",
                "parameter", iparam,
                "parameter", jparam);
        assertEquals(instance.getDimension(), 2);
        assertTrue(Math.abs(instance.getArrayValue(0)-31.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(1)-17.5)<1e-15);
        
        instance.initByName(
                "expression", "1.5^I^2",
                "parameter", iparam);
        assertEquals(instance.getDimension(), 3);
        assertTrue(Math.abs(instance.getArrayValue(0)-1.5)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(1)-5.0625)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(2)-38.443359375)<1e-15);

    }
}
