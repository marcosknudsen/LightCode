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

bloquefunct: beginfunct ss END
;

beginfunct: BEGIN {$$=new ParserVal(crear_terceto("begfunct",new ParserVal("-"),new ParserVal("-")));pila.push(reglas.size());}
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

condicionwhile: '(' condicion ')'
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

parametro: tipodato ID {guardarVariable($2.sval,new Simbolo($1.sval,"parametro"));$$=$2;pilaString.push($1.sval);}
    | ID {System.out.println("ERROR on line "+lex.line+": datatype expected");}
    | tipodato {System.out.println("ERROR on line "+lex.line+": identifier expected");}
;

retorno: RETURN '('expresion')' {$$=new ParserVal(crear_terceto("ret",$3,new ParserVal("-")));}
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

declaracion: tipodato FUN identificadorfunct '(' parametro ')' bloquefunct {
        ArrayList<String> errores=new ArrayList<String>();
        if(buscarVariable($3.sval)!=null)
            errores.add("declared");
        if(pilaString.pop().compareTo($1.sval)!=0)
            errores.add("typeNotMatch");
        $$=new ParserVal(crear_terceto("endfun",$3,new ParserVal("-"),errores));
        t=reglas.get(pila.peek()-1);
        t.a=$3;
        t.b=$5;
        reglas.set(pila.pop()-1,t);
        colaAmbito.remove(colaAmbito.size()-1);
        if (!errores.contains("declared")){
            guardarVariable($3.sval,new Simbolo($1.sval,"Fun"));
        };
    }
    | tipodato FUN identificadorfunct'('')' bloquefunct {
        ArrayList<String> errores=new ArrayList<String>();
        if(buscarVariable($3.sval)!=null)
            errores.add("declared");
        if(pilaString.pop().compareTo($1.sval)!=0)
            errores.add("typeNotMatch");
        $$=new ParserVal(crear_terceto("endfun",$3,new ParserVal("-"),errores));
        t=reglas.get(pila.peek()-1);
        t.a=new ParserVal($3.sval);
        reglas.set(pila.pop()-1,t);
        colaAmbito.remove(colaAmbito.size()-1);
        if (!errores.contains("declared")){
            guardarVariable($3.sval,new Simbolo($1.sval,"Fun"));
        };
    }
    | tipodato listavariables ';' {
        for (int i=0; i<variables.size(); i++) {
            ArrayList<String> errores=new ArrayList<String>();
            if(lex.tablaSimbolos.get("m:"+$3.sval)!=null)
                errores.add("declared");
            crear_terceto("decl",$1,new ParserVal(variables.get(i)),errores);
            guardarVariable(variables.get(i),new Simbolo($1.sval,"Var"));
        }
        variables.clear();
    }
;

identificadorfunct: ID {colaAmbito.add($1.sval+":");}
;


tipodato: UINTEGER  {$$=$1;}
    | LONGINT  {$$=$1;}
;

expresion: termino
    | expresion '+' termino {
        ArrayList<String> errores=new ArrayList<String>();
        if (buscarVariable($1.sval).tipo!=buscarVariable($3.sval).tipo){
            errores.add("datatype missmatch");    
        }
        $$=new ParserVal(crear_terceto("+",$1,$3,errores));}
    | expresion '-' termino {
        ArrayList<String> errores=new ArrayList<String>();
        if (buscarVariable($1.sval).tipo!=buscarVariable($3.sval).tipo){
            errores.add("datatype missmatch");    
        }
        $$=new ParserVal(crear_terceto("-",$1,$3,errores));}
;

termino: factor
    | termino '*' factor {
        ArrayList<String> errores=new ArrayList<String>();
        if (buscarVariable($1.sval).tipo!=buscarVariable($3.sval).tipo){
            errores.add("datatype missmatch");    
        }
        $$=new ParserVal(crear_terceto("*",$1,$3,errores));}
    | termino '/' factor {
        ArrayList<String> errores=new ArrayList<String>();
        if (buscarVariable($1.sval).tipo!=buscarVariable($3.sval).tipo){
            errores.add("datatype missmatch");    
        }
        $$=new ParserVal(crear_terceto("/",$1,$3,errores));}
;

factor:ID
    |UINTEGER
    |'-' UINTEGER
    | LONGINT
    | '-' LONGINT
    |invocacion
;

listavariables: ID ',' listavariables {variables.add($1.sval);}
    | ID {variables.add($1.sval);}
;

invocacion: ID '('')' {$$=new ParserVal(crear_terceto("exec",$1,new ParserVal("-")));pilaString.push(buscarVariable($1.sval).tipo);}
    | ID '('expresion')' {
        /* ArrayList<String> errores=new ArrayList<String>();
        if (buscarVariable($1.sval).tipo.compareTo(buscarVariable($3.sval).tipo)!=0){
            errores.add("datatype missmatch");    
        }
        pilaString.push(buscarVariable($1.sval).tipo); */
        $$=new ParserVal(crear_terceto("exec",$1,$3));}
;
%%

static Lex lex=null;
static Parser par=null;
int index=0;
static ArrayList<Terceto> reglas=new ArrayList<Terceto>();
static ArrayList<String> variables=new ArrayList<String>();
static Stack<Integer> pila = new Stack<>();
static Stack<String> pilaString=new Stack<>();
static ArrayList<String> colaAmbito=new ArrayList<String>();
ParserVal PV;


int pointer;
Terceto t;

public static void main(String[] args) throws FileNotFoundException{
    colaAmbito.add("m:");
    System.out.println("Iniciando compilacion...");
    lex=new Lex(args[0]);
    par=new Parser(false);
    par.run();
    System.out.println("Fin compilacion");
    System.out.println();
    System.out.println("Lista de Reglas");
    mostrarReglas(reglas);
    System.out.println();
    System.out.println("Tabla de Simbolos");
    mostrarTS();
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
    System.out.println(s+" on line "+lex.line);
}

String crear_terceto(String operando,ParserVal a,ParserVal b){
    Terceto t=new Terceto(operando, a, b);
    reglas.add(t);
    return "["+Integer.toString(reglas.indexOf(t))+"]";
}

String crear_terceto(String operando,ParserVal a,ParserVal b,ArrayList<String> errores){
    Terceto t=new Terceto(operando, a, b,errores);
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

static void mostrarTS(){
    for (String name: lex.tablaSimbolos.keySet()) {
        String key = name.toString();
        String value = lex.tablaSimbolos.get(name).toString();
        System.out.println(key + " " + value);
    }
}

Simbolo buscarVariable(String nombre){
    Simbolo variable;
    int N=0;
    do{
        variable=lex.tablaSimbolos.get(getVariableName(nombre,0));
        N++;
    }while(variable==null&&N<colaAmbito.size());
    return variable;
}

static String getVariableName(String nombre,int n){
    String sufijo="";
    for (int i=0;i<colaAmbito.size();i++){
        sufijo=sufijo+colaAmbito.get(i);
    }
    nombre=sufijo+nombre;
    return nombre;
}

void guardarVariable(String nombre,Simbolo s){
    String sufijo="";
    for (int i=0;i<colaAmbito.size();i++){
        sufijo=sufijo+colaAmbito.get(i);
    }
    nombre=sufijo+nombre;
    lex.tablaSimbolos.put(nombre,s);
}

static int decode(String str){
    str = str.substring(1, str.length() - 1);
    return Integer.valueOf(str);
}