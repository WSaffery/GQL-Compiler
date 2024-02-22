// Generated from GqlParser.g4 by ANTLR 4.10.1

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
public class GqlParser extends Parser {
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
		RULE_query = 0, RULE_queryConjunction = 1, RULE_queryExpression = 2, RULE_focusedQueryExpression = 3, 
		RULE_ambientQueryExpression = 4, RULE_focusedMatchClause = 5, RULE_matchClause = 6, 
		RULE_whereClause = 7, RULE_returnStatement = 8, RULE_pathPatternList = 9, 
		RULE_pathPattern = 10, RULE_pathPatternPrefix = 11, RULE_pathPatternExpression = 12, 
		RULE_pathTerm = 13, RULE_path = 14, RULE_nodePattern = 15, RULE_edgePattern = 16, 
		RULE_fullEdgePointingLeft = 17, RULE_fullEdgeUndirected = 18, RULE_fullEdgePointingRight = 19, 
		RULE_elementPatternFiller = 20, RULE_propertyList = 21, RULE_returnList = 22, 
		RULE_returnItem = 23, RULE_setOperator = 24, RULE_unionOperator = 25, 
		RULE_otherSetOperator = 26, RULE_setQuantifier = 27, RULE_len = 28, RULE_quantifier = 29, 
		RULE_expr = 30, RULE_isLabelExpr = 31, RULE_labelExpression = 32, RULE_labelTerm = 33, 
		RULE_labelFactor = 34, RULE_labelNegation = 35, RULE_labelPrimary = 36, 
		RULE_label = 37, RULE_labelWildcard = 38, RULE_parenthesizedLabelExpression = 39, 
		RULE_comparator = 40, RULE_booleanComparator = 41, RULE_compOp = 42, RULE_graphName = 43, 
		RULE_name = 44, RULE_value = 45, RULE_pathVariable = 46, RULE_elementVariable = 47, 
		RULE_key = 48;
	private static String[] makeRuleNames() {
		return new String[] {
			"query", "queryConjunction", "queryExpression", "focusedQueryExpression", 
			"ambientQueryExpression", "focusedMatchClause", "matchClause", "whereClause", 
			"returnStatement", "pathPatternList", "pathPattern", "pathPatternPrefix", 
			"pathPatternExpression", "pathTerm", "path", "nodePattern", "edgePattern", 
			"fullEdgePointingLeft", "fullEdgeUndirected", "fullEdgePointingRight", 
			"elementPatternFiller", "propertyList", "returnList", "returnItem", "setOperator", 
			"unionOperator", "otherSetOperator", "setQuantifier", "len", "quantifier", 
			"expr", "isLabelExpr", "labelExpression", "labelTerm", "labelFactor", 
			"labelNegation", "labelPrimary", "label", "labelWildcard", "parenthesizedLabelExpression", 
			"comparator", "booleanComparator", "compOp", "graphName", "name", "value", 
			"pathVariable", "elementVariable", "key"
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
	public String getGrammarFileName() { return "GqlParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GqlParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class QueryContext extends ParserRuleContext {
		public List<QueryExpressionContext> queryExpression() {
			return getRuleContexts(QueryExpressionContext.class);
		}
		public QueryExpressionContext queryExpression(int i) {
			return getRuleContext(QueryExpressionContext.class,i);
		}
		public TerminalNode EOF() { return getToken(GqlParser.EOF, 0); }
		public List<QueryConjunctionContext> queryConjunction() {
			return getRuleContexts(QueryConjunctionContext.class);
		}
		public QueryConjunctionContext queryConjunction(int i) {
			return getRuleContext(QueryConjunctionContext.class,i);
		}
		public QueryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_query; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitQuery(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryContext query() throws RecognitionException {
		QueryContext _localctx = new QueryContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_query);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			queryExpression();
			setState(104);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==EXCEPT || _la==INTERSECT || _la==OTHERWISE || _la==UNION) {
				{
				{
				setState(99);
				queryConjunction();
				setState(100);
				queryExpression();
				}
				}
				setState(106);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(107);
			match(EOF);
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

	public static class QueryConjunctionContext extends ParserRuleContext {
		public SetOperatorContext setOperator() {
			return getRuleContext(SetOperatorContext.class,0);
		}
		public TerminalNode OTHERWISE() { return getToken(GqlParser.OTHERWISE, 0); }
		public QueryConjunctionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryConjunction; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitQueryConjunction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryConjunctionContext queryConjunction() throws RecognitionException {
		QueryConjunctionContext _localctx = new QueryConjunctionContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_queryConjunction);
		try {
			setState(111);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case EXCEPT:
			case INTERSECT:
			case UNION:
				enterOuterAlt(_localctx, 1);
				{
				setState(109);
				setOperator();
				}
				break;
			case OTHERWISE:
				enterOuterAlt(_localctx, 2);
				{
				setState(110);
				match(OTHERWISE);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class QueryExpressionContext extends ParserRuleContext {
		public FocusedQueryExpressionContext focusedQueryExpression() {
			return getRuleContext(FocusedQueryExpressionContext.class,0);
		}
		public AmbientQueryExpressionContext ambientQueryExpression() {
			return getRuleContext(AmbientQueryExpressionContext.class,0);
		}
		public QueryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_queryExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitQueryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QueryExpressionContext queryExpression() throws RecognitionException {
		QueryExpressionContext _localctx = new QueryExpressionContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_queryExpression);
		try {
			setState(115);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case FROM:
				enterOuterAlt(_localctx, 1);
				{
				setState(113);
				focusedQueryExpression();
				}
				break;
			case MANDATORY:
			case MATCH:
			case OPTIONAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(114);
				ambientQueryExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class FocusedQueryExpressionContext extends ParserRuleContext {
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public List<FocusedMatchClauseContext> focusedMatchClause() {
			return getRuleContexts(FocusedMatchClauseContext.class);
		}
		public FocusedMatchClauseContext focusedMatchClause(int i) {
			return getRuleContext(FocusedMatchClauseContext.class,i);
		}
		public FocusedQueryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_focusedQueryExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitFocusedQueryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FocusedQueryExpressionContext focusedQueryExpression() throws RecognitionException {
		FocusedQueryExpressionContext _localctx = new FocusedQueryExpressionContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_focusedQueryExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(118); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(117);
				focusedMatchClause();
				}
				}
				setState(120); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==FROM );
			setState(122);
			returnStatement();
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

	public static class AmbientQueryExpressionContext extends ParserRuleContext {
		public ReturnStatementContext returnStatement() {
			return getRuleContext(ReturnStatementContext.class,0);
		}
		public List<MatchClauseContext> matchClause() {
			return getRuleContexts(MatchClauseContext.class);
		}
		public MatchClauseContext matchClause(int i) {
			return getRuleContext(MatchClauseContext.class,i);
		}
		public AmbientQueryExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ambientQueryExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitAmbientQueryExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AmbientQueryExpressionContext ambientQueryExpression() throws RecognitionException {
		AmbientQueryExpressionContext _localctx = new AmbientQueryExpressionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_ambientQueryExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(125); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(124);
				matchClause();
				}
				}
				setState(127); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((((_la - 131)) & ~0x3f) == 0 && ((1L << (_la - 131)) & ((1L << (MANDATORY - 131)) | (1L << (MATCH - 131)) | (1L << (OPTIONAL - 131)))) != 0) );
			setState(129);
			returnStatement();
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

	public static class FocusedMatchClauseContext extends ParserRuleContext {
		public TerminalNode FROM() { return getToken(GqlParser.FROM, 0); }
		public GraphNameContext graphName() {
			return getRuleContext(GraphNameContext.class,0);
		}
		public List<MatchClauseContext> matchClause() {
			return getRuleContexts(MatchClauseContext.class);
		}
		public MatchClauseContext matchClause(int i) {
			return getRuleContext(MatchClauseContext.class,i);
		}
		public FocusedMatchClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_focusedMatchClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitFocusedMatchClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FocusedMatchClauseContext focusedMatchClause() throws RecognitionException {
		FocusedMatchClauseContext _localctx = new FocusedMatchClauseContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_focusedMatchClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(131);
			match(FROM);
			setState(132);
			graphName();
			setState(134); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(133);
				matchClause();
				}
				}
				setState(136); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( ((((_la - 131)) & ~0x3f) == 0 && ((1L << (_la - 131)) & ((1L << (MANDATORY - 131)) | (1L << (MATCH - 131)) | (1L << (OPTIONAL - 131)))) != 0) );
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

	public static class MatchClauseContext extends ParserRuleContext {
		public TerminalNode MATCH() { return getToken(GqlParser.MATCH, 0); }
		public PathPatternListContext pathPatternList() {
			return getRuleContext(PathPatternListContext.class,0);
		}
		public WhereClauseContext whereClause() {
			return getRuleContext(WhereClauseContext.class,0);
		}
		public TerminalNode OPTIONAL() { return getToken(GqlParser.OPTIONAL, 0); }
		public TerminalNode MANDATORY() { return getToken(GqlParser.MANDATORY, 0); }
		public MatchClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_matchClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitMatchClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MatchClauseContext matchClause() throws RecognitionException {
		MatchClauseContext _localctx = new MatchClauseContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_matchClause);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==MANDATORY || _la==OPTIONAL) {
				{
				setState(138);
				_la = _input.LA(1);
				if ( !(_la==MANDATORY || _la==OPTIONAL) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				}
			}

			setState(141);
			match(MATCH);
			setState(142);
			pathPatternList();
			setState(144);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==WHERE) {
				{
				setState(143);
				whereClause();
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

	public static class WhereClauseContext extends ParserRuleContext {
		public TerminalNode WHERE() { return getToken(GqlParser.WHERE, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public WhereClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_whereClause; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitWhereClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final WhereClauseContext whereClause() throws RecognitionException {
		WhereClauseContext _localctx = new WhereClauseContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_whereClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			match(WHERE);
			setState(147);
			expr(0);
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

	public static class ReturnStatementContext extends ParserRuleContext {
		public TerminalNode RETURN() { return getToken(GqlParser.RETURN, 0); }
		public TerminalNode ASTERISK() { return getToken(GqlParser.ASTERISK, 0); }
		public ReturnListContext returnList() {
			return getRuleContext(ReturnListContext.class,0);
		}
		public SetQuantifierContext setQuantifier() {
			return getRuleContext(SetQuantifierContext.class,0);
		}
		public ReturnStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnStatement; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitReturnStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnStatementContext returnStatement() throws RecognitionException {
		ReturnStatementContext _localctx = new ReturnStatementContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_returnStatement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			match(RETURN);
			setState(151);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ALL || _la==DISTINCT) {
				{
				setState(150);
				setQuantifier();
				}
			}

			setState(155);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ASTERISK:
				{
				setState(153);
				match(ASTERISK);
				}
				break;
			case UNSIGNED_INTEGER:
			case SIGNED_INTEGER:
			case SIGNED_FIXED_POINT:
			case SIGNED_FLOAT:
			case TRUTH_VALUE:
			case NOT:
			case ID:
			case WORD:
				{
				setState(154);
				returnList();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class PathPatternListContext extends ParserRuleContext {
		public List<PathPatternContext> pathPattern() {
			return getRuleContexts(PathPatternContext.class);
		}
		public PathPatternContext pathPattern(int i) {
			return getRuleContext(PathPatternContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GqlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GqlParser.COMMA, i);
		}
		public PathPatternListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathPatternList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitPathPatternList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathPatternListContext pathPatternList() throws RecognitionException {
		PathPatternListContext _localctx = new PathPatternListContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_pathPatternList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(157);
			pathPattern();
			setState(162);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(158);
				match(COMMA);
				setState(159);
				pathPattern();
				}
				}
				setState(164);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class PathPatternContext extends ParserRuleContext {
		public PathPatternExpressionContext pathPatternExpression() {
			return getRuleContext(PathPatternExpressionContext.class,0);
		}
		public PathVariableContext pathVariable() {
			return getRuleContext(PathVariableContext.class,0);
		}
		public TerminalNode EQ() { return getToken(GqlParser.EQ, 0); }
		public PathPatternPrefixContext pathPatternPrefix() {
			return getRuleContext(PathPatternPrefixContext.class,0);
		}
		public PathPatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathPattern; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitPathPattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathPatternContext pathPattern() throws RecognitionException {
		PathPatternContext _localctx = new PathPatternContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_pathPattern);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(165);
				pathVariable();
				setState(166);
				match(EQ);
				}
			}

			setState(171);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (((((_la - 223)) & ~0x3f) == 0 && ((1L << (_la - 223)) & ((1L << (ACYCLIC - 223)) | (1L << (SIMPLE - 223)) | (1L << (TRAIL - 223)) | (1L << (WALK - 223)))) != 0)) {
				{
				setState(170);
				pathPatternPrefix();
				}
			}

			setState(173);
			pathPatternExpression();
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

	public static class PathPatternPrefixContext extends ParserRuleContext {
		public TerminalNode WALK() { return getToken(GqlParser.WALK, 0); }
		public TerminalNode TRAIL() { return getToken(GqlParser.TRAIL, 0); }
		public TerminalNode ACYCLIC() { return getToken(GqlParser.ACYCLIC, 0); }
		public TerminalNode SIMPLE() { return getToken(GqlParser.SIMPLE, 0); }
		public PathPatternPrefixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathPatternPrefix; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitPathPatternPrefix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathPatternPrefixContext pathPatternPrefix() throws RecognitionException {
		PathPatternPrefixContext _localctx = new PathPatternPrefixContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_pathPatternPrefix);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175);
			_la = _input.LA(1);
			if ( !(((((_la - 223)) & ~0x3f) == 0 && ((1L << (_la - 223)) & ((1L << (ACYCLIC - 223)) | (1L << (SIMPLE - 223)) | (1L << (TRAIL - 223)) | (1L << (WALK - 223)))) != 0)) ) {
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

	public static class PathPatternExpressionContext extends ParserRuleContext {
		public PathTermContext pathTerm() {
			return getRuleContext(PathTermContext.class,0);
		}
		public PathPatternExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathPatternExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitPathPatternExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathPatternExpressionContext pathPatternExpression() throws RecognitionException {
		PathPatternExpressionContext _localctx = new PathPatternExpressionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_pathPatternExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			pathTerm();
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

	public static class PathTermContext extends ParserRuleContext {
		public PathContext path() {
			return getRuleContext(PathContext.class,0);
		}
		public PathTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathTerm; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitPathTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathTermContext pathTerm() throws RecognitionException {
		PathTermContext _localctx = new PathTermContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_pathTerm);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			path();
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

	public static class PathContext extends ParserRuleContext {
		public List<NodePatternContext> nodePattern() {
			return getRuleContexts(NodePatternContext.class);
		}
		public NodePatternContext nodePattern(int i) {
			return getRuleContext(NodePatternContext.class,i);
		}
		public List<EdgePatternContext> edgePattern() {
			return getRuleContexts(EdgePatternContext.class);
		}
		public EdgePatternContext edgePattern(int i) {
			return getRuleContext(EdgePatternContext.class,i);
		}
		public PathContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_path; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitPath(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathContext path() throws RecognitionException {
		PathContext _localctx = new PathContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_path);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(181);
			nodePattern();
			setState(187);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 280)) & ~0x3f) == 0 && ((1L << (_la - 280)) & ((1L << (LEFT_ARROW_BRACKET - 280)) | (1L << (MINUS_LEFT_BRACKET - 280)) | (1L << (TILDE_LEFT_BRACKET - 280)))) != 0)) {
				{
				{
				setState(182);
				edgePattern();
				setState(183);
				nodePattern();
				}
				}
				setState(189);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class NodePatternContext extends ParserRuleContext {
		public TerminalNode LEFT_PAREN() { return getToken(GqlParser.LEFT_PAREN, 0); }
		public ElementPatternFillerContext elementPatternFiller() {
			return getRuleContext(ElementPatternFillerContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(GqlParser.RIGHT_PAREN, 0); }
		public NodePatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nodePattern; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitNodePattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NodePatternContext nodePattern() throws RecognitionException {
		NodePatternContext _localctx = new NodePatternContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_nodePattern);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(190);
			match(LEFT_PAREN);
			setState(191);
			elementPatternFiller();
			setState(192);
			match(RIGHT_PAREN);
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

	public static class EdgePatternContext extends ParserRuleContext {
		public FullEdgePointingLeftContext fullEdgePointingLeft() {
			return getRuleContext(FullEdgePointingLeftContext.class,0);
		}
		public FullEdgeUndirectedContext fullEdgeUndirected() {
			return getRuleContext(FullEdgeUndirectedContext.class,0);
		}
		public FullEdgePointingRightContext fullEdgePointingRight() {
			return getRuleContext(FullEdgePointingRightContext.class,0);
		}
		public LenContext len() {
			return getRuleContext(LenContext.class,0);
		}
		public EdgePatternContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_edgePattern; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitEdgePattern(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EdgePatternContext edgePattern() throws RecognitionException {
		EdgePatternContext _localctx = new EdgePatternContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_edgePattern);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LEFT_ARROW_BRACKET:
				{
				setState(194);
				fullEdgePointingLeft();
				}
				break;
			case TILDE_LEFT_BRACKET:
				{
				setState(195);
				fullEdgeUndirected();
				}
				break;
			case MINUS_LEFT_BRACKET:
				{
				setState(196);
				fullEdgePointingRight();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(200);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LEFT_BRACE) {
				{
				setState(199);
				len();
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

	public static class FullEdgePointingLeftContext extends ParserRuleContext {
		public TerminalNode LEFT_ARROW_BRACKET() { return getToken(GqlParser.LEFT_ARROW_BRACKET, 0); }
		public ElementPatternFillerContext elementPatternFiller() {
			return getRuleContext(ElementPatternFillerContext.class,0);
		}
		public TerminalNode RIGHT_BRACKET_MINUS() { return getToken(GqlParser.RIGHT_BRACKET_MINUS, 0); }
		public FullEdgePointingLeftContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fullEdgePointingLeft; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitFullEdgePointingLeft(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FullEdgePointingLeftContext fullEdgePointingLeft() throws RecognitionException {
		FullEdgePointingLeftContext _localctx = new FullEdgePointingLeftContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_fullEdgePointingLeft);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(202);
			match(LEFT_ARROW_BRACKET);
			setState(203);
			elementPatternFiller();
			setState(204);
			match(RIGHT_BRACKET_MINUS);
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

	public static class FullEdgeUndirectedContext extends ParserRuleContext {
		public TerminalNode TILDE_LEFT_BRACKET() { return getToken(GqlParser.TILDE_LEFT_BRACKET, 0); }
		public ElementPatternFillerContext elementPatternFiller() {
			return getRuleContext(ElementPatternFillerContext.class,0);
		}
		public TerminalNode RIGHT_BRACKET_TILDE() { return getToken(GqlParser.RIGHT_BRACKET_TILDE, 0); }
		public FullEdgeUndirectedContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fullEdgeUndirected; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitFullEdgeUndirected(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FullEdgeUndirectedContext fullEdgeUndirected() throws RecognitionException {
		FullEdgeUndirectedContext _localctx = new FullEdgeUndirectedContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_fullEdgeUndirected);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(206);
			match(TILDE_LEFT_BRACKET);
			setState(207);
			elementPatternFiller();
			setState(208);
			match(RIGHT_BRACKET_TILDE);
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

	public static class FullEdgePointingRightContext extends ParserRuleContext {
		public TerminalNode MINUS_LEFT_BRACKET() { return getToken(GqlParser.MINUS_LEFT_BRACKET, 0); }
		public ElementPatternFillerContext elementPatternFiller() {
			return getRuleContext(ElementPatternFillerContext.class,0);
		}
		public TerminalNode BRACKET_RIGHT_ARROW() { return getToken(GqlParser.BRACKET_RIGHT_ARROW, 0); }
		public FullEdgePointingRightContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fullEdgePointingRight; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitFullEdgePointingRight(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FullEdgePointingRightContext fullEdgePointingRight() throws RecognitionException {
		FullEdgePointingRightContext _localctx = new FullEdgePointingRightContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_fullEdgePointingRight);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(210);
			match(MINUS_LEFT_BRACKET);
			setState(211);
			elementPatternFiller();
			setState(212);
			match(BRACKET_RIGHT_ARROW);
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

	public static class ElementPatternFillerContext extends ParserRuleContext {
		public ElementVariableContext elementVariable() {
			return getRuleContext(ElementVariableContext.class,0);
		}
		public IsLabelExprContext isLabelExpr() {
			return getRuleContext(IsLabelExprContext.class,0);
		}
		public TerminalNode LEFT_BRACE() { return getToken(GqlParser.LEFT_BRACE, 0); }
		public PropertyListContext propertyList() {
			return getRuleContext(PropertyListContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(GqlParser.RIGHT_BRACE, 0); }
		public ElementPatternFillerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementPatternFiller; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitElementPatternFiller(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementPatternFillerContext elementPatternFiller() throws RecognitionException {
		ElementPatternFillerContext _localctx = new ElementPatternFillerContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_elementPatternFiller);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ID) {
				{
				setState(214);
				elementVariable();
				}
			}

			setState(218);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==IS || _la==COLON) {
				{
				setState(217);
				isLabelExpr();
				}
			}

			setState(224);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LEFT_BRACE) {
				{
				setState(220);
				match(LEFT_BRACE);
				setState(221);
				propertyList();
				setState(222);
				match(RIGHT_BRACE);
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

	public static class PropertyListContext extends ParserRuleContext {
		public List<KeyContext> key() {
			return getRuleContexts(KeyContext.class);
		}
		public KeyContext key(int i) {
			return getRuleContext(KeyContext.class,i);
		}
		public List<TerminalNode> COLON() { return getTokens(GqlParser.COLON); }
		public TerminalNode COLON(int i) {
			return getToken(GqlParser.COLON, i);
		}
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GqlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GqlParser.COMMA, i);
		}
		public PropertyListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_propertyList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitPropertyList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PropertyListContext propertyList() throws RecognitionException {
		PropertyListContext _localctx = new PropertyListContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_propertyList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(226);
			key();
			setState(227);
			match(COLON);
			setState(228);
			expr(0);
			setState(236);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(229);
				match(COMMA);
				setState(230);
				key();
				setState(231);
				match(COLON);
				setState(232);
				expr(0);
				}
				}
				setState(238);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class ReturnListContext extends ParserRuleContext {
		public List<ReturnItemContext> returnItem() {
			return getRuleContexts(ReturnItemContext.class);
		}
		public ReturnItemContext returnItem(int i) {
			return getRuleContext(ReturnItemContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(GqlParser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(GqlParser.COMMA, i);
		}
		public ReturnListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnList; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitReturnList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnListContext returnList() throws RecognitionException {
		ReturnListContext _localctx = new ReturnListContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_returnList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			returnItem();
			setState(244);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(240);
				match(COMMA);
				setState(241);
				returnItem();
				}
				}
				setState(246);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class ReturnItemContext extends ParserRuleContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode AS() { return getToken(GqlParser.AS, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public ReturnItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_returnItem; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitReturnItem(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ReturnItemContext returnItem() throws RecognitionException {
		ReturnItemContext _localctx = new ReturnItemContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_returnItem);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(247);
			expr(0);
			setState(250);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==AS) {
				{
				setState(248);
				match(AS);
				setState(249);
				name();
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

	public static class SetOperatorContext extends ParserRuleContext {
		public UnionOperatorContext unionOperator() {
			return getRuleContext(UnionOperatorContext.class,0);
		}
		public OtherSetOperatorContext otherSetOperator() {
			return getRuleContext(OtherSetOperatorContext.class,0);
		}
		public SetOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setOperator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitSetOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetOperatorContext setOperator() throws RecognitionException {
		SetOperatorContext _localctx = new SetOperatorContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_setOperator);
		try {
			setState(254);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case UNION:
				enterOuterAlt(_localctx, 1);
				{
				setState(252);
				unionOperator();
				}
				break;
			case EXCEPT:
			case INTERSECT:
				enterOuterAlt(_localctx, 2);
				{
				setState(253);
				otherSetOperator();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class UnionOperatorContext extends ParserRuleContext {
		public TerminalNode UNION() { return getToken(GqlParser.UNION, 0); }
		public SetQuantifierContext setQuantifier() {
			return getRuleContext(SetQuantifierContext.class,0);
		}
		public TerminalNode MAX() { return getToken(GqlParser.MAX, 0); }
		public UnionOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unionOperator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitUnionOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnionOperatorContext unionOperator() throws RecognitionException {
		UnionOperatorContext _localctx = new UnionOperatorContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_unionOperator);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			match(UNION);
			setState(259);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ALL:
			case DISTINCT:
				{
				setState(257);
				setQuantifier();
				}
				break;
			case MAX:
				{
				setState(258);
				match(MAX);
				}
				break;
			case FROM:
			case MANDATORY:
			case MATCH:
			case OPTIONAL:
				break;
			default:
				break;
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

	public static class OtherSetOperatorContext extends ParserRuleContext {
		public TerminalNode EXCEPT() { return getToken(GqlParser.EXCEPT, 0); }
		public TerminalNode INTERSECT() { return getToken(GqlParser.INTERSECT, 0); }
		public SetQuantifierContext setQuantifier() {
			return getRuleContext(SetQuantifierContext.class,0);
		}
		public OtherSetOperatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_otherSetOperator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitOtherSetOperator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OtherSetOperatorContext otherSetOperator() throws RecognitionException {
		OtherSetOperatorContext _localctx = new OtherSetOperatorContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_otherSetOperator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(261);
			_la = _input.LA(1);
			if ( !(_la==EXCEPT || _la==INTERSECT) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(263);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ALL || _la==DISTINCT) {
				{
				setState(262);
				setQuantifier();
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

	public static class SetQuantifierContext extends ParserRuleContext {
		public TerminalNode DISTINCT() { return getToken(GqlParser.DISTINCT, 0); }
		public TerminalNode ALL() { return getToken(GqlParser.ALL, 0); }
		public SetQuantifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setQuantifier; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitSetQuantifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetQuantifierContext setQuantifier() throws RecognitionException {
		SetQuantifierContext _localctx = new SetQuantifierContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_setQuantifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265);
			_la = _input.LA(1);
			if ( !(_la==ALL || _la==DISTINCT) ) {
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

	public static class LenContext extends ParserRuleContext {
		public TerminalNode LEFT_BRACE() { return getToken(GqlParser.LEFT_BRACE, 0); }
		public QuantifierContext quantifier() {
			return getRuleContext(QuantifierContext.class,0);
		}
		public TerminalNode RIGHT_BRACE() { return getToken(GqlParser.RIGHT_BRACE, 0); }
		public LenContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_len; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitLen(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LenContext len() throws RecognitionException {
		LenContext _localctx = new LenContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_len);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(267);
			match(LEFT_BRACE);
			setState(268);
			quantifier();
			setState(269);
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

	public static class QuantifierContext extends ParserRuleContext {
		public List<TerminalNode> UNSIGNED_INTEGER() { return getTokens(GqlParser.UNSIGNED_INTEGER); }
		public TerminalNode UNSIGNED_INTEGER(int i) {
			return getToken(GqlParser.UNSIGNED_INTEGER, i);
		}
		public TerminalNode COMMA() { return getToken(GqlParser.COMMA, 0); }
		public QuantifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_quantifier; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitQuantifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QuantifierContext quantifier() throws RecognitionException {
		QuantifierContext _localctx = new QuantifierContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_quantifier);
		try {
			setState(275);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(271);
				match(UNSIGNED_INTEGER);
				setState(272);
				match(COMMA);
				setState(273);
				match(UNSIGNED_INTEGER);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(274);
				match(UNSIGNED_INTEGER);
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

	public static class ExprContext extends ParserRuleContext {
		public ExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expr; }
	 
		public ExprContext() { }
		public void copyFrom(ExprContext ctx) {
			super.copyFrom(ctx);
		}
	}
	public static class BooleanComparisonContext extends ExprContext {
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public BooleanComparatorContext booleanComparator() {
			return getRuleContext(BooleanComparatorContext.class,0);
		}
		public TerminalNode TRUTH_VALUE() { return getToken(GqlParser.TRUTH_VALUE, 0); }
		public BooleanComparisonContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitBooleanComparison(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class PropertyReferenceContext extends ExprContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TerminalNode PERIOD() { return getToken(GqlParser.PERIOD, 0); }
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public PropertyReferenceContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitPropertyReference(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ValueExpressionContext extends ExprContext {
		public ValueContext value() {
			return getRuleContext(ValueContext.class,0);
		}
		public ValueExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitValueExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NameExpressionContext extends ExprContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public NameExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitNameExpression(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ValueComparisonContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public CompOpContext compOp() {
			return getRuleContext(CompOpContext.class,0);
		}
		public ValueComparisonContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitValueComparison(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class ExpressionComparisonContext extends ExprContext {
		public List<ExprContext> expr() {
			return getRuleContexts(ExprContext.class);
		}
		public ExprContext expr(int i) {
			return getRuleContext(ExprContext.class,i);
		}
		public ComparatorContext comparator() {
			return getRuleContext(ComparatorContext.class,0);
		}
		public ExpressionComparisonContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitExpressionComparison(this);
			else return visitor.visitChildren(this);
		}
	}
	public static class NegatedExpressionContext extends ExprContext {
		public TerminalNode NOT() { return getToken(GqlParser.NOT, 0); }
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public NegatedExpressionContext(ExprContext ctx) { copyFrom(ctx); }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitNegatedExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr() throws RecognitionException {
		return expr(0);
	}

	private ExprContext expr(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		ExprContext _localctx = new ExprContext(_ctx, _parentState);
		ExprContext _prevctx = _localctx;
		int _startState = 60;
		enterRecursionRule(_localctx, 60, RULE_expr, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,26,_ctx) ) {
			case 1:
				{
				_localctx = new ValueExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(278);
				value();
				}
				break;
			case 2:
				{
				_localctx = new NameExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(279);
				name();
				}
				break;
			case 3:
				{
				_localctx = new PropertyReferenceContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(280);
				name();
				setState(281);
				match(PERIOD);
				setState(282);
				key();
				}
				break;
			case 4:
				{
				_localctx = new NegatedExpressionContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(284);
				match(NOT);
				setState(285);
				expr(4);
				}
				break;
			}
			_ctx.stop = _input.LT(-1);
			setState(302);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					setState(300);
					_errHandler.sync(this);
					switch ( getInterpreter().adaptivePredict(_input,27,_ctx) ) {
					case 1:
						{
						_localctx = new ValueComparisonContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(288);
						if (!(precpred(_ctx, 3))) throw new FailedPredicateException(this, "precpred(_ctx, 3)");
						setState(289);
						compOp();
						setState(290);
						expr(4);
						}
						break;
					case 2:
						{
						_localctx = new ExpressionComparisonContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(292);
						if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
						setState(293);
						comparator();
						setState(294);
						expr(2);
						}
						break;
					case 3:
						{
						_localctx = new BooleanComparisonContext(new ExprContext(_parentctx, _parentState));
						pushNewRecursionContext(_localctx, _startState, RULE_expr);
						setState(296);
						if (!(precpred(_ctx, 2))) throw new FailedPredicateException(this, "precpred(_ctx, 2)");
						setState(297);
						booleanComparator();
						setState(298);
						match(TRUTH_VALUE);
						}
						break;
					}
					} 
				}
				setState(304);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,28,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public static class IsLabelExprContext extends ParserRuleContext {
		public LabelExpressionContext labelExpression() {
			return getRuleContext(LabelExpressionContext.class,0);
		}
		public TerminalNode IS() { return getToken(GqlParser.IS, 0); }
		public TerminalNode COLON() { return getToken(GqlParser.COLON, 0); }
		public IsLabelExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_isLabelExpr; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitIsLabelExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IsLabelExprContext isLabelExpr() throws RecognitionException {
		IsLabelExprContext _localctx = new IsLabelExprContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_isLabelExpr);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			_la = _input.LA(1);
			if ( !(_la==IS || _la==COLON) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(306);
			labelExpression();
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

	public static class LabelExpressionContext extends ParserRuleContext {
		public List<LabelTermContext> labelTerm() {
			return getRuleContexts(LabelTermContext.class);
		}
		public LabelTermContext labelTerm(int i) {
			return getRuleContext(LabelTermContext.class,i);
		}
		public List<TerminalNode> VERTICAL_BAR() { return getTokens(GqlParser.VERTICAL_BAR); }
		public TerminalNode VERTICAL_BAR(int i) {
			return getToken(GqlParser.VERTICAL_BAR, i);
		}
		public LabelExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitLabelExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelExpressionContext labelExpression() throws RecognitionException {
		LabelExpressionContext _localctx = new LabelExpressionContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_labelExpression);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(308);
			labelTerm();
			setState(313);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==VERTICAL_BAR) {
				{
				{
				setState(309);
				match(VERTICAL_BAR);
				setState(310);
				labelTerm();
				}
				}
				setState(315);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class LabelTermContext extends ParserRuleContext {
		public List<LabelFactorContext> labelFactor() {
			return getRuleContexts(LabelFactorContext.class);
		}
		public LabelFactorContext labelFactor(int i) {
			return getRuleContext(LabelFactorContext.class,i);
		}
		public List<TerminalNode> AMPERSAND() { return getTokens(GqlParser.AMPERSAND); }
		public TerminalNode AMPERSAND(int i) {
			return getToken(GqlParser.AMPERSAND, i);
		}
		public LabelTermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelTerm; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitLabelTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelTermContext labelTerm() throws RecognitionException {
		LabelTermContext _localctx = new LabelTermContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_labelTerm);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(316);
			labelFactor();
			setState(321);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==AMPERSAND) {
				{
				{
				setState(317);
				match(AMPERSAND);
				setState(318);
				labelFactor();
				}
				}
				setState(323);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class LabelFactorContext extends ParserRuleContext {
		public LabelPrimaryContext labelPrimary() {
			return getRuleContext(LabelPrimaryContext.class,0);
		}
		public LabelNegationContext labelNegation() {
			return getRuleContext(LabelNegationContext.class,0);
		}
		public LabelFactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelFactor; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitLabelFactor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelFactorContext labelFactor() throws RecognitionException {
		LabelFactorContext _localctx = new LabelFactorContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_labelFactor);
		try {
			setState(326);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case PERCENT:
			case LEFT_BRACKET:
			case LEFT_PAREN:
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(324);
				labelPrimary();
				}
				break;
			case EXCLAMATION_MARK:
				enterOuterAlt(_localctx, 2);
				{
				setState(325);
				labelNegation();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class LabelNegationContext extends ParserRuleContext {
		public TerminalNode EXCLAMATION_MARK() { return getToken(GqlParser.EXCLAMATION_MARK, 0); }
		public LabelPrimaryContext labelPrimary() {
			return getRuleContext(LabelPrimaryContext.class,0);
		}
		public LabelNegationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelNegation; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitLabelNegation(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelNegationContext labelNegation() throws RecognitionException {
		LabelNegationContext _localctx = new LabelNegationContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_labelNegation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(328);
			match(EXCLAMATION_MARK);
			setState(329);
			labelPrimary();
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

	public static class LabelPrimaryContext extends ParserRuleContext {
		public LabelContext label() {
			return getRuleContext(LabelContext.class,0);
		}
		public LabelWildcardContext labelWildcard() {
			return getRuleContext(LabelWildcardContext.class,0);
		}
		public ParenthesizedLabelExpressionContext parenthesizedLabelExpression() {
			return getRuleContext(ParenthesizedLabelExpressionContext.class,0);
		}
		public LabelPrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelPrimary; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitLabelPrimary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelPrimaryContext labelPrimary() throws RecognitionException {
		LabelPrimaryContext _localctx = new LabelPrimaryContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_labelPrimary);
		try {
			setState(334);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case ID:
				enterOuterAlt(_localctx, 1);
				{
				setState(331);
				label();
				}
				break;
			case PERCENT:
				enterOuterAlt(_localctx, 2);
				{
				setState(332);
				labelWildcard();
				}
				break;
			case LEFT_BRACKET:
			case LEFT_PAREN:
				enterOuterAlt(_localctx, 3);
				{
				setState(333);
				parenthesizedLabelExpression();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class LabelContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GqlParser.ID, 0); }
		public LabelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_label; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitLabel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelContext label() throws RecognitionException {
		LabelContext _localctx = new LabelContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_label);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(336);
			match(ID);
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

	public static class LabelWildcardContext extends ParserRuleContext {
		public TerminalNode PERCENT() { return getToken(GqlParser.PERCENT, 0); }
		public LabelWildcardContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labelWildcard; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitLabelWildcard(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabelWildcardContext labelWildcard() throws RecognitionException {
		LabelWildcardContext _localctx = new LabelWildcardContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_labelWildcard);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(338);
			match(PERCENT);
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

	public static class ParenthesizedLabelExpressionContext extends ParserRuleContext {
		public TerminalNode LEFT_PAREN() { return getToken(GqlParser.LEFT_PAREN, 0); }
		public LabelExpressionContext labelExpression() {
			return getRuleContext(LabelExpressionContext.class,0);
		}
		public TerminalNode RIGHT_PAREN() { return getToken(GqlParser.RIGHT_PAREN, 0); }
		public TerminalNode LEFT_BRACKET() { return getToken(GqlParser.LEFT_BRACKET, 0); }
		public TerminalNode RIGHT_BRACKET() { return getToken(GqlParser.RIGHT_BRACKET, 0); }
		public ParenthesizedLabelExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parenthesizedLabelExpression; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitParenthesizedLabelExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParenthesizedLabelExpressionContext parenthesizedLabelExpression() throws RecognitionException {
		ParenthesizedLabelExpressionContext _localctx = new ParenthesizedLabelExpressionContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_parenthesizedLabelExpression);
		try {
			setState(348);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LEFT_PAREN:
				enterOuterAlt(_localctx, 1);
				{
				setState(340);
				match(LEFT_PAREN);
				setState(341);
				labelExpression();
				setState(342);
				match(RIGHT_PAREN);
				}
				break;
			case LEFT_BRACKET:
				enterOuterAlt(_localctx, 2);
				{
				setState(344);
				match(LEFT_BRACKET);
				setState(345);
				labelExpression();
				setState(346);
				match(RIGHT_BRACKET);
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class ComparatorContext extends ParserRuleContext {
		public TerminalNode OR() { return getToken(GqlParser.OR, 0); }
		public TerminalNode AND() { return getToken(GqlParser.AND, 0); }
		public TerminalNode XOR() { return getToken(GqlParser.XOR, 0); }
		public ComparatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitComparator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparatorContext comparator() throws RecognitionException {
		ComparatorContext _localctx = new ComparatorContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_comparator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(350);
			_la = _input.LA(1);
			if ( !(_la==AND || _la==OR || _la==XOR) ) {
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

	public static class BooleanComparatorContext extends ParserRuleContext {
		public TerminalNode IS() { return getToken(GqlParser.IS, 0); }
		public TerminalNode NOT() { return getToken(GqlParser.NOT, 0); }
		public BooleanComparatorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_booleanComparator; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitBooleanComparator(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BooleanComparatorContext booleanComparator() throws RecognitionException {
		BooleanComparatorContext _localctx = new BooleanComparatorContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_booleanComparator);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(352);
			match(IS);
			setState(354);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==NOT) {
				{
				setState(353);
				match(NOT);
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

	public static class CompOpContext extends ParserRuleContext {
		public TerminalNode EQ() { return getToken(GqlParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(GqlParser.NEQ, 0); }
		public TerminalNode LT() { return getToken(GqlParser.LT, 0); }
		public TerminalNode GT() { return getToken(GqlParser.GT, 0); }
		public TerminalNode LEQ() { return getToken(GqlParser.LEQ, 0); }
		public TerminalNode GEQ() { return getToken(GqlParser.GEQ, 0); }
		public CompOpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_compOp; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitCompOp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CompOpContext compOp() throws RecognitionException {
		CompOpContext _localctx = new CompOpContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_compOp);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(356);
			_la = _input.LA(1);
			if ( !(((((_la - 324)) & ~0x3f) == 0 && ((1L << (_la - 324)) & ((1L << (GEQ - 324)) | (1L << (LEQ - 324)) | (1L << (NEQ - 324)) | (1L << (LT - 324)) | (1L << (GT - 324)) | (1L << (EQ - 324)))) != 0)) ) {
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

	public static class GraphNameContext extends ParserRuleContext {
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public GraphNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_graphName; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitGraphName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GraphNameContext graphName() throws RecognitionException {
		GraphNameContext _localctx = new GraphNameContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_graphName);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(358);
			name();
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

	public static class NameContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GqlParser.ID, 0); }
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_name);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(360);
			match(ID);
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
		public TerminalNode TRUTH_VALUE() { return getToken(GqlParser.TRUTH_VALUE, 0); }
		public TerminalNode UNSIGNED_INTEGER() { return getToken(GqlParser.UNSIGNED_INTEGER, 0); }
		public TerminalNode SIGNED_INTEGER() { return getToken(GqlParser.SIGNED_INTEGER, 0); }
		public TerminalNode SIGNED_FIXED_POINT() { return getToken(GqlParser.SIGNED_FIXED_POINT, 0); }
		public TerminalNode SIGNED_FLOAT() { return getToken(GqlParser.SIGNED_FLOAT, 0); }
		public TerminalNode WORD() { return getToken(GqlParser.WORD, 0); }
		public ValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_value; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ValueContext value() throws RecognitionException {
		ValueContext _localctx = new ValueContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_value);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(362);
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

	public static class PathVariableContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GqlParser.ID, 0); }
		public PathVariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pathVariable; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitPathVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PathVariableContext pathVariable() throws RecognitionException {
		PathVariableContext _localctx = new PathVariableContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_pathVariable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(364);
			match(ID);
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

	public static class ElementVariableContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GqlParser.ID, 0); }
		public ElementVariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementVariable; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitElementVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementVariableContext elementVariable() throws RecognitionException {
		ElementVariableContext _localctx = new ElementVariableContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_elementVariable);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(366);
			match(ID);
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

	public static class KeyContext extends ParserRuleContext {
		public TerminalNode ID() { return getToken(GqlParser.ID, 0); }
		public KeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_key; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GqlParserVisitor ) return ((GqlParserVisitor<? extends T>)visitor).visitKey(this);
			else return visitor.visitChildren(this);
		}
	}

	public final KeyContext key() throws RecognitionException {
		KeyContext _localctx = new KeyContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_key);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(368);
			match(ID);
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

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 30:
			return expr_sempred((ExprContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean expr_sempred(ExprContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 3);
		case 1:
			return precpred(_ctx, 1);
		case 2:
			return precpred(_ctx, 2);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001\u0154\u0173\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001"+
		"\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004"+
		"\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007"+
		"\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b"+
		"\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007"+
		"\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007"+
		"\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002\u0015\u0007"+
		"\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002\u0018\u0007"+
		"\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002\u001b\u0007"+
		"\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002\u001e\u0007"+
		"\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007!\u0002\"\u0007"+
		"\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007&\u0002\'\u0007"+
		"\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007+\u0002,\u0007"+
		",\u0002-\u0007-\u0002.\u0007.\u0002/\u0007/\u00020\u00070\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0005\u0000g\b\u0000\n\u0000\f\u0000"+
		"j\t\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0003\u0001"+
		"p\b\u0001\u0001\u0002\u0001\u0002\u0003\u0002t\b\u0002\u0001\u0003\u0004"+
		"\u0003w\b\u0003\u000b\u0003\f\u0003x\u0001\u0003\u0001\u0003\u0001\u0004"+
		"\u0004\u0004~\b\u0004\u000b\u0004\f\u0004\u007f\u0001\u0004\u0001\u0004"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0004\u0005\u0087\b\u0005\u000b\u0005"+
		"\f\u0005\u0088\u0001\u0006\u0003\u0006\u008c\b\u0006\u0001\u0006\u0001"+
		"\u0006\u0001\u0006\u0003\u0006\u0091\b\u0006\u0001\u0007\u0001\u0007\u0001"+
		"\u0007\u0001\b\u0001\b\u0003\b\u0098\b\b\u0001\b\u0001\b\u0003\b\u009c"+
		"\b\b\u0001\t\u0001\t\u0001\t\u0005\t\u00a1\b\t\n\t\f\t\u00a4\t\t\u0001"+
		"\n\u0001\n\u0001\n\u0003\n\u00a9\b\n\u0001\n\u0003\n\u00ac\b\n\u0001\n"+
		"\u0001\n\u0001\u000b\u0001\u000b\u0001\f\u0001\f\u0001\r\u0001\r\u0001"+
		"\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u00ba\b\u000e\n"+
		"\u000e\f\u000e\u00bd\t\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0003\u0010\u00c6\b\u0010\u0001"+
		"\u0010\u0003\u0010\u00c9\b\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001"+
		"\u0011\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0012\u0001\u0013\u0001"+
		"\u0013\u0001\u0013\u0001\u0013\u0001\u0014\u0003\u0014\u00d8\b\u0014\u0001"+
		"\u0014\u0003\u0014\u00db\b\u0014\u0001\u0014\u0001\u0014\u0001\u0014\u0001"+
		"\u0014\u0003\u0014\u00e1\b\u0014\u0001\u0015\u0001\u0015\u0001\u0015\u0001"+
		"\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0001\u0015\u0005\u0015\u00eb"+
		"\b\u0015\n\u0015\f\u0015\u00ee\t\u0015\u0001\u0016\u0001\u0016\u0001\u0016"+
		"\u0005\u0016\u00f3\b\u0016\n\u0016\f\u0016\u00f6\t\u0016\u0001\u0017\u0001"+
		"\u0017\u0001\u0017\u0003\u0017\u00fb\b\u0017\u0001\u0018\u0001\u0018\u0003"+
		"\u0018\u00ff\b\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0003\u0019\u0104"+
		"\b\u0019\u0001\u001a\u0001\u001a\u0003\u001a\u0108\b\u001a\u0001\u001b"+
		"\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001d"+
		"\u0001\u001d\u0001\u001d\u0001\u001d\u0003\u001d\u0114\b\u001d\u0001\u001e"+
		"\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001e\u0001\u001e\u0003\u001e\u011f\b\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e"+
		"\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001e\u0005\u001e\u012d\b\u001e"+
		"\n\u001e\f\u001e\u0130\t\u001e\u0001\u001f\u0001\u001f\u0001\u001f\u0001"+
		" \u0001 \u0001 \u0005 \u0138\b \n \f \u013b\t \u0001!\u0001!\u0001!\u0005"+
		"!\u0140\b!\n!\f!\u0143\t!\u0001\"\u0001\"\u0003\"\u0147\b\"\u0001#\u0001"+
		"#\u0001#\u0001$\u0001$\u0001$\u0003$\u014f\b$\u0001%\u0001%\u0001&\u0001"+
		"&\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0001\'\u0003"+
		"\'\u015d\b\'\u0001(\u0001(\u0001)\u0001)\u0003)\u0163\b)\u0001*\u0001"+
		"*\u0001+\u0001+\u0001,\u0001,\u0001-\u0001-\u0001.\u0001.\u0001/\u0001"+
		"/\u00010\u00010\u00010\u0000\u0001<1\u0000\u0002\u0004\u0006\b\n\f\u000e"+
		"\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&(*,.02468:<>@BDF"+
		"HJLNPRTVXZ\\^`\u0000\b\u0002\u0000\u0083\u0083\u0095\u0095\u0004\u0000"+
		"\u00df\u00df\u0106\u0106\u010c\u010c\u0113\u0113\u0002\u0000SSqq\u0002"+
		"\u0000\u0010\u0010HH\u0002\u0000ss\u0132\u0132\u0003\u0000\u0011\u0011"+
		"\u0096\u0096\u00dc\u00dc\u0001\u0000\u0144\u0149\u0003\u0000\u0005\b\n"+
		"\n\u0154\u0154\u016a\u0000b\u0001\u0000\u0000\u0000\u0002o\u0001\u0000"+
		"\u0000\u0000\u0004s\u0001\u0000\u0000\u0000\u0006v\u0001\u0000\u0000\u0000"+
		"\b}\u0001\u0000\u0000\u0000\n\u0083\u0001\u0000\u0000\u0000\f\u008b\u0001"+
		"\u0000\u0000\u0000\u000e\u0092\u0001\u0000\u0000\u0000\u0010\u0095\u0001"+
		"\u0000\u0000\u0000\u0012\u009d\u0001\u0000\u0000\u0000\u0014\u00a8\u0001"+
		"\u0000\u0000\u0000\u0016\u00af\u0001\u0000\u0000\u0000\u0018\u00b1\u0001"+
		"\u0000\u0000\u0000\u001a\u00b3\u0001\u0000\u0000\u0000\u001c\u00b5\u0001"+
		"\u0000\u0000\u0000\u001e\u00be\u0001\u0000\u0000\u0000 \u00c5\u0001\u0000"+
		"\u0000\u0000\"\u00ca\u0001\u0000\u0000\u0000$\u00ce\u0001\u0000\u0000"+
		"\u0000&\u00d2\u0001\u0000\u0000\u0000(\u00d7\u0001\u0000\u0000\u0000*"+
		"\u00e2\u0001\u0000\u0000\u0000,\u00ef\u0001\u0000\u0000\u0000.\u00f7\u0001"+
		"\u0000\u0000\u00000\u00fe\u0001\u0000\u0000\u00002\u0100\u0001\u0000\u0000"+
		"\u00004\u0105\u0001\u0000\u0000\u00006\u0109\u0001\u0000\u0000\u00008"+
		"\u010b\u0001\u0000\u0000\u0000:\u0113\u0001\u0000\u0000\u0000<\u011e\u0001"+
		"\u0000\u0000\u0000>\u0131\u0001\u0000\u0000\u0000@\u0134\u0001\u0000\u0000"+
		"\u0000B\u013c\u0001\u0000\u0000\u0000D\u0146\u0001\u0000\u0000\u0000F"+
		"\u0148\u0001\u0000\u0000\u0000H\u014e\u0001\u0000\u0000\u0000J\u0150\u0001"+
		"\u0000\u0000\u0000L\u0152\u0001\u0000\u0000\u0000N\u015c\u0001\u0000\u0000"+
		"\u0000P\u015e\u0001\u0000\u0000\u0000R\u0160\u0001\u0000\u0000\u0000T"+
		"\u0164\u0001\u0000\u0000\u0000V\u0166\u0001\u0000\u0000\u0000X\u0168\u0001"+
		"\u0000\u0000\u0000Z\u016a\u0001\u0000\u0000\u0000\\\u016c\u0001\u0000"+
		"\u0000\u0000^\u016e\u0001\u0000\u0000\u0000`\u0170\u0001\u0000\u0000\u0000"+
		"bh\u0003\u0004\u0002\u0000cd\u0003\u0002\u0001\u0000de\u0003\u0004\u0002"+
		"\u0000eg\u0001\u0000\u0000\u0000fc\u0001\u0000\u0000\u0000gj\u0001\u0000"+
		"\u0000\u0000hf\u0001\u0000\u0000\u0000hi\u0001\u0000\u0000\u0000ik\u0001"+
		"\u0000\u0000\u0000jh\u0001\u0000\u0000\u0000kl\u0005\u0000\u0000\u0001"+
		"l\u0001\u0001\u0000\u0000\u0000mp\u00030\u0018\u0000np\u0005\u0099\u0000"+
		"\u0000om\u0001\u0000\u0000\u0000on\u0001\u0000\u0000\u0000p\u0003\u0001"+
		"\u0000\u0000\u0000qt\u0003\u0006\u0003\u0000rt\u0003\b\u0004\u0000sq\u0001"+
		"\u0000\u0000\u0000sr\u0001\u0000\u0000\u0000t\u0005\u0001\u0000\u0000"+
		"\u0000uw\u0003\n\u0005\u0000vu\u0001\u0000\u0000\u0000wx\u0001\u0000\u0000"+
		"\u0000xv\u0001\u0000\u0000\u0000xy\u0001\u0000\u0000\u0000yz\u0001\u0000"+
		"\u0000\u0000z{\u0003\u0010\b\u0000{\u0007\u0001\u0000\u0000\u0000|~\u0003"+
		"\f\u0006\u0000}|\u0001\u0000\u0000\u0000~\u007f\u0001\u0000\u0000\u0000"+
		"\u007f}\u0001\u0000\u0000\u0000\u007f\u0080\u0001\u0000\u0000\u0000\u0080"+
		"\u0081\u0001\u0000\u0000\u0000\u0081\u0082\u0003\u0010\b\u0000\u0082\t"+
		"\u0001\u0000\u0000\u0000\u0083\u0084\u0005`\u0000\u0000\u0084\u0086\u0003"+
		"V+\u0000\u0085\u0087\u0003\f\u0006\u0000\u0086\u0085\u0001\u0000\u0000"+
		"\u0000\u0087\u0088\u0001\u0000\u0000\u0000\u0088\u0086\u0001\u0000\u0000"+
		"\u0000\u0088\u0089\u0001\u0000\u0000\u0000\u0089\u000b\u0001\u0000\u0000"+
		"\u0000\u008a\u008c\u0007\u0000\u0000\u0000\u008b\u008a\u0001\u0000\u0000"+
		"\u0000\u008b\u008c\u0001\u0000\u0000\u0000\u008c\u008d\u0001\u0000\u0000"+
		"\u0000\u008d\u008e\u0005\u0085\u0000\u0000\u008e\u0090\u0003\u0012\t\u0000"+
		"\u008f\u0091\u0003\u000e\u0007\u0000\u0090\u008f\u0001\u0000\u0000\u0000"+
		"\u0090\u0091\u0001\u0000\u0000\u0000\u0091\r\u0001\u0000\u0000\u0000\u0092"+
		"\u0093\u0005\u00d9\u0000\u0000\u0093\u0094\u0003<\u001e\u0000\u0094\u000f"+
		"\u0001\u0000\u0000\u0000\u0095\u0097\u0005\u00b0\u0000\u0000\u0096\u0098"+
		"\u00036\u001b\u0000\u0097\u0096\u0001\u0000\u0000\u0000\u0097\u0098\u0001"+
		"\u0000\u0000\u0000\u0098\u009b\u0001\u0000\u0000\u0000\u0099\u009c\u0005"+
		"\u0130\u0000\u0000\u009a\u009c\u0003,\u0016\u0000\u009b\u0099\u0001\u0000"+
		"\u0000\u0000\u009b\u009a\u0001\u0000\u0000\u0000\u009c\u0011\u0001\u0000"+
		"\u0000\u0000\u009d\u00a2\u0003\u0014\n\u0000\u009e\u009f\u0005\u0133\u0000"+
		"\u0000\u009f\u00a1\u0003\u0014\n\u0000\u00a0\u009e\u0001\u0000\u0000\u0000"+
		"\u00a1\u00a4\u0001\u0000\u0000\u0000\u00a2\u00a0\u0001\u0000\u0000\u0000"+
		"\u00a2\u00a3\u0001\u0000\u0000\u0000\u00a3\u0013\u0001\u0000\u0000\u0000"+
		"\u00a4\u00a2\u0001\u0000\u0000\u0000\u00a5\u00a6\u0003\\.\u0000\u00a6"+
		"\u00a7\u0005\u0149\u0000\u0000\u00a7\u00a9\u0001\u0000\u0000\u0000\u00a8"+
		"\u00a5\u0001\u0000\u0000\u0000\u00a8\u00a9\u0001\u0000\u0000\u0000\u00a9"+
		"\u00ab\u0001\u0000\u0000\u0000\u00aa\u00ac\u0003\u0016\u000b\u0000\u00ab"+
		"\u00aa\u0001\u0000\u0000\u0000\u00ab\u00ac\u0001\u0000\u0000\u0000\u00ac"+
		"\u00ad\u0001\u0000\u0000\u0000\u00ad\u00ae\u0003\u0018\f\u0000\u00ae\u0015"+
		"\u0001\u0000\u0000\u0000\u00af\u00b0\u0007\u0001\u0000\u0000\u00b0\u0017"+
		"\u0001\u0000\u0000\u0000\u00b1\u00b2\u0003\u001a\r\u0000\u00b2\u0019\u0001"+
		"\u0000\u0000\u0000\u00b3\u00b4\u0003\u001c\u000e\u0000\u00b4\u001b\u0001"+
		"\u0000\u0000\u0000\u00b5\u00bb\u0003\u001e\u000f\u0000\u00b6\u00b7\u0003"+
		" \u0010\u0000\u00b7\u00b8\u0003\u001e\u000f\u0000\u00b8\u00ba\u0001\u0000"+
		"\u0000\u0000\u00b9\u00b6\u0001\u0000\u0000\u0000\u00ba\u00bd\u0001\u0000"+
		"\u0000\u0000\u00bb\u00b9\u0001\u0000\u0000\u0000\u00bb\u00bc\u0001\u0000"+
		"\u0000\u0000\u00bc\u001d\u0001\u0000\u0000\u0000\u00bd\u00bb\u0001\u0000"+
		"\u0000\u0000\u00be\u00bf\u0005\u0150\u0000\u0000\u00bf\u00c0\u0003(\u0014"+
		"\u0000\u00c0\u00c1\u0005\u0151\u0000\u0000\u00c1\u001f\u0001\u0000\u0000"+
		"\u0000\u00c2\u00c6\u0003\"\u0011\u0000\u00c3\u00c6\u0003$\u0012\u0000"+
		"\u00c4\u00c6\u0003&\u0013\u0000\u00c5\u00c2\u0001\u0000\u0000\u0000\u00c5"+
		"\u00c3\u0001\u0000\u0000\u0000\u00c5\u00c4\u0001\u0000\u0000\u0000\u00c6"+
		"\u00c8\u0001\u0000\u0000\u0000\u00c7\u00c9\u00038\u001c\u0000\u00c8\u00c7"+
		"\u0001\u0000\u0000\u0000\u00c8\u00c9\u0001\u0000\u0000\u0000\u00c9!\u0001"+
		"\u0000\u0000\u0000\u00ca\u00cb\u0005\u0118\u0000\u0000\u00cb\u00cc\u0003"+
		"(\u0014\u0000\u00cc\u00cd\u0005\u011f\u0000\u0000\u00cd#\u0001\u0000\u0000"+
		"\u0000\u00ce\u00cf\u0005\u0120\u0000\u0000\u00cf\u00d0\u0003(\u0014\u0000"+
		"\u00d0\u00d1\u0005\u0121\u0000\u0000\u00d1%\u0001\u0000\u0000\u0000\u00d2"+
		"\u00d3\u0005\u011e\u0000\u0000\u00d3\u00d4\u0003(\u0014\u0000\u00d4\u00d5"+
		"\u0005\u0117\u0000\u0000\u00d5\'\u0001\u0000\u0000\u0000\u00d6\u00d8\u0003"+
		"^/\u0000\u00d7\u00d6\u0001\u0000\u0000\u0000\u00d7\u00d8\u0001\u0000\u0000"+
		"\u0000\u00d8\u00da\u0001\u0000\u0000\u0000\u00d9\u00db\u0003>\u001f\u0000"+
		"\u00da\u00d9\u0001\u0000\u0000\u0000\u00da\u00db\u0001\u0000\u0000\u0000"+
		"\u00db\u00e0\u0001\u0000\u0000\u0000\u00dc\u00dd\u0005\u014a\u0000\u0000"+
		"\u00dd\u00de\u0003*\u0015\u0000\u00de\u00df\u0005\u014b\u0000\u0000\u00df"+
		"\u00e1\u0001\u0000\u0000\u0000\u00e0\u00dc\u0001\u0000\u0000\u0000\u00e0"+
		"\u00e1\u0001\u0000\u0000\u0000\u00e1)\u0001\u0000\u0000\u0000\u00e2\u00e3"+
		"\u0003`0\u0000\u00e3\u00e4\u0005\u0132\u0000\u0000\u00e4\u00ec\u0003<"+
		"\u001e\u0000\u00e5\u00e6\u0005\u0133\u0000\u0000\u00e6\u00e7\u0003`0\u0000"+
		"\u00e7\u00e8\u0005\u0132\u0000\u0000\u00e8\u00e9\u0003<\u001e\u0000\u00e9"+
		"\u00eb\u0001\u0000\u0000\u0000\u00ea\u00e5\u0001\u0000\u0000\u0000\u00eb"+
		"\u00ee\u0001\u0000\u0000\u0000\u00ec\u00ea\u0001\u0000\u0000\u0000\u00ec"+
		"\u00ed\u0001\u0000\u0000\u0000\u00ed+\u0001\u0000\u0000\u0000\u00ee\u00ec"+
		"\u0001\u0000\u0000\u0000\u00ef\u00f4\u0003.\u0017\u0000\u00f0\u00f1\u0005"+
		"\u0133\u0000\u0000\u00f1\u00f3\u0003.\u0017\u0000\u00f2\u00f0\u0001\u0000"+
		"\u0000\u0000\u00f3\u00f6\u0001\u0000\u0000\u0000\u00f4\u00f2\u0001\u0000"+
		"\u0000\u0000\u00f4\u00f5\u0001\u0000\u0000\u0000\u00f5-\u0001\u0000\u0000"+
		"\u0000\u00f6\u00f4\u0001\u0000\u0000\u0000\u00f7\u00fa\u0003<\u001e\u0000"+
		"\u00f8\u00f9\u0005\u0014\u0000\u0000\u00f9\u00fb\u0003X,\u0000\u00fa\u00f8"+
		"\u0001\u0000\u0000\u0000\u00fa\u00fb\u0001\u0000\u0000\u0000\u00fb/\u0001"+
		"\u0000\u0000\u0000\u00fc\u00ff\u00032\u0019\u0000\u00fd\u00ff\u00034\u001a"+
		"\u0000\u00fe\u00fc\u0001\u0000\u0000\u0000\u00fe\u00fd\u0001\u0000\u0000"+
		"\u0000\u00ff1\u0001\u0000\u0000\u0000\u0100\u0103\u0005\u00cc\u0000\u0000"+
		"\u0101\u0104\u00036\u001b\u0000\u0102\u0104\u0005\u0087\u0000\u0000\u0103"+
		"\u0101\u0001\u0000\u0000\u0000\u0103\u0102\u0001\u0000\u0000\u0000\u0103"+
		"\u0104\u0001\u0000\u0000\u0000\u01043\u0001\u0000\u0000\u0000\u0105\u0107"+
		"\u0007\u0002\u0000\u0000\u0106\u0108\u00036\u001b\u0000\u0107\u0106\u0001"+
		"\u0000\u0000\u0000\u0107\u0108\u0001\u0000\u0000\u0000\u01085\u0001\u0000"+
		"\u0000\u0000\u0109\u010a\u0007\u0003\u0000\u0000\u010a7\u0001\u0000\u0000"+
		"\u0000\u010b\u010c\u0005\u014a\u0000\u0000\u010c\u010d\u0003:\u001d\u0000"+
		"\u010d\u010e\u0005\u014b\u0000\u0000\u010e9\u0001\u0000\u0000\u0000\u010f"+
		"\u0110\u0005\u0005\u0000\u0000\u0110\u0111\u0005\u0133\u0000\u0000\u0111"+
		"\u0114\u0005\u0005\u0000\u0000\u0112\u0114\u0005\u0005\u0000\u0000\u0113"+
		"\u010f\u0001\u0000\u0000\u0000\u0113\u0112\u0001\u0000\u0000\u0000\u0114"+
		";\u0001\u0000\u0000\u0000\u0115\u0116\u0006\u001e\uffff\uffff\u0000\u0116"+
		"\u011f\u0003Z-\u0000\u0117\u011f\u0003X,\u0000\u0118\u0119\u0003X,\u0000"+
		"\u0119\u011a\u0005\u013a\u0000\u0000\u011a\u011b\u0003`0\u0000\u011b\u011f"+
		"\u0001\u0000\u0000\u0000\u011c\u011d\u0005\u008e\u0000\u0000\u011d\u011f"+
		"\u0003<\u001e\u0004\u011e\u0115\u0001\u0000\u0000\u0000\u011e\u0117\u0001"+
		"\u0000\u0000\u0000\u011e\u0118\u0001\u0000\u0000\u0000\u011e\u011c\u0001"+
		"\u0000\u0000\u0000\u011f\u012e\u0001\u0000\u0000\u0000\u0120\u0121\n\u0003"+
		"\u0000\u0000\u0121\u0122\u0003T*\u0000\u0122\u0123\u0003<\u001e\u0004"+
		"\u0123\u012d\u0001\u0000\u0000\u0000\u0124\u0125\n\u0001\u0000\u0000\u0125"+
		"\u0126\u0003P(\u0000\u0126\u0127\u0003<\u001e\u0002\u0127\u012d\u0001"+
		"\u0000\u0000\u0000\u0128\u0129\n\u0002\u0000\u0000\u0129\u012a\u0003R"+
		")\u0000\u012a\u012b\u0005\n\u0000\u0000\u012b\u012d\u0001\u0000\u0000"+
		"\u0000\u012c\u0120\u0001\u0000\u0000\u0000\u012c\u0124\u0001\u0000\u0000"+
		"\u0000\u012c\u0128\u0001\u0000\u0000\u0000\u012d\u0130\u0001\u0000\u0000"+
		"\u0000\u012e\u012c\u0001\u0000\u0000\u0000\u012e\u012f\u0001\u0000\u0000"+
		"\u0000\u012f=\u0001\u0000\u0000\u0000\u0130\u012e\u0001\u0000\u0000\u0000"+
		"\u0131\u0132\u0007\u0004\u0000\u0000\u0132\u0133\u0003@ \u0000\u0133?"+
		"\u0001\u0000\u0000\u0000\u0134\u0139\u0003B!\u0000\u0135\u0136\u0005\u0141"+
		"\u0000\u0000\u0136\u0138\u0003B!\u0000\u0137\u0135\u0001\u0000\u0000\u0000"+
		"\u0138\u013b\u0001\u0000\u0000\u0000\u0139\u0137\u0001\u0000\u0000\u0000"+
		"\u0139\u013a\u0001\u0000\u0000\u0000\u013aA\u0001\u0000\u0000\u0000\u013b"+
		"\u0139\u0001\u0000\u0000\u0000\u013c\u0141\u0003D\"\u0000\u013d\u013e"+
		"\u0005\u012f\u0000\u0000\u013e\u0140\u0003D\"\u0000\u013f\u013d\u0001"+
		"\u0000\u0000\u0000\u0140\u0143\u0001\u0000\u0000\u0000\u0141\u013f\u0001"+
		"\u0000\u0000\u0000\u0141\u0142\u0001\u0000\u0000\u0000\u0142C\u0001\u0000"+
		"\u0000\u0000\u0143\u0141\u0001\u0000\u0000\u0000\u0144\u0147\u0003H$\u0000"+
		"\u0145\u0147\u0003F#\u0000\u0146\u0144\u0001\u0000\u0000\u0000\u0146\u0145"+
		"\u0001\u0000\u0000\u0000\u0147E\u0001\u0000\u0000\u0000\u0148\u0149\u0005"+
		"\u0136\u0000\u0000\u0149\u014a\u0003H$\u0000\u014aG\u0001\u0000\u0000"+
		"\u0000\u014b\u014f\u0003J%\u0000\u014c\u014f\u0003L&\u0000\u014d\u014f"+
		"\u0003N\'\u0000\u014e\u014b\u0001\u0000\u0000\u0000\u014e\u014c\u0001"+
		"\u0000\u0000\u0000\u014e\u014d\u0001\u0000\u0000\u0000\u014fI\u0001\u0000"+
		"\u0000\u0000\u0150\u0151\u0005\u0153\u0000\u0000\u0151K\u0001\u0000\u0000"+
		"\u0000\u0152\u0153\u0005\u0139\u0000\u0000\u0153M\u0001\u0000\u0000\u0000"+
		"\u0154\u0155\u0005\u0150\u0000\u0000\u0155\u0156\u0003@ \u0000\u0156\u0157"+
		"\u0005\u0151\u0000\u0000\u0157\u015d\u0001\u0000\u0000\u0000\u0158\u0159"+
		"\u0005\u014c\u0000\u0000\u0159\u015a\u0003@ \u0000\u015a\u015b\u0005\u014d"+
		"\u0000\u0000\u015b\u015d\u0001\u0000\u0000\u0000\u015c\u0154\u0001\u0000"+
		"\u0000\u0000\u015c\u0158\u0001\u0000\u0000\u0000\u015dO\u0001\u0000\u0000"+
		"\u0000\u015e\u015f\u0007\u0005\u0000\u0000\u015fQ\u0001\u0000\u0000\u0000"+
		"\u0160\u0162\u0005s\u0000\u0000\u0161\u0163\u0005\u008e\u0000\u0000\u0162"+
		"\u0161\u0001\u0000\u0000\u0000\u0162\u0163\u0001\u0000\u0000\u0000\u0163"+
		"S\u0001\u0000\u0000\u0000\u0164\u0165\u0007\u0006\u0000\u0000\u0165U\u0001"+
		"\u0000\u0000\u0000\u0166\u0167\u0003X,\u0000\u0167W\u0001\u0000\u0000"+
		"\u0000\u0168\u0169\u0005\u0153\u0000\u0000\u0169Y\u0001\u0000\u0000\u0000"+
		"\u016a\u016b\u0007\u0007\u0000\u0000\u016b[\u0001\u0000\u0000\u0000\u016c"+
		"\u016d\u0005\u0153\u0000\u0000\u016d]\u0001\u0000\u0000\u0000\u016e\u016f"+
		"\u0005\u0153\u0000\u0000\u016f_\u0001\u0000\u0000\u0000\u0170\u0171\u0005"+
		"\u0153\u0000\u0000\u0171a\u0001\u0000\u0000\u0000#hosx\u007f\u0088\u008b"+
		"\u0090\u0097\u009b\u00a2\u00a8\u00ab\u00bb\u00c5\u00c8\u00d7\u00da\u00e0"+
		"\u00ec\u00f4\u00fa\u00fe\u0103\u0107\u0113\u011e\u012c\u012e\u0139\u0141"+
		"\u0146\u014e\u015c\u0162";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}