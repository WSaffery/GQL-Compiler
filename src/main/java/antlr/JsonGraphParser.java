// Generated from JsonGraphParser.g4 by ANTLR 4.10.1

    package antlr;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class JsonGraphParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.10.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		ON=1, COMMENT=2, LINE_COMMENT=3, WS=4, UNSIGNED_INTEGER=5, SIGNED_INTEGER=6, 
		SIGNED_FIXED_POINT=7, SIGNED_FLOAT=8, KEYWORD=9, TRUTH_VALUE=10, ABS=11, 
		ACOS=12, ADD=13, AGGREGATE=14, ALIAS=15, ALL=16, AND=17, ANY=18, ARRAY=19, 
		AS=20, ASC=21, ASCENDING=22, ASIN=23, AT=24, ATAN=25, AVG=26, BINARY=27, 
		BOOLEAN=28, BOTH=29, BY=30, CALL=31, CASE=32, CATALOG=33, CEIL=34, CEILING=35, 
		CHARACHTER=36, CHARACTER_LENGTH=37, CLEAR=38, CLONE=39, CLOSE=40, COLLECT=41, 
		COMMIT=42, CONSTRAINT=43, CONSTANT=44, CONSTRCUT=45, COPY=46, COS=47, 
		COSH=48, COST=49, COT=50, COUNT=51, CURRENT_DATE=52, CURRENT_GRAPH=53, 
		CURRENT_PROPERTY_GRAPH=54, CURRENT_ROLE=55, CURRENT_SCHEMA=56, CURRENT_TIME=57, 
		CURRENT_TIMESTAMP=58, CURRENT_USER=59, CREATE=60, DATE=61, DATETIME=62, 
		DECIMAL=63, DEFAULT=64, DEGREES=65, DELETE=66, DETACH=67, DESC=68, DESCENDING=69, 
		DIRECTORIES=70, DIRECTORY=71, DISTINCT=72, DO=73, DROP=74, DURATION=75, 
		ELSE=76, END=77, ENDS=78, EMPTY_BINDING_TABLE=79, EMPTY_GRAPH=80, EMPTY_PROPERTY_GRAPH=81, 
		EMPTY_TABLE=82, EXCEPT=83, EXISTS=84, EXISTING=85, EXP=86, EXPLAIN=87, 
		FALSE=88, FILTER=89, FLOAT=90, FLOAT32=91, FLOAT64=92, FLOAT128=93, FLOOR=94, 
		FOR=95, FROM=96, FUNCTOPM=97, FUNCTIONS=98, GQLSTATUS=99, GROUP=100, HAVING=101, 
		HOME_GRAPH=102, HOME_PROPERTY_GRAPH=103, HOME_SCHEMA=104, IN=105, INSERT=106, 
		INTEGER=107, INTEGER8=108, INTEGER16=109, INTEGER32=110, INTEGER64=111, 
		INTEGER128=112, INTERSECT=113, IF=114, IS=115, KEEP=116, LEADING=117, 
		LEFT=118, LENGTH=119, LET=120, LIKE=121, LIMIT=122, LIST=123, LN=124, 
		LOCALDATETIME=125, LOCALTIME=126, LOCALTIMESTAMP=127, LOG=128, LOG10=129, 
		LOWER=130, MANDATORY=131, MAP=132, MATCH=133, MERGE=134, MAX=135, MIN=136, 
		MOD=137, MULTI=138, MULTIPLE=139, MULTISET=140, NEW=141, NOT=142, NORMALIZE=143, 
		NOTHING=144, NULL=145, OCTET_LENGTH=146, OF=147, OFFSET=148, OPTIONAL=149, 
		OR=150, ORDER=151, ORDERED=152, OTHERWISE=153, PARAMETER=154, PATH=155, 
		PATHS=156, PARTITION=157, POWER=158, PROCEDURE=159, PROCEDURES=160, PRODUCT=161, 
		PROFILE=162, PROJECT=163, QUERIES=164, QUERY=165, RADIANS=166, RCORD=167, 
		RECORDS=168, REFERENCE=169, REMOVE=170, RENAME=171, REPLACE=172, REQUIRE=173, 
		RESET=174, RESULT=175, RETURN=176, RIGHT=177, ROLLBACK=178, SCALAR=179, 
		SCHEMA=180, SCHEMAS=181, SCHEMATA=182, SELECT=183, SESSION=184, SET=185, 
		SIN=186, SINGLE=187, SINH=188, SQRT=189, START=190, STARTS=191, STRING=192, 
		SUBSTRING=193, SUM=194, TAN=195, TANH=196, THEN=197, TIME=198, TIMESTAMP=199, 
		TRAILING=200, TRIM=201, TRUE=202, TRUNCATE=203, UNION=204, UNIT=205, UNIT_BINDING_TABLE=206, 
		UNIT_TABLE=207, UNIQUE=208, UNNEST=209, UNKNOWN=210, UNWIND=211, UPPER=212, 
		USE=213, VALUE=214, VALUES=215, WHEN=216, WHERE=217, WITH=218, WORKING_GRAPH=219, 
		XOR=220, YIELD=221, ZERO=222, ACYCLIC=223, BINDING=224, CLASS_ORIGIN=225, 
		COMMAND_FUNCTION=226, COMMAND_FUNCTION_CODE=227, CONDITION_NUMBER=228, 
		CONNECTING=229, DESTINATION=230, DIRECTED=231, EDGE=232, EDGES=233, FINAL=234, 
		GRAPH=235, GRAPHS=236, GROUPS=237, INDEX=238, LABEL=239, LABELS=240, MESSAGE_TEXT=241, 
		MUTABLE=242, NFC=243, NFD=244, NFKC=245, NFKD=246, NODE=247, NODES=248, 
		NORMALIZED=249, NUMBER=250, ONLY=251, ORDINALITY=252, PATTERN=253, PATTERNS=254, 
		PROPERTY=255, PROPERTIES=256, READ=257, RELATIONSHIP=258, RELATIONSHIPS=259, 
		RETURNED_GQLSTATUS=260, SHORTEST=261, SIMPLE=262, SUBCLASS_ORIGIN=263, 
		TABLE=264, TABLES=265, TIES=266, TO=267, TRAIL=268, TRANSACTION=269, TYPE=270, 
		TYPES=271, UNDIRECTED=272, VERTEX=273, VERTICES=274, WALK=275, WRITE=276, 
		LEFT_ARROW=277, RIGHT_ARROW=278, BRACKET_RIGHT_ARROW=279, LEFT_ARROW_BRACKET=280, 
		DOUBLE_COLON=281, DOUBLE_MINUS_SIGN=282, DOUBLE_PERIOD=283, DOUBLE_SOLIDUS=284, 
		DOUBLED_GRAVE_ACCENT=285, MINUS_LEFT_BRACKET=286, RIGHT_BRACKET_MINUS=287, 
		TILDE_LEFT_BRACKET=288, RIGHT_BRACKET_TILDE=289, BRACKET_TILDE_RIGHT_ARROW=290, 
		LEFT_ARROW_TILDE_BRACKET=291, TILDE_RIGHT_ARROW=292, LEFT_ARROW_TILDE=293, 
		LEFT_MINUS_RIGHT=294, MINUS_SLASH=295, SLASH_MINUS=296, SLASH_MINUS_RIGHT=297, 
		LEFT_MINUS_SLASH=298, TILDE_SLASH=299, SLASH_TILDE=300, SLASH_TILDE_RIGHT=301, 
		LEFT_TILDE_SLASH=302, AMPERSAND=303, ASTERISK=304, CIRCUMFLEX=305, COLON=306, 
		COMMA=307, DOLLAR_SIGN=308, DOUBLE_QUOTE=309, EXCLAMATION_MARK=310, GRAVE_ACCENT=311, 
		MINUS_SIGN=312, PERCENT=313, PERIOD=314, PLUS_SIGN=315, QUESTION_MARK=316, 
		QUOTE=317, SEMICOLON=318, SOLIDUS=319, TILDE=320, VERTICAL_BAR=321, CONCATENATION=322, 
		MULTISET_ALTERNATION=323, GEQ=324, LEQ=325, NEQ=326, LT=327, GT=328, EQ=329, 
		LEFT_BRACE=330, RIGHT_BRACE=331, LEFT_BRACKET=332, RIGHT_BRACKET=333, 
		LEFT_ANGLE_BRACKET=334, RIGHT_ANGLE_BRACKET=335, LEFT_PAREN=336, RIGHT_PAREN=337, 
		NEWLINE=338, ID=339, WORD=340;
	public static final int
		RULE_jsonFile = 0, RULE_jsonNodeFile = 1, RULE_jsonEdgeFile = 2, RULE_node = 3, 
		RULE_edge = 4, RULE_nodeFiller = 5, RULE_edgeFiller = 6, RULE_identity = 7, 
		RULE_startNode = 8, RULE_endNode = 9, RULE_isDirected = 10, RULE_labels = 11, 
		RULE_properties = 12, RULE_pair = 13, RULE_quotedId = 14, RULE_value = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"jsonFile", "jsonNodeFile", "jsonEdgeFile", "node", "edge", "nodeFiller", 
			"edgeFiller", "identity", "startNode", "endNode", "isDirected", "labels", 
			"properties", "pair", "quotedId", "value"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, "'<-'", "'->'", "']->'", "'<-['", "'::'", "'--'", "'..'", "'//'", 
			"'``'", "'-['", "']-'", "'~['", "']~'", "']~>'", "'<~['", "'~>'", "'<~'", 
			"'<->'", "'-/'", "'/-'", "'/->'", "'<-/'", "'~/'", "'/~'", "'/~>'", "'<~/'", 
			"'&'", "'*'", "'^'", "':'", "','", "'$'", "'\"'", "'!'", "'`'", "'-'", 
			"'%'", "'.'", "'+'", "'?'", "'''", "';'", "'/'", "'~'", "'|'", "'||'", 
			"'|+|'", "'>='", "'<='", "'<>'", null, null, "'='", "'{'", "'}'", "'['", 
			"']'", "'<'", "'>'", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "ON", "COMMENT", "LINE_COMMENT", "WS", "UNSIGNED_INTEGER", "SIGNED_INTEGER", 
			"SIGNED_FIXED_POINT", "SIGNED_FLOAT", "KEYWORD", "TRUTH_VALUE", "ABS", 
			"ACOS", "ADD", "AGGREGATE", "ALIAS", "ALL", "AND", "ANY", "ARRAY", "AS", 
			"ASC", "ASCENDING", "ASIN", "AT", "ATAN", "AVG", "BINARY", "BOOLEAN", 
			"BOTH", "BY", "CALL", "CASE", "CATALOG", "CEIL", "CEILING", "CHARACHTER", 
			"CHARACTER_LENGTH", "CLEAR", "CLONE", "CLOSE", "COLLECT", "COMMIT", "CONSTRAINT", 
			"CONSTANT", "CONSTRCUT", "COPY", "COS", "COSH", "COST", "COT", "COUNT", 
			"CURRENT_DATE", "CURRENT_GRAPH", "CURRENT_PROPERTY_GRAPH", "CURRENT_ROLE", 
			"CURRENT_SCHEMA", "CURRENT_TIME", "CURRENT_TIMESTAMP", "CURRENT_USER", 
			"CREATE", "DATE", "DATETIME", "DECIMAL", "DEFAULT", "DEGREES", "DELETE", 
			"DETACH", "DESC", "DESCENDING", "DIRECTORIES", "DIRECTORY", "DISTINCT", 
			"DO", "DROP", "DURATION", "ELSE", "END", "ENDS", "EMPTY_BINDING_TABLE", 
			"EMPTY_GRAPH", "EMPTY_PROPERTY_GRAPH", "EMPTY_TABLE", "EXCEPT", "EXISTS", 
			"EXISTING", "EXP", "EXPLAIN", "FALSE", "FILTER", "FLOAT", "FLOAT32", 
			"FLOAT64", "FLOAT128", "FLOOR", "FOR", "FROM", "FUNCTOPM", "FUNCTIONS", 
			"GQLSTATUS", "GROUP", "HAVING", "HOME_GRAPH", "HOME_PROPERTY_GRAPH", 
			"HOME_SCHEMA", "IN", "INSERT", "INTEGER", "INTEGER8", "INTEGER16", "INTEGER32", 
			"INTEGER64", "INTEGER128", "INTERSECT", "IF", "IS", "KEEP", "LEADING", 
			"LEFT", "LENGTH", "LET", "LIKE", "LIMIT", "LIST", "LN", "LOCALDATETIME", 
			"LOCALTIME", "LOCALTIMESTAMP", "LOG", "LOG10", "LOWER", "MANDATORY", 
			"MAP", "MATCH", "MERGE", "MAX", "MIN", "MOD", "MULTI", "MULTIPLE", "MULTISET", 
			"NEW", "NOT", "NORMALIZE", "NOTHING", "NULL", "OCTET_LENGTH", "OF", "OFFSET", 
			"OPTIONAL", "OR", "ORDER", "ORDERED", "OTHERWISE", "PARAMETER", "PATH", 
			"PATHS", "PARTITION", "POWER", "PROCEDURE", "PROCEDURES", "PRODUCT", 
			"PROFILE", "PROJECT", "QUERIES", "QUERY", "RADIANS", "RCORD", "RECORDS", 
			"REFERENCE", "REMOVE", "RENAME", "REPLACE", "REQUIRE", "RESET", "RESULT", 
			"RETURN", "RIGHT", "ROLLBACK", "SCALAR", "SCHEMA", "SCHEMAS", "SCHEMATA", 
			"SELECT", "SESSION", "SET", "SIN", "SINGLE", "SINH", "SQRT", "START", 
			"STARTS", "STRING", "SUBSTRING", "SUM", "TAN", "TANH", "THEN", "TIME", 
			"TIMESTAMP", "TRAILING", "TRIM", "TRUE", "TRUNCATE", "UNION", "UNIT", 
			"UNIT_BINDING_TABLE", "UNIT_TABLE", "UNIQUE", "UNNEST", "UNKNOWN", "UNWIND", 
			"UPPER", "USE", "VALUE", "VALUES", "WHEN", "WHERE", "WITH", "WORKING_GRAPH", 
			"XOR", "YIELD", "ZERO", "ACYCLIC", "BINDING", "CLASS_ORIGIN", "COMMAND_FUNCTION", 
			"COMMAND_FUNCTION_CODE", "CONDITION_NUMBER", "CONNECTING", "DESTINATION", 
			"DIRECTED", "EDGE", "EDGES", "FINAL", "GRAPH", "GRAPHS", "GROUPS", "INDEX", 
			"LABEL", "LABELS", "MESSAGE_TEXT", "MUTABLE", "NFC", "NFD", "NFKC", "NFKD", 
			"NODE", "NODES", "NORMALIZED", "NUMBER", "ONLY", "ORDINALITY", "PATTERN", 
			"PATTERNS", "PROPERTY", "PROPERTIES", "READ", "RELATIONSHIP", "RELATIONSHIPS", 
			"RETURNED_GQLSTATUS", "SHORTEST", "SIMPLE", "SUBCLASS_ORIGIN", "TABLE", 
			"TABLES", "TIES", "TO", "TRAIL", "TRANSACTION", "TYPE", "TYPES", "UNDIRECTED", 
			"VERTEX", "VERTICES", "WALK", "WRITE", "LEFT_ARROW", "RIGHT_ARROW", "BRACKET_RIGHT_ARROW", 
			"LEFT_ARROW_BRACKET", "DOUBLE_COLON", "DOUBLE_MINUS_SIGN", "DOUBLE_PERIOD", 
			"DOUBLE_SOLIDUS", "DOUBLED_GRAVE_ACCENT", "MINUS_LEFT_BRACKET", "RIGHT_BRACKET_MINUS", 
			"TILDE_LEFT_BRACKET", "RIGHT_BRACKET_TILDE", "BRACKET_TILDE_RIGHT_ARROW", 
			"LEFT_ARROW_TILDE_BRACKET", "TILDE_RIGHT_ARROW", "LEFT_ARROW_TILDE", 
			"LEFT_MINUS_RIGHT", "MINUS_SLASH", "SLASH_MINUS", "SLASH_MINUS_RIGHT", 
			"LEFT_MINUS_SLASH", "TILDE_SLASH", "SLASH_TILDE", "SLASH_TILDE_RIGHT", 
			"LEFT_TILDE_SLASH", "AMPERSAND", "ASTERISK", "CIRCUMFLEX", "COLON", "COMMA", 
			"DOLLAR_SIGN", "DOUBLE_QUOTE", "EXCLAMATION_MARK", "GRAVE_ACCENT", "MINUS_SIGN", 
			"PERCENT", "PERIOD", "PLUS_SIGN", "QUESTION_MARK", "QUOTE", "SEMICOLON", 
			"SOLIDUS", "TILDE", "VERTICAL_BAR", "CONCATENATION", "MULTISET_ALTERNATION", 
			"GEQ", "LEQ", "NEQ", "LT", "GT", "EQ", "LEFT_BRACE", "RIGHT_BRACE", "LEFT_BRACKET", 
			"RIGHT_BRACKET", "LEFT_ANGLE_BRACKET", "RIGHT_ANGLE_BRACKET", "LEFT_PAREN", 
			"RIGHT_PAREN", "NEWLINE", "ID", "WORD"
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

	@Override
	public String getGrammarFileName() { return "JsonGraphParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public JsonGraphParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class JsonFileContext extends ParserRuleContext {
		public JsonNodeFileContext jsonNodeFile() {
			return getRuleContext(JsonNodeFileContext.class,0);
		}
		public JsonEdgeFileContext jsonEdgeFile() {
			return getRuleContext(JsonEdgeFileContext.class,0);
		}
		public JsonFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonFile; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitJsonFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonFileContext jsonFile() throws RecognitionException {
		JsonFileContext _localctx = new JsonFileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_jsonFile);
		try {
			setState(34);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,0,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(32);
				jsonNodeFile();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(33);
				jsonEdgeFile();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JsonNodeFileContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(JsonGraphParser.LEFT_BRACE, 0); }
		public List<NodeContext> node() {
			return getRuleContexts(NodeContext.class);
		}
		public NodeContext node(int i) {
			return getRuleContext(NodeContext.class,i);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(JsonGraphParser.RIGHT_BRACE, 0); }
		public TerminalNode EOF() { return getToken(JsonGraphParser.EOF, 0); }
		public List<TerminalNode> COMMA() { return getTokens(JsonGraphParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JsonGraphParser.COMMA, i);
		}
		public JsonNodeFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonNodeFile; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitJsonNodeFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonNodeFileContext jsonNodeFile() throws RecognitionException {
		JsonNodeFileContext _localctx = new JsonNodeFileContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_jsonNodeFile);
		int _la;
		try {
			setState(51);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,2,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(36);
				match(LEFT_BRACE);
				setState(37);
				node();
				setState(42);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(38);
					match(COMMA);
					setState(39);
					node();
					}
					}
					setState(44);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(45);
				match(RIGHT_BRACE);
				setState(46);
				match(EOF);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(48);
				match(LEFT_BRACE);
				setState(49);
				match(RIGHT_BRACE);
				setState(50);
				match(EOF);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class JsonEdgeFileContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(JsonGraphParser.LEFT_BRACE, 0); }
		public List<EdgeContext> edge() {
			return getRuleContexts(EdgeContext.class);
		}
		public EdgeContext edge(int i) {
			return getRuleContext(EdgeContext.class,i);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(JsonGraphParser.RIGHT_BRACE, 0); }
		public TerminalNode EOF() { return getToken(JsonGraphParser.EOF, 0); }
		public List<TerminalNode> COMMA() { return getTokens(JsonGraphParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JsonGraphParser.COMMA, i);
		}
		public JsonEdgeFileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jsonEdgeFile; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitJsonEdgeFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JsonEdgeFileContext jsonEdgeFile() throws RecognitionException {
		JsonEdgeFileContext _localctx = new JsonEdgeFileContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_jsonEdgeFile);
		int _la;
		try {
			setState(68);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(53);
				match(LEFT_BRACE);
				setState(54);
				edge();
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(55);
					match(COMMA);
					setState(56);
					edge();
					}
					}
					setState(61);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(62);
				match(RIGHT_BRACE);
				setState(63);
				match(EOF);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(65);
				match(LEFT_BRACE);
				setState(66);
				match(RIGHT_BRACE);
				setState(67);
				match(EOF);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NodeContext extends ParserRuleContext {
		public TerminalNode WORD() { return getToken(JsonGraphParser.WORD, 0); }
		public TerminalNode COLON() { return getToken(JsonGraphParser.COLON, 0); }
		public TerminalNode LEFT_BRACE() { return getToken(JsonGraphParser.LEFT_BRACE, 0); }
		public NodeFillerContext nodeFiller() {
			return getRuleContext(NodeFillerContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(JsonGraphParser.RIGHT_BRACE, 0); }
		public NodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_node; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitNode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NodeContext node() throws RecognitionException {
		NodeContext _localctx = new NodeContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_node);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(WORD);
			setState(71);
			match(COLON);
			setState(72);
			match(LEFT_BRACE);
			setState(73);
			nodeFiller();
			setState(74);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EdgeContext extends ParserRuleContext {
		public TerminalNode WORD() { return getToken(JsonGraphParser.WORD, 0); }
		public TerminalNode COLON() { return getToken(JsonGraphParser.COLON, 0); }
		public TerminalNode LEFT_BRACE() { return getToken(JsonGraphParser.LEFT_BRACE, 0); }
		public EdgeFillerContext edgeFiller() {
			return getRuleContext(EdgeFillerContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(JsonGraphParser.RIGHT_BRACE, 0); }
		public EdgeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_edge; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitEdge(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EdgeContext edge() throws RecognitionException {
		EdgeContext _localctx = new EdgeContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_edge);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			match(WORD);
			setState(77);
			match(COLON);
			setState(78);
			match(LEFT_BRACE);
			setState(79);
			edgeFiller();
			setState(80);
			match(RIGHT_BRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NodeFillerContext extends ParserRuleContext {
		public TerminalNode WORD() { return getToken(JsonGraphParser.WORD, 0); }
		public TerminalNode COLON() { return getToken(JsonGraphParser.COLON, 0); }
		public IdentityContext identity() {
			return getRuleContext(IdentityContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(JsonGraphParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JsonGraphParser.COMMA, i);
		}
		public LabelsContext labels() {
			return getRuleContext(LabelsContext.class,0);
		}
		public PropertiesContext properties() {
			return getRuleContext(PropertiesContext.class,0);
		}
		public NodeFillerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodeFiller; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitNodeFiller(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NodeFillerContext nodeFiller() throws RecognitionException {
		NodeFillerContext _localctx = new NodeFillerContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_nodeFiller);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(82);
			match(WORD);
			setState(83);
			match(COLON);
			setState(84);
			identity();
			setState(87);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
			case 1:
				{
				setState(85);
				match(COMMA);
				setState(86);
				labels();
				}
				break;
			}
			setState(91);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(89);
				match(COMMA);
				setState(90);
				properties();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EdgeFillerContext extends ParserRuleContext {
		public List<TerminalNode> WORD() { return getTokens(JsonGraphParser.WORD); }
		public TerminalNode WORD(int i) {
			return getToken(JsonGraphParser.WORD, i);
		}
		public List<TerminalNode> COLON() { return getTokens(JsonGraphParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(JsonGraphParser.COLON, i);
		}
		public IdentityContext identity() {
			return getRuleContext(IdentityContext.class,0);
		}
		public List<TerminalNode> COMMA() { return getTokens(JsonGraphParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JsonGraphParser.COMMA, i);
		}
		public StartNodeContext startNode() {
			return getRuleContext(StartNodeContext.class,0);
		}
		public EndNodeContext endNode() {
			return getRuleContext(EndNodeContext.class,0);
		}
		public LabelsContext labels() {
			return getRuleContext(LabelsContext.class,0);
		}
		public PropertiesContext properties() {
			return getRuleContext(PropertiesContext.class,0);
		}
		public IsDirectedContext isDirected() {
			return getRuleContext(IsDirectedContext.class,0);
		}
		public EdgeFillerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_edgeFiller; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitEdgeFiller(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EdgeFillerContext edgeFiller() throws RecognitionException {
		EdgeFillerContext _localctx = new EdgeFillerContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_edgeFiller);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(WORD);
			setState(94);
			match(COLON);
			setState(95);
			identity();
			setState(96);
			match(COMMA);
			setState(97);
			match(WORD);
			setState(98);
			match(COLON);
			setState(99);
			startNode();
			setState(100);
			match(COMMA);
			setState(101);
			match(WORD);
			setState(102);
			match(COLON);
			setState(103);
			endNode();
			setState(106);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				{
				setState(104);
				match(COMMA);
				setState(105);
				labels();
				}
				break;
			}
			setState(110);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
			case 1:
				{
				setState(108);
				match(COMMA);
				setState(109);
				properties();
				}
				break;
			}
			setState(113);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(112);
				isDirected();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdentityContext extends ParserRuleContext {
		public QuotedIdContext quotedId() {
			return getRuleContext(QuotedIdContext.class,0);
		}
		public IdentityContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_identity; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitIdentity(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdentityContext identity() throws RecognitionException {
		IdentityContext _localctx = new IdentityContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_identity);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			quotedId();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class StartNodeContext extends ParserRuleContext {
		public QuotedIdContext quotedId() {
			return getRuleContext(QuotedIdContext.class,0);
		}
		public StartNodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_startNode; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitStartNode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StartNodeContext startNode() throws RecognitionException {
		StartNodeContext _localctx = new StartNodeContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_startNode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(117);
			quotedId();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EndNodeContext extends ParserRuleContext {
		public QuotedIdContext quotedId() {
			return getRuleContext(QuotedIdContext.class,0);
		}
		public EndNodeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_endNode; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitEndNode(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EndNodeContext endNode() throws RecognitionException {
		EndNodeContext _localctx = new EndNodeContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_endNode);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			quotedId();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IsDirectedContext extends ParserRuleContext {
		public TerminalNode COMMA() { return getToken(JsonGraphParser.COMMA, 0); }
		public TerminalNode WORD() { return getToken(JsonGraphParser.WORD, 0); }
		public TerminalNode COLON() { return getToken(JsonGraphParser.COLON, 0); }
		public TerminalNode UNSIGNED_INTEGER() { return getToken(JsonGraphParser.UNSIGNED_INTEGER, 0); }
		public IsDirectedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_isDirected; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitIsDirected(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IsDirectedContext isDirected() throws RecognitionException {
		IsDirectedContext _localctx = new IsDirectedContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_isDirected);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(121);
			match(COMMA);
			setState(122);
			match(WORD);
			setState(123);
			match(COLON);
			setState(124);
			match(UNSIGNED_INTEGER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabelsContext extends ParserRuleContext {
		public TerminalNode WORD() { return getToken(JsonGraphParser.WORD, 0); }
		public TerminalNode COLON() { return getToken(JsonGraphParser.COLON, 0); }
		public TerminalNode LEFT_BRACKET() { return getToken(JsonGraphParser.LEFT_BRACKET, 0); }
		public List<QuotedIdContext> quotedId() {
			return getRuleContexts(QuotedIdContext.class);
		}
		public QuotedIdContext quotedId(int i) {
			return getRuleContext(QuotedIdContext.class,i);
		}
		public TerminalNode RIGHT_BRACKET() { return getToken(JsonGraphParser.RIGHT_BRACKET, 0); }
		public List<TerminalNode> COMMA() { return getTokens(JsonGraphParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JsonGraphParser.COMMA, i);
		}
		public LabelsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labels; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitLabels(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelsContext labels() throws RecognitionException {
		LabelsContext _localctx = new LabelsContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_labels);
		int _la;
		try {
			setState(143);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(126);
				match(WORD);
				setState(127);
				match(COLON);
				setState(128);
				match(LEFT_BRACKET);
				setState(129);
				quotedId();
				setState(134);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(130);
					match(COMMA);
					setState(131);
					quotedId();
					}
					}
					setState(136);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(137);
				match(RIGHT_BRACKET);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(139);
				match(WORD);
				setState(140);
				match(COLON);
				setState(141);
				match(LEFT_BRACKET);
				setState(142);
				match(RIGHT_BRACKET);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PropertiesContext extends ParserRuleContext {
		public TerminalNode WORD() { return getToken(JsonGraphParser.WORD, 0); }
		public TerminalNode COLON() { return getToken(JsonGraphParser.COLON, 0); }
		public TerminalNode LEFT_BRACE() { return getToken(JsonGraphParser.LEFT_BRACE, 0); }
		public List<PairContext> pair() {
			return getRuleContexts(PairContext.class);
		}
		public PairContext pair(int i) {
			return getRuleContext(PairContext.class,i);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(JsonGraphParser.RIGHT_BRACE, 0); }
		public List<TerminalNode> COMMA() { return getTokens(JsonGraphParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(JsonGraphParser.COMMA, i);
		}
		public PropertiesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_properties; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitProperties(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertiesContext properties() throws RecognitionException {
		PropertiesContext _localctx = new PropertiesContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_properties);
		int _la;
		try {
			setState(162);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,13,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(145);
				match(WORD);
				setState(146);
				match(COLON);
				setState(147);
				match(LEFT_BRACE);
				setState(148);
				pair();
				setState(153);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==COMMA) {
					{
					{
					setState(149);
					match(COMMA);
					setState(150);
					pair();
					}
					}
					setState(155);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(156);
				match(RIGHT_BRACE);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(158);
				match(WORD);
				setState(159);
				match(COLON);
				setState(160);
				match(LEFT_BRACE);
				setState(161);
				match(RIGHT_BRACE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PairContext extends ParserRuleContext {
		public QuotedIdContext quotedId() {
			return getRuleContext(QuotedIdContext.class,0);
		}
		public TerminalNode COLON() { return getToken(JsonGraphParser.COLON, 0); }
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public PairContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pair; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitPair(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PairContext pair() throws RecognitionException {
		PairContext _localctx = new PairContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_pair);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(164);
			quotedId();
			setState(165);
			match(COLON);
			{
			setState(166);
			value();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class QuotedIdContext extends ParserRuleContext {
		public TerminalNode WORD() { return getToken(JsonGraphParser.WORD, 0); }
		public QuotedIdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quotedId; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitQuotedId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuotedIdContext quotedId() throws RecognitionException {
		QuotedIdContext _localctx = new QuotedIdContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_quotedId);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			match(WORD);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ValueContext extends ParserRuleContext {
		public TerminalNode TRUTH_VALUE() { return getToken(JsonGraphParser.TRUTH_VALUE, 0); }
		public TerminalNode WORD() { return getToken(JsonGraphParser.WORD, 0); }
		public TerminalNode UNSIGNED_INTEGER() { return getToken(JsonGraphParser.UNSIGNED_INTEGER, 0); }
		public TerminalNode SIGNED_INTEGER() { return getToken(JsonGraphParser.SIGNED_INTEGER, 0); }
		public TerminalNode SIGNED_FIXED_POINT() { return getToken(JsonGraphParser.SIGNED_FIXED_POINT, 0); }
		public TerminalNode SIGNED_FLOAT() { return getToken(JsonGraphParser.SIGNED_FLOAT, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof JsonGraphParserVisitor ) return ((JsonGraphParserVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << UNSIGNED_INTEGER) | (1L << SIGNED_INTEGER) | (1L << SIGNED_FIXED_POINT) | (1L << SIGNED_FLOAT) | (1L << TRUTH_VALUE))) != 0) || _la==WORD) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0154\u00ad\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0001\u0000\u0001\u0000\u0003\u0000#\b\u0000\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0005\u0001)\b\u0001\n\u0001\f\u0001,\t"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0003\u00014\b\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0005\u0002:\b\u0002\n\u0002\f\u0002=\t\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0003\u0002E\b"+
		"\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001"+
		"\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0005\u0003"+
		"\u0005X\b\u0005\u0001\u0005\u0001\u0005\u0003\u0005\\\b\u0005\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0003\u0006k\b\u0006\u0001\u0006\u0001\u0006\u0003\u0006o\b\u0006\u0001"+
		"\u0006\u0003\u0006r\b\u0006\u0001\u0007\u0001\u0007\u0001\b\u0001\b\u0001"+
		"\t\u0001\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b"+
		"\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0005\u000b\u0085\b\u000b"+
		"\n\u000b\f\u000b\u0088\t\u000b\u0001\u000b\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\u000b\u0003\u000b\u0090\b\u000b\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0005\f\u0098\b\f\n\f\f\f\u009b\t\f"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003\f\u00a3\b\f\u0001"+
		"\r\u0001\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000f\u0001\u000f"+
		"\u0001\u000f\u0000\u0000\u0010\u0000\u0002\u0004\u0006\b\n\f\u000e\u0010"+
		"\u0012\u0014\u0016\u0018\u001a\u001c\u001e\u0000\u0001\u0003\u0000\u0005"+
		"\b\n\n\u0154\u0154\u00aa\u0000\"\u0001\u0000\u0000\u0000\u00023\u0001"+
		"\u0000\u0000\u0000\u0004D\u0001\u0000\u0000\u0000\u0006F\u0001\u0000\u0000"+
		"\u0000\bL\u0001\u0000\u0000\u0000\nR\u0001\u0000\u0000\u0000\f]\u0001"+
		"\u0000\u0000\u0000\u000es\u0001\u0000\u0000\u0000\u0010u\u0001\u0000\u0000"+
		"\u0000\u0012w\u0001\u0000\u0000\u0000\u0014y\u0001\u0000\u0000\u0000\u0016"+
		"\u008f\u0001\u0000\u0000\u0000\u0018\u00a2\u0001\u0000\u0000\u0000\u001a"+
		"\u00a4\u0001\u0000\u0000\u0000\u001c\u00a8\u0001\u0000\u0000\u0000\u001e"+
		"\u00aa\u0001\u0000\u0000\u0000 #\u0003\u0002\u0001\u0000!#\u0003\u0004"+
		"\u0002\u0000\" \u0001\u0000\u0000\u0000\"!\u0001\u0000\u0000\u0000#\u0001"+
		"\u0001\u0000\u0000\u0000$%\u0005\u014a\u0000\u0000%*\u0003\u0006\u0003"+
		"\u0000&\'\u0005\u0133\u0000\u0000\')\u0003\u0006\u0003\u0000(&\u0001\u0000"+
		"\u0000\u0000),\u0001\u0000\u0000\u0000*(\u0001\u0000\u0000\u0000*+\u0001"+
		"\u0000\u0000\u0000+-\u0001\u0000\u0000\u0000,*\u0001\u0000\u0000\u0000"+
		"-.\u0005\u014b\u0000\u0000./\u0005\u0000\u0000\u0001/4\u0001\u0000\u0000"+
		"\u000001\u0005\u014a\u0000\u000012\u0005\u014b\u0000\u000024\u0005\u0000"+
		"\u0000\u00013$\u0001\u0000\u0000\u000030\u0001\u0000\u0000\u00004\u0003"+
		"\u0001\u0000\u0000\u000056\u0005\u014a\u0000\u00006;\u0003\b\u0004\u0000"+
		"78\u0005\u0133\u0000\u00008:\u0003\b\u0004\u000097\u0001\u0000\u0000\u0000"+
		":=\u0001\u0000\u0000\u0000;9\u0001\u0000\u0000\u0000;<\u0001\u0000\u0000"+
		"\u0000<>\u0001\u0000\u0000\u0000=;\u0001\u0000\u0000\u0000>?\u0005\u014b"+
		"\u0000\u0000?@\u0005\u0000\u0000\u0001@E\u0001\u0000\u0000\u0000AB\u0005"+
		"\u014a\u0000\u0000BC\u0005\u014b\u0000\u0000CE\u0005\u0000\u0000\u0001"+
		"D5\u0001\u0000\u0000\u0000DA\u0001\u0000\u0000\u0000E\u0005\u0001\u0000"+
		"\u0000\u0000FG\u0005\u0154\u0000\u0000GH\u0005\u0132\u0000\u0000HI\u0005"+
		"\u014a\u0000\u0000IJ\u0003\n\u0005\u0000JK\u0005\u014b\u0000\u0000K\u0007"+
		"\u0001\u0000\u0000\u0000LM\u0005\u0154\u0000\u0000MN\u0005\u0132\u0000"+
		"\u0000NO\u0005\u014a\u0000\u0000OP\u0003\f\u0006\u0000PQ\u0005\u014b\u0000"+
		"\u0000Q\t\u0001\u0000\u0000\u0000RS\u0005\u0154\u0000\u0000ST\u0005\u0132"+
		"\u0000\u0000TW\u0003\u000e\u0007\u0000UV\u0005\u0133\u0000\u0000VX\u0003"+
		"\u0016\u000b\u0000WU\u0001\u0000\u0000\u0000WX\u0001\u0000\u0000\u0000"+
		"X[\u0001\u0000\u0000\u0000YZ\u0005\u0133\u0000\u0000Z\\\u0003\u0018\f"+
		"\u0000[Y\u0001\u0000\u0000\u0000[\\\u0001\u0000\u0000\u0000\\\u000b\u0001"+
		"\u0000\u0000\u0000]^\u0005\u0154\u0000\u0000^_\u0005\u0132\u0000\u0000"+
		"_`\u0003\u000e\u0007\u0000`a\u0005\u0133\u0000\u0000ab\u0005\u0154\u0000"+
		"\u0000bc\u0005\u0132\u0000\u0000cd\u0003\u0010\b\u0000de\u0005\u0133\u0000"+
		"\u0000ef\u0005\u0154\u0000\u0000fg\u0005\u0132\u0000\u0000gj\u0003\u0012"+
		"\t\u0000hi\u0005\u0133\u0000\u0000ik\u0003\u0016\u000b\u0000jh\u0001\u0000"+
		"\u0000\u0000jk\u0001\u0000\u0000\u0000kn\u0001\u0000\u0000\u0000lm\u0005"+
		"\u0133\u0000\u0000mo\u0003\u0018\f\u0000nl\u0001\u0000\u0000\u0000no\u0001"+
		"\u0000\u0000\u0000oq\u0001\u0000\u0000\u0000pr\u0003\u0014\n\u0000qp\u0001"+
		"\u0000\u0000\u0000qr\u0001\u0000\u0000\u0000r\r\u0001\u0000\u0000\u0000"+
		"st\u0003\u001c\u000e\u0000t\u000f\u0001\u0000\u0000\u0000uv\u0003\u001c"+
		"\u000e\u0000v\u0011\u0001\u0000\u0000\u0000wx\u0003\u001c\u000e\u0000"+
		"x\u0013\u0001\u0000\u0000\u0000yz\u0005\u0133\u0000\u0000z{\u0005\u0154"+
		"\u0000\u0000{|\u0005\u0132\u0000\u0000|}\u0005\u0005\u0000\u0000}\u0015"+
		"\u0001\u0000\u0000\u0000~\u007f\u0005\u0154\u0000\u0000\u007f\u0080\u0005"+
		"\u0132\u0000\u0000\u0080\u0081\u0005\u014c\u0000\u0000\u0081\u0086\u0003"+
		"\u001c\u000e\u0000\u0082\u0083\u0005\u0133\u0000\u0000\u0083\u0085\u0003"+
		"\u001c\u000e\u0000\u0084\u0082\u0001\u0000\u0000\u0000\u0085\u0088\u0001"+
		"\u0000\u0000\u0000\u0086\u0084\u0001\u0000\u0000\u0000\u0086\u0087\u0001"+
		"\u0000\u0000\u0000\u0087\u0089\u0001\u0000\u0000\u0000\u0088\u0086\u0001"+
		"\u0000\u0000\u0000\u0089\u008a\u0005\u014d\u0000\u0000\u008a\u0090\u0001"+
		"\u0000\u0000\u0000\u008b\u008c\u0005\u0154\u0000\u0000\u008c\u008d\u0005"+
		"\u0132\u0000\u0000\u008d\u008e\u0005\u014c\u0000\u0000\u008e\u0090\u0005"+
		"\u014d\u0000\u0000\u008f~\u0001\u0000\u0000\u0000\u008f\u008b\u0001\u0000"+
		"\u0000\u0000\u0090\u0017\u0001\u0000\u0000\u0000\u0091\u0092\u0005\u0154"+
		"\u0000\u0000\u0092\u0093\u0005\u0132\u0000\u0000\u0093\u0094\u0005\u014a"+
		"\u0000\u0000\u0094\u0099\u0003\u001a\r\u0000\u0095\u0096\u0005\u0133\u0000"+
		"\u0000\u0096\u0098\u0003\u001a\r\u0000\u0097\u0095\u0001\u0000\u0000\u0000"+
		"\u0098\u009b\u0001\u0000\u0000\u0000\u0099\u0097\u0001\u0000\u0000\u0000"+
		"\u0099\u009a\u0001\u0000\u0000\u0000\u009a\u009c\u0001\u0000\u0000\u0000"+
		"\u009b\u0099\u0001\u0000\u0000\u0000\u009c\u009d\u0005\u014b\u0000\u0000"+
		"\u009d\u00a3\u0001\u0000\u0000\u0000\u009e\u009f\u0005\u0154\u0000\u0000"+
		"\u009f\u00a0\u0005\u0132\u0000\u0000\u00a0\u00a1\u0005\u014a\u0000\u0000"+
		"\u00a1\u00a3\u0005\u014b\u0000\u0000\u00a2\u0091\u0001\u0000\u0000\u0000"+
		"\u00a2\u009e\u0001\u0000\u0000\u0000\u00a3\u0019\u0001\u0000\u0000\u0000"+
		"\u00a4\u00a5\u0003\u001c\u000e\u0000\u00a5\u00a6\u0005\u0132\u0000\u0000"+
		"\u00a6\u00a7\u0003\u001e\u000f\u0000\u00a7\u001b\u0001\u0000\u0000\u0000"+
		"\u00a8\u00a9\u0005\u0154\u0000\u0000\u00a9\u001d\u0001\u0000\u0000\u0000"+
		"\u00aa\u00ab\u0007\u0000\u0000\u0000\u00ab\u001f\u0001\u0000\u0000\u0000"+
		"\u000e\"*3;DW[jnq\u0086\u008f\u0099\u00a2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}