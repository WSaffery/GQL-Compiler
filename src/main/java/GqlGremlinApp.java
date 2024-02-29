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
import java.nio.file.NoSuchFileException;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import antlr.GqlLexer;
import antlr.GqlParser;
import gql_gremlin.GqlProgram;
import gql_gremlin.visitors.AstListener;
// import gql_gremlin.visitors.AstVisitor;
// import gql_gremlin.visitors.GremlinVisitor;

public class GqlGremlinApp {
    static String testQueryFolder = "/src/test/resources/queries/";

    public static void main(String[] args) throws Exception {
        assert(args.length > 1);
        
        System.out.println(args[0]);
        GqlLexer lexer = MakeGqlFileLexer(testQueryFolder + args[0]);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        GqlParser parser = new GqlParser(tokens);
        
        ParseTree antlrAST = parser.query();

        AstListener listener = new AstListener();

        // visitor.visit(antlrAST);
        // listener.enter(antlrAst);
        ParseTreeWalker.DEFAULT.walk(listener, antlrAST);

        GqlProgram program = listener.GetResult();

        
        program.queries.get(0).print();
        for (int i = 1; i < program.queries.size(); i++)
        {
            System.out.println(program.conjunctions.get(i-1));
            program.queries.get(i).print();
        }
    }

    public static GqlLexer MakeGqlFileLexer(String filePath)
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
