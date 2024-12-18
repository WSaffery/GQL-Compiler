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

import exceptions.InvalidNodeFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.stream.Stream;

public class NodeParser {
    ObjectMapper objectMapper;
    File fileToParse;

    public NodeParser(String filePath) throws FileNotFoundException {
        fileToParse = new File(filePath);

        if (!fileToParse.exists()) {
            throw new FileNotFoundException("There is no file with filepath " + filePath);
        }

        objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.WRAP_ROOT_VALUE);
    }

    public ArrayList<JsonNode> getNodes() throws InvalidNodeFormatException {
        ArrayList<JsonNode> nodes = null;
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        CollectionType type = typeFactory.constructCollectionType(ArrayList.class, JsonNode.class);

        try {
            nodes = objectMapper.readValue(fileToParse, type);
            validateNodes(nodes);
        } catch (JsonMappingException exception) {
            System.err.println("node.json is not formatted correctly at " + fileToParse.getAbsolutePath() + ".");
            System.err.println(exception.getMessage());
            throw new InvalidNodeFormatException(exception.getMessage());
        } catch (JsonEOFException exception) {
            System.err.println("node.json is not formatted correctly at " + fileToParse.getAbsolutePath() + ".");
            System.err.println(exception.getMessage());
            throw new InvalidNodeFormatException(exception.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return nodes;
    }

    public Stream<JsonNode> getNodeStream() throws InvalidNodeFormatException, IOException
    {
        return Files.lines(fileToParse.toPath()).map((s) -> {
            try {
                return objectMapper.readValue(s, JsonNode.class);
            }
            catch (Exception e) // todo! make this stricter
            {
                System.err.println(e.getMessage());
                return (JsonNode) null;
            }
        });
    }

    public void validateNodes(ArrayList<JsonNode> nodes)
    {
        return;
    }
}
