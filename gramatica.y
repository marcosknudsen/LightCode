%{
    import Lex;
%}

%token IF THEN ID ASSIGN ELSE BEGIN END END_IF PRINT WHILE DO FUN RETURN CTE CADENA UINTEGER LONGINT
%start programa

%%

programa:ID bloque
;

bloque: BEGIN SS END
;

bloqueejecutable:BEGIN SSE END
;

ss: ss s
    | s
;

s: declaracion
    |se
;

sse:se
    | sse se
;

se:seleccion ';'
    | iteracion';'
    | retorno ';'
    | asignacion ';'
    | print ';'
;

iteracion: DO bloqueejecutable WHILE '('condicion')'
;

seleccion: IF '('condicion')' THEN bloqueejecutable END_IF
    | IF '('condicion')' THEN bloqueejecutable ELSE bloqueejecutable END_IF
;

retorno: RETURN '('expresion')'
;

asignacion: ID ASSIGN expresion
;

print: PRINT '(' CADENA ')'
;

declaracion: tipodato FUN ID '(' parametro ')' bloque
    | tipodato FUN ID'('')' bloque
    | tipodato listavariables ';'
;

tipodato: UINTEGER
    | LONGINT
;

expresion: termino
    | expresion '+' termino
    | expresion '-' termino
;

termino: factor
    | termino '*' factor
    | termino '/' factor
;

factor:ID
    |CTE
    |'-' CTE
    |invocacion
;

listavariables: ID ',' listavariables
    | ID
;

invocacion: ID '('')'
    | ID '('expresion')'
    | ID '('ID')'
;
%%

int yylex(){
    int token=Lex.getToken();
    return token;
}