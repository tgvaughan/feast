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

package feast.expressions.parser;

import beast.core.Function;
import beast.math.statistic.DiscreteStatistics;
import org.apache.commons.math3.special.Gamma;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author Tim Vaughan
 */
public class ExpCalculatorVisitor extends ExpressionBaseVisitor<Double []>{

    Map<String, Function> functionsMap;
    
    /**
     * Create a new Expression AST visitor.
     * 
     * @param functionMap map from function names to Functions
     */
    public ExpCalculatorVisitor(Map<String, Function> functionMap) {
        this.functionsMap = functionMap;
    }
    
    @Override
    public Double[] visitNumber(ExpressionParser.NumberContext ctx) {

        double num = Double.parseDouble(ctx.val.getText());
        
        Double [] res = new Double[1];
        res[0] = num;
        
        return res;
    }

    @Override
    public Double[] visitVariable(ExpressionParser.VariableContext ctx) {

        String paramName = ctx.IDENT().getText();
        if (!functionsMap.containsKey(paramName))
            throw new IllegalArgumentException("Paramter/Function " + paramName
                    + " in expression was not found in list of provided"
                    + " parameters/functions.");
                
        Function param = functionsMap.get(paramName);

        Double [] res;
        res = new Double[param.getDimension()];
        for (int i=0; i<res.length; i++)
            res[i] = param.getArrayValue(i);

        return res;
    }

    @Override
    public Double[] visitEquality(ExpressionParser.EqualityContext ctx) {
        Double [] left = visit(ctx.expression(0));
        Double [] right = visit(ctx.expression(1));

        Double [] res = new Double[Math.max(left.length, right.length)];
        for (int i=0; i<res.length; i++) {
            switch (ctx.op.getType()) {
                case ExpressionParser.GT:
                    res[i] = left[i%left.length] > right[i%right.length] ? 1.0 : 0.0;
                    break;
                case ExpressionParser.LT:
                    res[i] = left[i%left.length] < right[i%right.length] ? 1.0 : 0.0;
                    break;
                case ExpressionParser.GE:
                    res[i] = left[i%left.length] >= right[i%right.length] ? 1.0 : 0.0;
                    break;
                case ExpressionParser.LE:
                    res[i] = left[i%left.length] <= right[i%right.length] ? 1.0 : 0.0;
                    break;
                case ExpressionParser.EQ:
                    res[i] = left[i%left.length].equals(right[i%right.length]) ? 1.0 : 0.0;
                    break;
                case ExpressionParser.NE:
                    res[i] = left[i%left.length].equals(right[i%right.length]) ? 0.0 : 1.0;
                    break;
            }
        }

        return res;
    }

    @Override
    public Double[] visitBooleanOp(ExpressionParser.BooleanOpContext ctx) {
        Double [] left = visit(ctx.expression(0));
        Double [] right = visit(ctx.expression(1));

        Double [] res = new Double[Math.max(left.length, right.length)];
        for (int i=0; i<res.length; i++) {
            switch (ctx.op.getType()) {
                case ExpressionParser.AND:
                    res[i] = ((left[i%left.length] > 0) && (right[i%right.length] > 0)) ? 1.0 : 0.0;
                    break;
                case ExpressionParser.OR:
                    res[i] = ((left[i%left.length] > 0) || (right[i%right.length] > 0)) ? 1.0 : 0.0;
                    break;
            }
        }

        return res;
    }

    @Override
    public Double[] visitIfThenElse(ExpressionParser.IfThenElseContext ctx) {
        Double [] pred = visit(ctx.expression(0));
        Double [] th = visit(ctx.expression(1));
        Double [] el = visit(ctx.expression(2));

        Double [] res = new Double[Math.max(Math.max(pred.length, th.length), el.length)];
        for (int i=0; i<res.length; i++) {
            res[i] = pred[i%pred.length] > 0.0 ? th[i%th.length] : el[i%el.length];
        }

        return res;
    }

    @Override
    public Double[] visitMulDiv(ExpressionParser.MulDivContext ctx) {
        Double [] left = visit(ctx.expression(0));
        Double [] right = visit(ctx.expression(1));
        
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
        Double [] left = visit(ctx.expression(0));
        Double [] right = visit(ctx.expression(1));
        
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
                
            case ExpressionParser.THETA:
                res = new Double[arg.length];
                for (int i=0; i<arg.length; i++)
                    res[i] = arg[i] < 0.0 ? 0.0 : 1.0;
                break;

            case ExpressionParser.MIN:
                res = new Double[1];
                res[0] = arg[0];
                for (int i=1; i<arg.length; i++) {
                    if (arg[i]<res[0])
                        res[0] = arg[i];
                }
                break;

            case ExpressionParser.MAX:
                res = new Double[1];
                res[0] = arg[0];
                for (int i=1; i<arg.length; i++) {
                    if (arg[i]>res[0])
                        res[0] = arg[i];
                }
                break;

            case ExpressionParser.LEN:
                res = new Double[1];
                res[0] = (double)arg.length;
                break;
        }
        
        return res;
    }

    @Override
    public Double[] visitNegation(ExpressionParser.NegationContext ctx) {
 
        Double [] res = visit(ctx.expression());
        for (int i=0; i<res.length; i++)
            res[i] = -res[i];
        
        return res;
    }

    @Override
    public Double[] visitExponentiation(ExpressionParser.ExponentiationContext ctx) {
        Double [] base = visit(ctx.expression(0));
        Double [] power = visit(ctx.expression(1));
        
        Double [] res = new Double[Math.max(base.length, power.length)];
        for (int i=0; i<res.length; i++) {
                res[i] = Math.pow(base[i%base.length], power[i%power.length]);
        }
        
        return res;
    }

    @Override
    public Double[] visitFactorial(ExpressionParser.FactorialContext ctx) {
        Double [] arg = visit(ctx.expression());

        Double [] res = new Double[arg.length];
        for (int i=0; i<res.length; i++) {
            res[i] = Gamma.gamma(1+arg[i]);
        }

        return res;
    }

    @Override
    public Double[] visitArray(ExpressionParser.ArrayContext ctx) {

        List<Double> resList = new ArrayList<>();
        for (ExpressionParser.ExpressionContext ectx : ctx.expression()) {
            resList.addAll(Arrays.asList(visit(ectx)));
        }
        
        return resList.toArray(new Double[resList.size()]);
    }

    @Override
    public Double[] visitArraySubscript(ExpressionParser.ArraySubscriptContext ctx) {
        Double [] res = new Double[1];
        res[0] = visit(ctx.expression(0))[(int)Math.round(visit(ctx.expression(1))[0])];

        return res;
    }
}
