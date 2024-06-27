// package gql_gremlin;

// import static gql_gremlin.helpers.GremlinHelpers.toGremlinDirection;
// import static org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.__.start;

// import java.util.List;

// import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;

// import ast.patterns.EdgePattern;
// import ast.patterns.ElementPattern;
// import ast.patterns.NodePattern;


// // old match based code
// // ought to bring back as hybrid component once rigid compiler is stable
// public class MatchCompiler {
//     // returns a traversal that establishes an instance of the element pattern
//     // at the start or end of a match pattern
//     // public GraphTraversal<?, ?> asInstance(OrderedElementPattern pattern)
//     // {
//     //     if (pattern.variableName().isPresent())
//     //     {
//     //         if (pattern.preceded()) 
//     //         {
//     //             return where(P.eq(pattern.variableName().get()));
//     //         }
//     //         else 
//     //         {
//     //             return as(pattern.variableName().get());
//     //         }
//     //     }
//     //     return identity();
//     // }

//     // Path-Based Helpers
//     // In a path when matching an ordered element pattern
//     // traversal = filterByPrior(traversal, orderedPattern); // filters by previous capture if pattern is preceeded
//     // traversal = filterByPattern(traversal, pattern);
//     // traversal = captureInstance(traversal, orderedPattern); // establishes initial capture if not preceeded



//     // if the pattern's variable has been preceed, ensure we match the preceeded captured elements
//     // otherwise, do nothing
//     // public GraphTraversal<?, ?> filterByPrior(GraphTraversal<?, ?> traversal, OrderedElementPattern pattern)
//     // {
//     //     if (pattern.variableName().isPresent() && pattern.preceded())
//     //     {
//     //         return traversal.where(P.eq(pattern.variableName().get()));
//     //     }
//     //     return traversal;
//     // }

//     // // if the pattern's variable hasn't been preceeded, capture the current elements as that variable
//     // // otherwise, do nothing
//     // public GraphTraversal<?, ?> captureInstance(GraphTraversal<?, ?> traversal, OrderedElementPattern pattern)
//     // {
//     //     if (pattern.variableName().isPresent() && !pattern.preceded())
//     //     {
//     //         return traversal.as(pattern.variableName().get());
//     //     }
//     //     return traversal;
//     // }

//     // Match-Based Helpers
//     // in a match when matching an element pattern

//     // Note match is truly stateless and doesn't need to use Ordered patterns, kept as is for now
//     public GraphTraversal<?, ?> asInstance(ElementPattern pattern)
//     {
//         if (pattern.variableName().isPresent())
//         {
//             return start().as(pattern.variableName().get());
//         }
//         return start();
//     }

//     public <S, T> GraphTraversal<S, T> asInstance(GraphTraversal<S, T> traversal, ElementPattern pattern)
//     {
//         if (pattern.variableName().isPresent())
//         {
//             return traversal.as(pattern.variableName().get());
//         }
//         return traversal.identity();
//     }

//     // in a match statement we always use as to capture and instance rather than where if it's preceeded
//     // this is because match will match against variables previously created using the traverser's path history
//     // public GraphTraversal<?, ?> captureMatchInstance(GraphTraversal<?, ?> traversal, OrderedElementPattern pattern)
//     // {
//     //     if (pattern.variableName().isPresent())
//     //     {
//     //         return traversal.as(pattern.variableName().get());
//     //     }
//     //     return traversal;
//     // }

//     // we need to use where if we're not splitting on anything with more than one reference outside of unrestricted
    
//     // every
//     // public GraphTraversal<?, ?> compileToTraversal(MatchPattern matchPattern)
//     // {
//     //     GraphTraversal<?, ?> traversal = null;

//     //     OrderedElementPattern head = matchPattern.headPattern();
//     //     OrderedElementPattern tail = matchPattern.tailPattern();

//     //     traversal = asInstance(head);

//     //     traversal = filterByPattern(traversal, head.pattern());
//     //     if (head.pattern() instanceof EdgePattern)
//     //     {
//     //         EdgePattern headEdgePattern = (EdgePattern) head.pattern();
//     //         traversal = traversal.toV(toGremlinDirection(headEdgePattern.direction).opposite());
//     //     }
//     //     else
//     //     {
//     //         assert(head.pattern() instanceof NodePattern);
//     //     }

