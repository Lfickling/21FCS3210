/*
 * CS3210 - Principles of Programming Languages - Fall 2021
 * Instructor: Thyago Mota
 * Description: Activity 07 - Lexeme (a symbol with a token value)
 */

class Lexeme(private var label: String, private var token: Token.Value) extends Symbol(label) {

  def getToken() = { token }

  override def toString: String = "(label:" + label + ", token:" + token + ")"
}