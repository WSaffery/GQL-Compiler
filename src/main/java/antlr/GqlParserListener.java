// Generated from GqlParser.g4 by ANTLR 4.10.1

    package antlr;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GqlParser}.
 */
public interface GqlParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GqlParser#query}.
	 * @param ctx the parse tree
	 */
	void enterQuery(GqlParser.QueryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#query}.
	 * @param ctx the parse tree
	 */
	void exitQuery(GqlParser.QueryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#queryConjunction}.
	 * @param ctx the parse tree
	 */
	void enterQueryConjunction(GqlParser.QueryConjunctionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#queryConjunction}.
	 * @param ctx the parse tree
	 */
	void exitQueryConjunction(GqlParser.QueryConjunctionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#queryExpression}.
	 * @param ctx the parse tree
	 */
	void enterQueryExpression(GqlParser.QueryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#queryExpression}.
	 * @param ctx the parse tree
	 */
	void exitQueryExpression(GqlParser.QueryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#focusedQueryExpression}.
	 * @param ctx the parse tree
	 */
	void enterFocusedQueryExpression(GqlParser.FocusedQueryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#focusedQueryExpression}.
	 * @param ctx the parse tree
	 */
	void exitFocusedQueryExpression(GqlParser.FocusedQueryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#ambientQueryExpression}.
	 * @param ctx the parse tree
	 */
	void enterAmbientQueryExpression(GqlParser.AmbientQueryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#ambientQueryExpression}.
	 * @param ctx the parse tree
	 */
	void exitAmbientQueryExpression(GqlParser.AmbientQueryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#focusedMatchClause}.
	 * @param ctx the parse tree
	 */
	void enterFocusedMatchClause(GqlParser.FocusedMatchClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#focusedMatchClause}.
	 * @param ctx the parse tree
	 */
	void exitFocusedMatchClause(GqlParser.FocusedMatchClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#matchClause}.
	 * @param ctx the parse tree
	 */
	void enterMatchClause(GqlParser.MatchClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#matchClause}.
	 * @param ctx the parse tree
	 */
	void exitMatchClause(GqlParser.MatchClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void enterWhereClause(GqlParser.WhereClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#whereClause}.
	 * @param ctx the parse tree
	 */
	void exitWhereClause(GqlParser.WhereClauseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(GqlParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(GqlParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#countAsterisk}.
	 * @param ctx the parse tree
	 */
	void enterCountAsterisk(GqlParser.CountAsteriskContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#countAsterisk}.
	 * @param ctx the parse tree
	 */
	void exitCountAsterisk(GqlParser.CountAsteriskContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#pathPatternList}.
	 * @param ctx the parse tree
	 */
	void enterPathPatternList(GqlParser.PathPatternListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#pathPatternList}.
	 * @param ctx the parse tree
	 */
	void exitPathPatternList(GqlParser.PathPatternListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#pathPattern}.
	 * @param ctx the parse tree
	 */
	void enterPathPattern(GqlParser.PathPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#pathPattern}.
	 * @param ctx the parse tree
	 */
	void exitPathPattern(GqlParser.PathPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#pathPatternPrefix}.
	 * @param ctx the parse tree
	 */
	void enterPathPatternPrefix(GqlParser.PathPatternPrefixContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#pathPatternPrefix}.
	 * @param ctx the parse tree
	 */
	void exitPathPatternPrefix(GqlParser.PathPatternPrefixContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#pathPatternExpression}.
	 * @param ctx the parse tree
	 */
	void enterPathPatternExpression(GqlParser.PathPatternExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#pathPatternExpression}.
	 * @param ctx the parse tree
	 */
	void exitPathPatternExpression(GqlParser.PathPatternExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#pointPattern}.
	 * @param ctx the parse tree
	 */
	void enterPointPattern(GqlParser.PointPatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#pointPattern}.
	 * @param ctx the parse tree
	 */
	void exitPointPattern(GqlParser.PointPatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#parenthesizedPathPatternExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedPathPatternExpression(GqlParser.ParenthesizedPathPatternExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#parenthesizedPathPatternExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedPathPatternExpression(GqlParser.ParenthesizedPathPatternExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#leftParenthesizedPathPatternExpression}.
	 * @param ctx the parse tree
	 */
	void enterLeftParenthesizedPathPatternExpression(GqlParser.LeftParenthesizedPathPatternExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#leftParenthesizedPathPatternExpression}.
	 * @param ctx the parse tree
	 */
	void exitLeftParenthesizedPathPatternExpression(GqlParser.LeftParenthesizedPathPatternExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#rightParenthesizedPathPatternExpression}.
	 * @param ctx the parse tree
	 */
	void enterRightParenthesizedPathPatternExpression(GqlParser.RightParenthesizedPathPatternExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#rightParenthesizedPathPatternExpression}.
	 * @param ctx the parse tree
	 */
	void exitRightParenthesizedPathPatternExpression(GqlParser.RightParenthesizedPathPatternExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#fullParenthesizedPathPatternExpression}.
	 * @param ctx the parse tree
	 */
	void enterFullParenthesizedPathPatternExpression(GqlParser.FullParenthesizedPathPatternExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#fullParenthesizedPathPatternExpression}.
	 * @param ctx the parse tree
	 */
	void exitFullParenthesizedPathPatternExpression(GqlParser.FullParenthesizedPathPatternExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#loneParenthesizedPathPatternExpression}.
	 * @param ctx the parse tree
	 */
	void enterLoneParenthesizedPathPatternExpression(GqlParser.LoneParenthesizedPathPatternExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#loneParenthesizedPathPatternExpression}.
	 * @param ctx the parse tree
	 */
	void exitLoneParenthesizedPathPatternExpression(GqlParser.LoneParenthesizedPathPatternExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#nodePattern}.
	 * @param ctx the parse tree
	 */
	void enterNodePattern(GqlParser.NodePatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#nodePattern}.
	 * @param ctx the parse tree
	 */
	void exitNodePattern(GqlParser.NodePatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#edgePattern}.
	 * @param ctx the parse tree
	 */
	void enterEdgePattern(GqlParser.EdgePatternContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#edgePattern}.
	 * @param ctx the parse tree
	 */
	void exitEdgePattern(GqlParser.EdgePatternContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#fullEdgePointingLeft}.
	 * @param ctx the parse tree
	 */
	void enterFullEdgePointingLeft(GqlParser.FullEdgePointingLeftContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#fullEdgePointingLeft}.
	 * @param ctx the parse tree
	 */
	void exitFullEdgePointingLeft(GqlParser.FullEdgePointingLeftContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#fullEdgeUndirected}.
	 * @param ctx the parse tree
	 */
	void enterFullEdgeUndirected(GqlParser.FullEdgeUndirectedContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#fullEdgeUndirected}.
	 * @param ctx the parse tree
	 */
	void exitFullEdgeUndirected(GqlParser.FullEdgeUndirectedContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#fullEdgePointingRight}.
	 * @param ctx the parse tree
	 */
	void enterFullEdgePointingRight(GqlParser.FullEdgePointingRightContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#fullEdgePointingRight}.
	 * @param ctx the parse tree
	 */
	void exitFullEdgePointingRight(GqlParser.FullEdgePointingRightContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#fullEdgeAnyOrientation}.
	 * @param ctx the parse tree
	 */
	void enterFullEdgeAnyOrientation(GqlParser.FullEdgeAnyOrientationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#fullEdgeAnyOrientation}.
	 * @param ctx the parse tree
	 */
	void exitFullEdgeAnyOrientation(GqlParser.FullEdgeAnyOrientationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#elementPatternFiller}.
	 * @param ctx the parse tree
	 */
	void enterElementPatternFiller(GqlParser.ElementPatternFillerContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#elementPatternFiller}.
	 * @param ctx the parse tree
	 */
	void exitElementPatternFiller(GqlParser.ElementPatternFillerContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#elementPatternPredicate}.
	 * @param ctx the parse tree
	 */
	void enterElementPatternPredicate(GqlParser.ElementPatternPredicateContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#elementPatternPredicate}.
	 * @param ctx the parse tree
	 */
	void exitElementPatternPredicate(GqlParser.ElementPatternPredicateContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#propertyList}.
	 * @param ctx the parse tree
	 */
	void enterPropertyList(GqlParser.PropertyListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#propertyList}.
	 * @param ctx the parse tree
	 */
	void exitPropertyList(GqlParser.PropertyListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#returnList}.
	 * @param ctx the parse tree
	 */
	void enterReturnList(GqlParser.ReturnListContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#returnList}.
	 * @param ctx the parse tree
	 */
	void exitReturnList(GqlParser.ReturnListContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#returnItem}.
	 * @param ctx the parse tree
	 */
	void enterReturnItem(GqlParser.ReturnItemContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#returnItem}.
	 * @param ctx the parse tree
	 */
	void exitReturnItem(GqlParser.ReturnItemContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#setOperator}.
	 * @param ctx the parse tree
	 */
	void enterSetOperator(GqlParser.SetOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#setOperator}.
	 * @param ctx the parse tree
	 */
	void exitSetOperator(GqlParser.SetOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#unionOperator}.
	 * @param ctx the parse tree
	 */
	void enterUnionOperator(GqlParser.UnionOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#unionOperator}.
	 * @param ctx the parse tree
	 */
	void exitUnionOperator(GqlParser.UnionOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#otherSetOperator}.
	 * @param ctx the parse tree
	 */
	void enterOtherSetOperator(GqlParser.OtherSetOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#otherSetOperator}.
	 * @param ctx the parse tree
	 */
	void exitOtherSetOperator(GqlParser.OtherSetOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#setQuantifier}.
	 * @param ctx the parse tree
	 */
	void enterSetQuantifier(GqlParser.SetQuantifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#setQuantifier}.
	 * @param ctx the parse tree
	 */
	void exitSetQuantifier(GqlParser.SetQuantifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#len}.
	 * @param ctx the parse tree
	 */
	void enterLen(GqlParser.LenContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#len}.
	 * @param ctx the parse tree
	 */
	void exitLen(GqlParser.LenContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#quantifier}.
	 * @param ctx the parse tree
	 */
	void enterQuantifier(GqlParser.QuantifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#quantifier}.
	 * @param ctx the parse tree
	 */
	void exitQuantifier(GqlParser.QuantifierContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BooleanComparison}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterBooleanComparison(GqlParser.BooleanComparisonContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BooleanComparison}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitBooleanComparison(GqlParser.BooleanComparisonContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PropertyReference}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterPropertyReference(GqlParser.PropertyReferenceContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PropertyReference}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitPropertyReference(GqlParser.PropertyReferenceContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ValueExpression}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterValueExpression(GqlParser.ValueExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ValueExpression}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitValueExpression(GqlParser.ValueExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NameExpression}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNameExpression(GqlParser.NameExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NameExpression}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNameExpression(GqlParser.NameExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ValueComparison}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterValueComparison(GqlParser.ValueComparisonContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ValueComparison}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitValueComparison(GqlParser.ValueComparisonContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpressionComparison}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpressionComparison(GqlParser.ExpressionComparisonContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpressionComparison}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpressionComparison(GqlParser.ExpressionComparisonContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GraphExistsExpression}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterGraphExistsExpression(GqlParser.GraphExistsExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GraphExistsExpression}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitGraphExistsExpression(GqlParser.GraphExistsExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CountNameExpression}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterCountNameExpression(GqlParser.CountNameExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CountNameExpression}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitCountNameExpression(GqlParser.CountNameExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NegatedExpression}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterNegatedExpression(GqlParser.NegatedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NegatedExpression}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitNegatedExpression(GqlParser.NegatedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#isLabelExpr}.
	 * @param ctx the parse tree
	 */
	void enterIsLabelExpr(GqlParser.IsLabelExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#isLabelExpr}.
	 * @param ctx the parse tree
	 */
	void exitIsLabelExpr(GqlParser.IsLabelExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#labelExpression}.
	 * @param ctx the parse tree
	 */
	void enterLabelExpression(GqlParser.LabelExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#labelExpression}.
	 * @param ctx the parse tree
	 */
	void exitLabelExpression(GqlParser.LabelExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#labelTerm}.
	 * @param ctx the parse tree
	 */
	void enterLabelTerm(GqlParser.LabelTermContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#labelTerm}.
	 * @param ctx the parse tree
	 */
	void exitLabelTerm(GqlParser.LabelTermContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#labelFactor}.
	 * @param ctx the parse tree
	 */
	void enterLabelFactor(GqlParser.LabelFactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#labelFactor}.
	 * @param ctx the parse tree
	 */
	void exitLabelFactor(GqlParser.LabelFactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#labelNegation}.
	 * @param ctx the parse tree
	 */
	void enterLabelNegation(GqlParser.LabelNegationContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#labelNegation}.
	 * @param ctx the parse tree
	 */
	void exitLabelNegation(GqlParser.LabelNegationContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#labelPrimary}.
	 * @param ctx the parse tree
	 */
	void enterLabelPrimary(GqlParser.LabelPrimaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#labelPrimary}.
	 * @param ctx the parse tree
	 */
	void exitLabelPrimary(GqlParser.LabelPrimaryContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#label}.
	 * @param ctx the parse tree
	 */
	void enterLabel(GqlParser.LabelContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#label}.
	 * @param ctx the parse tree
	 */
	void exitLabel(GqlParser.LabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#labelWildcard}.
	 * @param ctx the parse tree
	 */
	void enterLabelWildcard(GqlParser.LabelWildcardContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#labelWildcard}.
	 * @param ctx the parse tree
	 */
	void exitLabelWildcard(GqlParser.LabelWildcardContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#parenthesizedLabelExpression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedLabelExpression(GqlParser.ParenthesizedLabelExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#parenthesizedLabelExpression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedLabelExpression(GqlParser.ParenthesizedLabelExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#comparator}.
	 * @param ctx the parse tree
	 */
	void enterComparator(GqlParser.ComparatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#comparator}.
	 * @param ctx the parse tree
	 */
	void exitComparator(GqlParser.ComparatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#booleanComparator}.
	 * @param ctx the parse tree
	 */
	void enterBooleanComparator(GqlParser.BooleanComparatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#booleanComparator}.
	 * @param ctx the parse tree
	 */
	void exitBooleanComparator(GqlParser.BooleanComparatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#compOp}.
	 * @param ctx the parse tree
	 */
	void enterCompOp(GqlParser.CompOpContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#compOp}.
	 * @param ctx the parse tree
	 */
	void exitCompOp(GqlParser.CompOpContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#graphName}.
	 * @param ctx the parse tree
	 */
	void enterGraphName(GqlParser.GraphNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#graphName}.
	 * @param ctx the parse tree
	 */
	void exitGraphName(GqlParser.GraphNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#name}.
	 * @param ctx the parse tree
	 */
	void enterName(GqlParser.NameContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#name}.
	 * @param ctx the parse tree
	 */
	void exitName(GqlParser.NameContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#value}.
	 * @param ctx the parse tree
	 */
	void enterValue(GqlParser.ValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#value}.
	 * @param ctx the parse tree
	 */
	void exitValue(GqlParser.ValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#pathVariable}.
	 * @param ctx the parse tree
	 */
	void enterPathVariable(GqlParser.PathVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#pathVariable}.
	 * @param ctx the parse tree
	 */
	void exitPathVariable(GqlParser.PathVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#elementVariable}.
	 * @param ctx the parse tree
	 */
	void enterElementVariable(GqlParser.ElementVariableContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#elementVariable}.
	 * @param ctx the parse tree
	 */
	void exitElementVariable(GqlParser.ElementVariableContext ctx);
	/**
	 * Enter a parse tree produced by {@link GqlParser#key}.
	 * @param ctx the parse tree
	 */
	void enterKey(GqlParser.KeyContext ctx);
	/**
	 * Exit a parse tree produced by {@link GqlParser#key}.
	 * @param ctx the parse tree
	 */
	void exitKey(GqlParser.KeyContext ctx);
}