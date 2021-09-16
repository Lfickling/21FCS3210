/*
 * CS3210 - Principles of Programming Languages - Fall 2021
 * Instructor: Thyago Mota
 * Description: Activity 07 - SyntaxAnalyzer (an iterable syntax analyzer)
 */

/*
expression  = term expression'
expression' = ( ´+´  | ´-´ ) term expression' | epsilon
term        = factor term'
term'       = ( ´*´ | ´/´ ) factor term' | epsilon
factor      = identifier | literal | ´(´ expression ´)´
identifier  = letter { ( letter | digit ) }
letter      = ´a´ | ´b´ | ´c´ | ´d´ | ´e´ | ´f´ | ´g´ | ´h´ | ´i´ | ´j´ | ´k´ | ´l´ | ´m´ | ´n´ | ´o´ | ´p´ | ´q´ | ´r´ | ´s´ | ´t´ | ´u´ | ´v´ | ´w´ | ´x´ | ´y´ | ´z´
literal     = digit { digit }
digit       = ´0´ | ´1´ | ´2´ | ´3´ | ´4´ | ´5´ | ´6´ | ´7´ | ´8´ | ´9´
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

  // parses the program, returning its corresponding parse tree
  def parse() = {
    parseExpr()
  }

  // expression  = term expression'
  private def parseExpr() = {

    // TODOd: create a parse tree with non-terminal value "expression"

    // TODOd: parse a term

    // TODOd: parse a expressionPrime

    // TODOd: return the parse tree
  }

  // term = factor term'
  private def parseTerm() = {

    // TODOd: create a parse tree with non-terminal value "expression"

    // TODOd: parse a factor

    // TODOd: parse a termPrime

    // TODOd: return the parse tree
  }

  // factor = identifier | literal | ´(´ expression ´)´
  private def parseFactor(): Tree = {

    // TODOd: create a parse tree with non-terminal value "factor"


  }

  // term' = ( ´*´ | ´/´ ) factor term' | epsilon
  def parseTermPrime(): Tree = {

  }

  // expression' = ( ´+´  | ´-´ ) term expression' | epsilon
  def parseExprPrime(): Tree = {


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
