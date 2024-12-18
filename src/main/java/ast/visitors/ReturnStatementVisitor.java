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

import antlr.GqlParser.CountAsteriskContext;
import antlr.GqlParser.ReturnItemContext;
import antlr.GqlParser.ReturnListContext;
import antlr.GqlParser.ReturnStatementContext;
import antlr.GqlParser.SetQuantifierContext;
import ast.expressions.Expression;
import ast.expressions.references.CountNameExpression;
import ast.expressions.references.NameExpression;
import ast.returns.Asterisk;
import ast.returns.CountAsterisk;
import ast.returns.ReturnExpression;
import ast.returns.ReturnItem;
import ast.returns.ReturnStatement;
import ast.variables.GqlVariables;
import enums.SetQuantifier;
import exceptions.SyntaxErrorException;
import antlr.GqlParserBaseVisitor;

import java.util.ArrayList;


public class ReturnStatementVisitor extends GqlParserBaseVisitor {
    ExpressionVisitor expressionVisitor;

    public ReturnStatementVisitor(GqlVariables variables) {
        expressionVisitor = new ExpressionVisitor(variables);
    }

    @Override
    public ReturnStatement visitReturnStatement(ReturnStatementContext ctx) {
        if (ctx.ASTERISK() != null)
        {
            ArrayList<ReturnItem> returnItems = new ArrayList<>();
            returnItems.add(new Asterisk());
            return new ReturnStatement(
                visitSetQuantifier(ctx.setQuantifier()), returnItems);
        }
        else if (ctx.countAsterisk() != null)
        {
            return new ReturnStatement(
                visitSetQuantifier(ctx.setQuantifier()), visitCountAsterisk(ctx.countAsterisk()));
        }

        return new ReturnStatement(
            visitSetQuantifier(ctx.setQuantifier()), visitReturnList(ctx.returnList()));
    }

    @Override
    public SetQuantifier visitSetQuantifier(SetQuantifierContext ctx) {
        if (ctx == null) return SetQuantifier.ALL;
        if (ctx.ALL() != null) return SetQuantifier.ALL;
        return SetQuantifier.DISTINCT;
    }

    @Override
    public ArrayList<ReturnItem> visitCountAsterisk(CountAsteriskContext ctx)
    {
        ArrayList<ReturnItem> returnItems = new ArrayList<>();
        
        if (ctx.name() != null)
        {
            returnItems.add(new CountAsterisk(ctx.name().ID().getText()));
        }
        else 
        {
            returnItems.add(new CountAsterisk());
        }

        return returnItems;
    }

    @Override
    public ArrayList<ReturnItem> visitReturnList(ReturnListContext ctx) {
        ArrayList<ReturnItem> returnItems = new ArrayList<>();

        for (ReturnItemContext returnItemContext: ctx.returnItem()) {
            returnItems.add(visitReturnItem(returnItemContext));
        }

        return returnItems;
    }

    @Override
    public ReturnExpression visitReturnItem(ReturnItemContext ctx) {
        Expression expr = expressionVisitor.visitExpr(ctx.expr());
        if (ctx.name() != null) return new ReturnExpression(expr, ctx.name().ID().getText());

        if (expr instanceof NameExpression)
        {
            NameExpression namedExpression = (NameExpression) expr;
            return new ReturnExpression(namedExpression);
        }
        else if (expr instanceof CountNameExpression)
        {
            CountNameExpression namedCountExpression = (CountNameExpression) expr;
            return new ReturnExpression(namedCountExpression, "COUNT(" + namedCountExpression.name() + ")");
        }
        else 
        {
            throw new SyntaxErrorException("Only a variable name is allowed without alias.");
        }
        
    }
}
