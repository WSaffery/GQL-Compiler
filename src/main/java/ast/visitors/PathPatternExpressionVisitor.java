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
import ast.patterns.label.Label;
import ast.patterns.label.LabelExpression;
import ast.patterns.label.WildcardLabel;
import enums.Direction;
import exceptions.SemanticErrorException;
import exceptions.SyntaxErrorException;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class PathPatternExpressionVisitor extends GqlParserBaseVisitor {    
    ExpressionVisitor expressionVisitor = new ExpressionVisitor();

    @Override
    public PathPattern visitPathPatternExpression(PathPatternExpressionContext ctx) {
        ArrayList<PathComponent> pathSequence = new ArrayList<>();

        int actual_idx = 0;
        // the index of the element when added to pathSequence
        // consecutive edges will add anonymous nodes 
        // inbetween themselves, making this differ from i

        for (int i = 0; i < ctx.getChildCount(); i++) {
            
            boolean pointIndex = actual_idx % 2 == 0; // nodes and paren paths (points) are at even indexes (zeroth, second, fourth, etc)

            ParseTree child = ctx.getChild(i);

            if (child instanceof PointPatternContext) {
                if (!pointIndex)
                {
                    throw new SemanticErrorException("Duplicate nodes in path without edges inbetween");
                }

                ParseTree pointChild = child.getChild(0);

                if (pointChild instanceof NodePatternContext)
                {
                    pathSequence.add(visitNodePattern((NodePatternContext) pointChild));
                }
                else if (pointChild instanceof ParenthesizedPathPatternExpressionContext)
                {
                    pathSequence.add(visitParenthesizedPathPatternExpression((ParenthesizedPathPatternExpressionContext) pointChild));
                }
                
            } else if (child instanceof EdgePatternContext) {
                if (pointIndex)
                {
                    pathSequence.add(NodePattern.getAnon());
                    actual_idx++;
                }

                pathSequence.add(visitEdgePattern((EdgePatternContext) child));
            }

            actual_idx++;
        }

        return new PathPattern(pathSequence);
    }

    @Override
    public ParenPathPattern visitParenthesizedPathPatternExpression(ParenthesizedPathPatternExpressionContext ctx)
    {
        if (ctx.whereClause() != null)
        {
            throw new SemanticErrorException("Where clause currently unsupported");
        }

        return new ParenPathPattern(
            visitPathPatternExpression(ctx.pathPatternExpression()), 
            null, 
            visitLen(ctx.len()));
    }

    @Override
    public NodePattern visitNodePattern(NodePatternContext ctx) {
        Optional<String> variableName = visitElementVariable(ctx.elementPatternFiller().elementVariable());
        LabelExpression labels = visitIsLabelExpr(ctx.elementPatternFiller().isLabelExpr());

        HashMap<String, Value> properties = getProperties(ctx.elementPatternFiller());

        return new NodePattern(variableName, labels, properties);
    }

    @Override
    public EdgePattern visitEdgePattern(EdgePatternContext ctx) {
        // default unlabelled right pointing edge
        EdgePattern edge = new EdgePattern(Optional.empty(), null, null, Optional.empty());

        if (ctx.getChild(0) instanceof FullEdgeUndirectedContext) {
            edge = visitFullEdgeUndirected((FullEdgeUndirectedContext) ctx.getChild(0));
        } else if (ctx.getChild(0) instanceof FullEdgePointingLeftContext) {
            edge = visitFullEdgePointingLeft((FullEdgePointingLeftContext) ctx.getChild(0));
        } else if (ctx.getChild(0) instanceof FullEdgePointingRightContext) {
            edge = visitFullEdgePointingRight((FullEdgePointingRightContext) ctx.getChild(0));
        } else if (ctx.getChild(0) instanceof FullEdgeAnyOrientationContext)
        {
            edge = visitFullEdgeAnyOrientation((FullEdgeAnyOrientationContext) ctx.getChild(0));
        }

        if (ctx.len() != null) {
            throw new SemanticErrorException("Quantified edges not currently supported");
        }

        return edge;
    }

    @Override
    public EdgePattern visitFullEdgePointingLeft(FullEdgePointingLeftContext ctx) {
        return getEdgePattern(ctx.elementPatternFiller(), Optional.of(Direction.RIGHT_TO_LEFT));
    }

    @Override
    public EdgePattern visitFullEdgePointingRight(FullEdgePointingRightContext ctx) {
        return getEdgePattern(ctx.elementPatternFiller(), Optional.of(Direction.LEFT_TO_RIGHT));
    }

    @Override
    public EdgePattern visitFullEdgeUndirected(FullEdgeUndirectedContext ctx) {
        return getEdgePattern(ctx.elementPatternFiller(), Optional.of(Direction.UNDIRECTED));
    }

    @Override
    public EdgePattern visitFullEdgeAnyOrientation(FullEdgeAnyOrientationContext ctx) {
        return getEdgePattern(ctx.elementPatternFiller(), Optional.empty());
    }

    private EdgePattern getEdgePattern(ElementPatternFillerContext ctx, Optional<Direction> direction) {
        Optional<String> variableName = visitElementVariable(ctx.elementVariable());
        LabelExpression labels = visitIsLabelExpr(ctx.isLabelExpr());

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
