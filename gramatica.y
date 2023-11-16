%{
    import java.io.FileNotFoundException;
    import java.io.IOException;
    import java.util.ArrayList;
    import java.util.Stack;
%}

%token IF THEN ID ASSIGN ELSE BEGIN END END_IF PRINT WHILE DO FUN RETURN CTE CADENA UINTEGER LONGINT MAYOR_IGUAL MENOR_IGUAL DISTINTO
%start programa

%%

programa:ID bloque
;

bloque: BEGIN ss END
;

bloqueejecutable: BEGIN ss END
;

bloquewhile: beginwhile ss END 
;

beginwhile: BEGIN {pila.push(reglas.size());}
;

bloquethen: BEGIN ss END {
    pointer=pila.pop();
    Terceto t = reglas.get(pointer);
    PV = new ParserVal(
      crear_terceto("BI",new ParserVal("-"),new ParserVal("-"))
    );
    t.b = new ParserVal(reglas.size());
    reglas.set(pointer, t);
    pila.push(reglas.size()-1);$$=PV;}
;

ss: ss s
    | s
;

s: declaracion
    | se
;

se:seleccion ';' {pointer=pila.pop();
    Terceto t = reglas.get(pointer);
    t.b = new ParserVal(reglas.size());
    reglas.set(pointer, t);}
    | iteracion';'
    | retorno ';'
    | asignacion ';'
    | print ';'
    | error ';' {System.out.println("ERROR on line "+lex.line+" sentencia invalida");}
;

iteracion: DO bloquewhile WHILE condicionwhile {    
    crear_terceto(
      "BF",
      new ParserVal("[" + (reglas.size() - 1) + "]"),
      new ParserVal("-")
    );
    pila.push(reglas.size());
    crear_terceto(
      "BI",
      new ParserVal("-"),
      new ParserVal("-")
    );
    t=reglas.get(pila.peek()-1);
    t.b=new ParserVal(reglas.size());
    reglas.set(pila.pop()-1,t);
    t=reglas.get(reglas.size()-1);
    t.b=new ParserVal("["+pila.pop()+"]");
    }
    | bloquewhile WHILE condicionwhile {System.out.println("ERROR on line "+lex.line+" 'do' expected");}
;

seleccion: IF condicionif THEN bloquethen END_IF
    | IF condicionif THEN bloquethen ELSE bloqueejecutable END_IF
    | IF '(' condicion THEN bloquethen ELSE bloqueejecutable END_IF {System.out.println("ERROR on line "+lex.line+": ')' expected");}
    | IF condicion ')' THEN bloquethen ELSE bloqueejecutable END_IF {System.out.println("ERROR on line "+lex.line+": '(' expected");}
    | IF condicionif bloquethen ELSE bloqueejecutable END_IF {System.out.println("ERROR on line "+lex.line+": 'then' expected");}
    | IF condicionif THEN bloquethen ELSE bloqueejecutable {System.out.println("ERROR on line "+lex.line+": 'end_if' expected");}
;

condicionif: '(' condicion ')' {ParserVal PV = new ParserVal(crear_terceto("BF",new ParserVal("["+(reglas.size()-1)+"]"),new ParserVal("-")));pila.push(reglas.size()-1);$$=PV;}
;

condicionwhile: '(' condicion ')' {}
;

condicion: expresion '>' expresion  {$$=new ParserVal(crear_terceto(">",$1,$3));}
        | expresion '<' expresion  {$$=new ParserVal(crear_terceto("<",$1,$3));}
        | expresion '=' expresion  {$$=new ParserVal(crear_terceto("=",$1,$3));}
        | expresion MAYOR_IGUAL expresion {$$=new ParserVal(crear_terceto(">=",$1,$3));}
        | expresion MENOR_IGUAL expresion {$$=new ParserVal(crear_terceto("<=",$1,$3));}
        | expresion DISTINTO expresion {$$=new ParserVal(crear_terceto("<>",$1,$3));}
        | expresion '>' {System.out.println("ERROR on line "+lex.line+": second expresion expected");}
        | expresion '<' {System.out.println("ERROR on line "+lex.line+": second expresion expected");}
        | expresion '=' {System.out.println("ERROR on line "+lex.line+": second expresion expected");}
        | expresion MAYOR_IGUAL {System.out.println("ERROR on line "+lex.line+": second expresion expected");}
        | expresion MENOR_IGUAL {System.out.println("ERROR on line "+lex.line+": second expresion expected");}
        | expresion DISTINTO {System.out.println("ERROR on line "+lex.line+": second expresion expected");}
