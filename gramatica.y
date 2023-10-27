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

se:seleccion ';' {System.out.println("seleccion");}
    | iteracion';' {System.out.println("iteracion");}
    | retorno ';' {System.out.println("retorno");}
    | asignacion ';' {System.out.println("asignacion");}
    | print ';' {System.out.println("print");}
    | error ';' {System.out.println("sentencia invalida");}
;

iteracion: DO bloqueejecutable WHILE '('condicion')' {System.out.println("do while");}
    bloqueejecutable WHILE '(' condicion ')' {System.out.println("ERROR on line "+lex.line+" 'do' expected");}
;

seleccion: IF '('condicion')' THEN bloqueejecutable END_IF {System.out.println("if");}
    | IF '('condicion')' THEN bloqueejecutable ELSE bloqueejecutable END_IF {System.out.println("if_else");}
    | IF '(' condicion THEN bloqueejecutable ELSE bloqueejecutable END_IF {System.out.println("ERROR on line "+lex.line+": ')' expected");}
    | IF condicion ')' THEN bloqueejecutable ELSE bloqueejecutable END_IF {System.out.println("ERROR on line "+lex.line+": '(' expected");}
    | IF '(' condicion ')' bloqueejecutable ELSE bloqueejecutable END_IF {System.out.println("ERROR on line "+lex.line+": 'then' expected");}
    | IF '(' condicion ')' THEN bloqueejecutable ELSE bloque {System.out.println("ERROR on line "+lex.line+": 'end_if' expected");}
    | error ';' {System.out.println("ERROR on line "+lex.line+": seleccion inválida");}
;

condicion: expresion '>' expresion {System.out.println("comparacion mayor");}
        | expresion '<' expresion {System.out.println("comparacion menor");}
        | expresion '=' expresion {System.out.println("comparacion igual");}
        | expresion MAYOR_IGUAL expresion {System.out.println("comparacion mayor igual");}
        | expresion MENOR_IGUAL expresion {System.out.println("comparacion menor igual");}
        | expresion DISTINTO expresion {System.out.println("comparacion distinto");}
        | expresion '>' {System.out.println("ERROR on line "+lex.line+": second expresion expected");}
        | expresion '<' {System.out.println("ERROR on line "+lex.line+": second expresion expected");}
        | expresion '=' {System.out.println("ERROR on line "+lex.line+": second expresion expected");}
        | expresion MAYOR_IGUAL {System.out.println("ERROR on line "+lex.line+": second expresion expected");}
        | expresion MENOR_IGUAL {System.out.println("ERROR on line "+lex.line+": second expresion expected");}
        | expresion DISTINTO {System.out.println("ERROR on line "+lex.line+": second expresion expected");}
        | error ';' {System.out.println("ERROR on line "+lex.line+": condicion inválida");}
;

parametro: tipodato ID {System.out.println("parametro");}
    | ID {System.out.println("ERROR on line "+lex.line+": datatype expected");}
    | tipodato {System.out.println("ERROR on line "+lex.line+": identifier expected");}
;

retorno: RETURN '('expresion')' {System.out.println("retorno");}
    | RETURN '(' expresion {System.out.println("ERROR on line "+lex.line+": ')' expected");}
    | RETURN expresion ')' {System.out.println("ERROR on line "+lex.line+": '(' expected");}
;

asignacion: ID ASSIGN expresion {System.out.println("asignacion");}
    | ID ASSIGN {System.out.println("ERROR on line "+lex.line+": expresion expected");}
    | ASSIGN expresion {System.out.println("ERROR on line "+lex.line+": identifier expected");}
;

print: PRINT '(' CADENA ')' {System.out.println("print");}
    | PRINT CADENA ')' {System.out.println("ERROR on line "+lex.line+": '(' expected");}
    | PRINT '(' CADENA {System.out.println("ERROR on line "+lex.line+": ')' expected");}
    | PRINT '(' ')' {System.out.println("ERROR on line "+lex.line+": String expected");}
;

declaracion: tipodato FUN ID '(' parametro ')' bloque {System.out.println("declaracion funcion c/parametro");}
    | tipodato FUN ID'('')' bloque {System.out.println("declaracion funcion s/parametro");}
    | tipodato listavariables ';' {System.out.println("declaracion variables");}
;

tipodato: UINTEGER
    | LONGINT
;

expresion: termino
    | expresion '+' termino
    | expresion '-' termino
    | expresion '+' {System.out.println("ERROR on line "+lex.line+": term expected");}
    | expresion '-' {System.out.println("ERROR on line "+lex.line+": term expected");}
;

termino: factor
    | termino '*' factor
    | termino '/' factor
;

factor:ID
    |UINTEGER
    |'-' UINTEGER
    | LONGINT
    | '-' LONGINT
    |invocacion {System.out.println("invocacion");}
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