//     //     for (OrderedElementPattern orderedPattern : matchPattern.middlePatterns())
//     //     {
//     //         // assert(pattern.variableName == null);
//     //         final ElementPattern pattern = orderedPattern.pattern();
//     //         if (pattern instanceof EdgePattern) 
//     //         {
//     //             EdgePattern edgePattern = (EdgePattern) pattern;
//     //             // can use labels parameter in toE, but hasLabels is added in filterByPattern anyway
//     //             traversal = traversal.toE(toGremlinDirection(edgePattern.direction));
//     //             traversal = filterByPattern(traversal, edgePattern);
//     //             traversal = captureMatchInstance(traversal, orderedPattern);
//     //             traversal = traversal.toV(toGremlinDirection(edgePattern.direction).opposite());
//     //         }
//     //         else if (pattern instanceof NodePattern)
//     //         {
//     //             NodePattern nodePattern = (NodePattern) pattern;
//     //             traversal = filterByPattern(traversal, nodePattern);
//     //             traversal = captureMatchInstance(traversal, orderedPattern);
//     //         }
//     //         else 
//     //         {
//     //             System.out.println("Pattern of type " + pattern.getClass().getName() + " is not supported");
//     //         }
//     //     }

        
//     //     if (tail.pattern() instanceof EdgePattern)
//     //     {
//     //         EdgePattern tailEdgePattern = (EdgePattern) tail.pattern();
//     //         traversal = traversal.toE(toGremlinDirection(tailEdgePattern.direction));
//     //     }
//     //     else
//     //     {
//     //         assert(tail.pattern() instanceof NodePattern);
//     //     }

//     //     traversal = filterByPattern(traversal, tail.pattern());
//     //     traversal = captureMatchInstance(traversal, tail); 
//     //     // will be joined against other patterns with the same variable in the match statement

//     //     System.out.println("(0) match traversal: " + traversal.explain().prettyPrint());
//     //     return traversal;
//     // }

//     // public GraphTraversal<?, ?> compileToSimpleTraversal(GraphTraversal<?, ?> start, List<OrderedElementPattern> matchPattern)
//     // {
        
//     //     return start;
//     // }


//     // public GraphTraversal<?, ?> compileToMatchTraversal(List<ElementPattern> matchPattern)
//     // {
//     //     ElementPattern head = matchPattern.get(0);
//     //     ElementPattern tail = matchPattern.get(matchPattern.size()-1);

//     //     // Although we can determine the eventual type parameters <S,E> of our traversal 
//     //     // from head and tail, we can only do this at runtime. Furthermore graph traversal's
//     //     // type parameter mutates over the course of building the traversal and starts 
//     //     // inevitably as <S,S> rather than <S,E>.
//     //     // As such we must take parameters ?,? to make the variable valid throughout the process
//     //     // of building the final traversal. Even if we could somehow set the parameters at runtime.
        
//     //     GraphTraversal<?, ?> traversal = asInstance(head);
//     //     traversal = filterByPattern(traversal, head);

//     //     if (head instanceof EdgePattern)
//     //     {
//     //         EdgePattern headEdgePattern = (EdgePattern) head;

//     //         traversal = traversal.toV(toGremlinDirection(headEdgePattern.direction).opposite());
//     //     }

//     //     for (int i = 1; i < matchPattern.size()-1; i++)
//     //     {
//     //         final ElementPattern pattern = matchPattern.get(i);

//     //         if (pattern instanceof EdgePattern) 
//     //         {
//     //             EdgePattern edgePattern = (EdgePattern) pattern;
//     //             // can use labels parameter in toE, but hasLabels is added in filterByPattern anyway
//     //             traversal = traversal.toE(toGremlinDirection(edgePattern.direction));
//     //             traversal = filterByPattern(traversal, edgePattern);
//     //             traversal = asInstance(traversal, edgePattern);
//     //             traversal = traversal.toV(toGremlinDirection(edgePattern.direction).opposite());
//     //         }
//     //         else if (pattern instanceof NodePattern)
//     //         {
//     //             traversal = filterByPattern(traversal, pattern);
//     //             traversal = asInstance(traversal, pattern);
//     //         }
//     //         else 
//     //         {
//     //             System.out.println("Pattern of type " + pattern.getClass().getName() + " is not supported");
//     //         }
//     //     }
        
//     //     if (tail instanceof EdgePattern)
//     //     {
//     //         EdgePattern tailEdgePattern = (EdgePattern) tail;
//     //         traversal = traversal.toE(toGremlinDirection(tailEdgePattern.direction));
//     //     }

//     //     traversal = filterByPattern(traversal, tail);
//     //     traversal = asInstance(traversal, tail);


//     //     System.out.println("(1) match traversal: " + traversal.explain().prettyPrint());
//     //     return traversal;
//     // }


// }
