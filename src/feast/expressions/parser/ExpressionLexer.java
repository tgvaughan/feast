// Generated from /Users/vaughant/code/beast_and_friends/feast/src/feast/expressions/parser/Expression.g4 by ANTLR 4.9.1
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
	static { RuntimeMetaData.checkVersion("4.9.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, ADD=11, SUB=12, MUL=13, DIV=14, MOD=15, POW=16, EXP=17, LOG=18, 
		SQRT=19, SUM=20, THETA=21, ABS=22, MIN=23, MAX=24, LEN=25, SORT=26, AND=27, 
		OR=28, EQ=29, GT=30, LT=31, GE=32, LE=33, NE=34, ZERO=35, NZINT=36, NNFLOAT=37, 
		IDENT=38, COMMENT_SINGLELINE=39, COMMENT_MULTILINE=40, WHITESPACE=41;
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
			"SUM", "THETA", "ABS", "MIN", "MAX", "LEN", "SORT", "AND", "OR", "EQ", 
			"GT", "LT", "GE", "LE", "NE", "ZERO", "NZINT", "NNFLOAT", "D", "NZD", 
			"IDENT", "COMMENT_SINGLELINE", "COMMENT_MULTILINE", "WHITESPACE"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'('", "')'", "'{'", "','", "'}'", "'['", "']'", "':'", "'!'", 
			"'?'", "'+'", "'-'", "'*'", "'/'", "'%'", "'^'", "'exp'", "'log'", "'sqrt'", 
			"'sum'", "'theta'", "'abs'", "'min'", "'max'", "'len'", "'sort'", "'&&'", 
			"'||'", "'=='", "'>'", "'<'", "'>='", "'<='", "'!='", "'0'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, "ADD", 
			"SUB", "MUL", "DIV", "MOD", "POW", "EXP", "LOG", "SQRT", "SUM", "THETA", 
			"ABS", "MIN", "MAX", "LEN", "SORT", "AND", "OR", "EQ", "GT", "LT", "GE", 
			"LE", "NE", "ZERO", "NZINT", "NNFLOAT", "IDENT", "COMMENT_SINGLELINE", 
			"COMMENT_MULTILINE", "WHITESPACE"
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2+\u0107\b\1\4\2\t"+
		"\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3"+
		"\n\3\n\3\13\3\13\3\f\3\f\3\r\3\r\3\16\3\16\3\17\3\17\3\20\3\20\3\21\3"+
		"\21\3\22\3\22\3\22\3\22\3\23\3\23\3\23\3\23\3\24\3\24\3\24\3\24\3\24\3"+
		"\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\26\3\26\3\27\3\27\3\27\3\27\3"+
		"\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3\32\3\33\3\33\3"+
		"\33\3\33\3\33\3\34\3\34\3\34\3\35\3\35\3\35\3\36\3\36\3\36\3\37\3\37\3"+
		" \3 \3!\3!\3!\3\"\3\"\3\"\3#\3#\3#\3$\3$\3%\3%\7%\u00c0\n%\f%\16%\u00c3"+
		"\13%\3&\3&\5&\u00c7\n&\3&\3&\7&\u00cb\n&\f&\16&\u00ce\13&\3&\3&\5&\u00d2"+
		"\n&\3&\6&\u00d5\n&\r&\16&\u00d6\5&\u00d9\n&\3\'\3\'\3(\3(\3)\3)\7)\u00e1"+
		"\n)\f)\16)\u00e4\13)\3*\3*\3*\3*\7*\u00ea\n*\f*\16*\u00ed\13*\3*\3*\3"+
		"*\3*\3+\3+\3+\3+\7+\u00f7\n+\f+\16+\u00fa\13+\3+\3+\3+\3+\3+\3,\6,\u0102"+
		"\n,\r,\16,\u0103\3,\3,\4\u00eb\u00f8\2-\3\3\5\4\7\5\t\6\13\7\r\b\17\t"+
		"\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25)\26+\27"+
		"-\30/\31\61\32\63\33\65\34\67\359\36;\37= ?!A\"C#E$G%I&K\'M\2O\2Q(S)U"+
		"*W+\3\2\b\4\2GGgg\3\2\62;\3\2\63;\5\2C\\aac|\7\2\60\60\62;C\\aac|\5\2"+
		"\13\f\17\17\"\"\2\u010e\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2"+
		"\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2"+
		"\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3"+
		"\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2"+
		"\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2\2K\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2"+
		"U\3\2\2\2\2W\3\2\2\2\3Y\3\2\2\2\5[\3\2\2\2\7]\3\2\2\2\t_\3\2\2\2\13a\3"+
		"\2\2\2\rc\3\2\2\2\17e\3\2\2\2\21g\3\2\2\2\23i\3\2\2\2\25k\3\2\2\2\27m"+
		"\3\2\2\2\31o\3\2\2\2\33q\3\2\2\2\35s\3\2\2\2\37u\3\2\2\2!w\3\2\2\2#y\3"+
		"\2\2\2%}\3\2\2\2\'\u0081\3\2\2\2)\u0086\3\2\2\2+\u008a\3\2\2\2-\u0090"+
		"\3\2\2\2/\u0094\3\2\2\2\61\u0098\3\2\2\2\63\u009c\3\2\2\2\65\u00a0\3\2"+
		"\2\2\67\u00a5\3\2\2\29\u00a8\3\2\2\2;\u00ab\3\2\2\2=\u00ae\3\2\2\2?\u00b0"+
		"\3\2\2\2A\u00b2\3\2\2\2C\u00b5\3\2\2\2E\u00b8\3\2\2\2G\u00bb\3\2\2\2I"+
		"\u00bd\3\2\2\2K\u00c6\3\2\2\2M\u00da\3\2\2\2O\u00dc\3\2\2\2Q\u00de\3\2"+
		"\2\2S\u00e5\3\2\2\2U\u00f2\3\2\2\2W\u0101\3\2\2\2YZ\7*\2\2Z\4\3\2\2\2"+
		"[\\\7+\2\2\\\6\3\2\2\2]^\7}\2\2^\b\3\2\2\2_`\7.\2\2`\n\3\2\2\2ab\7\177"+
		"\2\2b\f\3\2\2\2cd\7]\2\2d\16\3\2\2\2ef\7_\2\2f\20\3\2\2\2gh\7<\2\2h\22"+
		"\3\2\2\2ij\7#\2\2j\24\3\2\2\2kl\7A\2\2l\26\3\2\2\2mn\7-\2\2n\30\3\2\2"+
		"\2op\7/\2\2p\32\3\2\2\2qr\7,\2\2r\34\3\2\2\2st\7\61\2\2t\36\3\2\2\2uv"+
		"\7\'\2\2v \3\2\2\2wx\7`\2\2x\"\3\2\2\2yz\7g\2\2z{\7z\2\2{|\7r\2\2|$\3"+
		"\2\2\2}~\7n\2\2~\177\7q\2\2\177\u0080\7i\2\2\u0080&\3\2\2\2\u0081\u0082"+
		"\7u\2\2\u0082\u0083\7s\2\2\u0083\u0084\7t\2\2\u0084\u0085\7v\2\2\u0085"+
		"(\3\2\2\2\u0086\u0087\7u\2\2\u0087\u0088\7w\2\2\u0088\u0089\7o\2\2\u0089"+
		"*\3\2\2\2\u008a\u008b\7v\2\2\u008b\u008c\7j\2\2\u008c\u008d\7g\2\2\u008d"+
		"\u008e\7v\2\2\u008e\u008f\7c\2\2\u008f,\3\2\2\2\u0090\u0091\7c\2\2\u0091"+
		"\u0092\7d\2\2\u0092\u0093\7u\2\2\u0093.\3\2\2\2\u0094\u0095\7o\2\2\u0095"+
		"\u0096\7k\2\2\u0096\u0097\7p\2\2\u0097\60\3\2\2\2\u0098\u0099\7o\2\2\u0099"+
		"\u009a\7c\2\2\u009a\u009b\7z\2\2\u009b\62\3\2\2\2\u009c\u009d\7n\2\2\u009d"+
		"\u009e\7g\2\2\u009e\u009f\7p\2\2\u009f\64\3\2\2\2\u00a0\u00a1\7u\2\2\u00a1"+
		"\u00a2\7q\2\2\u00a2\u00a3\7t\2\2\u00a3\u00a4\7v\2\2\u00a4\66\3\2\2\2\u00a5"+
		"\u00a6\7(\2\2\u00a6\u00a7\7(\2\2\u00a78\3\2\2\2\u00a8\u00a9\7~\2\2\u00a9"+
		"\u00aa\7~\2\2\u00aa:\3\2\2\2\u00ab\u00ac\7?\2\2\u00ac\u00ad\7?\2\2\u00ad"+
		"<\3\2\2\2\u00ae\u00af\7@\2\2\u00af>\3\2\2\2\u00b0\u00b1\7>\2\2\u00b1@"+
		"\3\2\2\2\u00b2\u00b3\7@\2\2\u00b3\u00b4\7?\2\2\u00b4B\3\2\2\2\u00b5\u00b6"+
		"\7>\2\2\u00b6\u00b7\7?\2\2\u00b7D\3\2\2\2\u00b8\u00b9\7#\2\2\u00b9\u00ba"+
		"\7?\2\2\u00baF\3\2\2\2\u00bb\u00bc\7\62\2\2\u00bcH\3\2\2\2\u00bd\u00c1"+
		"\5O(\2\u00be\u00c0\5M\'\2\u00bf\u00be\3\2\2\2\u00c0\u00c3\3\2\2\2\u00c1"+
		"\u00bf\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2J\3\2\2\2\u00c3\u00c1\3\2\2\2"+
		"\u00c4\u00c7\7\62\2\2\u00c5\u00c7\5I%\2\u00c6\u00c4\3\2\2\2\u00c6\u00c5"+
		"\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00cc\7\60\2\2\u00c9\u00cb\5M\'\2\u00ca"+
		"\u00c9\3\2\2\2\u00cb\u00ce\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cc\u00cd\3\2"+
		"\2\2\u00cd\u00d8\3\2\2\2\u00ce\u00cc\3\2\2\2\u00cf\u00d1\t\2\2\2\u00d0"+
		"\u00d2\7/\2\2\u00d1\u00d0\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d2\u00d4\3\2"+
		"\2\2\u00d3\u00d5\5M\'\2\u00d4\u00d3\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6"+
		"\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00d9\3\2\2\2\u00d8\u00cf\3\2"+
		"\2\2\u00d8\u00d9\3\2\2\2\u00d9L\3\2\2\2\u00da\u00db\t\3\2\2\u00dbN\3\2"+
		"\2\2\u00dc\u00dd\t\4\2\2\u00ddP\3\2\2\2\u00de\u00e2\t\5\2\2\u00df\u00e1"+
		"\t\6\2\2\u00e0\u00df\3\2\2\2\u00e1\u00e4\3\2\2\2\u00e2\u00e0\3\2\2\2\u00e2"+
		"\u00e3\3\2\2\2\u00e3R\3\2\2\2\u00e4\u00e2\3\2\2\2\u00e5\u00e6\7\61\2\2"+
		"\u00e6\u00e7\7\61\2\2\u00e7\u00eb\3\2\2\2\u00e8\u00ea\13\2\2\2\u00e9\u00e8"+
		"\3\2\2\2\u00ea\u00ed\3\2\2\2\u00eb\u00ec\3\2\2\2\u00eb\u00e9\3\2\2\2\u00ec"+
		"\u00ee\3\2\2\2\u00ed\u00eb\3\2\2\2\u00ee\u00ef\7\f\2\2\u00ef\u00f0\3\2"+
		"\2\2\u00f0\u00f1\b*\2\2\u00f1T\3\2\2\2\u00f2\u00f3\7\61\2\2\u00f3\u00f4"+
		"\7,\2\2\u00f4\u00f8\3\2\2\2\u00f5\u00f7\13\2\2\2\u00f6\u00f5\3\2\2\2\u00f7"+
		"\u00fa\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f9\u00fb\3\2"+
		"\2\2\u00fa\u00f8\3\2\2\2\u00fb\u00fc\7,\2\2\u00fc\u00fd\7\61\2\2\u00fd"+
		"\u00fe\3\2\2\2\u00fe\u00ff\b+\2\2\u00ffV\3\2\2\2\u0100\u0102\t\7\2\2\u0101"+
		"\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103\u0101\3\2\2\2\u0103\u0104\3\2"+
		"\2\2\u0104\u0105\3\2\2\2\u0105\u0106\b,\2\2\u0106X\3\2\2\2\r\2\u00c1\u00c6"+
		"\u00cc\u00d1\u00d6\u00d8\u00e2\u00eb\u00f8\u0103\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}