// Generated from Expression.g4 by ANTLR 4.2
package feast.expressions.parser;
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
		T__4=1, T__3=2, T__2=3, T__1=4, T__0=5, ADD=6, SUB=7, MUL=8, DIV=9, POW=10, 
		EXP=11, LOG=12, SQRT=13, SUM=14, NNINT=15, NNFLOAT=16, VARNAME=17, WHITESPACE=18;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"']'", "')'", "','", "'['", "'('", "'+'", "'-'", "'*'", "'/'", "'^'", 
		"'exp'", "'log'", "'sqrt'", "'sum'", "NNINT", "NNFLOAT", "VARNAME", "WHITESPACE"
	};
	public static final String[] ruleNames = {
		"T__4", "T__3", "T__2", "T__1", "T__0", "ADD", "SUB", "MUL", "DIV", "POW", 
		"EXP", "LOG", "SQRT", "SUM", "NNINT", "NNFLOAT", "D", "NZD", "VARNAME", 
		"WHITESPACE"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\24\177\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6"+
		"\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r"+
		"\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\20\3\20\3\20\7\20"+
		"T\n\20\f\20\16\20W\13\20\5\20Y\n\20\3\21\3\21\3\21\7\21^\n\21\f\21\16"+
		"\21a\13\21\3\21\3\21\5\21e\n\21\3\21\6\21h\n\21\r\21\16\21i\5\21l\n\21"+
		"\3\22\3\22\3\23\3\23\3\24\3\24\7\24t\n\24\f\24\16\24w\13\24\3\25\6\25"+
		"z\n\25\r\25\16\25{\3\25\3\25\2\2\26\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n"+
		"\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\2%\2\'\23)\24\3\2\b\4\2"+
		"GGgg\3\2\62;\3\2\63;\5\2C\\aac|\6\2\62;C\\aac|\5\2\13\f\17\17\"\"\u0084"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2\'\3"+
		"\2\2\2\2)\3\2\2\2\3+\3\2\2\2\5-\3\2\2\2\7/\3\2\2\2\t\61\3\2\2\2\13\63"+
		"\3\2\2\2\r\65\3\2\2\2\17\67\3\2\2\2\219\3\2\2\2\23;\3\2\2\2\25=\3\2\2"+
		"\2\27?\3\2\2\2\31C\3\2\2\2\33G\3\2\2\2\35L\3\2\2\2\37X\3\2\2\2!Z\3\2\2"+
		"\2#m\3\2\2\2%o\3\2\2\2\'q\3\2\2\2)y\3\2\2\2+,\7_\2\2,\4\3\2\2\2-.\7+\2"+
		"\2.\6\3\2\2\2/\60\7.\2\2\60\b\3\2\2\2\61\62\7]\2\2\62\n\3\2\2\2\63\64"+
		"\7*\2\2\64\f\3\2\2\2\65\66\7-\2\2\66\16\3\2\2\2\678\7/\2\28\20\3\2\2\2"+
		"9:\7,\2\2:\22\3\2\2\2;<\7\61\2\2<\24\3\2\2\2=>\7`\2\2>\26\3\2\2\2?@\7"+
		"g\2\2@A\7z\2\2AB\7r\2\2B\30\3\2\2\2CD\7n\2\2DE\7q\2\2EF\7i\2\2F\32\3\2"+
		"\2\2GH\7u\2\2HI\7s\2\2IJ\7t\2\2JK\7v\2\2K\34\3\2\2\2LM\7u\2\2MN\7w\2\2"+
		"NO\7o\2\2O\36\3\2\2\2PY\7\62\2\2QU\5%\23\2RT\5#\22\2SR\3\2\2\2TW\3\2\2"+
		"\2US\3\2\2\2UV\3\2\2\2VY\3\2\2\2WU\3\2\2\2XP\3\2\2\2XQ\3\2\2\2Y \3\2\2"+
		"\2Z[\5\37\20\2[_\7\60\2\2\\^\5#\22\2]\\\3\2\2\2^a\3\2\2\2_]\3\2\2\2_`"+
		"\3\2\2\2`k\3\2\2\2a_\3\2\2\2bd\t\2\2\2ce\7/\2\2dc\3\2\2\2de\3\2\2\2eg"+
		"\3\2\2\2fh\5#\22\2gf\3\2\2\2hi\3\2\2\2ig\3\2\2\2ij\3\2\2\2jl\3\2\2\2k"+
		"b\3\2\2\2kl\3\2\2\2l\"\3\2\2\2mn\t\3\2\2n$\3\2\2\2op\t\4\2\2p&\3\2\2\2"+
		"qu\t\5\2\2rt\t\6\2\2sr\3\2\2\2tw\3\2\2\2us\3\2\2\2uv\3\2\2\2v(\3\2\2\2"+
		"wu\3\2\2\2xz\t\7\2\2yx\3\2\2\2z{\3\2\2\2{y\3\2\2\2{|\3\2\2\2|}\3\2\2\2"+
		"}~\b\25\2\2~*\3\2\2\2\13\2UX_diku{\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}