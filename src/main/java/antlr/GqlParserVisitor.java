// Generated from GqlParser.g4 by ANTLR 4.10.1

    package antlr;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GqlParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GqlParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GqlParser#query}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuery(GqlParser.QueryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#queryConjunction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryConjunction(GqlParser.QueryConjunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#queryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQueryExpression(GqlParser.QueryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#focusedQueryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFocusedQueryExpression(GqlParser.FocusedQueryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#ambientQueryExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAmbientQueryExpression(GqlParser.AmbientQueryExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#focusedMatchClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFocusedMatchClause(GqlParser.FocusedMatchClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#matchClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMatchClause(GqlParser.MatchClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#whereClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhereClause(GqlParser.WhereClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(GqlParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#countAsterisk}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCountAsterisk(GqlParser.CountAsteriskContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#pathPatternList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathPatternList(GqlParser.PathPatternListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#pathPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathPattern(GqlParser.PathPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#pathPatternPrefix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathPatternPrefix(GqlParser.PathPatternPrefixContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#pathPatternExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathPatternExpression(GqlParser.PathPatternExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#pointPattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPointPattern(GqlParser.PointPatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#parenthesizedPathPatternExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedPathPatternExpression(GqlParser.ParenthesizedPathPatternExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#leftParenthesizedPathPatternExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLeftParenthesizedPathPatternExpression(GqlParser.LeftParenthesizedPathPatternExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#rightParenthesizedPathPatternExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRightParenthesizedPathPatternExpression(GqlParser.RightParenthesizedPathPatternExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#fullParenthesizedPathPatternExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFullParenthesizedPathPatternExpression(GqlParser.FullParenthesizedPathPatternExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#loneParenthesizedPathPatternExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoneParenthesizedPathPatternExpression(GqlParser.LoneParenthesizedPathPatternExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#nodePattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNodePattern(GqlParser.NodePatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#edgePattern}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEdgePattern(GqlParser.EdgePatternContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#fullEdgePointingLeft}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFullEdgePointingLeft(GqlParser.FullEdgePointingLeftContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#fullEdgeUndirected}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFullEdgeUndirected(GqlParser.FullEdgeUndirectedContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#fullEdgePointingRight}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFullEdgePointingRight(GqlParser.FullEdgePointingRightContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#fullEdgeAnyOrientation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFullEdgeAnyOrientation(GqlParser.FullEdgeAnyOrientationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#elementPatternFiller}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementPatternFiller(GqlParser.ElementPatternFillerContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#elementPatternPredicate}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementPatternPredicate(GqlParser.ElementPatternPredicateContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#propertyList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyList(GqlParser.PropertyListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#returnList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnList(GqlParser.ReturnListContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#returnItem}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnItem(GqlParser.ReturnItemContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#setOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetOperator(GqlParser.SetOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#unionOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnionOperator(GqlParser.UnionOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#otherSetOperator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOtherSetOperator(GqlParser.OtherSetOperatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#setQuantifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetQuantifier(GqlParser.SetQuantifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#len}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLen(GqlParser.LenContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#quantifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitQuantifier(GqlParser.QuantifierContext ctx);
	/**
	 * Visit a parse tree produced by the {@code BooleanComparison}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanComparison(GqlParser.BooleanComparisonContext ctx);
	/**
	 * Visit a parse tree produced by the {@code PropertyReference}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPropertyReference(GqlParser.PropertyReferenceContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ValueExpression}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueExpression(GqlParser.ValueExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NameExpression}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNameExpression(GqlParser.NameExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ValueComparison}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValueComparison(GqlParser.ValueComparisonContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ExpressionComparison}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionComparison(GqlParser.ExpressionComparisonContext ctx);
	/**
	 * Visit a parse tree produced by the {@code GraphExistsExpression}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphExistsExpression(GqlParser.GraphExistsExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code CountNameExpression}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCountNameExpression(GqlParser.CountNameExpressionContext ctx);
	/**
	 * Visit a parse tree produced by the {@code NegatedExpression}
	 * labeled alternative in {@link GqlParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNegatedExpression(GqlParser.NegatedExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#isLabelExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIsLabelExpr(GqlParser.IsLabelExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#labelExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelExpression(GqlParser.LabelExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#labelTerm}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelTerm(GqlParser.LabelTermContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#labelFactor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelFactor(GqlParser.LabelFactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#labelNegation}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelNegation(GqlParser.LabelNegationContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#labelPrimary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelPrimary(GqlParser.LabelPrimaryContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#label}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabel(GqlParser.LabelContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#labelWildcard}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabelWildcard(GqlParser.LabelWildcardContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#parenthesizedLabelExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenthesizedLabelExpression(GqlParser.ParenthesizedLabelExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#comparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparator(GqlParser.ComparatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#booleanComparator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBooleanComparator(GqlParser.BooleanComparatorContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#compOp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCompOp(GqlParser.CompOpContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#graphName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGraphName(GqlParser.GraphNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#name}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitName(GqlParser.NameContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#value}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitValue(GqlParser.ValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#pathVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPathVariable(GqlParser.PathVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#elementVariable}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementVariable(GqlParser.ElementVariableContext ctx);
	/**
	 * Visit a parse tree produced by {@link GqlParser#key}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitKey(GqlParser.KeyContext ctx);
}