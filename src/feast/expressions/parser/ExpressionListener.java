// Generated from Expression.g4 by ANTLR 4.5
package feast.expressions.parser;
import org.antlr.v4.runtime.misc.NotNull;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ExpressionParser}.
 */
public interface ExpressionListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAddSub(ExpressionParser.AddSubContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AddSub}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAddSub(ExpressionParser.AddSubContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ELSEWHERE1}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterELSEWHERE1(ExpressionParser.ELSEWHERE1Context ctx);
	/**
	 * Exit a parse tree produced by the {@code ELSEWHERE1}
	 * labeled alternative in {@link ExpressionParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitELSEWHERE1(ExpressionParser.ELSEWHERE1Context ctx);
	/**
	 * Enter a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link ExpressionParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterMulDiv(ExpressionParser.MulDivContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MulDiv}
	 * labeled alternative in {@link ExpressionParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitMulDiv(ExpressionParser.MulDivContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ELSEWHERE2}
	 * labeled alternative in {@link ExpressionParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterELSEWHERE2(ExpressionParser.ELSEWHERE2Context ctx);
	/**
	 * Exit a parse tree produced by the {@code ELSEWHERE2}
	 * labeled alternative in {@link ExpressionParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitELSEWHERE2(ExpressionParser.ELSEWHERE2Context ctx);
	/**
	 * Enter a parse tree produced by the {@code Negation}
	 * labeled alternative in {@link ExpressionParser#molecule}.
	 * @param ctx the parse tree
	 */
	void enterNegation(ExpressionParser.NegationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Negation}
	 * labeled alternative in {@link ExpressionParser#molecule}.
	 * @param ctx the parse tree
	 */
	void exitNegation(ExpressionParser.NegationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Exponentiation}
	 * labeled alternative in {@link ExpressionParser#molecule}.
	 * @param ctx the parse tree
	 */
	void enterExponentiation(ExpressionParser.ExponentiationContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Exponentiation}
	 * labeled alternative in {@link ExpressionParser#molecule}.
	 * @param ctx the parse tree
	 */
	void exitExponentiation(ExpressionParser.ExponentiationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ELSEWHERE3}
	 * labeled alternative in {@link ExpressionParser#molecule}.
	 * @param ctx the parse tree
	 */
	void enterELSEWHERE3(ExpressionParser.ELSEWHERE3Context ctx);
	/**
	 * Exit a parse tree produced by the {@code ELSEWHERE3}
	 * labeled alternative in {@link ExpressionParser#molecule}.
	 * @param ctx the parse tree
	 */
	void exitELSEWHERE3(ExpressionParser.ELSEWHERE3Context ctx);
	/**
	 * Enter a parse tree produced by the {@code Bracketed}
	 * labeled alternative in {@link ExpressionParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterBracketed(ExpressionParser.BracketedContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Bracketed}
	 * labeled alternative in {@link ExpressionParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitBracketed(ExpressionParser.BracketedContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Array}
	 * labeled alternative in {@link ExpressionParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterArray(ExpressionParser.ArrayContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Array}
	 * labeled alternative in {@link ExpressionParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitArray(ExpressionParser.ArrayContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnaryOp}
	 * labeled alternative in {@link ExpressionParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterUnaryOp(ExpressionParser.UnaryOpContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnaryOp}
	 * labeled alternative in {@link ExpressionParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitUnaryOp(ExpressionParser.UnaryOpContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Variable}
	 * labeled alternative in {@link ExpressionParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterVariable(ExpressionParser.VariableContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Variable}
	 * labeled alternative in {@link ExpressionParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitVariable(ExpressionParser.VariableContext ctx);
	/**
	 * Enter a parse tree produced by the {@code Number}
	 * labeled alternative in {@link ExpressionParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterNumber(ExpressionParser.NumberContext ctx);
	/**
	 * Exit a parse tree produced by the {@code Number}
	 * labeled alternative in {@link ExpressionParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitNumber(ExpressionParser.NumberContext ctx);
}