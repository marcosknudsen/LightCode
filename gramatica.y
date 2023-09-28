%{
    import java.io.FileNotFoundException;
    import java.io.IOException;
%}

%token IF THEN ID ASSIGN ELSE BEGIN END END_IF PRINT WHILE DO FUN RETURN CTE CADENA UINTEGER LONGINT MAYOR_IGUAL MENOR_IGUAL DISTINTO
%start programa

%%

programa:ID bloque
;

bloque: BEGIN ss END
;

bloqueejecutable:BEGIN sse END
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

se:seleccion ';'{System.out.println("A");}
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

condicion: expresion '>' expresion
        | expresion '<' expresion
        | expresion '=' expresion
        | expresion MAYOR_IGUAL expresion
        | expresion MENOR_IGUAL expresion
        | expresion DISTINTO expresion
;

parametro: tipodato ID
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
;
%%


static Lex lex=null;
static Parser par=null;

public static void main(String[] args) throws FileNotFoundException{
    System.out.println("Iniciando compilacion...");
    lex=new Lex(args[0]);
    par=new Parser(false);
    par.run();
    System.out.println("Fin compilacion");
}


int yylex(){
    int token;
    try {
      token = lex.getToken();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      token=-1;
    }
    return token;
}

void yyerror(String s){
    System.out.println(s);
}