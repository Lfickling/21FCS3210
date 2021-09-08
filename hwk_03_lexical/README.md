# Homework 03

## A Lexical Analyzer

## Goal

To implement a lexical analyzer from scratch in Scala. 

## Instructions

Consider the EBNF syntax definition of a class declaration in Java. For simplification, the body of the class definition was left empty.  

```
class_decl = [ class_mod ] ´class´ identifier [ extends ] [ implements ] class_body
class_mod  = ´public´ | ´abstract´ | ´final´
identifier = ( letter | ´_´ | ´$´ ) { ( letter | ´_´ | ´$´ | digit ) }  
extends    = ´extends´ type
implements = ´implements´ type_list
class_body = ´{´ ´}´
letter     = ´a´ | ... | ´z´ | ´A´ | ... | ´Z´
digit      = ´0´ | ... | ´9´
type       = identifier
type_list  = type { ´,´ type }
```

From the gramar specification we can infer the lexical unit types (tokens) of the grammar.

1. class keyword
2. identifier keyword
3. public keyword
4. abstract keyword
5. final keyword
6. extends keyword
7. implements keyword
8. block opening
9. block closing
10. comma

Your goal is to write a lexical analyzer for the language described by the grammar.  Get the code template (in Scala) under src.  The output of your lexical analyzer should be a list of pairs containing a lexical unit followed by its token number, in the order of their appearance.  

You just need to submit LexicalAnalyzer.scala this time!

Below are some source codes (with expected outputs) for you to try.  

source1.java 
```
public class Player {

} 
```

output
```
(label:public, token:PUBLIC)
(label:class, token:CLASS)
(label:Player, token:IDENTIFIER)
(label:{, token:BLOCK_OPEN)
(label:}, token:BLOCK_CLOSE) 
```

source2.java
```
final class SpiderMan extends Player {

}
```
 
output
```
(label:final, token:FINAL)
(label:class, token:CLASS)
(label:SpiderMan, token:IDENTIFIER)
(label:extends, token:EXTENDS)
(label:Player, token:IDENTIFIER)
(label:{, token:BLOCK_OPEN)
(label:}, token:BLOCK_CLOSE)
```

source3.java
```
abstract class SpiderMan extends Player implements Ability2Jump {

}
```

output
```
(label:abstract, token:ABSTRACT)
(label:class, token:CLASS)
(label:SpiderMan, token:IDENTIFIER)
(label:extends, token:EXTENDS)
(label:Player, token:IDENTIFIER)
(label:implements, token:IMPLEMENTS)
(label:Ability2Jump, token:IDENTIFIER)
(label:{, token:BLOCK_OPEN)
(label:}, token:BLOCK_CLOSE)
```

source4.java
```
abstract class SpiderMan extends Player implements Ability2Jump, Iterable {

}
```

output
```
(label:abstract, token:ABSTRACT)
(label:class, token:CLASS)
(label:SpiderMan, token:IDENTIFIER)
(label:extends, token:EXTENDS)
(label:Player, token:IDENTIFIER)
(label:implements, token:IMPLEMENTS)
(label:Ability2Jump, token:IDENTIFIER)
(label:,, token:COMMA)
(label:Iterable, token:IDENTIFIER)
(label:{, token:BLOCK_OPEN)
(label:}, token:BLOCK_CLOSE)
```

source5.java
```
_Money$Money class } extends Wall Street {
```

output
```
(label:_Money$Money, token:IDENTIFIER)
(label:class, token:CLASS)
(label:}, token:BLOCK_CLOSE)
(label:extends, token:EXTENDS)
(label:Wall, token:IDENTIFIER)
(label:Street, token:IDENTIFIER)
(label:{, token:BLOCK_OPEN)
```

source6.java
```
_Money$Money class } > Wall Street {
```

output
```
(label:_Money$Money, token:IDENTIFIER)
(label:class, token:CLASS)
(label:}, token:BLOCK_CLOSE)
Exception in thread "main" java.lang.Exception: Lexical Analyzer Error: unrecognizable symbol found!
```
