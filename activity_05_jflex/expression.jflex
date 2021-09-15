%%
%class LexicalAnalyzer
%type Lexeme
%debug

spaces     = [ \t\n\r]*
literal    = [0-9]+
identifier = [a-z]([a-z]|[0-9])*
%%

<YYINITIAL> {
    "\+"         { new Lexeme(yytext(), Token.ADDITION);       }
    "-"          { new Lexeme(yytext(), Token.SUBTRACTION);    }
    "*"          { new Lexeme(yytext(), Token.MULTIPLICATION); }
    "/"          { new Lexeme(yytext(), Token.DIVISION);       }
    {identifier} { new Lexeme(yytext(), Token.IDENTIFIER);     }
    {literal}    { new Lexeme(yytext(), Token.LITERAL);        }
    {spaces}     { }
}

<<EOF>> { return new Lexeme("eof", Token.EOF); }