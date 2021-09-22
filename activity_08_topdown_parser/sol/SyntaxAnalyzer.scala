/*
 * CS3210 - Principles of Programming Languages - Fall 2021
 * Instructor: Thyago Mota
 * Description: Activity 08 - SyntaxAnalyzer (an iterable syntax analyzer)
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

  private val it = new LexicalAnalyzer(source).iterator
  private var current: Lexeme = null

  // returns the current lexeme
  private def getLexeme(): Lexeme = {
    if (current == null) {
      current = it.next
    }
    //    println(current)
    current
  }

  // advances the input one lexeme
  private def nextLexeme() = {
    current = it.next
  }

  // TODO: finish the recursive descent parser

  // parses the program, returning its corresponding parse tree
  def parse() = {
    parseSyntax()
  }

  // syntax = syntax-rule { ´\n´ syntax-rule }
  private def parseSyntax() = {
    val tree = new Tree("syntax")
    tree.add(parseSyntaxRule())
    while (getLexeme().getToken() == Token.NEW_LINE) {
      val lexeme = getLexeme()
      tree.add(new Tree(lexeme.getLabel()))
      nextLexeme()
      tree.add(parseSyntaxRule())
    }
    tree
  }

  // syntax-rule = meta-identifier ´=´ definitions-list
  private def parseSyntaxRule() = {
    val tree = new Tree("syntax-rule")
    var lexeme = getLexeme()
    if (lexeme.getToken() == Token.META_IDENTIFIER) {
      tree.add(new Tree(lexeme.getLabel()))
      nextLexeme()
      lexeme = getLexeme()
      if (lexeme.getToken() == Token.EQUAL) {
        tree.add(new Tree(lexeme.getLabel()))
        nextLexeme()
        tree.add(parseDefinitionsList())
      }
      else
        throw new Exception("Syntax error: '=' expected!")
    }
    else
      throw new Exception("Syntax error: meta-identifier expected!")
    tree
  }

  // definitions-list = single-definition { ´|´ single-definition }
  private def parseDefinitionsList(): Tree = {
    val tree = new Tree("definitions-list")
    tree.add(parseSingleDefinition())
    while (getLexeme().getToken() == Token.PIPE) {
      val lexeme = getLexeme()
      tree.add(new Tree(lexeme.getLabel()))
      nextLexeme()
      tree.add(parseSingleDefinition())
    }
    tree
  }

  // single-definition = term { term }
  private def parseSingleDefinition() = {
    val tree = new Tree("single-definition")
    tree.add(parseTerm())
    var done = false
    while (!done) {
      val lexeme = getLexeme()
      if (lexeme.getToken() != Token.OPEN_BRACKET &&
      lexeme.getToken() != Token.OPEN_BRACE &&
      lexeme.getToken() != Token.OPEN_PAR &&
      lexeme.getToken() != Token.META_IDENTIFIER &&
      lexeme.getToken() != Token.TERMINAL_STRING)
        done = true
      else
        tree.add(parseTerm())
    }
    tree
  }

  // term = optional-sequence | repeated-sequence | grouped-sequence | meta-identifier | terminal-string
  private def parseTerm() = {
    val tree = new Tree("term")
    val lexeme = getLexeme()
    if (lexeme.getToken() == Token.OPEN_BRACKET)
      tree.add(parseOptionalSequence())
    else if (lexeme.getToken() == Token.OPEN_BRACE)
      tree.add(parseRepeatedSequence())
    else if (lexeme.getToken() == Token.OPEN_PAR)
      tree.add(parseGroupedSequence())
    else if (lexeme.getToken() == Token.META_IDENTIFIER || lexeme.getToken() == Token.TERMINAL_STRING) {
      tree.add(new Tree(lexeme.getLabel()))
      nextLexeme()
    }
    else
      throw new Exception("Syntax error: '[', '{', '(', meta-identifier, or terminal-string expected!")
    tree
  }

  // optional-sequence = ´[´ definitions-list ´]´
  private def parseOptionalSequence() = {
    val tree = new Tree("optional-sequence")
    var lexeme = getLexeme()
    if (lexeme.getToken() == Token.OPEN_BRACKET) {
      tree.add(new Tree(lexeme.getLabel()))
      nextLexeme()
      tree.add(parseDefinitionsList())
      lexeme = getLexeme()
      if (lexeme.getToken() == Token.CLOSE_BRACKET) {
        tree.add(new Tree(lexeme.getLabel()))
        nextLexeme()
      }
      else
        throw new Exception("Syntax error: ']' expected!")
    }
    else
      throw new Exception("Syntax error: '[' expected!")
    tree
  }

  // repeated-sequence = ´{´ definitions-list ´}´
  private def parseRepeatedSequence() = {
    val tree = new Tree("repeated-sequence")
    var lexeme = getLexeme()
    if (lexeme.getToken() == Token.OPEN_BRACE) {
      tree.add(new Tree(lexeme.getLabel()))
      nextLexeme()
      tree.add(parseDefinitionsList())
      lexeme = getLexeme()
      if (lexeme.getToken() == Token.CLOSE_BRACE) {
        tree.add(new Tree(lexeme.getLabel()))
        nextLexeme()
      }
      else
        throw new Exception("Syntax error: '}' expected!")
    }
    else
      throw new Exception("Syntax error: '{' expected!")
    tree
  }

  // grouped-sequence = ´(´ definitions-list ´)´
  private def parseGroupedSequence() = {
    val tree = new Tree("grouped-sequence")
    var lexeme = getLexeme()
    if (lexeme.getToken() == Token.OPEN_PAR) {
      tree.add(new Tree(lexeme.getLabel()))
      nextLexeme()
      tree.add(parseDefinitionsList())
      lexeme = getLexeme()
      if (lexeme.getToken() == Token.CLOSE_PAR) {
        tree.add(new Tree(lexeme.getLabel()))
        nextLexeme()
      }
      else
        throw new Exception("Syntax error: ')' expected!")
    }
    else
      throw new Exception("Syntax error: '(' expected!")
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
  }
}
