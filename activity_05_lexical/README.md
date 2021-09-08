# Activity 05

## A Lexical Analyzer - Example 1

## Goal

To illustrate how to implement a lexical analyzer from scratch in Scala. 

## Instructions

Consider the grammar below specified using EBNF notation.  

```
expression = expression ( ´+´  | ´-´ ) term | term  
term       = term ( ´*´ | ´/´ ) factor | factor 
factor     = identifier | literal 
identifier = ´a´ | ´b´ | ´c´ | ´d´ | ´e´ | ´f´ | ´g´ | ´h´ | ´i´ | ´j´ | ´k´ | ´l´ | ´m´  
          | ´n´ | ´o´ | ´p´ | ´q´ | ´r´ | ´s´ | ´t´ | ´u´ | ´v´ | ´w´ | ´x´ | ´y´ | ´z´   
literal    = digit { digit } 
digit      = ´0´ | ´1´ | ´2´ | ´3´ | ´4´ | ´5´ | ´6´ | ´7´ | ´8´ | ´9´   
```

From the gramar specification we can infer the lexical unit types (tokens) of the grammar.

1. addition operator
2. subtraction operator
3. multiplication operator
4. division operator
5. identifier
6. literal

Your goal is to write a lexical analyzer for the language described by the grammar.  Get the code template (in Scala) under src.  The output of your lexical analyzer should be a list of pairs containing a lexical unit followed by its token number, in the order of their appearance.  Below are some source codes (with expected outputs) for you to try.  

source1.exp
```
52 + x * 231 / y - 8 
```

output
```
(label:52, token:LITERAL) 
(label:+, token:ADDITION) 
(label:x, token:IDENTIFIER) 
(label:*, token:MULTIPLICATION) 
(label:231, token:LITERAL) 
(label:/, token:DIVISION) 
(label:y, token:IDENTIFIER) 
(label:-, token:SUBTRACTION) 
(label:8, token:LITERAL) 
```

source2.exp 
```
z + 3 - x * 2931 
```
 
output 
```
(label:z, token:IDENTIFIER) 
(label:+, token:ADDITION) 
(label:3, token:LITERAL) 
(label:-, token:SUBTRACTION) 
(label:x, token:IDENTIFIER) 
(label:*, token:MULTIPLICATION) 
(label:2931, token:LITERAL) 
```
 
source3.exp (note the two tabs after “+”, new lines, and multiple letters “abc”) 
```
x +   4  

- 2 / abc + 2
```

output 
```
(label:x, token:IDENTIFIER) 
(label:+, token:ADDITION) 
(label:4, token:LITERAL) 
(label:-, token:SUBTRACTION) 
(label:2, token:LITERAL) 
(label:/, token:DIVISION) 
(label:a, token:IDENTIFIER) 
(label:b, token:IDENTIFIER) 
(label:c, token:IDENTIFIER) 
(label:+, token:ADDITION) 
(label:2, token:LITERAL) 
```
 
source4.exp (note that there is an unrecognizable symbol) 
```
x > 4 * c 
```
 
output 
```
Exception: Lexical Analyzer Error: unrecognizable symbol found! 
``` 