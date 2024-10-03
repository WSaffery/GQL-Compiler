package benchmark;

public record Result(
    String system, String systemVariant, double scaleFactor, int queryId, 
    double duration, long count
)
{
    Result(String system, double scaleFactor, int queryId, double duration, long count)
    {
        this(system, "", scaleFactor, queryId, duration, count);
    }

    public static Result FailedResult(String system, String systemVariant, double scaleFactor, int queryId)
    {
        return new Result(system, systemVariant, scaleFactor, queryId, -1, -1);
    }

    public String toTsvString()
    {
        if (duration != -1)
        {
            return String.format("%s\t%s\t%s\t%s\t%.4f\t%s", 
                system, systemVariant, scaleFactor, queryId, duration, count);
        }
        else 
        {
            return String.format("%s\t%s\t%s\t%s\t%s\t%s", 
                system, systemVariant, scaleFactor, queryId, "FAILED", "");
        }
    }
}