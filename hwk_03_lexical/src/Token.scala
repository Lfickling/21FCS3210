/*
 * CS3210 - Principles of Programming Languages - Fall 2021
 * Instructor: Thyago Mota
 * Description: Homework 03 - Token
 * Student(s) Name(s):
 */

object Token extends Enumeration {
  val EOF         = Value
  val CLASS       = Value
  val IDENTIFIER  = Value
  val PUBLIC      = Value
  val ABSTRACT    = Value
  val FINAL       = Value
  val EXTENDS     = Value
  val IMPLEMENTS  = Value
  val BLOCK_OPEN  = Value
  val BLOCK_CLOSE = Value
  val COMMA       = Value
}
