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

import beast.core.parameter.Parameter;
import java.util.Map;

/**
 * @author Tim Vaughan <tgvaughan@gmail.com>
 */
public class ExpCalculatorVisitor extends ExpressionBaseVisitor<Double []>{

    Map<String, Parameter> parameterMap;
    
    /**
     * Create a new Expression AST visitor.
     * 
     * @param parameterMap
     */
    public ExpCalculatorVisitor(Map<String, Parameter> parameterMap) {
        this.parameterMap = parameterMap;
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
        if (!parameterMap.containsKey(paramName))
            throw new IllegalArgumentException("Paramter " + paramName
                    + " in expression was not found in list of provided"
                    + " parameters.");
                
        Parameter param = parameterMap.get(paramName);
        
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
        Double [] right = visit(ctx.atom());
        
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
 
        Double [] res = visit(ctx.atom());
        for (int i=0; i<res.length; i++)
            res[i] = -res[i];
        
        return res;
    }
    
}
