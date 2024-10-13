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
import ast.atoms.Quantifier;
import ast.expressions.Value;
import ast.patterns.EdgePattern;
import ast.patterns.NodePattern;
import ast.patterns.ParenPathPattern;
import ast.patterns.PathComponent;
import ast.patterns.PathPattern;
import ast.patterns.label.BinaryLabelExpression;
import ast.patterns.label.BinaryLabelOperator;
import ast.patterns.label.Label;
import ast.patterns.label.LabelExpression;
import ast.patterns.label.LabelPattern;
import ast.patterns.label.WildcardLabel;
import ast.variables.GqlVariables;
import ast.variables.VariableType;
import enums.Direction;
import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.lang.StackWalker.Option;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class PathPatternExpressionVisitor extends GqlParserBaseVisitor {    
    GqlVariables variables;
    ExpressionVisitor expressionVisitor;
    boolean existential;
    // If existential is True this visitor will throw if a pattern variable does not 
    // already exist rather than add it to the variable set.

    public PathPatternExpressionVisitor(GqlVariables variables, ExpressionVisitor expressionVisitor, boolean existential)
    {
        this.variables = variables;
        this.expressionVisitor = expressionVisitor;
        this.existential = existential;
    }

    public PathPatternExpressionVisitor(GqlVariables variables, boolean existential)
    {
        this(variables, new ExpressionVisitor(variables), existential);
    }

    public PathPatternExpressionVisitor(GqlVariables variables)
    {
        this(variables, false);
    }

    public PathPattern visitPathPatternExpression(PathPatternExpressionContext ctx, boolean parenPath) {

        ArrayList<PathComponent> pathSequence = new ArrayList<>();

        int actual_idx = 0;
        // the index of the element when added to pathSequence
        // consecutive edges will add anonymous nodes 
        // in-between themselves, making this differ from i

        for (int i = 0; i < ctx.getChildCount(); i++) {
            
            boolean pointIndex = actual_idx % 2 == 0; // nodes and paren paths (points) are at even indexes (zeroth, second, fourth, etc)

            ParseTree child = ctx.getChild(i);

            if (child instanceof PointPatternContext) {
                if (!pointIndex)
                {
                    throw new SemanticErrorException("Duplicate nodes in path without edges in-between");
                }

                ParseTree pointChild = child.getChild(0);

                if (pointChild instanceof NodePatternContext)
                {
                    pathSequence.add(visitNodePattern((NodePatternContext) pointChild, parenPath));
                }
                else if (pointChild instanceof ParenthesizedPathPatternExpressionContext)
                {
                    pathSequence.add(visitParenthesizedPathPatternExpression((ParenthesizedPathPatternExpressionContext) pointChild, parenPath));
                }
                
            } else if (child instanceof EdgePatternContext) {
                if (pointIndex)
                {
                    pathSequence.add(NodePattern.getAnon());
                    actual_idx++;
                }

                pathSequence.add(visitEdgePattern((EdgePatternContext) child, parenPath));
            }

            actual_idx++;
        }

        return new PathPattern(pathSequence);
    }

    public ParenPathPattern visitParenthesizedPathPatternExpression(ParenthesizedPathPatternExpressionContext ctx, boolean parenPath)
    {
        WhereClauseContext where = null;
        LenContext len = null;
        PathPatternExpressionContext path = null;
        NodePatternContext head = null;
        NodePatternContext tail = null;

        System.out.println(ctx);
        if (ctx.leftParenthesizedPathPatternExpression() != null)
        {
            LeftParenthesizedPathPatternExpressionContext lower_ctx = ctx.leftParenthesizedPathPatternExpression();
            where = lower_ctx.whereClause();
            len = lower_ctx.len();
            path = lower_ctx.pathPatternExpression();
            head = lower_ctx.nodePattern();
        }
        else if (ctx.rightParenthesizedPathPatternExpression() != null)
        {
            RightParenthesizedPathPatternExpressionContext lower_ctx = ctx.rightParenthesizedPathPatternExpression();
            where = lower_ctx.whereClause();
            len = lower_ctx.len();
            path = lower_ctx.pathPatternExpression();
            tail = lower_ctx.nodePattern();
        }
        else if (ctx.loneParenthesizedPathPatternExpression() != null)
        {
            LoneParenthesizedPathPatternExpressionContext lower_ctx = ctx.loneParenthesizedPathPatternExpression();
            where = lower_ctx.whereClause();
            len = lower_ctx.len();
            path = lower_ctx.pathPatternExpression();
        }
        else
        {
            assert ctx.fullParenthesizedPathPatternExpression() != null : "Bad paren path";
            FullParenthesizedPathPatternExpressionContext lower_ctx = ctx.fullParenthesizedPathPatternExpression();
            where = lower_ctx.whereClause();
            len = lower_ctx.len();
            path = lower_ctx.pathPatternExpression();
            head = lower_ctx.nodePattern(0);
            tail = lower_ctx.nodePattern(1);
        }

        if (where != null)
        {
            throw new SemanticErrorException("Where clause currently unsupported");
        }
        assert path != null : "bad paren path";

        return new ParenPathPattern(
            visitPathPatternExpression(path,true), 
            null, 
            visitLen(len),
            Optional.ofNullable(head).map(p -> visitNodePattern(p, parenPath)),
            Optional.ofNullable(tail).map(p -> visitNodePattern(p, parenPath))); // incase we're in a nested paren path
    }

    public NodePattern visitNodePattern(NodePatternContext ctx, boolean parenPath) {
        Optional<String> variableName = visitElementVariable(ctx.elementPatternFiller().elementVariable());
        if (variableName.isPresent())
        {
            String name = variableName.get();
            if (!variables.variableExists(name) && existential)
            {
                throw new SemanticErrorException("Non-existent variable reference in existential pattern");
            }
            else 
            {
                variables.addVariable(name, VariableType.NODE, parenPath);
            }
        }

        LabelPattern labels = visitIsLabelExpr(ctx.elementPatternFiller().isLabelExpr());
        HashMap<String, Value> properties = getProperties(ctx.elementPatternFiller());
        
        return new NodePattern(variableName, labels, properties);
    }

    public EdgePattern visitEdgePattern(EdgePatternContext ctx, boolean parenPath) {
        // default unlabelled right pointing edge
        EdgePattern edge = new EdgePattern(Optional.empty(), null, null, Optional.empty());

        if (ctx.getChild(0) instanceof FullEdgeUndirectedContext) {
            edge = visitFullEdgeUndirected((FullEdgeUndirectedContext) ctx.getChild(0), parenPath);
        } else if (ctx.getChild(0) instanceof FullEdgePointingLeftContext) {
            edge = visitFullEdgePointingLeft((FullEdgePointingLeftContext) ctx.getChild(0), parenPath);
        } else if (ctx.getChild(0) instanceof FullEdgePointingRightContext) {
            edge = visitFullEdgePointingRight((FullEdgePointingRightContext) ctx.getChild(0), parenPath);
        } else if (ctx.getChild(0) instanceof FullEdgeAnyOrientationContext)
        {
            edge = visitFullEdgeAnyOrientation((FullEdgeAnyOrientationContext) ctx.getChild(0), parenPath);
        }

        if (ctx.len() != null) {
            throw new SemanticErrorException("Quantified edges not currently supported");
        }

        return edge;
    }

    public EdgePattern visitFullEdgePointingLeft(FullEdgePointingLeftContext ctx, boolean parenPath) {
        return getEdgePattern(ctx.elementPatternFiller(), Optional.of(Direction.RIGHT_TO_LEFT), parenPath);
    }

    public EdgePattern visitFullEdgePointingRight(FullEdgePointingRightContext ctx, boolean parenPath) {
        return getEdgePattern(ctx.elementPatternFiller(), Optional.of(Direction.LEFT_TO_RIGHT), parenPath);
    }

    public EdgePattern visitFullEdgeUndirected(FullEdgeUndirectedContext ctx, boolean parenPath) {
        return getEdgePattern(ctx.elementPatternFiller(), Optional.of(Direction.UNDIRECTED), parenPath);
    }

    public EdgePattern visitFullEdgeAnyOrientation(FullEdgeAnyOrientationContext ctx, boolean parenPath) {
        return getEdgePattern(ctx.elementPatternFiller(), Optional.empty(), parenPath);
    }

    private EdgePattern getEdgePattern(ElementPatternFillerContext ctx, Optional<Direction> direction, boolean parenPath) {
        Optional<String> variableName = visitElementVariable(ctx.elementVariable());
        if (variableName.isPresent())
        {
            String name = variableName.get();
            if (!variables.variableExists(name) && existential)
            {
                throw new SemanticErrorException("Non-existent variable reference in existential pattern");
            }
            else 
            {
                // checks type is correct if variable exists
                variables.addVariable(name, VariableType.EDGE, parenPath);
            }
        }

        LabelPattern labels = visitIsLabelExpr(ctx.isLabelExpr());

        HashMap<String, Value> properties = getProperties(ctx);

        return new EdgePattern(variableName, labels, properties, direction);
    }

    private HashMap<String, Value> getProperties(ElementPatternFillerContext ctx)
    {
        if (ctx.elementPatternPredicate() == null)
        {
            return new HashMap<>();
        }

        if (ctx.elementPatternPredicate().whereClause() != null)
        {
            throw new SyntaxErrorException("Where Clause in element patterns is unsupported");
        }

        return visitPropertyList(ctx.elementPatternPredicate().propertyList());
    }

    @Override 
    public Quantifier visitLen(LenContext ctx)
    {
        if (ctx == null)
        {
            return new Quantifier(1, 1);
        }
        else 
        {
            return visitQuantifier(ctx.quantifier());
        }
    }

    @Override
    public Quantifier visitQuantifier(QuantifierContext ctx) {

        List<TerminalNode> quantifier = ctx.UNSIGNED_INTEGER();
         
        if (quantifier.size() == 1) {
            int k = Integer.parseInt(quantifier.get(0).getText());
            return new Quantifier(k, k);
        }
        else 
        {
            int a = Integer.parseInt(quantifier.get(0).getText());
            int b = Integer.parseInt(quantifier.get(1).getText());
            return new Quantifier(a, b);
        }
    }

    @Override
    public Optional<String> visitElementVariable(ElementVariableContext ctx) {
        if (ctx == null) return Optional.empty();
        return Optional.of(ctx.ID().getText());
    }

    public boolean isStarLabelExpr(LabelExpressionContext ctx)
    {
        return ctx.labelTerm().size() == 1 && 
        ctx.labelTerm(0).labelFactor().size() == 1 &&
        ctx.labelTerm(0).labelFactor(0).labelNegation() == null &&
        ctx.labelTerm(0).labelFactor(0).labelPrimary() != null &&
        ctx.labelTerm(0).labelFactor(0).labelPrimary().labelWildcard() != null;
    }

    @Override
    public LabelPattern visitIsLabelExpr(GqlParser.IsLabelExprContext ctx) {
        if (ctx == null || isStarLabelExpr(ctx.labelExpression())) return new WildcardLabel();
        return visitLabelExpression(ctx.labelExpression());
    }

    // can't be a single wildcard, checked above
    @Override
    public LabelExpression visitLabelExpression(LabelExpressionContext ctx) {
        // ArrayList<ArrayList<Label>> labels = new ArrayList<>();
        if (ctx == null) throw new SyntaxErrorException("Bad label expression");

        List<LabelTermContext> terms = ctx.labelTerm();
        List<LabelFactorContext> factors = terms.get(0).labelFactor();

        if (terms.size() == 1 && factors.size() == 1)
        {
            LabelFactorContext factor = factors.get(0);
            return visitLabelFactor(factor);
        }
        else 
        {
            ArrayList<LabelExpression> labelExpressions = new ArrayList<>();
            for (LabelTermContext term: terms)
            {
                labelExpressions.add(visitLabelTerm(term));
            }
            return new BinaryLabelExpression(labelExpressions, BinaryLabelOperator.OR);
        }
    }

    public LabelExpression visitLabelTerm(LabelTermContext ctx) {
        // ArrayList<ArrayList<Label>> labels = new ArrayList<>();
        if (ctx == null) throw new SyntaxErrorException("Bad label expression");

        List<LabelFactorContext> factors = ctx.labelFactor();

        if (factors.size() == 1)
        {
            LabelFactorContext factor = factors.get(0);
            return visitLabelFactor(factor);
        }
        else 
        {
            ArrayList<LabelExpression> labelExpressions = new ArrayList<>();
            for (LabelFactorContext factor: factors)
            {
                labelExpressions.add(visitLabelFactor(factor));
            }
            return new BinaryLabelExpression(labelExpressions, BinaryLabelOperator.AND);
        }
    }

    @Override
    public LabelExpression visitLabelFactor(LabelFactorContext ctx) {
        if (ctx.labelPrimary() != null) return visitLabelPrimary(ctx.labelPrimary());

        throw new SemanticErrorException("Label negations are not implemented, hence, rewrite the query.");
    }

    @Override
    public LabelExpression visitLabelPrimary(LabelPrimaryContext ctx) {
        if (ctx.label() != null) return new Label(ctx.label().ID().getText());
        if (ctx.labelWildcard() != null) throw new SemanticErrorException("Wildcard in complex label expression");

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
