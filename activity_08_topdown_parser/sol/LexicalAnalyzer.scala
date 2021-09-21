import scala.io.Source

/*
 * CS3210 - Principles of Programming Languages - Fall 2020
 * Instructor: Thyago Mota
 * Description: Activity 07 - Lexical Analyzer
 */

/*
syntax            = syntax-rule { syntax-rule }
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

class LexicalAnalyzer(private var source: String) extends Iterable[LexemeUnit] {

  private var input = ""
  for (line <- Source.fromFile(source).getLines)
    input += line + "\n"
  // determines the class of a given character
  private def getCharClass(c: Char): CharClass.Value = {
    if (LexicalAnalyzer.LETTERS.contains(c))
      CharClass.LETTER
    else if (LexicalAnalyzer.DIGITS.contains(c))
      CharClass.DIGIT
    else if (LexicalAnalyzer.BLANKS.contains(c))
      CharClass.BLANK
    else if (c == '+' || c == '-' || c == '*' || c == '/')
      CharClass.OPERATOR
    else if (c == '(' || c == ')' || c == '[' || c == ']' || c == '{' || c == '}')
      CharClass.DELIMITER
    else
      CharClass.OTHER
  }

  // reads the input until a non-blank character is found, returning the input updated
  private def readBlanks: Unit = {
    var foundNonBlank = false
    while (input.length > 0 && !foundNonBlank) {
      val c = input(0)
      if (getCharClass(c) == CharClass.BLANK)
        input = input.substring(1)
      else
        foundNonBlank = true
    }
  }

  def iterator: Iterator[LexemeUnit] = {
    new Iterator[LexemeUnit] {

      override def hasNext: Boolean = {
        readBlanks
        input.length > 0
      }

      override def next(): LexemeUnit = {
        if (!hasNext)
          new LexemeUnit("", Token.EOF)
        else {
          var lexeme = ""
          readBlanks
          if (input.length == 0)
            new LexemeUnit(lexeme, Token.EOF)
          else {
            var c = input(0)
            var charClass = getCharClass(c)

            // TODO: recognize a meta-identifier
            if (charClass == CharClass.LETTER) {
              input = input.substring(1)
              lexeme += c
              var done = false
              while (!done) {
                if (input.length == 0)
                  done = true
                else {
                  c = input(0)
                  charClass = getCharClass(c)
                  if (charClass == CharClass.LETTER || charClass == CharClass.DIGIT || c == '-' || c == '_') {
                    input = input.substring(1)
                    lexeme += c
                  }
                  else
                    done = true
                }
              }
              return new LexemeUnit(lexeme, Token.META_IDENT)
            }

            // TODO: recognize a terminal-string
            if (c == '´') {
              input = input.substring(1)
              lexeme += c
              var done = false
              while (!done) {
                if (input.length == 0)
                  done = true
                else {
                  c = input(0)
                  charClass = getCharClass(c)
                  if (c != '´') {
                    input = input.substring(1)
                    lexeme += c
                  }
                  else
                    done = true
                }
              }
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.TERMINAL_STRING)
            }

            // TODO: recognize new line symbol
            if (c == '\n') {
              input = input.substring(1)
              lexeme += "nl"
              return new LexemeUnit(lexeme, Token.NEW_LINE)
            }

            // TODO: recognize defining symbol
            if (c == '=') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.DEFINING_SYMBOL)
            }

            // TODO: recognize pipe symbol
            if (c == '|') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.PIPE)
            }

            // TODO: recognize open straight bracket symbol
            if (c == '[') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.OPEN_BRACKET)
            }

            // TODO: recognize close straight bracket symbol
            if (c == ']') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.CLOSE_BRACKET)
            }

            // TODO: recognize open curly brace symbol
            if (c == '{') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.OPEN_BRACE)
            }

            // TODO: recognize close curly brace symbol
            if (c == '}') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.CLOSE_BRACE)
            }

            // TODO: recognize open parenthesis symbol
            if (c == '(') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.OPEN_PAR)
            }

            // TODO: recognize close parenthesis symbol
            if (c == ')') {
              input = input.substring(1)
              lexeme += c
              return new LexemeUnit(lexeme, Token.CLOSE_PAR)
            }

            // throw an exception if an unrecognizable symbol is found
            throw new Exception("Lexical Analyzer Error: unrecognizable symbol '" + c + "' found!")
          }
        }
      } // end next
    } // end 'new' iterator
  } // end iterator method
} // end LexicalAnalyzer class

object LexicalAnalyzer {
  val LETTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
  val DIGITS  = "0123456789"
  val BLANKS  = " \t"

  def main(args: Array[String]): Unit = {
    // check if source file was passed through the command-line
    if (args.length != 1) {
      print("Missing source file!")
      System.exit(1)
    }

    val lex = new LexicalAnalyzer(args(0))
    val it = lex.iterator
    while (it.hasNext) {
      val lexemeUnit = it.next()
      println(lexemeUnit)
    }
  } // end main method
} // end LexicalAnalyzer object