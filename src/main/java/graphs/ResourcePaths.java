package graphs;

public class ResourcePaths {
    static final String testGraphFolder = "/src/test/resources/database/";
    static final String mainGraphFolder = "/src/main/resources/database/";
    static final String testQueryFolder = "/src/test/resources/queries/";
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
        return getResourceFolder() + mainGraphFolder;
    }

    public static String getGraphString(boolean isTest)
    {
        return getResourceFolder() + (isTest ? testGraphFolder : mainGraphFolder );
    }

    public static String getQueryFolder()
    {
        return getResourceFolder() + testQueryFolder;
    }
}
