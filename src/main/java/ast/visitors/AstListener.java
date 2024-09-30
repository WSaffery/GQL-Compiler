package ast.visitors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import antlr.GqlParserBaseListener;
import antlr.GqlParser.FocusedMatchClauseContext;
import antlr.GqlParser.MatchClauseContext;
import antlr.GqlParser.OtherSetOperatorContext;
import antlr.GqlParser.PathPatternContext;
import antlr.GqlParser.PathPatternPrefixContext;
import antlr.GqlParser.QueryConjunctionContext;
import antlr.GqlParser.QueryExpressionContext;
import antlr.GqlParser.ReturnStatementContext;
import antlr.GqlParser.UnionOperatorContext;
import antlr.GqlParser.WhereClauseContext;
import exceptions.SemanticErrorException;
import ast.expressions.Expression;
import ast.GqlProgram;
import ast.patterns.PathPattern;
import ast.patterns.QualifiedPathPattern;
import ast.queries.GqlQuery;
import ast.queries.QueryConjunctor;
import ast.variables.VariableType;
import enums.EvaluationMode;
import gql_gremlin.matching.MatchExpression;

// Ast Listener for single graph query
// builds up the program result using local state, accessible via GetResult.
public class AstListener extends GqlParserBaseListener {
    public List<String> semanticErrors = new ArrayList<>();
    private GqlProgram result = new GqlProgram();
    private PathPatternExpressionVisitor pathPatternExpressionVisitor = new PathPatternExpressionVisitor(result.variables);
    private WhereClauseVisitor whereClauseVisitor = new WhereClauseVisitor(result.variables);
    private ReturnStatementVisitor returnStatementVisitor = new ReturnStatementVisitor(result.variables);
    private GqlQuery currentQuery = null;

    public GqlProgram GetResult()
    {
        return result;
    }

    public void enterQueryExpression(QueryExpressionContext ctx)
    {
        currentQuery = new GqlQuery();
        result.body.addQuery(currentQuery);
    }

    public void exitQueryExpression(QueryExpressionContext ctx)
    {
        currentQuery = null;
    }

    public void enterFocusedMatchClause(FocusedMatchClauseContext ctx)
    {
        if (result.graphName.isPresent())
        {
            throw new SemanticErrorException("Queries with multiple FROM statement's aren't supported");
        }
        result.graphName = Optional.of(ctx.graphName().getText());
    }

    public void enterMatchClause(MatchClauseContext ctx)
    {
        boolean isMandatory = ctx.OPTIONAL() == null;
        
        ArrayList<QualifiedPathPattern> pathPatterns = new ArrayList<>();

        for (PathPatternContext pathPatternCtx : ctx.pathPatternList().pathPattern()) {
            
            EvaluationMode mode = getEvaluationMode(pathPatternCtx.pathPatternPrefix());
            Optional<String> var = pathPatternCtx.pathVariable() == null ?
                Optional.empty() :
                Optional.of(pathPatternCtx.pathVariable().getText());
            
            PathPattern path = pathPatternExpressionVisitor.visitPathPatternExpression(pathPatternCtx.pathPatternExpression());

            pathPatterns.add(new QualifiedPathPattern(var, mode, path));
            var.ifPresent((name) -> result.variables.addVariable(name, VariableType.PATH));
        }

        WhereClauseContext whereClause = ctx.whereClause();
        Optional<Expression> whereClauseExpression = whereClause != null ? 
            Optional.of(whereClauseVisitor.visitWhereClause(whereClause)) : 
            Optional.empty();

        Optional<String> graphName = Optional.empty();
        if (ctx.getParent() instanceof FocusedMatchClauseContext) {
            FocusedMatchClauseContext focused = (FocusedMatchClauseContext) ctx.getParent();
            graphName = Optional.of(focused.graphName().getText());
        }

        MatchExpression expression = new MatchExpression(graphName, isMandatory, whereClauseExpression, pathPatterns);
        currentQuery.matchExpressions.add(expression);
    }

    public void enterQueryConjunction(QueryConjunctionContext ctx) 
    {
        result.body.addConjunctor(getQueryConjunctor(ctx));
    }

    public void enterReturnStatement(ReturnStatementContext ctx)
    {
        currentQuery.returnStatement = returnStatementVisitor.visitReturnStatement(ctx);
    }

    public EvaluationMode getEvaluationMode(PathPatternPrefixContext ctx) {
        if (ctx == null) return EvaluationMode.WALK;

        if (ctx.ACYCLIC() != null) {
            return EvaluationMode.ACYCLIC;
        } else if (ctx.TRAIL() != null) {
            return EvaluationMode.TRAIL;
        } else if (ctx.SIMPLE() != null) {
            return EvaluationMode.SIMPLE;
        }

        return EvaluationMode.WALK;
    }

    public QueryConjunctor getQueryConjunctor(QueryConjunctionContext ctx) {
        if (ctx.OTHERWISE() != null) return QueryConjunctor.OTHERWISE;

        if (ctx.setOperator().unionOperator() != null) {
            return getUnionConjunctor(ctx.setOperator().unionOperator());
        }

        if (ctx.setOperator().otherSetOperator().EXCEPT() != null) {
            return getExceptConjunctor(ctx.setOperator().otherSetOperator());
        }

        if (ctx.setOperator().otherSetOperator().INTERSECT() != null) {
            return getIntersectConjunctor(ctx.setOperator().otherSetOperator());
        }

        throw new SemanticErrorException("No such conjunction method: " + ctx.getText());
    }

    private QueryConjunctor getUnionConjunctor(UnionOperatorContext union) {
        if (union.MAX() != null) return QueryConjunctor.UNION_MAX;
        if (union.setQuantifier().ALL() != null) return QueryConjunctor.UNION_ALL;
        return QueryConjunctor.UNION_DISTINCT;
    }

    private QueryConjunctor getExceptConjunctor(OtherSetOperatorContext except) {
        if (except.setQuantifier() != null && except.setQuantifier().ALL() != null) return QueryConjunctor.EXCEPT_ALL;
        return QueryConjunctor.EXCEPT_DISTINCT;
    }

    private QueryConjunctor getIntersectConjunctor(OtherSetOperatorContext intersect) {
        if (intersect.setQuantifier() != null && intersect.setQuantifier().ALL() != null) return QueryConjunctor.INTERSECT_ALL;
        return QueryConjunctor.INTERSECT_DISTINCT;
    }






    
}
