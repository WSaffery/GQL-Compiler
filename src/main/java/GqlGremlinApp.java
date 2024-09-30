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

import static gql_gremlin.helpers.GremlinHelpers.appendTraversal;

import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversalSource;
import org.apache.tinkerpop.gremlin.process.traversal.util.TraversalExplanation;
import org.apache.tinkerpop.gremlin.structure.Edge;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import antlr.GqlLexer;
import antlr.GqlParser;
import ast.GqlProgram;
import ast.visitors.AstListener;
import gql_gremlin.GremlinCompiler;
import graphs.GremlinGraph;
import graphs.GremlinGraphFactory;
import graphs.ResourcePaths;

public class GqlGremlinApp {
    static final String testQueryFolder = "/src/test/resources/queries/";

    public static void main(String[] args) throws Exception {
        assert(args.length >= 1);        
        System.out.println(args[0]);
        // System.out.println(Files.size(Paths.get(System.getProperty("user.dir") + testQueryFolder + args[0])));
        // System.out.println(testQueryFolder.substring(0, testQueryFolder.length()-1));
        // System.out.println(
        //     Files.list(
        //         Paths.get(
        //             System.getProperty("user.dir") + testQueryFolder + "gql"
        //             )
        //         )
        //     .map(path -> path.toString()).toList()
        // );

        assert Paths.get(System.getProperty("user.dir") + testQueryFolder + args[0]).toAbsolutePath().toFile().exists() : "File doesn't exist";

        GqlLexer lexer = makeGqlFileLexer(testQueryFolder + args[0]);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GqlParser parser = new GqlParser(tokens);
        
        ParseTree antlrAST = parser.query();
        AstListener listener = new AstListener();
        ParseTreeWalker.DEFAULT.walk(listener, antlrAST);
        GqlProgram program = listener.GetResult();

        printProgram(program);

        GremlinCompiler compiler = new GremlinCompiler();

        GraphTraversal<Vertex, Map<String,Object>> traversal = compiler.compileToTraversal(program);

        TraversalExplanation expl = traversal.explain();
        
        System.out.println("Final Traversal:");
        System.out.println(expl.prettyPrint());

        if (args.length >= 2 && args[1].equals("-p"))
        {
            return;
        }

        assert(program.graphName.isPresent());
        
        System.out.println("Graph name: " + program.graphName.get());

        GremlinGraph graph = new GremlinGraphFactory(
            ResourcePaths.getGraphFolder()).
                makeGremlinGraph(
                    program.graphName.get()
                    );
        GraphTraversalSource g = graph.currentGraph;

        // printGraph(g);

        // takes anonymous traversal and applies it to our target graph
        traversal = appendTraversal(g, traversal.asAdmin().getBytecode());

        List<Map<String,Object>> res = traversal.toList();

        printTable(res);

        
    }

    public static void printProgram(GqlProgram program)
    {
        program.body.getQuery(0).print();
        for (int i = 1; i < program.body.getQueryCount(); i++)
        {
            System.out.println(program.body.getConjunctor(i-1));
            program.body.getQuery(i).print();
        }
    }

    public static void printGraph(GraphTraversalSource g)
    {
        List<Vertex> vertices = g.V().toList();
        System.out.println("VERTICES: ");
        System.out.println(vertices);

        List<Map<Object,Object>> vertexProperties = g.V().valueMap().toList();
        System.out.println("VERTEX PROPERTIES: ");
        System.out.println(vertexProperties);

        List<Edge> edges = g.E().toList();
        System.out.println("EDGES: ");
        System.out.println(edges);

        List<Map<Object,Object>> edgeProperties = g.E().valueMap().toList();
        System.out.println("EDGES PROPERTIES: ");
        System.out.println(edgeProperties);

        // System.out.println("GRAPH: ");
        // System.out.println(res);

    }

    public static void printTable(List<Map<String,Object>> maps)
    {   
        Map<String, Integer> sizes = new HashMap<>();
        for (Map<String, Object> map : maps) 
        {
            for (Map.Entry<String, Object> e : map.entrySet())
            {
                sizes.putIfAbsent(e.getKey(), 0);
                Integer len = e.getValue().toString().length();
                sizes.compute(e.getKey(), (s, v) -> Integer.max(v, len));
            }
        }

        for (Map.Entry<String, Integer> e : sizes.entrySet())
        {
            // System.out.println(e);
            // String width = Integer.toString(e.getValue());
            String format = " %%-%ds|".formatted(e.getValue());
            // get "%-[width]s"

            System.out.printf(format, e.getKey());
        }
        System.out.println();

        for (Map<String, Object> map : maps)
        {
            for (String key : sizes.keySet())
            {
                Integer size = sizes.get(key);
                String format = " %%-%ds|".formatted(size);
                if (map.containsKey(key))
                {
                    System.out.printf(format, map.get(key));
                }
                else 
                {
                    System.out.printf(format, "NO MATCH");
                }
            }
            System.out.println();
        }

    }

    public static GqlLexer makeGqlFileLexer(String filePath)
    {
        CharStream input = null;

        try {
            input = CharStreams.fromFileName(filePath);
        } catch (NoSuchFileException exception) {
            try {
                input = CharStreams.fromFileName(System.getProperty("user.dir") + filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new GqlLexer(input);
    }

}
