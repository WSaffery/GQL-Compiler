package data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import graphs.ResourcePaths;

public class SummaryStorage {

    public static String getSummaryPath(String summaryName)
    {
        return ResourcePaths.getSummaryFolder() + summaryName;
    }
    
    public static Summary getSummary(String summaryName) 
        throws IOException, ClassNotFoundException
    {
        String summaryPath = getSummaryPath(summaryName);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(summaryPath));
        Object stored = in.readObject();
        assert in.available() == 0: "Too many objects in summary file";            
        in.close();

        assert stored instanceof Summary : "Non-summary object stored in summary file";

        return (Summary) stored;
    }

    public static void storeSummary(String summaryName, Summary summary) 
        throws FileNotFoundException, IOException
    {
        String summaryPath = getSummaryPath(summaryName);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(summaryPath));
        out.writeObject(summary);
        out.close();
    }
}
