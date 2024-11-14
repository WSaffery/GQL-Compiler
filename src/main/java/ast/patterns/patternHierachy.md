# Parsing Parenthesised Paths

## Node-Link-Node Formulation
() [() ... ()] ()

```
LinkPattern = EdgePattern | ParenPathPattern
pathPattern = NodePattern |  NodePattern (LinkPattern NodePattern)*
pathPart = LinkPattern | Node Pattern
Path = pathPart*
```

## Node-Link-Path Formulation
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

(A) ->{1,k} (B)

(A) -> (B) // k = 1
(A) -> () -> (B) // k = 2
(A) ->[() -> ()]{1,k-2} -> (B) // k >= 3


## Node-Link*-Path

() -> -> ... -> ()
() -> [() ... ()] -> ()

```
path = point edge* path
point = node | parenPath
parenPath = LEFT path RIGHT
```

// support standalone edges but not standalone nodes

## Our Approach

We ended up using approximately the following grammar

```
path = point edge* path
point = node | parenPath
parenPath = node? LEFT path RIGHT node?
```

That is parenthesised paths can have one adjacent node on either side, but otherwise our formulation is equivalent to Node-Link*-Path.
I think this gives all the meaningful expressions using adjacent nodes, while also making implementation relatively easy.