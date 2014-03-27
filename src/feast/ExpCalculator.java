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

import beast.core.BEASTObject;
import beast.core.CalculationNode;
import beast.core.Description;
import beast.core.Function;
import beast.core.Input;
import beast.core.Input.Validate;
import beast.core.Loggable;
import feast.expparser.ExpCalculatorVisitor;
import feast.expparser.ExpressionLexer;
import feast.expparser.ExpressionParser;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * Simple expression calculator.  Takes simple arithmetic expressions
 * and returns the result by acting as a Loggable ora Function.
 * Binary operators can be applied to Parameters of different lengths as in R,
 * with the result having the maximum of the two lengths, and the index into
 * the shortest parameter being the result index modulo the length of that
 * parameter.
 * 
 * Example expressions (I and J are IDs of RealParameters with elements
 * {1.0, 2.0, 3.0} and {5.0, 10.0}, respectively.)
 * 
 * Expression                 |  Loggable/Function value
 * ------------------------------------------------------
 * 2*(I+J)                    | {6.0, 12.0, 8.0}
 * exp(I[0])                  | {2.718...}
 * -log(exp(J))/(1.5+0.5*I[0])| {-2.5, -5.0}
 * sqrt(J)                    | {2.236..., 3.162...}
 * sum(I)                     | {6.0}
 * 
 * Inspired by RPNcalculator by Joseph Heled (BEAST1, BEAST 2 port by 
 * Denise Kuehnert).  (This parser uses ANTLR, which is cheating.)
 * 
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
@Description("Evaluates simple expressions of parameters involving parameters,"
        + " including parameters of different lengths.  Individual elements of"
        + " parameters can be specified using [] notation.  Parameters with"
        + " differing dimension are combined as in R, with the shortest "
        + " parameter being repeated as many times as necessary.")
public class ExpCalculator extends CalculationNode implements Loggable, Function {
    
    public Input<String> expressionInput = new Input<String>("expression",
            "Expression needed for calculations.", Validate.REQUIRED);
    
    public Input<List<Function>> functionsInput = new Input<List<Function>>(
            "parameter", "Parameters/functions needed for the calculation",
            new ArrayList<Function>());

    
    ParseTree parseTree;
    ExpCalculatorVisitor visitor;
    
    Double [] res;

    public ExpCalculator() {
    }
    
    @Override
    public void initAndValidate() throws Exception {
        
        // Assemble name->param map
        Map<String, Function> functionsMap = new HashMap<String, Function>();
        for (Function func : functionsInput.get()) {
            BEASTObject obj = (BEASTObject)func;
            functionsMap.put(obj.getID(), func);
        }

        // Build AST from expression string
        ANTLRInputStream input = new ANTLRInputStream(expressionInput.get());
        ExpressionLexer lexer = new ExpressionLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExpressionParser parser = new ExpressionParser(tokens);
        parseTree = parser.expression();
        
        // Create new visitor for calculating expression values:
        visitor = new ExpCalculatorVisitor(functionsMap);
        
        update();
    }

    private void update() {
        res = visitor.visit(parseTree);
    }
    
    @Override
    public void init(PrintStream out) throws Exception {
        String name = this.getID();
        if (name == null)
            name = "expression";

        if (res.length==1)
            out.print(name + "\t");
        else
            for (int i = 0; i < res.length; i++)
                out.print(name + "_" + i + "\t");
    }

    @Override
    public void log(int nSample, PrintStream out) {
        update();
        for (int i = 0; i < getDimension(); i++)
            out.print(res[i] + "\t");
    }

    @Override
    public void close(PrintStream out) { }

    @Override
    public int getDimension() {
        return res.length;
    }

    @Override
    public double getArrayValue() {
        update();
        return res[0];
    }

    @Override
    public double getArrayValue(int i) {
        update();
        return res[i];
    }
}
