package graphs;

public class ResourcePaths {
    static final String testGraphFolder = "/src/test/resources/database/";
    static final String mainGraphFolder = "/src/main/resources/database/";

    public static String getResourceFolder()
    {
        return System.getProperty("user.dir");
    }

    public static String getGraphFolder()
    {
        return getResourceFolder() + mainGraphFolder;
    }

    public static String getGraphString(boolean isTest)
    {
        return getResourceFolder() + (isTest ? testGraphFolder : mainGraphFolder );
    }
}
