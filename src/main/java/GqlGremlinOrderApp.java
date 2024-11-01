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
import cli.Arg;
import cli.CliArgParser;
import gql_gremlin.Compiler;
import graphs.ResourcePaths;



public class GqlGremlinOrderApp {
    public static final String defaultQuery = "gql/noop.gql";
    public static final String defaultCompiler = "rigid";
    public static final String defaultSummaryName = "lsqb";

    public static final CliArgParser argParser = new CliArgParser(Map.of(
        "query", Arg.single(defaultQuery),
        "compiler", Arg.single(defaultCompiler),
        "summary", Arg.single(defaultSummaryName)
    ));

    public static void main(String[] args) throws Exception {
        argParser.parseArgs(args);
        String compilerType = argParser.getArgSingle("compiler");
        String summaryName = argParser.getArgSingle("summary");
        String queryArg = argParser.getArgSingle("query");
        String queryPath = ResourcePaths.getQueryFolder() + queryArg;
        assert Paths.get(queryPath).toAbsolutePath().toFile().exists() : "File doesn't exist";

        GqlProgram program = GqlProgram.buildProgram(queryPath);
        Compiler compiler = Compiler.getCompiler(compilerType, summaryName);
        
        compiler.optimiseProgram(program);
        program.describeOrdering(System.out);
    }

}
