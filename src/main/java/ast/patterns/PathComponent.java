package ast.patterns;

import java.util.Optional;

public abstract sealed interface PathComponent permits ElementPattern, ParenPathPattern {
}
