// Generated from /Users/vaughant/code/beast_and_friends/feast/src/feast/expressions/parser/Expression.g4 by ANTLR 4.12.0
package feast.expressions.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class ExpressionLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.12.0", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, ADD=11, SUB=12, MUL=13, DIV=14, MOD=15, POW=16, EXP=17, LOG=18, 
		SQRT=19, SUM=20, THETA=21, ABS=22, MIN=23, MAX=24, LEN=25, SORT=26, DIFF=27, 
		CUMSUM=28, SIN=29, COS=30, AND=31, OR=32, EQ=33, GT=34, LT=35, GE=36, 
		LE=37, NE=38, ZERO=39, NZINT=40, NNFLOAT=41, IDENT=42, COMMENT_SINGLELINE=43, 
		COMMENT_MULTILINE=44, WHITESPACE=45;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8", 
			"T__9", "ADD", "SUB", "MUL", "DIV", "MOD", "POW", "EXP", "LOG", "SQRT", 
			"SUM", "THETA", "ABS", "MIN", "MAX", "LEN", "SORT", "DIFF", "CUMSUM", 
			"SIN", "COS", "AND", "OR", "EQ", "GT", "LT", "GE", "LE", "NE", "ZERO", 
			"NZINT", "NNFLOAT", "D", "NZD", "IDENT", "COMMENT_SINGLELINE", "COMMENT_MULTILINE", 
			"WHITESPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'{'", "','", "'}'", "'['", "']'", "':'", "'!'", 
			"'?'", "'+'", "'-'", "'*'", "'/'", "'%'", "'^'", "'exp'", "'log'", "'sqrt'", 
			"'sum'", "'theta'", "'abs'", "'min'", "'max'", "'len'", "'sort'", "'diff'", 
			"'cumsum'", "'sin'", "'cos'", "'&&'", "'||'", "'=='", "'>'", "'<'", "'>='", 
			"'<='", "'!='", "'0'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "ADD", 
			"SUB", "MUL", "DIV", "MOD", "POW", "EXP", "LOG", "SQRT", "SUM", "THETA", 
			"ABS", "MIN", "MAX", "LEN", "SORT", "DIFF", "CUMSUM", "SIN", "COS", "AND", 
			"OR", "EQ", "GT", "LT", "GE", "LE", "NE", "ZERO", "NZINT", "NNFLOAT", 
			"IDENT", "COMMENT_SINGLELINE", "COMMENT_MULTILINE", "WHITESPACE"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}


	public ExpressionLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Expression.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000-\u0121\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007"+
		"!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007"+
		"&\u0002\'\u0007\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007"+
		"+\u0002,\u0007,\u0002-\u0007-\u0002.\u0007.\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001"+
		"\u0007\u0001\u0007\u0001\b\u0001\b\u0001\t\u0001\t\u0001\n\u0001\n\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001\u000e\u0001\u000e"+
		"\u0001\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010"+
		"\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012"+
		"\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001\u0014"+
		"\u0001\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0016"+
		"\u0001\u0016\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0017"+
		"\u0001\u0017\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0018\u0001\u0019"+
		"\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a"+
		"\u0001\u001a\u0001\u001a\u0001\u001a\u0001\u001b\u0001\u001b\u0001\u001b"+
		"\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001b\u0001\u001c\u0001\u001c"+
		"\u0001\u001c\u0001\u001c\u0001\u001d\u0001\u001d\u0001\u001d\u0001\u001d"+
		"\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001f\u0001\u001f\u0001\u001f"+
		"\u0001 \u0001 \u0001 \u0001!\u0001!\u0001\"\u0001\"\u0001#\u0001#\u0001"+
		"#\u0001$\u0001$\u0001$\u0001%\u0001%\u0001%\u0001&\u0001&\u0001\'\u0001"+
		"\'\u0005\'\u00da\b\'\n\'\f\'\u00dd\t\'\u0001(\u0001(\u0003(\u00e1\b(\u0001"+
		"(\u0001(\u0005(\u00e5\b(\n(\f(\u00e8\t(\u0001(\u0001(\u0003(\u00ec\b("+
		"\u0001(\u0004(\u00ef\b(\u000b(\f(\u00f0\u0003(\u00f3\b(\u0001)\u0001)"+
		"\u0001*\u0001*\u0001+\u0001+\u0005+\u00fb\b+\n+\f+\u00fe\t+\u0001,\u0001"+
		",\u0001,\u0001,\u0005,\u0104\b,\n,\f,\u0107\t,\u0001,\u0001,\u0001,\u0001"+
		",\u0001-\u0001-\u0001-\u0001-\u0005-\u0111\b-\n-\f-\u0114\t-\u0001-\u0001"+
		"-\u0001-\u0001-\u0001-\u0001.\u0004.\u011c\b.\u000b.\f.\u011d\u0001.\u0001"+
		".\u0002\u0105\u0112\u0000/\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004"+
		"\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017"+
		"\f\u0019\r\u001b\u000e\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'"+
		"\u0014)\u0015+\u0016-\u0017/\u00181\u00193\u001a5\u001b7\u001c9\u001d"+
		";\u001e=\u001f? A!C\"E#G$I%K&M\'O(Q)S\u0000U\u0000W*Y+[,]-\u0001\u0000"+
		"\u0006\u0002\u0000EEee\u0001\u000009\u0001\u000019\u0003\u0000AZ__az\u0005"+
		"\u0000..09AZ__az\u0003\u0000\t\n\r\r  \u0128\u0000\u0001\u0001\u0000\u0000"+
		"\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000"+
		"\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000"+
		"\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000"+
		"\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000"+
		"\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000\u0000\u0000"+
		"\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000\u0000\u0000"+
		"\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000\u0000\u0000"+
		"\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000\u0000#\u0001"+
		"\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'\u0001\u0000"+
		"\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000\u0000\u0000"+
		"\u0000-\u0001\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000\u00001"+
		"\u0001\u0000\u0000\u0000\u00003\u0001\u0000\u0000\u0000\u00005\u0001\u0000"+
		"\u0000\u0000\u00007\u0001\u0000\u0000\u0000\u00009\u0001\u0000\u0000\u0000"+
		"\u0000;\u0001\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000\u0000?"+
		"\u0001\u0000\u0000\u0000\u0000A\u0001\u0000\u0000\u0000\u0000C\u0001\u0000"+
		"\u0000\u0000\u0000E\u0001\u0000\u0000\u0000\u0000G\u0001\u0000\u0000\u0000"+
		"\u0000I\u0001\u0000\u0000\u0000\u0000K\u0001\u0000\u0000\u0000\u0000M"+
		"\u0001\u0000\u0000\u0000\u0000O\u0001\u0000\u0000\u0000\u0000Q\u0001\u0000"+
		"\u0000\u0000\u0000W\u0001\u0000\u0000\u0000\u0000Y\u0001\u0000\u0000\u0000"+
		"\u0000[\u0001\u0000\u0000\u0000\u0000]\u0001\u0000\u0000\u0000\u0001_"+
		"\u0001\u0000\u0000\u0000\u0003a\u0001\u0000\u0000\u0000\u0005c\u0001\u0000"+
		"\u0000\u0000\u0007e\u0001\u0000\u0000\u0000\tg\u0001\u0000\u0000\u0000"+
		"\u000bi\u0001\u0000\u0000\u0000\rk\u0001\u0000\u0000\u0000\u000fm\u0001"+
		"\u0000\u0000\u0000\u0011o\u0001\u0000\u0000\u0000\u0013q\u0001\u0000\u0000"+
		"\u0000\u0015s\u0001\u0000\u0000\u0000\u0017u\u0001\u0000\u0000\u0000\u0019"+
		"w\u0001\u0000\u0000\u0000\u001by\u0001\u0000\u0000\u0000\u001d{\u0001"+
		"\u0000\u0000\u0000\u001f}\u0001\u0000\u0000\u0000!\u007f\u0001\u0000\u0000"+
		"\u0000#\u0083\u0001\u0000\u0000\u0000%\u0087\u0001\u0000\u0000\u0000\'"+
		"\u008c\u0001\u0000\u0000\u0000)\u0090\u0001\u0000\u0000\u0000+\u0096\u0001"+
		"\u0000\u0000\u0000-\u009a\u0001\u0000\u0000\u0000/\u009e\u0001\u0000\u0000"+
		"\u00001\u00a2\u0001\u0000\u0000\u00003\u00a6\u0001\u0000\u0000\u00005"+
		"\u00ab\u0001\u0000\u0000\u00007\u00b0\u0001\u0000\u0000\u00009\u00b7\u0001"+
		"\u0000\u0000\u0000;\u00bb\u0001\u0000\u0000\u0000=\u00bf\u0001\u0000\u0000"+
		"\u0000?\u00c2\u0001\u0000\u0000\u0000A\u00c5\u0001\u0000\u0000\u0000C"+
		"\u00c8\u0001\u0000\u0000\u0000E\u00ca\u0001\u0000\u0000\u0000G\u00cc\u0001"+
		"\u0000\u0000\u0000I\u00cf\u0001\u0000\u0000\u0000K\u00d2\u0001\u0000\u0000"+
		"\u0000M\u00d5\u0001\u0000\u0000\u0000O\u00d7\u0001\u0000\u0000\u0000Q"+
		"\u00e0\u0001\u0000\u0000\u0000S\u00f4\u0001\u0000\u0000\u0000U\u00f6\u0001"+
		"\u0000\u0000\u0000W\u00f8\u0001\u0000\u0000\u0000Y\u00ff\u0001\u0000\u0000"+
		"\u0000[\u010c\u0001\u0000\u0000\u0000]\u011b\u0001\u0000\u0000\u0000_"+
		"`\u0005(\u0000\u0000`\u0002\u0001\u0000\u0000\u0000ab\u0005)\u0000\u0000"+
		"b\u0004\u0001\u0000\u0000\u0000cd\u0005{\u0000\u0000d\u0006\u0001\u0000"+
		"\u0000\u0000ef\u0005,\u0000\u0000f\b\u0001\u0000\u0000\u0000gh\u0005}"+
		"\u0000\u0000h\n\u0001\u0000\u0000\u0000ij\u0005[\u0000\u0000j\f\u0001"+
		"\u0000\u0000\u0000kl\u0005]\u0000\u0000l\u000e\u0001\u0000\u0000\u0000"+
		"mn\u0005:\u0000\u0000n\u0010\u0001\u0000\u0000\u0000op\u0005!\u0000\u0000"+
		"p\u0012\u0001\u0000\u0000\u0000qr\u0005?\u0000\u0000r\u0014\u0001\u0000"+
		"\u0000\u0000st\u0005+\u0000\u0000t\u0016\u0001\u0000\u0000\u0000uv\u0005"+
		"-\u0000\u0000v\u0018\u0001\u0000\u0000\u0000wx\u0005*\u0000\u0000x\u001a"+
		"\u0001\u0000\u0000\u0000yz\u0005/\u0000\u0000z\u001c\u0001\u0000\u0000"+
		"\u0000{|\u0005%\u0000\u0000|\u001e\u0001\u0000\u0000\u0000}~\u0005^\u0000"+
		"\u0000~ \u0001\u0000\u0000\u0000\u007f\u0080\u0005e\u0000\u0000\u0080"+
		"\u0081\u0005x\u0000\u0000\u0081\u0082\u0005p\u0000\u0000\u0082\"\u0001"+
		"\u0000\u0000\u0000\u0083\u0084\u0005l\u0000\u0000\u0084\u0085\u0005o\u0000"+
		"\u0000\u0085\u0086\u0005g\u0000\u0000\u0086$\u0001\u0000\u0000\u0000\u0087"+
		"\u0088\u0005s\u0000\u0000\u0088\u0089\u0005q\u0000\u0000\u0089\u008a\u0005"+
		"r\u0000\u0000\u008a\u008b\u0005t\u0000\u0000\u008b&\u0001\u0000\u0000"+
		"\u0000\u008c\u008d\u0005s\u0000\u0000\u008d\u008e\u0005u\u0000\u0000\u008e"+
		"\u008f\u0005m\u0000\u0000\u008f(\u0001\u0000\u0000\u0000\u0090\u0091\u0005"+
		"t\u0000\u0000\u0091\u0092\u0005h\u0000\u0000\u0092\u0093\u0005e\u0000"+
		"\u0000\u0093\u0094\u0005t\u0000\u0000\u0094\u0095\u0005a\u0000\u0000\u0095"+
		"*\u0001\u0000\u0000\u0000\u0096\u0097\u0005a\u0000\u0000\u0097\u0098\u0005"+
		"b\u0000\u0000\u0098\u0099\u0005s\u0000\u0000\u0099,\u0001\u0000\u0000"+
		"\u0000\u009a\u009b\u0005m\u0000\u0000\u009b\u009c\u0005i\u0000\u0000\u009c"+
		"\u009d\u0005n\u0000\u0000\u009d.\u0001\u0000\u0000\u0000\u009e\u009f\u0005"+
		"m\u0000\u0000\u009f\u00a0\u0005a\u0000\u0000\u00a0\u00a1\u0005x\u0000"+
		"\u0000\u00a10\u0001\u0000\u0000\u0000\u00a2\u00a3\u0005l\u0000\u0000\u00a3"+
		"\u00a4\u0005e\u0000\u0000\u00a4\u00a5\u0005n\u0000\u0000\u00a52\u0001"+
		"\u0000\u0000\u0000\u00a6\u00a7\u0005s\u0000\u0000\u00a7\u00a8\u0005o\u0000"+
		"\u0000\u00a8\u00a9\u0005r\u0000\u0000\u00a9\u00aa\u0005t\u0000\u0000\u00aa"+
		"4\u0001\u0000\u0000\u0000\u00ab\u00ac\u0005d\u0000\u0000\u00ac\u00ad\u0005"+
		"i\u0000\u0000\u00ad\u00ae\u0005f\u0000\u0000\u00ae\u00af\u0005f\u0000"+
		"\u0000\u00af6\u0001\u0000\u0000\u0000\u00b0\u00b1\u0005c\u0000\u0000\u00b1"+
		"\u00b2\u0005u\u0000\u0000\u00b2\u00b3\u0005m\u0000\u0000\u00b3\u00b4\u0005"+
		"s\u0000\u0000\u00b4\u00b5\u0005u\u0000\u0000\u00b5\u00b6\u0005m\u0000"+
		"\u0000\u00b68\u0001\u0000\u0000\u0000\u00b7\u00b8\u0005s\u0000\u0000\u00b8"+
		"\u00b9\u0005i\u0000\u0000\u00b9\u00ba\u0005n\u0000\u0000\u00ba:\u0001"+
		"\u0000\u0000\u0000\u00bb\u00bc\u0005c\u0000\u0000\u00bc\u00bd\u0005o\u0000"+
		"\u0000\u00bd\u00be\u0005s\u0000\u0000\u00be<\u0001\u0000\u0000\u0000\u00bf"+
		"\u00c0\u0005&\u0000\u0000\u00c0\u00c1\u0005&\u0000\u0000\u00c1>\u0001"+
		"\u0000\u0000\u0000\u00c2\u00c3\u0005|\u0000\u0000\u00c3\u00c4\u0005|\u0000"+
		"\u0000\u00c4@\u0001\u0000\u0000\u0000\u00c5\u00c6\u0005=\u0000\u0000\u00c6"+
		"\u00c7\u0005=\u0000\u0000\u00c7B\u0001\u0000\u0000\u0000\u00c8\u00c9\u0005"+
		">\u0000\u0000\u00c9D\u0001\u0000\u0000\u0000\u00ca\u00cb\u0005<\u0000"+
		"\u0000\u00cbF\u0001\u0000\u0000\u0000\u00cc\u00cd\u0005>\u0000\u0000\u00cd"+
		"\u00ce\u0005=\u0000\u0000\u00ceH\u0001\u0000\u0000\u0000\u00cf\u00d0\u0005"+
		"<\u0000\u0000\u00d0\u00d1\u0005=\u0000\u0000\u00d1J\u0001\u0000\u0000"+
		"\u0000\u00d2\u00d3\u0005!\u0000\u0000\u00d3\u00d4\u0005=\u0000\u0000\u00d4"+
		"L\u0001\u0000\u0000\u0000\u00d5\u00d6\u00050\u0000\u0000\u00d6N\u0001"+
		"\u0000\u0000\u0000\u00d7\u00db\u0003U*\u0000\u00d8\u00da\u0003S)\u0000"+
		"\u00d9\u00d8\u0001\u0000\u0000\u0000\u00da\u00dd\u0001\u0000\u0000\u0000"+
		"\u00db\u00d9\u0001\u0000\u0000\u0000\u00db\u00dc\u0001\u0000\u0000\u0000"+
		"\u00dcP\u0001\u0000\u0000\u0000\u00dd\u00db\u0001\u0000\u0000\u0000\u00de"+
		"\u00e1\u00050\u0000\u0000\u00df\u00e1\u0003O\'\u0000\u00e0\u00de\u0001"+
		"\u0000\u0000\u0000\u00e0\u00df\u0001\u0000\u0000\u0000\u00e1\u00e2\u0001"+
		"\u0000\u0000\u0000\u00e2\u00e6\u0005.\u0000\u0000\u00e3\u00e5\u0003S)"+
		"\u0000\u00e4\u00e3\u0001\u0000\u0000\u0000\u00e5\u00e8\u0001\u0000\u0000"+
		"\u0000\u00e6\u00e4\u0001\u0000\u0000\u0000\u00e6\u00e7\u0001\u0000\u0000"+
		"\u0000\u00e7\u00f2\u0001\u0000\u0000\u0000\u00e8\u00e6\u0001\u0000\u0000"+
		"\u0000\u00e9\u00eb\u0007\u0000\u0000\u0000\u00ea\u00ec\u0005-\u0000\u0000"+
		"\u00eb\u00ea\u0001\u0000\u0000\u0000\u00eb\u00ec\u0001\u0000\u0000\u0000"+
		"\u00ec\u00ee\u0001\u0000\u0000\u0000\u00ed\u00ef\u0003S)\u0000\u00ee\u00ed"+
		"\u0001\u0000\u0000\u0000\u00ef\u00f0\u0001\u0000\u0000\u0000\u00f0\u00ee"+
		"\u0001\u0000\u0000\u0000\u00f0\u00f1\u0001\u0000\u0000\u0000\u00f1\u00f3"+
		"\u0001\u0000\u0000\u0000\u00f2\u00e9\u0001\u0000\u0000\u0000\u00f2\u00f3"+
		"\u0001\u0000\u0000\u0000\u00f3R\u0001\u0000\u0000\u0000\u00f4\u00f5\u0007"+
		"\u0001\u0000\u0000\u00f5T\u0001\u0000\u0000\u0000\u00f6\u00f7\u0007\u0002"+
		"\u0000\u0000\u00f7V\u0001\u0000\u0000\u0000\u00f8\u00fc\u0007\u0003\u0000"+
		"\u0000\u00f9\u00fb\u0007\u0004\u0000\u0000\u00fa\u00f9\u0001\u0000\u0000"+
		"\u0000\u00fb\u00fe\u0001\u0000\u0000\u0000\u00fc\u00fa\u0001\u0000\u0000"+
		"\u0000\u00fc\u00fd\u0001\u0000\u0000\u0000\u00fdX\u0001\u0000\u0000\u0000"+
		"\u00fe\u00fc\u0001\u0000\u0000\u0000\u00ff\u0100\u0005/\u0000\u0000\u0100"+
		"\u0101\u0005/\u0000\u0000\u0101\u0105\u0001\u0000\u0000\u0000\u0102\u0104"+
		"\t\u0000\u0000\u0000\u0103\u0102\u0001\u0000\u0000\u0000\u0104\u0107\u0001"+
		"\u0000\u0000\u0000\u0105\u0106\u0001\u0000\u0000\u0000\u0105\u0103\u0001"+
		"\u0000\u0000\u0000\u0106\u0108\u0001\u0000\u0000\u0000\u0107\u0105\u0001"+
		"\u0000\u0000\u0000\u0108\u0109\u0005\n\u0000\u0000\u0109\u010a\u0001\u0000"+
		"\u0000\u0000\u010a\u010b\u0006,\u0000\u0000\u010bZ\u0001\u0000\u0000\u0000"+
		"\u010c\u010d\u0005/\u0000\u0000\u010d\u010e\u0005*\u0000\u0000\u010e\u0112"+
		"\u0001\u0000\u0000\u0000\u010f\u0111\t\u0000\u0000\u0000\u0110\u010f\u0001"+
		"\u0000\u0000\u0000\u0111\u0114\u0001\u0000\u0000\u0000\u0112\u0113\u0001"+
		"\u0000\u0000\u0000\u0112\u0110\u0001\u0000\u0000\u0000\u0113\u0115\u0001"+
		"\u0000\u0000\u0000\u0114\u0112\u0001\u0000\u0000\u0000\u0115\u0116\u0005"+
		"*\u0000\u0000\u0116\u0117\u0005/\u0000\u0000\u0117\u0118\u0001\u0000\u0000"+
		"\u0000\u0118\u0119\u0006-\u0000\u0000\u0119\\\u0001\u0000\u0000\u0000"+
		"\u011a\u011c\u0007\u0005\u0000\u0000\u011b\u011a\u0001\u0000\u0000\u0000"+
		"\u011c\u011d\u0001\u0000\u0000\u0000\u011d\u011b\u0001\u0000\u0000\u0000"+
		"\u011d\u011e\u0001\u0000\u0000\u0000\u011e\u011f\u0001\u0000\u0000\u0000"+
		"\u011f\u0120\u0006.\u0000\u0000\u0120^\u0001\u0000\u0000\u0000\u000b\u0000"+
		"\u00db\u00e0\u00e6\u00eb\u00f0\u00f2\u00fc\u0105\u0112\u011d\u0001\u0006"+
		"\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}