#!/bin/bash
# wrapper around mvn:exec for ease of use

class_name="$1"
shift
remaining_args="$@"

mvn exec:java -Dexec.mainClass="$class_name" -e -Dexec.args="$remaining_args"
