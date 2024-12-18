/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package ast.visitors;

import antlr.GqlParser;
import antlr.GqlParser.*;
import exceptions.SemanticErrorException;
import enums.BooleanComparator;
import enums.ValueComparator;
import antlr.GqlParserBaseVisitor;
import ast.expressions.Expression;
import ast.expressions.Value;
import ast.expressions.atomic.Decimal;
import ast.expressions.atomic.GqlString;
import ast.expressions.atomic.TruthValue;
import ast.expressions.composite.BooleanConjunctionExpression;
import ast.expressions.composite.ComparisonExpression;
import ast.expressions.composite.NegatedExpression;
import ast.expressions.graph.GraphExistsExpression;
import ast.expressions.references.CountNameExpression;
import ast.expressions.references.NameExpression;
import ast.expressions.references.PropertyReference;
import ast.patterns.PathPattern;
import ast.variables.GqlVariables;

import org.antlr.v4.runtime.tree.TerminalNode;


public class ExpressionVisitor extends GqlParserBaseVisitor<Expression> {
    GqlVariables variables;
    PathPatternExpressionVisitor pathPatternExpressionVisitor;

    public ExpressionVisitor(GqlVariables variables) {
        this.variables = variables;
        pathPatternExpressionVisitor = new PathPatternExpressionVisitor(variables, this, true);
    }

    public Expression visitExpr(ExprContext ctx) {
        if (ctx instanceof ValueExpressionContext) return visitValue(((ValueExpressionContext) ctx).value());
        if (ctx instanceof NameExpressionContext) return visitNameExpression((NameExpressionContext) ctx);
        if (ctx instanceof PropertyReferenceContext) return visitPropertyReference((PropertyReferenceContext) ctx);
        if (ctx instanceof ExpressionComparisonContext) return visitExpressionComparison((ExpressionComparisonContext) ctx);
        if (ctx instanceof NegatedExpressionContext) return visitNegatedExpression((NegatedExpressionContext) ctx);
        if (ctx instanceof BooleanComparisonContext) return visitBooleanComparison((BooleanComparisonContext) ctx);
        if (ctx instanceof ValueComparisonContext) return visitValueComparison((ValueComparisonContext) ctx);
        if (ctx instanceof GraphExistsExpressionContext) return visitGraphExistsExpression((GraphExistsExpressionContext) ctx);
        if (ctx instanceof CountNameExpressionContext) return visitCountNameExpression((CountNameExpressionContext) ctx);
        throw new SemanticErrorException("Invalid expression given at line " + ctx.getStart().getLine() + " column " + ctx.getStart().getCharPositionInLine() + ".");
    }

    public Expression visitCountNameExpression(CountNameExpressionContext ctx) {
        return new CountNameExpression(ctx.name().ID().getText());
    }

    public Expression visitGraphExistsExpression(GraphExistsExpressionContext ctx) {
        assert ctx.pathPatternExpression() != null : "Exists expression without path pattern";
        PathPattern existentialPattern = pathPatternExpressionVisitor.visitPathPatternExpression(ctx.pathPatternExpression(), false);
        return new GraphExistsExpression(existentialPattern);
    }

    public Value visitPropertyExpr(ExprContext ctx) {
        if (ctx instanceof ValueExpressionContext) return visitValue(((ValueExpressionContext) ctx).value());

        throw new SemanticErrorException("Invalid property value given at line " + ctx.getStart().getLine() + " column " + ctx.getStart().getCharPositionInLine() + ".");
    }

    @Override
    public Value visitValue(ValueContext ctx) {
        switch (ctx.getStart().getType()) {
            case GqlParser.WORD:
                String word = ctx.getText().replace("\"", "");
                if (word.matches("[Tt][Rr][Uu][Ee]")) return new TruthValue(true);
                if (word.matches("[Ff][Aa][Ll][Ss][Ee]")) return new TruthValue(false);
                if (word.matches("[Uu][Nn][Kk][Nn][Oo][Ww][Nn]") ||
                        word.matches("[Nn][Uu][Ll][Ll]")) return new TruthValue(null);

                return new GqlString(word);
            case GqlParser.TRUTH_VALUE:
                return getTruthValue(ctx.TRUTH_VALUE());
            case GqlParser.UNSIGNED_INTEGER:
            case GqlParser.SIGNED_INTEGER:
            case GqlParser.SIGNED_FIXED_POINT:
                return new Decimal(Float.parseFloat(ctx.getText().replace("\"", "")));
            default:
                return new Decimal(Float.parseFloat(ctx.getText().replace("\"", "")));
        }
    }

    @Override
    public Expression visitNameExpression(NameExpressionContext ctx) {
        return new NameExpression(ctx.name().ID().getText());
    }

    @Override
    public Expression visitPropertyReference(PropertyReferenceContext ctx) {
        return new PropertyReference(
            ctx.name().ID().getText(), 
            ctx.key().ID() == null ? "*" : ctx.key().ID().getText());
    }

    @Override
    public Expression visitExpressionComparison(ExpressionComparisonContext ctx) {
        Expression left = visitExpr(ctx.expr(0));
        BooleanComparator comparator = getComparator(ctx.comparator());
        Expression right = visitExpr(ctx.expr(1));

        return new BooleanConjunctionExpression(left, comparator, right);
    }

    @Override
    public Expression visitNegatedExpression(NegatedExpressionContext ctx) {
        return new NegatedExpression(visitExpr(ctx.expr()));
    }

    @Override
    public Expression visitBooleanComparison(BooleanComparisonContext ctx) {
        Expression left = visitExpr(ctx.expr());
        BooleanComparator comparator = getBooleanComparator(ctx.booleanComparator());
        TruthValue right = getTruthValue(ctx.TRUTH_VALUE());

        return new BooleanConjunctionExpression(left, comparator, right);
    }

    @Override
    public Expression visitValueComparison(ValueComparisonContext ctx) {
        Expression left = visitExpr(ctx.expr(0));
        ValueComparator comparator = getValueComparator(ctx.compOp());
        Expression right = visitExpr(ctx.expr(1));

        return new ComparisonExpression(left, comparator, right);
    }

    public BooleanComparator getComparator(ComparatorContext ctx) {
        if (ctx.OR() != null) return BooleanComparator.OR;
        if (ctx.XOR() != null) return BooleanComparator.XOR;
        if (ctx.AND() != null) return BooleanComparator.AND;

        throw new SemanticErrorException("Incorrect boolean comparator at line " + ctx.getStart().getLine() + ".");
    }

    private BooleanComparator getBooleanComparator(BooleanComparatorContext ctx) {
        if (ctx.NOT() != null) return BooleanComparator.IS_NOT;
        return BooleanComparator.IS;
    }

    private ValueComparator getValueComparator(CompOpContext ctx) {
        if (ctx.EQ() != null) return ValueComparator.EQ;
        if (ctx.NEQ() != null) return ValueComparator.NEQ;
        if (ctx.LT() != null) return ValueComparator.LT;
        if (ctx.GT() != null) return ValueComparator.GT;
        if (ctx.LEQ() != null) return ValueComparator.LEQ;
        if (ctx.GEQ() != null) return ValueComparator.GEQ;

        throw new SemanticErrorException("Incorrect value comparator at line " + ctx.getStart().getLine() + ".");
    }

    private TruthValue getTruthValue(TerminalNode truthValue) {
        switch (truthValue.getText()) {
            case "TRUE":
                return new TruthValue(true);
            case "FALSE":
                return new TruthValue(false);
            default:
                return new TruthValue(null);
        }
    }
}
