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

import java.util.*;

/**
 * @author Tim Vaughan
 */
public class ExpCalculatorVisitor extends ExpressionBaseVisitor<Double []>{

    private Map<ExpressionParser.ExpressionContext, Double[]> cache = new HashMap<>();

    private Double[] getResultArray(ExpressionParser.ExpressionContext ctx, int length) {
        if (cache.containsKey(ctx)) {
            Double[] res = cache.get(ctx);
            if (res.length != length) {
                res = new Double[length];
                cache.put(ctx, res);
            }
            return res;
        } else {
            Double[] res = new Double[length];
            cache.put(ctx, res);
            return res;
        }
    }

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

        if (cache.containsKey(ctx))
            return cache.get(ctx); // Numbers never change

        double num = Double.parseDouble(ctx.val.getText());
        Double[] res = new Double[1];
        res[0] = num;
        cache.put(ctx, res);

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

        Double[] res = getResultArray(ctx, param.getDimension());

        for (int i = 0; i < res.length; i++)
            res[i] = param.getArrayValue(i);

        return res;
    }

    @Override
    public Double[] visitEquality(ExpressionParser.EqualityContext ctx) {
        Double [] left = visit(ctx.expression(0));
        Double [] right = visit(ctx.expression(1));

        Double [] res = getResultArray(ctx, Math.max(left.length, right.length));
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

        Double [] res = getResultArray(ctx, Math.max(left.length, right.length));
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

        Double [] res = getResultArray(ctx, Math.max(Math.max(pred.length, th.length), el.length));
        for (int i=0; i<res.length; i++) {
            res[i] = pred[i%pred.length] > 0.0 ? th[i%th.length] : el[i%el.length];
        }

        return res;
    }

    @Override
    public Double[] visitMulDiv(ExpressionParser.MulDivContext ctx) {
        Double [] left = visit(ctx.expression(0));
        Double [] right = visit(ctx.expression(1));

        Double [] res = getResultArray(ctx, Math.max(left.length, right.length));
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

        Double [] res = getResultArray(ctx, Math.max(left.length, right.length));
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
                res = getResultArray(ctx, arg.length);
                for (int i=0; i<arg.length; i++)
                    res[i] = Math.exp(arg[i]);
                break;
                
            case ExpressionParser.LOG:
                res = getResultArray(ctx, arg.length);
                for (int i=0; i<arg.length; i++)
                    res[i] = Math.log(arg[i]);
                break;
                
            case ExpressionParser.SQRT:
                res = getResultArray(ctx, arg.length);
                for (int i=0; i<arg.length; i++)
                    res[i] = Math.sqrt(arg[i]);
                break;
                
            case ExpressionParser.SUM:
                res = getResultArray(ctx, 1);
                res[0] = 0.0;
                for (Double el : arg)
                    res[0] += el;
                break;
                
            case ExpressionParser.THETA:
                res = getResultArray(ctx, arg.length);
                for (int i=0; i<arg.length; i++)
                    res[i] = arg[i] < 0.0 ? 0.0 : 1.0;
                break;

            case ExpressionParser.ABS:
                res = getResultArray(ctx, arg.length);
                for (int i=0; i<arg.length; i++)
                    res[i] = Math.abs(arg[i]);
                break;

            case ExpressionParser.MIN:
                res = getResultArray(ctx, 1);
                res[0] = arg[0];
                for (int i=1; i<arg.length; i++) {
                    if (arg[i]<res[0])
                        res[0] = arg[i];
                }
                break;

            case ExpressionParser.MAX:
                res = getResultArray(ctx, 1);
                res[0] = arg[0];
                for (int i=1; i<arg.length; i++) {
                    if (arg[i]>res[0])
                        res[0] = arg[i];
                }
                break;

            case ExpressionParser.LEN:
                res = getResultArray(ctx, 1);
                res[0] = (double)arg.length;
                break;

            case ExpressionParser.SORT:
                res = getResultArray(ctx, arg.length);
                System.arraycopy(arg, 0, res, 0, arg.length);
                Arrays.sort(res);
                break;
        }
        
        return res;
    }

    @Override
    public Double[] visitNegation(ExpressionParser.NegationContext ctx) {

        Double[] orig = visit(ctx.expression());
        Double[] res = getResultArray(ctx, orig.length);
        for (int i=0; i<res.length; i++)
            res[i] = -orig[i];
        
        return res;
    }

    @Override
    public Double[] visitExponentiation(ExpressionParser.ExponentiationContext ctx) {
        Double [] base = visit(ctx.expression(0));
        Double [] power = visit(ctx.expression(1));
        
        Double [] res = getResultArray(ctx, Math.max(base.length, power.length));
        for (int i=0; i<res.length; i++) {
                res[i] = Math.pow(base[i%base.length], power[i%power.length]);
        }
        
        return res;
    }

    @Override
    public Double[] visitFactorial(ExpressionParser.FactorialContext ctx) {
        Double [] arg = visit(ctx.expression());

        Double [] res = getResultArray(ctx, arg.length);
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
        Double [] array = visit(ctx.expression(0));
        Double [] indices = visit(ctx.expression(1));
        Double [] res = getResultArray(ctx, indices.length);

        for (int i=0; i<indices.length; i++)
            res[i] = array[(int)Math.round(indices[i]) % array.length];

        return res;
    }

    @Override
    public Double[] visitSequence(ExpressionParser.SequenceContext ctx) {

        int start = (int)Math.round(visit(ctx.expression(0))[0]);
        int stop = (int)Math.round(visit(ctx.expression(1))[0]);

        Double[] res = getResultArray(ctx, Math.abs(stop-start)+1);

        double delta = stop > start ? 1.0 : -1.0;

        for (int i=0; i<res.length; i++)
            res[i] = start + i*delta;

        return res;
    }
}
