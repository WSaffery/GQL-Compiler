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
import antlr.GqlParserBaseVisitor;
import ast.expressions.Value;
import ast.expressions.atomic.GqlIdentifier;
import ast.patterns.EdgePattern;
import ast.patterns.ElementPattern;
import ast.patterns.NodePattern;
import ast.patterns.PathPattern;
import ast.patterns.label.Label;
import ast.patterns.label.LabelExpression;
import ast.patterns.label.WildcardLabel;
import enums.Direction;
import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;

import org.antlr.v4.runtime.tree.TerminalNode;
import org.apache.commons.lang.NotImplementedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PathPatternExpressionVisitor extends GqlParserBaseVisitor {
    ExpressionVisitor expressionVisitor = new ExpressionVisitor();

    @Override
    public PathPattern visitPathPatternExpression(PathPatternExpressionContext ctx) {
        return visitPathTerm(ctx.pathTerm());
    }

    @Override
    public PathPattern visitPathTerm(PathTermContext ctx) {
        if (ctx.path() == null) throw new NotImplementedException("A parenthesized path pattern expression should be rewritten.");

        return visitPath(ctx.path());
    }

    @Override
    public PathPattern visitPath(PathContext ctx) {
        ArrayList<ElementPattern> pathSequence = new ArrayList<>();

        for (int i = 0; i < ctx.getChildCount(); i++) {
            if (ctx.getChild(i) instanceof NodePatternContext) {
                pathSequence.add(visitNodePattern((NodePatternContext) ctx.getChild(i)));
            } else if (ctx.getChild(i) instanceof EdgePatternContext) {
                pathSequence.add(visitEdgePattern((EdgePatternContext) ctx.getChild(i)));
            }
        }

        // TODO: implement path variable name correctly
        return new PathPattern(null, pathSequence);
    }

    @Override
    public NodePattern visitNodePattern(NodePatternContext ctx) {
        String variableName = visitElementVariable(ctx.elementPatternFiller().elementVariable());
        LabelExpression labels = visitIsLabelExpr(ctx.elementPatternFiller().isLabelExpr());
        HashMap<String, Value> properties = visitPropertyList(ctx.elementPatternFiller().propertyList());

        return new NodePattern(variableName, labels, properties);
    }

    @Override
    public EdgePattern visitEdgePattern(EdgePatternContext ctx) {
        EdgePattern edge = new EdgePattern(null, null, null, Direction.LEFT_TO_RIGHT);

        if (ctx.getChild(0) instanceof FullEdgeUndirectedContext) {
            edge = visitFullEdgeUndirected((FullEdgeUndirectedContext) ctx.getChild(0));
        } else if (ctx.getChild(0) instanceof FullEdgePointingLeftContext) {
            edge = visitFullEdgePointingLeft((FullEdgePointingLeftContext) ctx.getChild(0));
        } else if (ctx.getChild(0) instanceof FullEdgePointingRightContext) {
            edge = visitFullEdgePointingRight((FullEdgePointingRightContext) ctx.getChild(0));
        }

        if (ctx.len() != null) {
            throw new SemanticErrorException("Quantified edges not currently supported");
        }

        return edge;
    }

    @Override
    public EdgePattern visitFullEdgePointingLeft(FullEdgePointingLeftContext ctx) {
        return getEdgePattern(ctx.elementPatternFiller(), Direction.RIGHT_TO_LEFT);
    }

    @Override
    public EdgePattern visitFullEdgePointingRight(FullEdgePointingRightContext ctx) {
        return getEdgePattern(ctx.elementPatternFiller(), Direction.LEFT_TO_RIGHT);
    }

    @Override
    public EdgePattern visitFullEdgeUndirected(FullEdgeUndirectedContext ctx) {
        return getEdgePattern(ctx.elementPatternFiller(), Direction.UNDIRECTED);
    }

    private EdgePattern getEdgePattern(ElementPatternFillerContext ctx, Direction direction) {
        String variableName = visitElementVariable(ctx.elementVariable());
        LabelExpression labels = visitIsLabelExpr(ctx.isLabelExpr());
        HashMap<String, Value> properties = visitPropertyList(ctx.propertyList());

        return new EdgePattern(variableName, labels, properties, direction);
    }

    @Override
    public Integer visitQuantifier(QuantifierContext ctx) {
         List<TerminalNode> quantifier = ctx.UNSIGNED_INTEGER();
         if (quantifier.size() == 1)  return Integer.parseInt(quantifier.get(0).getText());
         if (quantifier.get(0).getText().equals(quantifier.get(1).getText())) return Integer.parseInt(quantifier.get(0).getText());

         throw new SemanticErrorException("A quantifier for an edge must be a single number or two numbers that are equal to each other.");
    }

    @Override
    public String visitElementVariable(ElementVariableContext ctx) {
        if (ctx == null) return null;
        return ctx.ID().getText();
    }

    @Override
    public LabelExpression visitIsLabelExpr(GqlParser.IsLabelExprContext ctx) {
        if (ctx == null) return new WildcardLabel();

        return visitLabelExpression(ctx.labelExpression());
    }

    @Override
    public LabelExpression visitLabelExpression(LabelExpressionContext ctx) {
        // ArrayList<ArrayList<Label>> labels = new ArrayList<>();
        if (ctx == null) throw new SyntaxErrorException("Bad label expression");

        List<LabelTermContext> terms = ctx.labelTerm();
        if (terms.size() > 1) throw new SyntaxErrorException("Complex Label Expressions Unsupported");

        List<LabelFactorContext> factors = terms.get(0).labelFactor();
        if (factors.size() > 1) throw new SyntaxErrorException("Complex Label Expressions Unsupported");

        LabelFactorContext factor = factors.get(0);

        return visitLabelFactor(factor);
    }

    @Override
    public LabelExpression visitLabelFactor(LabelFactorContext ctx) {
        if (ctx.labelPrimary() != null) return visitLabelPrimary(ctx.labelPrimary());

        throw new SemanticErrorException("Label negations are not implemented, hence, rewrite the query.");
    }

    @Override
    public LabelExpression visitLabelPrimary(LabelPrimaryContext ctx) {
        if (ctx.label() != null) return new Label(ctx.label().ID().getText());
        if (ctx.labelWildcard() != null) return new WildcardLabel();

        throw new SemanticErrorException("Parenthesized label expressions are not yet implemented, hence, rewrite the query.");
    }

    @Override
    public HashMap<String, Value> visitPropertyList(PropertyListContext ctx) {
        HashMap<String, Value> properties = new HashMap<>();
        if (ctx == null) return properties;

        List<KeyContext> keyContexts = ctx.key();
        List<ExprContext> exprContexts = ctx.expr();

        for (int i = 0; i < keyContexts.size(); i++) {
            String key = keyContexts.get(i).ID().getText();
            Value value = expressionVisitor.visitPropertyExpr(exprContexts.get(i));
            properties.put(key, value);
        }

        return properties;
    }
}
