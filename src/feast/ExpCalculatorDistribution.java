/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package feast;

import beast.core.BEASTObject;
import beast.core.Description;
import beast.core.Distribution;
import beast.core.Function;
import beast.core.Input;
import beast.core.State;
import feast.expparser.ExpCalculatorVisitor;
import feast.expparser.ExpressionLexer;
import feast.expparser.ExpressionParser;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
@Description("A distribution composed from an expression string.")
public class ExpCalculatorDistribution extends Distribution {
    
    public Input<String> expressionInput = new Input<String>("expression",
            "Expression needed for calculations.", Input.Validate.REQUIRED);
    
    public Input<List<Function>> functionsInput = new Input<List<Function>>(
            "parameter", "Parameters/functions needed for the calculation",
            new ArrayList<Function>());

    public Input<Boolean> isLogInput = new Input<Boolean>("isLog",
            "True if expression represents log(P), false if it represents P. "
            + "Default is false.", false);
    
    
    ParseTree parseTree;
    ExpCalculatorVisitor visitor;
    
    Double [] res;

    public ExpCalculatorDistribution () { }
    
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
        
        if (res.length != 1)
            throw new IllegalArgumentException("ExpCalculatorDistribution "
                    + "expressions must be single-valued.");
    }

    private void update() {
        res = visitor.visit(parseTree);
    }

    @Override
    public double calculateLogP() throws Exception {
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
