# Front

find
```
as\('([a-zA-Z]*)'\)\.
```
replace
```
as('$1').hasLabel('$1').
```

Then manually fix case

# Back

find
```
\.as\('([a-zA-Z]*)'\)
```
replace
```
.hasLabel('$1').as('$1')
```

Then manually fix case
