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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.tinkerpop.gremlin.process.traversal.Script;
import org.apache.tinkerpop.gremlin.process.traversal.dsl.graph.GraphTraversal;
import org.apache.tinkerpop.gremlin.structure.Vertex;

import antlr.GqlLexer;
import antlr.GqlParser;
import ast.GqlProgram;
import ast.visitors.AstListener;
import gql_gremlin.GremlinCompiler;

import org.apache.tinkerpop.gremlin.process.traversal.translator.GroovyTranslator;


public class GqlGremlinScriptApp {
    static final String testQueryFolder = "/src/test/resources/queries/";

    public static void main(String[] args) throws Exception {
        assert(args.length >= 1);

        assert Paths.get(System.getProperty("user.dir") + testQueryFolder + args[0]).toFile().exists() : "File doesn't exist";

        GqlLexer lexer = makeGqlFileLexer(testQueryFolder + args[0]);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GqlParser parser = new GqlParser(tokens);
        
        ParseTree antlrAST = parser.query();
        AstListener listener = new AstListener();
        ParseTreeWalker.DEFAULT.walk(listener, antlrAST);
        GqlProgram program = listener.GetResult();
        
        if (program.graphName.isPresent())
        {
            System.err.println("[WARN] Graph loading not performed by gremlin script.");
        }

        GremlinCompiler compiler = new GremlinCompiler();

        GraphTraversal<Vertex, Map<String,Object>> traversal = compiler.compileToTraversal(program);
        
        GroovyTranslator translator = GroovyTranslator.of("g");
        Script script = translator.translate(traversal);
        
        System.out.println(script.getScript());
        Files.writeString(Paths.get(System.getProperty("user.dir") + testQueryFolder + args[0] + ".gremlin"), script.getScript());
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
