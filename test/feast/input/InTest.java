/*
 * Copyright (C) 2014 Tim Vaughan <tgvaughan@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package feast.input;

import beast.core.BEASTObject;
import beast.core.Input;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Tests that Inputs created using In class have the correct rules.
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class InTest extends BEASTObject {
    
    public Input<String> stringInput = In.create("string", "A test string input.");
    
    public Input<Integer> requiredInput = new In<Integer>("requiredInt","An integer")
            .setDefault(5).setRequired();
    
    public Input<Double> xorAInput = In.create("xorA", "An XOR input (A).");
    
    public Input<Double> xorBInput = new In<Double>("xorB", "An XOR input (B).")
            .setXOR(xorAInput);

    public InTest() {
    }

    @Override
    public void initAndValidate() throws Exception { }
    
    /**
     * Test that setting inputs string, requiredInt and xorA validates.
     */
    @Test
    public void test1() {
        
        boolean valid = true;
        try {
            InTest instance = new InTest();
            instance.initByName("string", "My string",
                    "requiredInt", 5,
                    "xorA", 1.0);
            instance.validateInputs();
        } catch (Exception ex) {
            valid = false;
        }
        assertTrue(valid);
    }
    
    /**
     * Test that setting inputs requiredInt and xorA validates.
     */
    @Test
    public void test2() {
        
        boolean valid = true;
        try {
            InTest instance = new InTest();
            instance.initByName("requiredInt", 5,
                    "xorA", 1.0);
            instance.validateInputs();
        } catch (Exception ex) {
            valid = false;
        }
        assertTrue(valid);
    }
    
    /**
     * Test that setting missing requiredInt fails to validate.
     */
    @Test
    public void test3() {
        
        boolean valid = true;
        try {
            InTest instance = new InTest();
            instance.initByName("string", "My string",
                    "xorA", 1.0);
            instance.validateInputs();
        } catch (Exception ex) {
            valid = false;
        }
        assertFalse(valid);
    }
    
    /**
     * Test that setting missing xorA and xorB fails to validate.
     */
    @Test
    public void test4() {
        
        boolean valid = true;
        try {
            InTest instance = new InTest();
            instance.initByName("requiredInt", 5);
            instance.validateInputs();
        } catch (Exception ex) {
            valid = false;
        }
        assertFalse(valid);
    }
    
    /**
     * Test that setting requiredInt and xorB validates.
     */
    @Test
    public void test5() {
        
        boolean valid = true;
        try {
            InTest instance = new InTest();
            instance.initByName("requiredInt", 5,
                    "xorB", 1.0);
            instance.validateInputs();
        } catch (Exception ex) {
            valid = false;
        }
        assertTrue(valid);
    }
    
    /**
     * Test that setting requiredInt and both xorA and xorB fails to validate.
     */
    @Test
    public void test6() {
        
        boolean valid = true;
        try {
            InTest instance = new InTest();
            instance.initByName("requiredInt", 5,
                    "xorA", 1.0,
                    "xorB", 1.0);
            instance.validateInputs();
        } catch (Exception ex) {
            valid = false;
        }
        assertFalse(valid);
    }
}
