package cli;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

// for some reason can't get remote-graph.properties files working
// have to instantiate connection directly using remote-objects.yaml files

public class CliArgParser {
    Map<String, Arg> argSettings;
    
    Map<String, List<String>> argMap;
    Set<String> setFlags;
    List<String> unlabelledArgs;

    public CliArgParser(Map<String, Arg> argSettings)
    {
        this.argSettings = argSettings;
        this.argMap = new HashMap<>();
        this.unlabelledArgs = new ArrayList<>();
        this.setFlags = new HashSet<>();
    }

    public void parseArgs(String[] args)
        throws BadArgException
    {   
        List<String> currentList = null;
        String currentKey = null;
        ArgType currentType = null;

        for (String arg : args)
        {
            if (arg.charAt(0) == '-')
            {
                String key = arg.charAt(1) == '-' ? arg.substring(2) : arg.substring(1);
                if (!argSettings.containsKey(key))
                {
                    throw new BadArgException("Unexpected argument " + key);
                }

                currentKey = key;
                currentType = argSettings.get(key).type();
                if (currentType == ArgType.FLAG)
                {
                    setFlags.add(key);
                }
                else 
                {
                    currentList = new ArrayList<>();
                    argMap.put(key, currentList);
                }
                
            }
            else if (currentList == null)
            {
                if (unlabelledArgs == null)
                {
                    throw new BadArgException("Unexpected unflagged argument.");
                }
                else 
                {
                    unlabelledArgs.add(arg);
                }
            }
            else if (currentType == ArgType.FLAG)
            {
                throw new BadArgException("Too many arguments for flag '" + currentKey + "' .");
            }
            else if (currentList.size() > 0 && currentType == ArgType.SINGLE)
            {
                throw new BadArgException("Too many arguments for flag '" + currentKey + "' .");
            }
            else 
            {
                currentList.add(arg);
            }
        }
    }

    public String getArgSingle(String argKey)
    {
        assert(argSettings.get(argKey).type().equals(ArgType.SINGLE));
        argMap.computeIfAbsent(argKey, key -> argSettings.get(key).defaultValue());
        return argMap.get(argKey).get(0);
    }

    public List<String> getArgs(String argKey)
    {
        assert(argSettings.get(argKey).type().equals(ArgType.MULTI));
        argMap.computeIfAbsent(argKey, key -> argSettings.get(key).defaultValue());
        return argMap.get(argKey);
    }

    public boolean checkFlagged(String flag)
    {
        assert(argSettings.get(flag).type().equals(ArgType.FLAG));
        return setFlags.contains(flag);
    }

    public List<String> getUnlabelledArgs()
    {
        return unlabelledArgs;
    }

}
