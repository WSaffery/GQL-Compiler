package ast.returns;

import java.util.ArrayList;

import enums.SetQuantifier;

public record ReturnStatement(SetQuantifier setQuantifier, ArrayList<ReturnItem> returnItems)
{}