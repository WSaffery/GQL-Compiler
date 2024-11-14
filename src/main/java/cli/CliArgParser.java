package cli;
import java.util.ArrayList;
import java.util.Comparator;
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

        if (args.length == 0 || args[0].equals("-help") || args[0].equals("--help"))
        {
            printHelp();
            System.exit(1);
        }

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

    List<Map.Entry<String, Arg>> argSettingsList()
    {
        List<Map.Entry<String, Arg>> singleArgs = new ArrayList<>();
        List<Map.Entry<String, Arg>> multiArgs = new ArrayList<>();
        List<Map.Entry<String, Arg>> flagArgs = new ArrayList<>();
        for (Map.Entry<String, Arg> arg : argSettings.entrySet())
        {
            switch (arg.getValue().type()) {
                case SINGLE:
                    singleArgs.add(arg);
                    break;
                case MULTI:
                    multiArgs.add(arg);
                    break;
                case FLAG:
                    flagArgs.add(arg);
                    break;
                default:
                    throw new RuntimeException("Invalid Argument Type in ArgParser");
            }
        }
        Comparator<Map.Entry<String, Arg>> argSettingComparator = (a, b) -> a.getKey().compareTo(b.getKey());
        singleArgs.sort(argSettingComparator);
        multiArgs.sort(argSettingComparator);
        flagArgs.sort(argSettingComparator);

        List<Map.Entry<String, Arg>> args = new ArrayList<>();
        args.addAll(flagArgs);
        args.addAll(multiArgs);
        args.addAll(singleArgs);
        return args;
    }

    public void printHelp()
    {
        List<Map.Entry<String, Arg>> argSettingsList = argSettingsList();
        System.out.println("Program arguments and their form are given below");
        List<String> formatStrings = argSettingsList.stream().map(e -> formatString(e.getKey(), e.getValue())).toList();
        System.out.println(String.join(" ", formatStrings));
        System.out.println("Program arguments and their defaults are given below");
        List<String> defaultStrings = argSettingsList.stream().map(e -> defaultString(e.getKey(), e.getValue())).filter(s -> !s.isEmpty()).toList();
        System.out.println(String.join(" ", defaultStrings));
    }

    String formatString(String name, Arg data)
    {
        switch (data.type()) {
            case SINGLE:
                return String.format("-%s %s", name, name);
            case MULTI:
                return String.format("-%s %s1 %s2 ...", name, name, name);
            case FLAG:
                return String.format("[-%s]", name);
            default:
                throw new RuntimeException("Invalid Argument Type in ArgParser");
        }
    }

    String defaultString(String name, Arg data)
    {
        switch (data.type()) {
            case SINGLE:
            case MULTI:
                if (data.defaultValue().isEmpty())
                {
                    return "";
                }
                else 
                {
                    String defaults = String.join(" ", data.defaultValue().stream().map(s -> "'" + s + "'").toList());
                    return String.format("-%s %s", name, defaults);
                }
            case FLAG:
                return ""; // all flags disabled by default
            default:
                throw new RuntimeException("Invalid Argument Type in ArgParser");
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
