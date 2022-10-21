/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package feast.expressions;

import beast.base.core.BEASTObject;
import beast.base.core.Description;
import beast.base.core.Function;
import beast.base.core.Input;
import beast.base.inference.Distribution;
import beast.base.inference.State;
import feast.expressions.parser.ExpCalculatorVisitor;
import feast.expressions.parser.ExpressionLexer;
import feast.expressions.parser.ExpressionParser;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import java.util.*;

/**
 *
 * @author Tim Vaughan
 */
@Description("A distribution composed from an expression string.")
public class ExpCalculatorDistribution extends Distribution {
    
    public Input<String> expressionInput = new Input<>("value",
            "Expression needed for calculations.", Input.Validate.REQUIRED);
    
    public Input<List<Function>> functionsInput = new Input<>(
            "arg", "Parameters/functions needed for the calculation",
            new ArrayList<>());

    public Input<Boolean> isLogInput = new Input<>("isLog",
            "True if expression represents log(P), false if it represents P. "
            + "Default is false.", false);
    
    
    ParseTree parseTree;
    ExpCalculatorVisitor visitor;
    
    Double [] res;

    public ExpCalculatorDistribution () { }
    
    @Override
    public void initAndValidate() {
        
        // Assemble name->param map
        Map<String, Function> functionsMap = new HashMap<>();
        for (Function func : functionsInput.get()) {
            BEASTObject obj = (BEASTObject)func;
            functionsMap.put(obj.getID(), func);
        }

        // Build AST from expression string
        CharStream input = CharStreams.fromString(expressionInput.get());
        ExpressionLexer lexer = new ExpressionLexer(input);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        ExpressionParser parser = new ExpressionParser(tokens);
        parseTree = parser.expression();
        
        // Create new visitor for calculating expression values:
        visitor = new ExpCalculatorVisitor(functionsMap);
        
        update();
        
        if (res.length != 1)
            throw new IllegalArgumentException("ExpCalculatorDistribution "
                    + "expressions must be single-valued.");
    }

    private void update() {
        if (parseTree != null)
            res = visitor.visit(parseTree);
    }

    @Override
    public double calculateLogP() {
        update();
        if (isLogInput.get())
            logP = res[0];
        else
            logP = Math.log(res[0]);
        
        return logP;
    }
    
    

    @Override
    public List<String> getArguments() {
        return null;
    }

    @Override
    public List<String> getConditions() {
        return null;
    }

    @Override
    public void sample(State state, Random random) {
        throw new UnsupportedOperationException("Not supported yet."); 
    }
    
}
