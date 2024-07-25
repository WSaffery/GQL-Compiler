package cli;

import java.util.List;

// type, default if valued, default if flag
public record Arg(ArgType type, List<String> defaultValue) {
    public static Arg single(String defaultValue)
    {
        return new Arg(ArgType.SINGLE, List.of(defaultValue));
    }

    public static Arg multi(List<String> defaultValue)
    {
        return new Arg(ArgType.MULTI, defaultValue);
    }

    public static Arg multi(String defaultValue)
    {
        return new Arg(ArgType.MULTI, List.of(defaultValue));
    }

    public static Arg multi()
    {
        return new Arg(ArgType.MULTI, List.of());
    }

    public static Arg flag()
    {
        return new Arg(ArgType.FLAG, null);
    }
}
