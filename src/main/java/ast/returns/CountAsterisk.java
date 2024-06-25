package ast.returns;

import java.util.Optional;

public record CountAsterisk(Optional<String> name) implements ReturnItem {
    public CountAsterisk(String name)
    {
        this(Optional.of(name));
    }

    public CountAsterisk()
    {
        this(Optional.empty());
    }
}
