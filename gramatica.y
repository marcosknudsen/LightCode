%{
    import Lex;
%}

%token IF THEN ID ASSIGN ELSE BEGIN END END_IF PRINT WHILE DO FUN RETURN CTE
%start programa

%%

programa:ID Bloque
;

Bloque: BEGIN SS END
;

BloqueEjecutable:BEGIN SSE END
;

SS: SS S
    | S
;

S: Declaracion ';'
S: SE ';'

%%


int yylex(){
    int token=Lex.getToken();
    return token;
}