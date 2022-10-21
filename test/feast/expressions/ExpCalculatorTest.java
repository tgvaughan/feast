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

package feast.expressions;

import static org.junit.Assert.*;

import beast.base.inference.parameter.RealParameter;
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
                "value", "-(J[0]/J + log(exp(I))*-3 - 1.5 + 1.5 + 1 + -3)",
                "arg", iparam,
                "arg", jparam);
        
        assertEquals(instance.getDimension(), 3);
        assertTrue(Math.abs(instance.getArrayValue(0)-4.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(1)-6.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(2)-10.0)<1e-15);
        
        instance.initByName(
                "value", "sum(I*2)",
                "arg", iparam);
        assertEquals(instance.getDimension(), 1);
        assertTrue(Math.abs(instance.getArrayValue(0)-12.0)<1e-15);
        
        instance.initByName(
                "value", "sqrt(sum(I*2)+4) + J",
                "arg", iparam,
                "arg", jparam);
        assertEquals(instance.getDimension(), 2);
        assertTrue(Math.abs(instance.getArrayValue(0)-31.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(1)-17.5)<1e-15);
        
        instance.initByName(
                "value", "1.5^I^2",
                "arg", iparam);
        assertEquals(instance.getDimension(), 3);
        assertTrue(Math.abs(instance.getArrayValue(0)-1.5)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(1)-5.0625)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(2)-38.443359375)<1e-15);
        
        instance.initByName(
                "value", "{I,J} + {1,2,3,4,5}",
                "arg", iparam);
        assertEquals(instance.getDimension(), 5);
        assertTrue(Math.abs(instance.getArrayValue(0)-2.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(1)-4.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(2)-6.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(3)-31.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(4)-18.5)<1e-15);

        instance.initByName(
                "value", "theta(I-2)",
                "arg", iparam);
        assertEquals(instance.getDimension(), 3);
        assertTrue(Math.abs(instance.getArrayValue(0)-0.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(1)-1.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(2)-1.0)<1e-15);

        instance.initByName(
                "value", "I>2",
                "arg", iparam);
        assertEquals(instance.getDimension(), 3);
        assertTrue(Math.abs(instance.getArrayValue(0)-0.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(1)-0.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(2)-1.0)<1e-15);

        instance.initByName(
                "value", "I>=2 ? 42.0 : 53.0",
                "arg", iparam);
        assertEquals(instance.getDimension(), 3);
        assertTrue(Math.abs(instance.getArrayValue(0)-53.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(1)-42.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(2)-42.0)<1e-15);

    }

    @Test
    public void testUnaryOps() {

        ExpCalculator instance = new ExpCalculator();
        RealParameter iparam = new RealParameter("1.0 2.0 3.0");
        iparam.setID("I");

        instance.initByName(
                "value", "{min(I),max(I),len(I),{1,2}[1]}",
                "arg", iparam);
        assertEquals(instance.getDimension(), 4);
        assertTrue(Math.abs(instance.getArrayValue(0)-1.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(1)-3.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(2)-3.0)<1e-15);
        assertTrue(Math.abs(instance.getArrayValue(3)-2.0)<1e-15);

        instance.initByName(
                "value", "sort({4,2.5,3,0})"
        );

        assertEquals(4, instance.getDimension());
        assertEquals(0.0, instance.getArrayValue(0), 1e-15);
        assertEquals(2.5, instance.getArrayValue(1), 1e-15);
        assertEquals(3.0, instance.getArrayValue(2), 1e-15);
        assertEquals(4.0, instance.getArrayValue(3), 1e-15);

        instance.initByName( "value", "cumsum({1,3,4,4})");
        // Expected output: 1, 4, 8, 12

        assertEquals(4, instance.getDimension());
        assertEquals(1, instance.getArrayValue(0), 1e-15);
        assertEquals(4, instance.getArrayValue(1), 1e-15);
        assertEquals(8, instance.getArrayValue(2), 1e-15);
        assertEquals(12, instance.getArrayValue(3), 1e-15);

        instance.initByName( "value", "diff({1,3,4,4})");
        // Expected output: 1, 4, 8, 12

        assertEquals(3, instance.getDimension());
        assertEquals(2, instance.getArrayValue(0), 1e-15);
        assertEquals(1, instance.getArrayValue(1), 1e-15);
        assertEquals(0, instance.getArrayValue(2), 1e-15);
    }

    @Test
    public void testIndexing() {

        ExpCalculator instance = new ExpCalculator();
        instance.initByName("value", "0[1:5]");

        assertEquals(instance.getDimension(), 5);
        assertEquals(0.0, instance.getArrayValue(0), 1e-15);
        assertEquals(0.0, instance.getArrayValue(1), 1e-15);
        assertEquals(0.0, instance.getArrayValue(2), 1e-15);
        assertEquals(0.0, instance.getArrayValue(3), 1e-15);
        assertEquals(0.0, instance.getArrayValue(4), 1e-15);

        instance.initByName("value", "1:5");

        assertEquals(instance.getDimension(), 5);
        assertEquals(1.0, instance.getArrayValue(0), 1e-15);
        assertEquals(2.0, instance.getArrayValue(1), 1e-15);
        assertEquals(3.0, instance.getArrayValue(2), 1e-15);
        assertEquals(4.0, instance.getArrayValue(3), 1e-15);
        assertEquals(5.0, instance.getArrayValue(4), 1e-15);

        instance.initByName("value", "5:1");

        assertEquals(instance.getDimension(), 5);
        assertEquals(5.0, instance.getArrayValue(0), 1e-15);
        assertEquals(4.0, instance.getArrayValue(1), 1e-15);
        assertEquals(3.0, instance.getArrayValue(2), 1e-15);
        assertEquals(2.0, instance.getArrayValue(3), 1e-15);
        assertEquals(1.0, instance.getArrayValue(4), 1e-15);

        instance.initByName("value", "(11:15)[4:2]");

        assertEquals(instance.getDimension(), 3);
        assertEquals(15.0, instance.getArrayValue(0), 1e-15);
        assertEquals(14.0, instance.getArrayValue(1), 1e-15);
        assertEquals(13.0, instance.getArrayValue(2), 1e-15);

        instance.log(0L, System.out);
    }
}
