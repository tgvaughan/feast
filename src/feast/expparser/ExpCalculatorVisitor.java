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

package feast.expparser;

import beast.core.Function;
import beast.core.parameter.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class ExpCalculatorVisitor extends ExpressionBaseVisitor<Double []>{

    Map<String, Function> functionsMap;
    
    /**
     * Create a new Expression AST visitor.
     * 
     * @param functionMap
     */
    public ExpCalculatorVisitor(Map<String, Function> functionMap) {
        this.functionsMap = functionMap;
    }
    
    @Override
    public Double[] visitNumber(ExpressionParser.NumberContext ctx) {

        double num = Double.valueOf(ctx.val.getText());
        
        Double [] res = new Double[1];
        res[0] = num;
        
        return res;
    }

    @Override
    public Double[] visitVariable(ExpressionParser.VariableContext ctx) {

        String paramName = ctx.VARNAME().getText();
        if (!functionsMap.containsKey(paramName))
            throw new IllegalArgumentException("Paramter/Function " + paramName
                    + " in expression was not found in list of provided"
                    + " parameters/functions.");
                
        Function param = functionsMap.get(paramName);
        
        int paramIdx = -1;
        if (ctx.i != null)
            paramIdx = Integer.valueOf(ctx.i.getText());
        
        Double [] res;
        if (paramIdx<0) {
            res = new Double[param.getDimension()];
            for (int i=0; i<res.length; i++)
                res[i] = param.getArrayValue(i);
        } else {
            res = new Double[1];
            res[0] = param.getArrayValue(paramIdx);
        }
        
        return res;
    }

    @Override
    public Double[] visitMulDiv(ExpressionParser.MulDivContext ctx) {
        Double [] left = visit(ctx.factor());
        Double [] right = visit(ctx.molecule());
        
        Double [] res = new Double[Math.max(left.length, right.length)];
        for (int i=0; i<res.length; i++) {
            if (ctx.op.getType() == ExpressionParser.MUL)
                res[i] = left[i%left.length]*right[i%right.length];
            else
                res[i] = left[i%left.length]/right[i%right.length];
        }
        
        return res;
    }

    @Override
    public Double[] visitAddSub(ExpressionParser.AddSubContext ctx) {
        Double [] left = visit(ctx.expression());
        Double [] right = visit(ctx.factor());
        
        Double [] res = new Double[Math.max(left.length, right.length)];
        for (int i=0; i<res.length; i++) {
            if (ctx.op.getType() == ExpressionParser.ADD)
                res[i] = left[i%left.length]+right[i%right.length];
            else
                res[i] = left[i%left.length]-right[i%right.length];
        }
        
        return res;
    }

    @Override
    public Double[] visitBracketed(ExpressionParser.BracketedContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public Double[] visitUnaryOp(ExpressionParser.UnaryOpContext ctx) {
        

        Double [] arg = visit(ctx.expression());
        Double [] res = null;
        
        switch(ctx.op.getType()) {
            case ExpressionParser.EXP:
                res = new Double[arg.length];
                for (int i=0; i<arg.length; i++)
                    res[i] = Math.exp(arg[i]);
                break;
                
            case ExpressionParser.LOG:
                res = new Double[arg.length];
                for (int i=0; i<arg.length; i++)
                    res[i] = Math.log(arg[i]);
                break;
                
            case ExpressionParser.SQRT:
                res = new Double[arg.length];
                for (int i=0; i<arg.length; i++)
                    res[i] = Math.sqrt(arg[i]);
                break;
                
            case ExpressionParser.SUM:
                res = new Double[1];
                res[0] = 0.0;
                for (Double el : arg)
                    res[0] += el;
                break;
        }
        
        return res;
    }

    @Override
    public Double[] visitNegation(ExpressionParser.NegationContext ctx) {
 
        Double [] res = visit(ctx.molecule());
        for (int i=0; i<res.length; i++)
            res[i] = -res[i];
        
        return res;
    }

    @Override
    public Double[] visitExponentiation(ExpressionParser.ExponentiationContext ctx) {
        Double [] base = visit(ctx.atom());
        Double [] power = visit(ctx.molecule());
        
        Double [] res = new Double[Math.max(base.length, power.length)];
        for (int i=0; i<res.length; i++) {
                res[i] = Math.pow(base[i%base.length], power[i%power.length]);
        }
        
        return res;
    }

    @Override
    public Double[] visitArray(ExpressionParser.ArrayContext ctx) {

        List<Double> resList = new ArrayList<Double>();
        for (ExpressionParser.ExpressionContext ectx : ctx.expression()) {
            resList.addAll(Arrays.asList(visit(ectx)));
        }
        
        return resList.toArray(new Double[resList.size()]);
    }
}
