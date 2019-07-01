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

package feast.expressions;

import beast.core.Description;
import beast.core.Function;
import beast.core.Input;
import beast.math.distributions.ParametricDistribution;
import feast.expressions.parser.ExpCalculatorVisitor;
import feast.expressions.parser.ExpressionLexer;
import feast.expressions.parser.ExpressionParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.apache.commons.math.MathException;
import org.apache.commons.math.distribution.ContinuousDistribution;
import org.apache.commons.math.distribution.Distribution;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tim Vaughan
 */
@Description("Expression calculator distribution for use with the Prior "
        + "class.  This is a somewhat restricted version of "
        + "ExpCalculatorDistribution as it does not allow for general "
        + "multivariate distributions.")
public class ExpCalculatorParametricDistribution extends ParametricDistribution {

    public Input<String> expressionInput = new Input<>("expression",
            "Expression needed for calculations.", Input.Validate.REQUIRED);
    
    public Input<Boolean> isLogInput = new Input<>("isLog",
            "True if expression represents log(P), false if it represents P. "
            + "Default is false.", false);
    
    ParseTree parseTree;
    ExpCalculatorVisitor visitor;
    LightParameter param;
        
    public ExpCalculatorParametricDistribution() {
    }

    @Override
    public void initAndValidate() {
        
        // Parameter to house real value
        param = new LightParameter(1.0);
        
        // Assemble name->param map
        Map<String, Function> functionsMap = new HashMap<>();
        functionsMap.put("x", param);

        // Build AST from expression string
//        ANTLRInputStream input = new ANTLRInputStream(expressionInput.get());
        CharStream input = CharStreams.fromString(expressionInput.get());
        ExpressionLexer lexer = new ExpressionLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExpressionParser parser = new ExpressionParser(tokens);
        parseTree = parser.expression();
        
        // Create new visitor for calculating expression values:
        visitor = new ExpCalculatorVisitor(functionsMap);
        
        if (visitor.visit(parseTree).length != 1)
            throw new IllegalArgumentException("ExpCalculatorDistribution "
                    + "expressions must be single-valued.");
    }

    private double getExpressionValue(double x) {
        param.setValue(x);
        return visitor.visit(parseTree)[0];
    }
    
    @Override
    public Distribution getDistribution() {

        return new ContinuousDistribution() {

            @Override
            public double inverseCumulativeProbability(double p) throws MathException {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public double density(double x) {
                if (isLogInput.get())
                    return Math.exp(getExpressionValue(x));
                else
                    return getExpressionValue(x);
            }

            @Override
            public double logDensity(double x) {
                if (isLogInput.get())
                    return getExpressionValue(x);
                else
                    return Math.log(getExpressionValue(x));
            }

            @Override
            public double cumulativeProbability(double x) throws MathException {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public double cumulativeProbability(double x0, double x1) throws MathException {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        };
        
    }
    
    private static class LightParameter implements Function {

        double value;

        public LightParameter(double value) {
            this.value = value;
        }
        
        @Override
        public int getDimension() {
            return 1;
        }

        @Override
        public double getArrayValue() {
            return value;
        }

        @Override
        public double getArrayValue(int iDim) {
            return value;
        }
        
        public void setValue(double newValue) {
            value = newValue;
        }
        
    }
    
}
