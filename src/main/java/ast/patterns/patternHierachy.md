
# Node-Link-Node Formulation
() [() ... ()] ()

```
LinkPattern = EdgePattern | ParenPathPattern
pathPattern = NodePattern |  NodePattern (LinkPattern NodePattern)*
pathPart = LinkPattern | Node Pattern
Path = pathPart*
```

# Node-Link-Path Formulation
() -> [() ... ()]
() -> [() ... ()] -> [() ... ()] -> ...
() -> [() ... ()] -> () -> ... -> () | []
```
path = point (edge point)*
point = node | parenPath
```

// simpler
// there isn't really any reason to support `() ()` semantics
// a lot easier to write
// downside -> harder to show equivalence between this notation and simple edge quantification

(A) ->{1,k} (B)
(A) [()->()]{1,k} (B)
===
[(A) -> ()]{1,k-1} -> (B)


# Node-Link*-Path

() -> -> ... -> ()
() -> [() ... ()] -> ()

```
path = point edge* path
point = node | parenPath
parenPath = LEFT path RIGHT
```

// support standalone edges but not standalone nodes