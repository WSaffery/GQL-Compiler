package graphs;

public class ResourcePaths {
    static final String graphFolder = "/src/main/resources/database/";
    static final String queryFolder = "/src/main/resources/queries/";
    static final String summaryFolder = "/src/main/resources/summaries/";


    public static String getResourceFolder()
    {
        return System.getProperty("user.dir");
    }

    public static String getSummaryFolder()
    {
        return getResourceFolder() + summaryFolder;
    }
    
    public static String getGraphFolder()
    {
        return getResourceFolder() + graphFolder;
    }

    public static String getQueryFolder()
    {
        return getResourceFolder() + queryFolder;
    }
}
