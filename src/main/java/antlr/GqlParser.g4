parser grammar GqlParser;

@header {
    package antlr;
}

options
{
    language=Java;
    tokenVocab=GqlLexer;
}

query
    : queryExpression (queryConjunction queryExpression)* EOF
    ;

queryConjunction
    : setOperator
    | OTHERWISE
    ;

queryExpression
    : focusedQueryExpression
    | ambientQueryExpression
    ;

focusedQueryExpression
    : focusedMatchClause+ returnStatement
    ;

ambientQueryExpression
    : matchClause+ returnStatement
    ;

focusedMatchClause
    : FROM graphName matchClause+
    ;

matchClause
    : (OPTIONAL | MANDATORY)? MATCH pathPatternList whereClause?
    ;

whereClause
    : WHERE expr
    ;

returnStatement
    : RETURN setQuantifier? (ASTERISK | returnList | countAsterisk)
    ;

countAsterisk
    : COUNT LEFT_PAREN ASTERISK RIGHT_PAREN (AS name)?
    ;

pathPatternList
    : pathPattern (COMMA pathPattern)*
    ;

pathPattern
    : (pathVariable EQ)? pathPatternPrefix? pathPatternExpression
    ;

pathPatternPrefix
    : WALK
    | TRAIL
    | ACYCLIC
    | SIMPLE
    ;

pathPatternExpression
    : pointPattern (edgePattern+ pointPattern)*
    ;

pointPattern
    : nodePattern | parenthesizedPathPatternExpression
    ;

parenthesizedPathPatternExpression
    : LEFT_BRACKET pathPatternExpression whereClause? RIGHT_BRACKET len?
    ;

nodePattern
    : LEFT_PAREN elementPatternFiller RIGHT_PAREN
    ;

edgePattern
    : (fullEdgePointingLeft | fullEdgeUndirected | fullEdgePointingRight | fullEdgeAnyOrientation) len?
    ;

fullEdgePointingLeft
    : LEFT_ARROW_BRACKET elementPatternFiller RIGHT_BRACKET_MINUS
    ;

fullEdgeUndirected
    : TILDE_LEFT_BRACKET elementPatternFiller RIGHT_BRACKET_TILDE
    ;

fullEdgePointingRight
    : MINUS_LEFT_BRACKET elementPatternFiller BRACKET_RIGHT_ARROW
    ;

fullEdgeAnyOrientation
    : MINUS_LEFT_BRACKET elementPatternFiller RIGHT_BRACKET_MINUS
    ;

elementPatternFiller
    : elementVariable? isLabelExpr? elementPatternPredicate?
    ;

elementPatternPredicate
    : (LEFT_BRACE propertyList RIGHT_BRACE)
    | whereClause
    ;

propertyList
    : key COLON expr (COMMA key COLON expr)*
    ;

returnList
    : returnItem (COMMA returnItem)*
    ;

returnItem
    : expr (AS name)?
    ;

setOperator
    : unionOperator
    | otherSetOperator
    ;

unionOperator
    : UNION (setQuantifier | MAX)?
    ;

otherSetOperator
    : (EXCEPT | INTERSECT) setQuantifier?
    ;

setQuantifier
    : DISTINCT
    | ALL
    ;

len
    : LEFT_BRACE quantifier RIGHT_BRACE
    | ASTERISK
    ;

quantifier
    : UNSIGNED_INTEGER COMMA UNSIGNED_INTEGER
    | UNSIGNED_INTEGER
    ;

expr
    : value                                 # ValueExpression
    | name                                  # NameExpression
    | name PERIOD key                       # PropertyReference
    | NOT expr                              # NegatedExpression
    | expr compOp expr                      # ValueComparison
    | expr booleanComparator TRUTH_VALUE    # BooleanComparison
    | expr comparator expr                  # ExpressionComparison
    ;

isLabelExpr
    : (IS | COLON) labelExpression
    ;

labelExpression
    : labelTerm (VERTICAL_BAR labelTerm)*
    ;

labelTerm
    : labelFactor (AMPERSAND labelFactor)*
    ;

labelFactor
    : labelPrimary
    | labelNegation
    ;

labelNegation
    : EXCLAMATION_MARK labelPrimary
    ;

labelPrimary
    : label
    | labelWildcard
    | parenthesizedLabelExpression
    ;

label
    : ID
    ;

labelWildcard
    : PERCENT
    ;

parenthesizedLabelExpression
    : LEFT_PAREN labelExpression RIGHT_PAREN
    | LEFT_BRACKET labelExpression RIGHT_BRACKET
    ;

comparator
    : OR
    | AND
    | XOR
    ;

booleanComparator
    : IS (NOT)?
    ;

compOp
    : EQ
    | NEQ
    | LT
    | GT
    | LEQ
    | GEQ
    ;

graphName
    : name
    ;

name
    : ID
    ;

value
    : TRUTH_VALUE
    | UNSIGNED_INTEGER
    | SIGNED_INTEGER
    | SIGNED_FIXED_POINT
    | SIGNED_FLOAT
    | WORD
    ;

pathVariable
    : ID
    ;

elementVariable
    : ID
    ;

key
    : ID
    | ASTERISK
    ;