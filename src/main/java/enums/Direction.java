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

package enums;

import javax.management.RuntimeErrorException;

// TODO! Add other orientations
// Orientation               | Symbol
// Left                      | <-
// Undirected                |  ~
// Right                     |  ->
// Left or Undirected        | <~
// Undirected or Right       |  ~>
// Left, or right            | <->
// Left, undirected or right |  -

public enum Direction {
    LEFT_TO_RIGHT {
        @Override
        public String toString() {
            return "->";
        }
    },
    RIGHT_TO_LEFT {
        @Override
        public String toString() {
            return "<-";
        }
    },
    UNDIRECTED {
        @Override
        public String toString() {
            return "~";
        }
    };

    public String toLatex() {
        switch (this) {
            case LEFT_TO_RIGHT:
                return "\\rightarrow";
            case RIGHT_TO_LEFT:
                return "\\leftarrow";
            case UNDIRECTED:
                return "-";
            default:
                return "";
        }
    }

    public Direction reversed()
    {
        switch (this)
        {
            case LEFT_TO_RIGHT:
                return Direction.RIGHT_TO_LEFT;
            case RIGHT_TO_LEFT:
                return Direction.LEFT_TO_RIGHT;
            case UNDIRECTED:
                return Direction.UNDIRECTED;
            default:
                throw new RuntimeException("bad direction: " + this);
        }
    }
}
