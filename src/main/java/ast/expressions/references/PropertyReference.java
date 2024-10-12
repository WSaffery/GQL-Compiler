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

package ast.expressions.references;

import java.util.List;

import ast.expressions.ReferenceExpression;

// key = "*" implies return all as map
public class PropertyReference extends ReferenceExpression {
    public String name;
    public String key;

    public PropertyReference(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public String name()
    {
        return name;
    }

    public String key()
    {
        return key;
    }

    public List<String> referencedVariables()
    {
        return List.of(name);
    }

    @Override
    public String toString() {
        return name.toString() + "." + key.toString();
    }
}