;

parametro: tipodato ID
    | ID {System.out.println("ERROR on line "+lex.line+": datatype expected");}
    | tipodato {System.out.println("ERROR on line "+lex.line+": identifier expected");}
;

retorno: RETURN '('expresion')'
    | RETURN '(' expresion {System.out.println("ERROR on line "+lex.line+": ')' expected");}
    | RETURN expresion ')' {System.out.println("ERROR on line "+lex.line+": '(' expected");}
;

asignacion: ID ASSIGN expresion {$$=new ParserVal(crear_terceto(":=",$1,$3));}
    | ID ASSIGN {System.out.println("ERROR on line "+lex.line+": expresion expected");}
    | ASSIGN expresion {System.out.println("ERROR on line "+lex.line+": identifier expected");}
;

print: PRINT '(' CADENA ')'
    | PRINT CADENA ')' {System.out.println("ERROR on line "+lex.line+": '(' expected");}
    | PRINT '(' CADENA {System.out.println("ERROR on line "+lex.line+": ')' expected");}
    | PRINT '(' ')' {System.out.println("ERROR on line "+lex.line+": String expected");}
;

declaracion: tipodato FUN ID '(' parametro ')' bloque
    | tipodato FUN ID'('')' bloque
    | tipodato listavariables ';'
;

tipodato: UINTEGER  {$$=$1;}
    | LONGINT  {$$=$1;}
;

expresion: termino
    | expresion '+' termino {$$=new ParserVal(crear_terceto("+",$1,$3));}
    | expresion '-' termino {$$=new ParserVal(crear_terceto("-",new ParserVal($1.sval),new ParserVal($3.sval)));}
;

termino: factor
    | termino '*' factor {$$=new ParserVal(crear_terceto("*",$1,$3));}
    | termino '/' factor {$$=new ParserVal(crear_terceto("/",$1,$3));}
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
int index=0;
static ArrayList<Terceto> reglas=new ArrayList<Terceto>();
static Stack<Integer> pila = new Stack<>();
ParserVal PV;

int pointer;
Terceto t;

public static void main(String[] args) throws FileNotFoundException{
    System.out.println("Iniciando compilacion...");
    lex=new Lex(args[0]);
    par=new Parser(false);
    par.run();
    System.out.println("Fin compilacion");
    mostrarReglas(reglas);
    
}


int yylex(){
    int token;
    try {
      token = lex.getToken();
      yylval=new ParserVal(lex.yylval);
    } catch (IOException e) {
      token=-1;
    }
    return token;
}

void yyerror(String s){
    System.out.println(s);
}

String crear_terceto(String operando,ParserVal a,ParserVal b){
    Terceto t=new Terceto(operando, a, b);
    reglas.add(t);
    return "["+Integer.toString(reglas.indexOf(t))+"]";
}


static int mostrarPila(Stack<Integer> pila){
    if (pila.empty())
        System.out.println("Pila Vacía");
    else
        for (int i=0;i<pila.size();i++)
            System.out.println(pila.get(i));
    return 0;
}

static int mostrarReglas(ArrayList<Terceto> reglas){
    if (reglas.size()==0)
        System.out.println("No hay reglas");
    else
        for (int i=0;i<reglas.size();i++)
            System.out.println("["+i+"] "+reglas.get(i));
    return 0;
}
