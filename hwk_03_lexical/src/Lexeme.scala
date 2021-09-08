/*
 * CS3210 - Principles of Programming Languages - Fall 2021
 * Instructor: Thyago Mota
 * Description: Homework 03 - Lexeme (a symbol with a token value)
 * Student(s) Name(s):
 */

class Lexeme(private var label: String, private var token: Token.Value) extends Symbol(label) {

  def getToken() = { token }

  override def toString: String = "(label:" + label + ", token:" + token + ")"
}