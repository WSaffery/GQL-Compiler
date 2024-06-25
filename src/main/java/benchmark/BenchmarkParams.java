package benchmark;

import java.nio.file.Path;

// A benchmark's parameters.
// A benchmark loads a given graph into all databases being tested
// it then runs and times all queries being tested on each database.
public class BenchmarkParams {
    public Path querySet;
    public String graphName;
}
