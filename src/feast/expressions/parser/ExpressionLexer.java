// Generated from /Users/vaughant/code/beast_and_friends/feast/src/feast/expressions/parser/Expression.g4 by ANTLR 4.8
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
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, ADD=11, SUB=12, MUL=13, DIV=14, MOD=15, POW=16, EXP=17, LOG=18, 
		SQRT=19, SUM=20, THETA=21, ABS=22, MIN=23, MAX=24, LEN=25, AND=26, OR=27, 
		EQ=28, GT=29, LT=30, GE=31, LE=32, NE=33, ZERO=34, NZINT=35, NNFLOAT=36, 
		IDENT=37, COMMENT_SINGLELINE=38, COMMENT_MULTILINE=39, WHITESPACE=40;
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
			"SUM", "THETA", "ABS", "MIN", "MAX", "LEN", "AND", "OR", "EQ", "GT", 
			"LT", "GE", "LE", "NE", "ZERO", "NZINT", "NNFLOAT", "D", "NZD", "IDENT", 
			"COMMENT_SINGLELINE", "COMMENT_MULTILINE", "WHITESPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'{'", "','", "'}'", "'['", "']'", "'!'", "'?'", 
			"':'", "'+'", "'-'", "'*'", "'/'", "'%'", "'^'", "'exp'", "'log'", "'sqrt'", 
			"'sum'", "'theta'", "'abs'", "'min'", "'max'", "'len'", "'&&'", "'||'", 
			"'=='", "'>'", "'<'", "'>='", "'<='", "'!='", "'0'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "ADD", 
			"SUB", "MUL", "DIV", "MOD", "POW", "EXP", "LOG", "SQRT", "SUM", "THETA", 
			"ABS", "MIN", "MAX", "LEN", "AND", "OR", "EQ", "GT", "LT", "GE", "LE", 
			"NE", "ZERO", "NZINT", "NNFLOAT", "IDENT", "COMMENT_SINGLELINE", "COMMENT_MULTILINE", 
			"WHITESPACE"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2*\u0100\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\3"+
		"\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n"+
		"\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3\21\3\22"+
		"\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3\25\3\25"+
		"\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3\30\3\30"+
		"\3\30\3\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\34"+
		"\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\37\3\37\3 \3 \3 \3!\3!\3!\3\"\3"+
		"\"\3\"\3#\3#\3$\3$\7$\u00b9\n$\f$\16$\u00bc\13$\3%\3%\5%\u00c0\n%\3%\3"+
		"%\7%\u00c4\n%\f%\16%\u00c7\13%\3%\3%\5%\u00cb\n%\3%\6%\u00ce\n%\r%\16"+
		"%\u00cf\5%\u00d2\n%\3&\3&\3\'\3\'\3(\3(\7(\u00da\n(\f(\16(\u00dd\13(\3"+
		")\3)\3)\3)\7)\u00e3\n)\f)\16)\u00e6\13)\3)\3)\3)\3)\3*\3*\3*\3*\7*\u00f0"+
		"\n*\f*\16*\u00f3\13*\3*\3*\3*\3*\3*\3+\6+\u00fb\n+\r+\16+\u00fc\3+\3+"+
		"\4\u00e4\u00f1\2,\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31"+
		"\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27-\30/\31\61\32\63\33\65"+
		"\34\67\359\36;\37= ?!A\"C#E$G%I&K\2M\2O\'Q(S)U*\3\2\b\4\2GGgg\3\2\62;"+
		"\3\2\63;\5\2C\\aac|\6\2\62;C\\aac|\5\2\13\f\17\17\"\"\2\u0107\2\3\3\2"+
		"\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17"+
		"\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2"+
		"\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3"+
		"\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3"+
		"\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2"+
		"=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3"+
		"\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\3W\3\2\2\2\5Y\3\2\2"+
		"\2\7[\3\2\2\2\t]\3\2\2\2\13_\3\2\2\2\ra\3\2\2\2\17c\3\2\2\2\21e\3\2\2"+
		"\2\23g\3\2\2\2\25i\3\2\2\2\27k\3\2\2\2\31m\3\2\2\2\33o\3\2\2\2\35q\3\2"+
		"\2\2\37s\3\2\2\2!u\3\2\2\2#w\3\2\2\2%{\3\2\2\2\'\177\3\2\2\2)\u0084\3"+
		"\2\2\2+\u0088\3\2\2\2-\u008e\3\2\2\2/\u0092\3\2\2\2\61\u0096\3\2\2\2\63"+
		"\u009a\3\2\2\2\65\u009e\3\2\2\2\67\u00a1\3\2\2\29\u00a4\3\2\2\2;\u00a7"+
		"\3\2\2\2=\u00a9\3\2\2\2?\u00ab\3\2\2\2A\u00ae\3\2\2\2C\u00b1\3\2\2\2E"+
		"\u00b4\3\2\2\2G\u00b6\3\2\2\2I\u00bf\3\2\2\2K\u00d3\3\2\2\2M\u00d5\3\2"+
		"\2\2O\u00d7\3\2\2\2Q\u00de\3\2\2\2S\u00eb\3\2\2\2U\u00fa\3\2\2\2WX\7*"+
		"\2\2X\4\3\2\2\2YZ\7+\2\2Z\6\3\2\2\2[\\\7}\2\2\\\b\3\2\2\2]^\7.\2\2^\n"+
		"\3\2\2\2_`\7\177\2\2`\f\3\2\2\2ab\7]\2\2b\16\3\2\2\2cd\7_\2\2d\20\3\2"+
		"\2\2ef\7#\2\2f\22\3\2\2\2gh\7A\2\2h\24\3\2\2\2ij\7<\2\2j\26\3\2\2\2kl"+
		"\7-\2\2l\30\3\2\2\2mn\7/\2\2n\32\3\2\2\2op\7,\2\2p\34\3\2\2\2qr\7\61\2"+
		"\2r\36\3\2\2\2st\7\'\2\2t \3\2\2\2uv\7`\2\2v\"\3\2\2\2wx\7g\2\2xy\7z\2"+
		"\2yz\7r\2\2z$\3\2\2\2{|\7n\2\2|}\7q\2\2}~\7i\2\2~&\3\2\2\2\177\u0080\7"+
		"u\2\2\u0080\u0081\7s\2\2\u0081\u0082\7t\2\2\u0082\u0083\7v\2\2\u0083("+
		"\3\2\2\2\u0084\u0085\7u\2\2\u0085\u0086\7w\2\2\u0086\u0087\7o\2\2\u0087"+
		"*\3\2\2\2\u0088\u0089\7v\2\2\u0089\u008a\7j\2\2\u008a\u008b\7g\2\2\u008b"+
		"\u008c\7v\2\2\u008c\u008d\7c\2\2\u008d,\3\2\2\2\u008e\u008f\7c\2\2\u008f"+
		"\u0090\7d\2\2\u0090\u0091\7u\2\2\u0091.\3\2\2\2\u0092\u0093\7o\2\2\u0093"+
		"\u0094\7k\2\2\u0094\u0095\7p\2\2\u0095\60\3\2\2\2\u0096\u0097\7o\2\2\u0097"+
		"\u0098\7c\2\2\u0098\u0099\7z\2\2\u0099\62\3\2\2\2\u009a\u009b\7n\2\2\u009b"+
		"\u009c\7g\2\2\u009c\u009d\7p\2\2\u009d\64\3\2\2\2\u009e\u009f\7(\2\2\u009f"+
		"\u00a0\7(\2\2\u00a0\66\3\2\2\2\u00a1\u00a2\7~\2\2\u00a2\u00a3\7~\2\2\u00a3"+
		"8\3\2\2\2\u00a4\u00a5\7?\2\2\u00a5\u00a6\7?\2\2\u00a6:\3\2\2\2\u00a7\u00a8"+
		"\7@\2\2\u00a8<\3\2\2\2\u00a9\u00aa\7>\2\2\u00aa>\3\2\2\2\u00ab\u00ac\7"+
		"@\2\2\u00ac\u00ad\7?\2\2\u00ad@\3\2\2\2\u00ae\u00af\7>\2\2\u00af\u00b0"+
		"\7?\2\2\u00b0B\3\2\2\2\u00b1\u00b2\7#\2\2\u00b2\u00b3\7?\2\2\u00b3D\3"+
		"\2\2\2\u00b4\u00b5\7\62\2\2\u00b5F\3\2\2\2\u00b6\u00ba\5M\'\2\u00b7\u00b9"+
		"\5K&\2\u00b8\u00b7\3\2\2\2\u00b9\u00bc\3\2\2\2\u00ba\u00b8\3\2\2\2\u00ba"+
		"\u00bb\3\2\2\2\u00bbH\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bd\u00c0\7\62\2\2"+
		"\u00be\u00c0\5G$\2\u00bf\u00bd\3\2\2\2\u00bf\u00be\3\2\2\2\u00c0\u00c1"+
		"\3\2\2\2\u00c1\u00c5\7\60\2\2\u00c2\u00c4\5K&\2\u00c3\u00c2\3\2\2\2\u00c4"+
		"\u00c7\3\2\2\2\u00c5\u00c3\3\2\2\2\u00c5\u00c6\3\2\2\2\u00c6\u00d1\3\2"+
		"\2\2\u00c7\u00c5\3\2\2\2\u00c8\u00ca\t\2\2\2\u00c9\u00cb\7/\2\2\u00ca"+
		"\u00c9\3\2\2\2\u00ca\u00cb\3\2\2\2\u00cb\u00cd\3\2\2\2\u00cc\u00ce\5K"+
		"&\2\u00cd\u00cc\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf\u00cd\3\2\2\2\u00cf"+
		"\u00d0\3\2\2\2\u00d0\u00d2\3\2\2\2\u00d1\u00c8\3\2\2\2\u00d1\u00d2\3\2"+
		"\2\2\u00d2J\3\2\2\2\u00d3\u00d4\t\3\2\2\u00d4L\3\2\2\2\u00d5\u00d6\t\4"+
		"\2\2\u00d6N\3\2\2\2\u00d7\u00db\t\5\2\2\u00d8\u00da\t\6\2\2\u00d9\u00d8"+
		"\3\2\2\2\u00da\u00dd\3\2\2\2\u00db\u00d9\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc"+
		"P\3\2\2\2\u00dd\u00db\3\2\2\2\u00de\u00df\7\61\2\2\u00df\u00e0\7\61\2"+
		"\2\u00e0\u00e4\3\2\2\2\u00e1\u00e3\13\2\2\2\u00e2\u00e1\3\2\2\2\u00e3"+
		"\u00e6\3\2\2\2\u00e4\u00e5\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e5\u00e7\3\2"+
		"\2\2\u00e6\u00e4\3\2\2\2\u00e7\u00e8\7\f\2\2\u00e8\u00e9\3\2\2\2\u00e9"+
		"\u00ea\b)\2\2\u00eaR\3\2\2\2\u00eb\u00ec\7\61\2\2\u00ec\u00ed\7,\2\2\u00ed"+
		"\u00f1\3\2\2\2\u00ee\u00f0\13\2\2\2\u00ef\u00ee\3\2\2\2\u00f0\u00f3\3"+
		"\2\2\2\u00f1\u00f2\3\2\2\2\u00f1\u00ef\3\2\2\2\u00f2\u00f4\3\2\2\2\u00f3"+
		"\u00f1\3\2\2\2\u00f4\u00f5\7,\2\2\u00f5\u00f6\7\61\2\2\u00f6\u00f7\3\2"+
		"\2\2\u00f7\u00f8\b*\2\2\u00f8T\3\2\2\2\u00f9\u00fb\t\7\2\2\u00fa\u00f9"+
		"\3\2\2\2\u00fb\u00fc\3\2\2\2\u00fc\u00fa\3\2\2\2\u00fc\u00fd\3\2\2\2\u00fd"+
		"\u00fe\3\2\2\2\u00fe\u00ff\b+\2\2\u00ffV\3\2\2\2\r\2\u00ba\u00bf\u00c5"+
		"\u00ca\u00cf\u00d1\u00db\u00e4\u00f1\u00fc\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}