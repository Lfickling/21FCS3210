import java.io.{FileOutputStream, ObjectOutputStream}

/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 08 - Syntax Analyzer
 */

/*
syntax            = syntax-rule { ´\n´ syntax-rule }
syntax-rule       = meta-identifier ´=´ definitions-list
meta-identifier   = letter { ( letter | digit | ´-´ | ´_´ ) }
definitions-list  = single-definition { ´|´ single-definition }
single-definition = term { term }
term              = optional-sequence | repeated-sequence | grouped-sequence | meta-identifier | terminal-string
optional-sequence = ´[´ definitions-list ´]´
repeated-sequence = ´{´ definitions-list ´}´
grouped-sequence  = ´(´ definitions-list ´)´
terminal-string   = ´´´ character { character } ´´´
integer           = digit {digit}
 */

class SyntaxAnalyzer(private var source: String) {

  private var it = new LexicalAnalyzer(source).iterator
  private var lexemeUnit: LexemeUnit = null

  private def getLexemeUnit() = {
    if (lexemeUnit == null) {
      lexemeUnit = it.next()
//      println(lexemeUnit)
    }
  }

  def parse(): Tree = {
    parseSyntax()
  }

  // syntax = syntax-rule { syntax-rule }
  private def parseSyntax() = {
    val tree = new Tree("syntax")
    getLexemeUnit()
    while (lexemeUnit.getToken() != Token.EOF) {
      tree.add(parseSyntaxRule())
      if (lexemeUnit.getToken() == Token.NEW_LINE) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
        getLexemeUnit()
      }
    }
    tree
  }

  // syntax-rule = meta-identifier ´=´ definitions-list
  private def parseSyntaxRule(): Tree = {
//    println("parseSyntaxRule")
    val tree = new Tree("syntax-rule")
    getLexemeUnit()
    if (lexemeUnit.getToken() == Token.META_IDENT) {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
      if (lexemeUnit.getToken() == Token.DEFINING_SYMBOL) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
        getLexemeUnit()
        tree.add(parseDefinitionList())
      }
      else
        throw new Exception("Syntax Analyzer Error: defining symbol expected!")
    }
    else
      throw new Exception("Syntax Analyzer Error: meta identifier expected!")
    tree
  }

  // definitions-list = single-definition { ´|´ single-definition }
  private def parseDefinitionList(): Tree = {
//    println("parseDefinitionList")
    val tree = new Tree("definition-list")
    getLexemeUnit()
    var done = false
    while (!done) {
      tree.add(parseSingleDefinition())
      if (lexemeUnit.getToken() == Token.PIPE) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
        getLexemeUnit()
      }
      else
        done = true
    }
    tree
  }

  // single-definition = term { term }
  private def parseSingleDefinition(): Tree = {
//    println("parseSingleDefinition")
    val tree = new Tree("single-definition")
    getLexemeUnit()
    var done = false
    while (!done) {
      tree.add(parseTerm())
//      println(lexemeUnit)
      if (lexemeUnit.getToken() == Token.NEW_LINE) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
        getLexemeUnit()
        done = true
      }
      else if (lexemeUnit.getToken() == Token.PIPE ||
        lexemeUnit.getToken() == Token.NEW_LINE ||
        lexemeUnit.getToken() == Token.CLOSE_PAR ||
        lexemeUnit.getToken() == Token.CLOSE_BRACE ||
        lexemeUnit.getToken() == Token.CLOSE_BRACKET
      )
        done = true
    }
    tree
  }

  // term = optional-sequence | repeated-sequence | grouped-sequence | meta-identifier | terminal-string
  private def parseTerm(): Tree = {
//    println("parseTerm")
    val tree = new Tree("term")
    getLexemeUnit()
//    println(lexemeUnit)
    if (lexemeUnit.getToken() == Token.OPEN_BRACKET)
      tree.add(parseOptionalSequence())
    else if (lexemeUnit.getToken() == Token.OPEN_BRACE)
      tree.add(parseRepeatedSequence())
    else if (lexemeUnit.getToken() == Token.OPEN_PAR)
      tree.add(parseGroupedSequence())
    else if (lexemeUnit.getToken() == Token.META_IDENT || lexemeUnit.getToken() == Token.TERMINAL_STRING)  {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
    }
    else if (lexemeUnit.getToken() != Token.PIPE &&
      lexemeUnit.getToken() != Token.NEW_LINE &&
      lexemeUnit.getToken() != Token.CLOSE_PAR &&
      lexemeUnit.getToken() != Token.CLOSE_BRACE &&
      lexemeUnit.getToken() != Token.CLOSE_BRACKET
     )
      throw new Exception("Syntax Analyzer Error: valid term expected!")
    tree
  }

  // optional-sequence = ´[´ definitions-list ´]´
  private def parseOptionalSequence(): Tree = {
//    println("parseOptionalSequence")
    val tree = new Tree("optional-sequence")
    getLexemeUnit()
    if (lexemeUnit.getToken() == Token.OPEN_BRACKET) {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
      tree.add(parseDefinitionList())
      if (lexemeUnit.getToken() == Token.CLOSE_BRACKET) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
        getLexemeUnit()
      }
      else
        throw new Exception("Syntax Analyzer Error: closing bracket expected!")
    }
    else
      throw new Exception("Syntax Analyzer Error: opening bracket expected!")
    tree
  }

  // repeated-sequence = ´{´ definitions-list ´}´
  private def parseRepeatedSequence(): Tree = {
//    println("parseRepeatedSequence")
    val tree = new Tree("repeated-sequence")
    getLexemeUnit()
    if (lexemeUnit.getToken() == Token.OPEN_BRACE) {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
      tree.add(parseDefinitionList())
      if (lexemeUnit.getToken() == Token.CLOSE_BRACE) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
        getLexemeUnit()
      }
      else
        throw new Exception("Syntax Analyzer Error: closing curly brace expected!")
    }
    else
      throw new Exception("Syntax Analyzer Error: opening curly brace expected!")
    tree
  }

  // grouped-sequence  = ´(´ definitions-list ´)´
  private def parseGroupedSequence(): Tree = {
//    println("parseGroupedSequence")
    val tree = new Tree("grouped-sequence")
    getLexemeUnit()
    if (lexemeUnit.getToken() == Token.OPEN_PAR) {
      tree.add(new Tree(lexemeUnit.getLexeme()))
      lexemeUnit = null
      getLexemeUnit()
      tree.add(parseDefinitionList())
      if (lexemeUnit.getToken() == Token.CLOSE_PAR) {
        tree.add(new Tree(lexemeUnit.getLexeme()))
        lexemeUnit = null
        getLexemeUnit()
      }
      else
        throw new Exception("Syntax Analyzer Error: closing parenthesis expected!")
    }
    else
      throw new Exception("Syntax Analyzer Error: opening parenthesis expected!")
    tree
  }

}

object SyntaxAnalyzer {
  def main(args: Array[String]): Unit = {
    // check if source file was passed through the command-line
    if (args.length != 1) {
      print("Missing source file!")
      System.exit(1)
    }

    val syntaxAnalyzer = new SyntaxAnalyzer(args(0))
    val parseTree = syntaxAnalyzer.parse()
    print(parseTree)

    // serializing the parse tree
    val oos = new ObjectOutputStream(new FileOutputStream(args(0) + ".tree"))
    oos.writeObject(parseTree)
    oos.close
  }
}
