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
		EXP=11, LOG=12, SQRT=13, SUM=14, THETA=15, NNINT=16, NNFLOAT=17, VARNAME=18, 
		WHITESPACE=19;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"']'", "')'", "','", "'['", "'('", "'+'", "'-'", "'*'", "'/'", "'^'", 
		"'exp'", "'log'", "'sqrt'", "'sum'", "'theta'", "NNINT", "NNFLOAT", "VARNAME", 
		"WHITESPACE"
	};
	public static final String[] ruleNames = {
		"T__4", "T__3", "T__2", "T__1", "T__0", "ADD", "SUB", "MUL", "DIV", "POW", 
		"EXP", "LOG", "SQRT", "SUM", "THETA", "NNINT", "NNFLOAT", "D", "NZD", 
		"VARNAME", "WHITESPACE"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\25\u0087\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\3\2\3\2\3\3\3\3\3\4\3\4"+
		"\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\f\3\f\3\f"+
		"\3\f\3\r\3\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\20"+
		"\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\7\21\\\n\21\f\21\16\21_\13\21"+
		"\5\21a\n\21\3\22\3\22\3\22\7\22f\n\22\f\22\16\22i\13\22\3\22\3\22\5\22"+
		"m\n\22\3\22\6\22p\n\22\r\22\16\22q\5\22t\n\22\3\23\3\23\3\24\3\24\3\25"+
		"\3\25\7\25|\n\25\f\25\16\25\177\13\25\3\26\6\26\u0082\n\26\r\26\16\26"+
		"\u0083\3\26\3\26\2\2\27\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f"+
		"\27\r\31\16\33\17\35\20\37\21!\22#\23%\2\'\2)\24+\25\3\2\b\4\2GGgg\3\2"+
		"\62;\3\2\63;\5\2C\\aac|\6\2\62;C\\aac|\5\2\13\f\17\17\"\"\u008c\2\3\3"+
		"\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2"+
		"\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3"+
		"\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2"+
		")\3\2\2\2\2+\3\2\2\2\3-\3\2\2\2\5/\3\2\2\2\7\61\3\2\2\2\t\63\3\2\2\2\13"+
		"\65\3\2\2\2\r\67\3\2\2\2\179\3\2\2\2\21;\3\2\2\2\23=\3\2\2\2\25?\3\2\2"+
		"\2\27A\3\2\2\2\31E\3\2\2\2\33I\3\2\2\2\35N\3\2\2\2\37R\3\2\2\2!`\3\2\2"+
		"\2#b\3\2\2\2%u\3\2\2\2\'w\3\2\2\2)y\3\2\2\2+\u0081\3\2\2\2-.\7_\2\2.\4"+
		"\3\2\2\2/\60\7+\2\2\60\6\3\2\2\2\61\62\7.\2\2\62\b\3\2\2\2\63\64\7]\2"+
		"\2\64\n\3\2\2\2\65\66\7*\2\2\66\f\3\2\2\2\678\7-\2\28\16\3\2\2\29:\7/"+
		"\2\2:\20\3\2\2\2;<\7,\2\2<\22\3\2\2\2=>\7\61\2\2>\24\3\2\2\2?@\7`\2\2"+
		"@\26\3\2\2\2AB\7g\2\2BC\7z\2\2CD\7r\2\2D\30\3\2\2\2EF\7n\2\2FG\7q\2\2"+
		"GH\7i\2\2H\32\3\2\2\2IJ\7u\2\2JK\7s\2\2KL\7t\2\2LM\7v\2\2M\34\3\2\2\2"+
		"NO\7u\2\2OP\7w\2\2PQ\7o\2\2Q\36\3\2\2\2RS\7v\2\2ST\7j\2\2TU\7g\2\2UV\7"+
		"v\2\2VW\7c\2\2W \3\2\2\2Xa\7\62\2\2Y]\5\'\24\2Z\\\5%\23\2[Z\3\2\2\2\\"+
		"_\3\2\2\2][\3\2\2\2]^\3\2\2\2^a\3\2\2\2_]\3\2\2\2`X\3\2\2\2`Y\3\2\2\2"+
		"a\"\3\2\2\2bc\5!\21\2cg\7\60\2\2df\5%\23\2ed\3\2\2\2fi\3\2\2\2ge\3\2\2"+
		"\2gh\3\2\2\2hs\3\2\2\2ig\3\2\2\2jl\t\2\2\2km\7/\2\2lk\3\2\2\2lm\3\2\2"+
		"\2mo\3\2\2\2np\5%\23\2on\3\2\2\2pq\3\2\2\2qo\3\2\2\2qr\3\2\2\2rt\3\2\2"+
		"\2sj\3\2\2\2st\3\2\2\2t$\3\2\2\2uv\t\3\2\2v&\3\2\2\2wx\t\4\2\2x(\3\2\2"+
		"\2y}\t\5\2\2z|\t\6\2\2{z\3\2\2\2|\177\3\2\2\2}{\3\2\2\2}~\3\2\2\2~*\3"+
		"\2\2\2\177}\3\2\2\2\u0080\u0082\t\7\2\2\u0081\u0080\3\2\2\2\u0082\u0083"+
		"\3\2\2\2\u0083\u0081\3\2\2\2\u0083\u0084\3\2\2\2\u0084\u0085\3\2\2\2\u0085"+
		"\u0086\b\26\2\2\u0086,\3\2\2\2\13\2]`glqs}\u0083\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}