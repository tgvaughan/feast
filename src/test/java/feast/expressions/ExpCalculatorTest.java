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

import beast.base.spec.domain.Real;
import beast.base.spec.inference.parameter.RealVectorParam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class ExpCalculatorTest {
    
    @Test
    public void test() throws Exception {
        RealVectorParam<Real> iparam = new RealVectorParam<>(new double[] {1.0,2.0,3.0}, Real.INSTANCE);
        iparam.setID("I");

        RealVectorParam<Real> jparam = new RealVectorParam<>(new double[] {27.0,13.5}, Real.INSTANCE);
        jparam.setID("J");

        
        ExpCalculator<Real> instance = new ExpCalculator<>();
        instance.initByName(
                "value", "-(J[0]/J + log(exp(I))*-3 - 1.5 + 1.5 + 1 + -3)",
                "arg", iparam,
                "arg", jparam);
        
        assertEquals(instance.size(), 3);
        assertTrue(Math.abs(instance.get(0)-4.0)<1e-15);
        assertTrue(Math.abs(instance.get(1)-6.0)<1e-15);
        assertTrue(Math.abs(instance.get(2)-10.0)<1e-15);
        
        instance.initByName(
                "value", "sum(I*2)",
                "arg", iparam);
        assertEquals(instance.size(), 1);
        assertTrue(Math.abs(instance.get(0)-12.0)<1e-15);
        
        instance.initByName(
                "value", "sqrt(sum(I*2)+4) + J",
                "arg", iparam,
                "arg", jparam);
        assertEquals(instance.size(), 2);
        assertTrue(Math.abs(instance.get(0)-31.0)<1e-15);
        assertTrue(Math.abs(instance.get(1)-17.5)<1e-15);
        
        instance.initByName(
                "value", "1.5^I^2",
                "arg", iparam);
        assertEquals(instance.size(), 3);
        assertTrue(Math.abs(instance.get(0)-1.5)<1e-15);
        assertTrue(Math.abs(instance.get(1)-5.0625)<1e-15);
        assertTrue(Math.abs(instance.get(2)-38.443359375)<1e-15);
        
        instance.initByName(
                "value", "{I,J} + {1,2,3,4,5}",
                "arg", iparam);
        assertEquals(instance.size(), 5);
        assertTrue(Math.abs(instance.get(0)-2.0)<1e-15);
        assertTrue(Math.abs(instance.get(1)-4.0)<1e-15);
        assertTrue(Math.abs(instance.get(2)-6.0)<1e-15);
        assertTrue(Math.abs(instance.get(3)-31.0)<1e-15);
        assertTrue(Math.abs(instance.get(4)-18.5)<1e-15);

        instance.initByName(
                "value", "theta(I-2)",
                "arg", iparam);
        assertEquals(instance.size(), 3);
        assertTrue(Math.abs(instance.get(0)-0.0)<1e-15);
        assertTrue(Math.abs(instance.get(1)-1.0)<1e-15);
        assertTrue(Math.abs(instance.get(2)-1.0)<1e-15);

        instance.initByName(
                "value", "I>2",
                "arg", iparam);
        assertEquals(instance.size(), 3);
        assertTrue(Math.abs(instance.get(0)-0.0)<1e-15);
        assertTrue(Math.abs(instance.get(1)-0.0)<1e-15);
        assertTrue(Math.abs(instance.get(2)-1.0)<1e-15);

        instance.initByName(
                "value", "I>=2 ? 42.0 : 53.0",
                "arg", iparam);
        assertEquals(instance.size(), 3);
        assertTrue(Math.abs(instance.get(0)-53.0)<1e-15);
        assertTrue(Math.abs(instance.get(1)-42.0)<1e-15);
        assertTrue(Math.abs(instance.get(2)-42.0)<1e-15);

    }

    @Test
    public void testUnaryOps() {

        ExpCalculator<Real> instance = new ExpCalculator<>();
        RealVectorParam<Real> iparam = new RealVectorParam<>(new double[] {1.0, 2.0, 3.0}, Real.INSTANCE);
        iparam.setID("I");

        instance.initByName(
                "value", "{min(I),max(I),len(I),{1,2}[1]}",
                "arg", iparam);
        assertEquals(instance.size(), 4);
        assertTrue(Math.abs(instance.get(0)-1.0)<1e-15);
        assertTrue(Math.abs(instance.get(1)-3.0)<1e-15);
        assertTrue(Math.abs(instance.get(2)-3.0)<1e-15);
        assertTrue(Math.abs(instance.get(3)-2.0)<1e-15);

        instance.initByName(
                "value", "sort({4,2.5,3,0})"
        );

        assertEquals(4, instance.size());
        assertEquals(0.0, instance.get(0), 1e-15);
        assertEquals(2.5, instance.get(1), 1e-15);
        assertEquals(3.0, instance.get(2), 1e-15);
        assertEquals(4.0, instance.get(3), 1e-15);

        instance.initByName( "value", "cumsum({1,3,4,4})");
        // Expected output: 1, 4, 8, 12

        assertEquals(4, instance.size());
        assertEquals(1, instance.get(0), 1e-15);
        assertEquals(4, instance.get(1), 1e-15);
        assertEquals(8, instance.get(2), 1e-15);
        assertEquals(12, instance.get(3), 1e-15);

        instance.initByName( "value", "diff({1,3,4,4})");
        // Expected output: 1, 4, 8, 12

        assertEquals(3, instance.size());
        assertEquals(2, instance.get(0), 1e-15);
        assertEquals(1, instance.get(1), 1e-15);
        assertEquals(0, instance.get(2), 1e-15);
    }

    @Test
    public void testIndexing() {

        ExpCalculator<Real> instance = new ExpCalculator<>();
        instance.initByName("value", "0[1:5]");

        assertEquals(instance.size(), 5);
        assertEquals(0.0, instance.get(0), 1e-15);
        assertEquals(0.0, instance.get(1), 1e-15);
        assertEquals(0.0, instance.get(2), 1e-15);
        assertEquals(0.0, instance.get(3), 1e-15);
        assertEquals(0.0, instance.get(4), 1e-15);

        instance.initByName("value", "1:5");

        assertEquals(instance.size(), 5);
        assertEquals(1.0, instance.get(0), 1e-15);
        assertEquals(2.0, instance.get(1), 1e-15);
        assertEquals(3.0, instance.get(2), 1e-15);
        assertEquals(4.0, instance.get(3), 1e-15);
        assertEquals(5.0, instance.get(4), 1e-15);

        instance.initByName("value", "5:1");

        assertEquals(instance.size(), 5);
        assertEquals(5.0, instance.get(0), 1e-15);
        assertEquals(4.0, instance.get(1), 1e-15);
        assertEquals(3.0, instance.get(2), 1e-15);
        assertEquals(2.0, instance.get(3), 1e-15);
        assertEquals(1.0, instance.get(4), 1e-15);

        instance.initByName("value", "(11:15)[4:2]");

        assertEquals(instance.size(), 3);
        assertEquals(15.0, instance.get(0), 1e-15);
        assertEquals(14.0, instance.get(1), 1e-15);
        assertEquals(13.0, instance.get(2), 1e-15);

        instance.log(0L, System.out);
    }
}
