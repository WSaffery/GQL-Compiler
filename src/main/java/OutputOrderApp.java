import java.util.Map;

import cli.Arg;
import cli.CliArgParser;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OutputOrderApp {
    public static final String defaultSeed = "123";
    public static final String defaultRepetitions = "10";
    public static final List<String> defaultFilter = List.of();

    public static final CliArgParser argParser = new CliArgParser(Map.of(
        "seed", Arg.single(defaultSeed),
        "repetitions", Arg.single(defaultRepetitions),
        "filter", Arg.multi(defaultFilter)
    ));

    public static void main(String[] args) throws Exception {
        argParser.parseArgs(args);

        int seed = Integer.parseInt(argParser.getArgSingle("seed"));
        int repetitions = Integer.parseInt(argParser.getArgSingle("repetitions"));
        List<Integer> filter = (argParser.getArgs("filter").stream().map(s -> Integer.parseInt(s))).toList();


        ArrayList<Integer> queryIds = new ArrayList<>();
        Random rnd = new Random(seed);
        for (int i = 0; i < repetitions; i++)
        {
            ArrayList<Integer> repIds = new ArrayList<>(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9));
            Collections.shuffle(repIds, rnd);
            queryIds.addAll(repIds);
        }

        // remove each instance of the filtered queries
        for (Integer f : filter)
        {
            for (int i = 0; i < repetitions; i++)
            {
                queryIds.remove(f);
            }
        }


        System.out.println(queryIds);
    }

}
