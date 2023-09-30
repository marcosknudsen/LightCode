%{
    import java.io.FileNotFoundException;
    import java.io.IOException;
%}

%token IF THEN ID ASSIGN ELSE BEGIN END END_IF PRINT WHILE DO FUN RETURN CTE CADENA UINTEGER LONGINT MAYOR_IGUAL MENOR_IGUAL DISTINTO
%start programa

%%

programa:ID bloque {System.out.println("programa");}
;

bloque: BEGIN ss END {System.out.println("bloque");}
;

bloqueejecutable:BEGIN sse END {System.out.println("bloqueejecutable");}
;

ss: ss s {System.out.println("ss");}
    | s  {System.out.println("s");}
;

s: declaracion {System.out.println("declaracion");}
    |se {System.out.println("se");}
;

sse:se {System.out.println("se");}
    | sse se {System.out.println("sse");}
;

se:seleccion ';' {System.out.println("seleccion");}
    | iteracion';' {System.out.println("iteracion");}
    | retorno ';' {System.out.println("retorno");}
    | asignacion ';' {System.out.println("asignacion");}
    | print ';' {System.out.println("print");}
;

iteracion: DO bloqueejecutable WHILE '('condicion')' {System.out.println("do while");}
;

seleccion: IF '('condicion')' THEN bloqueejecutable END_IF {System.out.println("if");}
    | IF '('condicion')' THEN bloqueejecutable ELSE bloqueejecutable END_IF {System.out.println("if_else");}
;

condicion: expresion '>' expresion {System.out.println(">");}
        | expresion '<' expresion {System.out.println("<");}
        | expresion '=' expresion {System.out.println("=");}
        | expresion MAYOR_IGUAL expresion {System.out.println("Mayor igual");}
        | expresion MENOR_IGUAL expresion {System.out.println("Menor igual");}
        | expresion DISTINTO expresion {System.out.println("Distinto");}
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

expresion: termino {System.out.println("Expresion");}
    | expresion '+' termino
    | expresion '-' termino
;

termino: factor {System.out.println("termino");}
    | termino '*' factor {System.out.println("factor");}
    | termino '/' factor {System.out.println("factor");}
;

factor:ID
    |UINTEGER
    |'-' UINTEGER
    | LONGINT
    | '-' LONGINT
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
    par=new Parser(true);
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