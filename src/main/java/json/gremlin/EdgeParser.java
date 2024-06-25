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

package json.gremlin;

import com.fasterxml.jackson.core.io.JsonEOFException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import exceptions.InvalidEdgeFormatException;
import exceptions.InvalidNodeFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Stream;

public class EdgeParser {
    ObjectMapper objectMapper;
    File fileToParse;

    public EdgeParser(String filePath) throws FileNotFoundException {
        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
        fileToParse = new File(filePath);

        if (!fileToParse.exists()) {
            throw new FileNotFoundException("There is no file with filepath " + filePath);
        }
    }

    public ArrayList<JsonEdge> getEdges() throws InvalidEdgeFormatException {
        ArrayList<JsonEdge> edges = null;

        TypeFactory typeFactory = objectMapper.getTypeFactory();
        CollectionType type = typeFactory.constructCollectionType(ArrayList.class, JsonEdge.class);


        try {
            edges = objectMapper.readValue(fileToParse, type);
        } catch (JsonMappingException exception) {
            System.err.println("edge.json is not formatted correctly at " + fileToParse.getAbsolutePath() + ".");
            System.err.println(exception.getMessage());
            throw new InvalidEdgeFormatException(exception.getMessage());
        } catch (JsonEOFException exception) {
            System.err.println("edge.json is not formatted correctly at " + fileToParse.getAbsolutePath() + ".");
            System.err.println(exception.getMessage());
            throw new InvalidEdgeFormatException(exception.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return edges;
    }

    public Stream<JsonEdge> getEdgeStream() throws InvalidEdgeFormatException, IOException
    {
        return Files.lines(fileToParse.toPath()).map((s) -> {
            try {
                return objectMapper.readValue(s, JsonEdge.class);
            }
            catch (Exception e) // todo! make this stricter
            {
                System.err.println(e.getMessage());
                return (JsonEdge) null;
            }
        });
    }

    public void validateEdges(ArrayList<JsonEdge> edges)
    {
        return;
    }
}
