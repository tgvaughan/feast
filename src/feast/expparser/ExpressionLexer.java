// Generated from Expression.g4 by ANTLR 4.2
package feast.expparser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ExpressionLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__3=1, T__2=2, T__1=3, T__0=4, ADD=5, SUB=6, MUL=7, DIV=8, EXP=9, LOG=10, 
		SQRT=11, SUM=12, NNINT=13, NNFLOAT=14, VARNAME=15, WHITESPACE=16;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"']'", "')'", "'['", "'('", "'+'", "'-'", "'*'", "'/'", "'exp'", "'log'", 
		"'sqrt'", "'sum'", "NNINT", "NNFLOAT", "VARNAME", "WHITESPACE"
	};
	public static final String[] ruleNames = {
		"T__3", "T__2", "T__1", "T__0", "ADD", "SUB", "MUL", "DIV", "EXP", "LOG", 
		"SQRT", "SUM", "NNINT", "NNFLOAT", "D", "NZD", "VARNAME", "WHITESPACE"
	};


	public ExpressionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Expression.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\22w\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t"+
		"\3\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r\3\r\3"+
		"\r\3\r\3\16\3\16\3\16\7\16L\n\16\f\16\16\16O\13\16\5\16Q\n\16\3\17\3\17"+
		"\3\17\7\17V\n\17\f\17\16\17Y\13\17\3\17\3\17\5\17]\n\17\3\17\6\17`\n\17"+
		"\r\17\16\17a\5\17d\n\17\3\20\3\20\3\21\3\21\3\22\3\22\7\22l\n\22\f\22"+
		"\16\22o\13\22\3\23\6\23r\n\23\r\23\16\23s\3\23\3\23\2\2\24\3\3\5\4\7\5"+
		"\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\2!\2#\21"+
		"%\22\3\2\b\4\2GGgg\3\2\62;\3\2\63;\5\2C\\aac|\7\2//\62;C\\aac|\5\2\13"+
		"\f\17\17\"\"|\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2"+
		"\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2"+
		"\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2#\3\2\2\2\2%\3\2\2"+
		"\2\3\'\3\2\2\2\5)\3\2\2\2\7+\3\2\2\2\t-\3\2\2\2\13/\3\2\2\2\r\61\3\2\2"+
		"\2\17\63\3\2\2\2\21\65\3\2\2\2\23\67\3\2\2\2\25;\3\2\2\2\27?\3\2\2\2\31"+
		"D\3\2\2\2\33P\3\2\2\2\35R\3\2\2\2\37e\3\2\2\2!g\3\2\2\2#i\3\2\2\2%q\3"+
		"\2\2\2\'(\7_\2\2(\4\3\2\2\2)*\7+\2\2*\6\3\2\2\2+,\7]\2\2,\b\3\2\2\2-."+
		"\7*\2\2.\n\3\2\2\2/\60\7-\2\2\60\f\3\2\2\2\61\62\7/\2\2\62\16\3\2\2\2"+
		"\63\64\7,\2\2\64\20\3\2\2\2\65\66\7\61\2\2\66\22\3\2\2\2\678\7g\2\289"+
		"\7z\2\29:\7r\2\2:\24\3\2\2\2;<\7n\2\2<=\7q\2\2=>\7i\2\2>\26\3\2\2\2?@"+
		"\7u\2\2@A\7s\2\2AB\7t\2\2BC\7v\2\2C\30\3\2\2\2DE\7u\2\2EF\7w\2\2FG\7o"+
		"\2\2G\32\3\2\2\2HQ\7\62\2\2IM\5!\21\2JL\5\37\20\2KJ\3\2\2\2LO\3\2\2\2"+
		"MK\3\2\2\2MN\3\2\2\2NQ\3\2\2\2OM\3\2\2\2PH\3\2\2\2PI\3\2\2\2Q\34\3\2\2"+
		"\2RS\5\33\16\2SW\7\60\2\2TV\5\37\20\2UT\3\2\2\2VY\3\2\2\2WU\3\2\2\2WX"+
		"\3\2\2\2Xc\3\2\2\2YW\3\2\2\2Z\\\t\2\2\2[]\7/\2\2\\[\3\2\2\2\\]\3\2\2\2"+
		"]_\3\2\2\2^`\5\37\20\2_^\3\2\2\2`a\3\2\2\2a_\3\2\2\2ab\3\2\2\2bd\3\2\2"+
		"\2cZ\3\2\2\2cd\3\2\2\2d\36\3\2\2\2ef\t\3\2\2f \3\2\2\2gh\t\4\2\2h\"\3"+
		"\2\2\2im\t\5\2\2jl\t\6\2\2kj\3\2\2\2lo\3\2\2\2mk\3\2\2\2mn\3\2\2\2n$\3"+
		"\2\2\2om\3\2\2\2pr\t\7\2\2qp\3\2\2\2rs\3\2\2\2sq\3\2\2\2st\3\2\2\2tu\3"+
		"\2\2\2uv\b\23\2\2v&\3\2\2\2\13\2MPW\\acms\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}