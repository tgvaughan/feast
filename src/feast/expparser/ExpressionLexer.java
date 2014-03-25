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
		T__3=1, T__2=2, T__1=3, T__0=4, ADD=5, SUB=6, MUL=7, DIV=8, POW=9, EXP=10, 
		LOG=11, SQRT=12, SUM=13, NNINT=14, NNFLOAT=15, VARNAME=16, WHITESPACE=17;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"']'", "')'", "'['", "'('", "'+'", "'-'", "'*'", "'/'", "'^'", "'exp'", 
		"'log'", "'sqrt'", "'sum'", "NNINT", "NNFLOAT", "VARNAME", "WHITESPACE"
	};
	public static final String[] ruleNames = {
		"T__3", "T__2", "T__1", "T__0", "ADD", "SUB", "MUL", "DIV", "POW", "EXP", 
		"LOG", "SQRT", "SUM", "NNINT", "NNFLOAT", "D", "NZD", "VARNAME", "WHITESPACE"
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\23{\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3"+
		"\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\r"+
		"\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\7\17P\n\17\f\17\16\17S\13"+
		"\17\5\17U\n\17\3\20\3\20\3\20\7\20Z\n\20\f\20\16\20]\13\20\3\20\3\20\5"+
		"\20a\n\20\3\20\6\20d\n\20\r\20\16\20e\5\20h\n\20\3\21\3\21\3\22\3\22\3"+
		"\23\3\23\7\23p\n\23\f\23\16\23s\13\23\3\24\6\24v\n\24\r\24\16\24w\3\24"+
		"\3\24\2\2\25\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16"+
		"\33\17\35\20\37\21!\2#\2%\22\'\23\3\2\b\4\2GGgg\3\2\62;\3\2\63;\5\2C\\"+
		"aac|\6\2\62;C\\aac|\5\2\13\f\17\17\"\"\u0080\2\3\3\2\2\2\2\5\3\2\2\2\2"+
		"\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2"+
		"\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2"+
		"\2\35\3\2\2\2\2\37\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\3)\3\2\2\2\5+\3\2\2"+
		"\2\7-\3\2\2\2\t/\3\2\2\2\13\61\3\2\2\2\r\63\3\2\2\2\17\65\3\2\2\2\21\67"+
		"\3\2\2\2\239\3\2\2\2\25;\3\2\2\2\27?\3\2\2\2\31C\3\2\2\2\33H\3\2\2\2\35"+
		"T\3\2\2\2\37V\3\2\2\2!i\3\2\2\2#k\3\2\2\2%m\3\2\2\2\'u\3\2\2\2)*\7_\2"+
		"\2*\4\3\2\2\2+,\7+\2\2,\6\3\2\2\2-.\7]\2\2.\b\3\2\2\2/\60\7*\2\2\60\n"+
		"\3\2\2\2\61\62\7-\2\2\62\f\3\2\2\2\63\64\7/\2\2\64\16\3\2\2\2\65\66\7"+
		",\2\2\66\20\3\2\2\2\678\7\61\2\28\22\3\2\2\29:\7`\2\2:\24\3\2\2\2;<\7"+
		"g\2\2<=\7z\2\2=>\7r\2\2>\26\3\2\2\2?@\7n\2\2@A\7q\2\2AB\7i\2\2B\30\3\2"+
		"\2\2CD\7u\2\2DE\7s\2\2EF\7t\2\2FG\7v\2\2G\32\3\2\2\2HI\7u\2\2IJ\7w\2\2"+
		"JK\7o\2\2K\34\3\2\2\2LU\7\62\2\2MQ\5#\22\2NP\5!\21\2ON\3\2\2\2PS\3\2\2"+
		"\2QO\3\2\2\2QR\3\2\2\2RU\3\2\2\2SQ\3\2\2\2TL\3\2\2\2TM\3\2\2\2U\36\3\2"+
		"\2\2VW\5\35\17\2W[\7\60\2\2XZ\5!\21\2YX\3\2\2\2Z]\3\2\2\2[Y\3\2\2\2[\\"+
		"\3\2\2\2\\g\3\2\2\2][\3\2\2\2^`\t\2\2\2_a\7/\2\2`_\3\2\2\2`a\3\2\2\2a"+
		"c\3\2\2\2bd\5!\21\2cb\3\2\2\2de\3\2\2\2ec\3\2\2\2ef\3\2\2\2fh\3\2\2\2"+
		"g^\3\2\2\2gh\3\2\2\2h \3\2\2\2ij\t\3\2\2j\"\3\2\2\2kl\t\4\2\2l$\3\2\2"+
		"\2mq\t\5\2\2np\t\6\2\2on\3\2\2\2ps\3\2\2\2qo\3\2\2\2qr\3\2\2\2r&\3\2\2"+
		"\2sq\3\2\2\2tv\t\7\2\2ut\3\2\2\2vw\3\2\2\2wu\3\2\2\2wx\3\2\2\2xy\3\2\2"+
		"\2yz\b\24\2\2z(\3\2\2\2\13\2QT[`egqw\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}