import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Lex {
        HashMap<String, Simbolo> tablaSimbolos = new HashMap<>();
        HashMap<String, Integer> tablaPRes = new HashMap<>();

        ParserVal yylval;
        private BufferedReader codigoFuente;
        private String cadena;
        Pointer pointer;
        int line=1;

        public Lex(String filename) throws FileNotFoundException {
                this.codigoFuente = new BufferedReader(
                                new FileReader(filename));
                tablaPRes.put("if", 257);
                tablaPRes.put("then", 258);
                tablaPRes.put("else", 261);
                tablaPRes.put("begin", 262);
                tablaPRes.put("end", 263);
                tablaPRes.put("end_if", 264);
                tablaPRes.put("print", 265);
                tablaPRes.put("while", 266);
                tablaPRes.put("do", 267);
                tablaPRes.put("fun", 268);
                tablaPRes.put("return", 269);
                tablaPRes.put("uinteger", 272);
                tablaPRes.put("longint", 273);

                tablaSimbolos.put("if", new Simbolo("String", "pr"));
                tablaSimbolos.put("then", new Simbolo("String", "pr"));
                tablaSimbolos.put("else", new Simbolo("String", "pr"));
                tablaSimbolos.put("begin", new Simbolo("String", "pr"));
                tablaSimbolos.put("end", new Simbolo("String", "pr"));
                tablaSimbolos.put("end_if", new Simbolo("String", "pr"));
                tablaSimbolos.put("print", new Simbolo("String", "pr"));
                tablaSimbolos.put("while", new Simbolo("String", "pr"));
                tablaSimbolos.put("do", new Simbolo("String", "pr"));
                tablaSimbolos.put("fun", new Simbolo("String", "pr"));
                tablaSimbolos.put("return", new Simbolo("String", "pr"));
                tablaSimbolos.put("uinteger", new Simbolo("String", "pr"));
                tablaSimbolos.put("longint", new Simbolo("String", "pr"));
        }

        AccionSemantica none = new None();
        AccionSemantica strt = new Start();
        AccionSemantica stst = new StartString();
        AccionSemantica newl = new NewLine();
        AccionSemantica writ = new Write();
        AccionSemantica fnid = new FinishId();
        AccionSemantica fnil = new finishIdNl();
        AccionSemantica aste = new Asterisco();
        AccionSemantica lite = new Literal();
        AccionSemantica astl = new finishAst();
        AccionSemantica mayo = new Mayor();
        AccionSemantica meql = new MayorIgual();
        AccionSemantica meno = new Menor();
        AccionSemantica meoi = new MenorIgual();
        AccionSemantica dist = new Distinto();
        AccionSemantica wnst = new WarningStringNL();
        AccionSemantica fnst = new FinishString();
        AccionSemantica fnct = new FinishConstante();
        AccionSemantica fncn = new finishConsNL();
        AccionSemantica menl = new finishMenor();
        AccionSemantica mayl = new FinishMayor();
        AccionSemantica assi = new Assign();

 
        // L 1
        // D 2
        // / 3D
        // * 4
        // + 5
        // - 6
        // = 7
        // < 8
        // > 9
        // : 10
        // " 11
        // @ 12
        // ( 13
        // ) 14
        // , 15
        // ; 16
        // otro 17
        // bl/tab/nl 18
        // $ (eof) 19

        // ESTADO TRAMPA -2
        int matrizestados[][] = {
                //        L  D  /  *  +  -  =  <  >  :  "  @  (  )  ,  ; otr bl/tab  nl eof 
                        { 1, 7,-1, 2,-1,-1,-1, 6, 5, 8, 9, 1,-1,-1,-1,-1,-2,    0  , 0 ,-1 }, // 0
                        { 1, 1,-1,-1,-1,-1,-1,-1,-1,-1,-1, 1,-1,-1,-1,-1,-1,   -1  , -1,-1 }, // 1
                        {-1,-1, 3,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,   -1  , -1,-1 }, // 2
                        { 3, 3, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,    3  , 3 ,-2}, // 3
                        { 3, 3, 4, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3,    3  , 3 ,-2}, // 4
                        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,   -1  ,-1 ,-1 }, // 5
                        {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,   -1  ,-1 ,-1 }, // 6
                        {-1, 7,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,   -1  ,-1 ,-1 }, // 7
                        {-2,-2,-2,-2,-2,-2,-1,-2,-2,-2,-2,-2,-2,-2,-2,-2,-2,   -2  ,-2 ,-2 }, // 8
                        { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9,-1, 9, 9, 9, 9, 9, 9,    9  ,-1 , 9 }, // 9
        };



        AccionSemantica matrizAS[][]={
                // L     D    /      *     +     -    =     <     >      :     "     @     (    )      ,    ;  otro bl/tab  nl   eof
                {strt, strt, lite, none, lite, lite, lite, none, none, none, stst, strt, lite, lite, lite,lite,null, none ,newl,none},//0
                {writ, writ, fnid, fnid, fnid, fnid, fnid, fnid, fnid, fnid, fnid, writ, fnid, fnid, fnid,fnid,fnid, fnid ,fnil,fnid},//1
                {aste, aste, none, aste, aste, aste, aste, aste, aste, aste, aste, aste, aste, aste, aste,aste,aste, aste ,astl,aste},//2
                {none, none, none, none, none, none, none, none, none, none, none, none, none, none, none,none,none, none ,newl,none},//3
                {none, none, none, none, none, none, none, none, none, none, none, none, none, none, none,none,none, none ,newl,none},//4
                {mayo, mayo, mayo, mayo, mayo, mayo, meql, mayo, mayo, mayo, mayo, mayo, mayo, mayo, mayo,mayo,mayo, mayo ,mayl,mayo},//5
                {meno, meno, meno, meno, meno, meno, meoi, meno, dist, meno, meno, meno, meno, meno, meno,meno,meno, meno ,menl,meno},//6
                {fnct, writ, fnct, fnct, fnct, fnct, fnct, fnct, fnct, fnct, fnct, fnct, fnct, fnct, fnct,fnct,fnct, fnct ,fncn,fnct},//7
                {none, none, none, none, none, none, assi, none, none, none, none, none, none, none, none,none,none, none ,none,none},//8         
                {writ, writ, writ, writ, writ, writ, writ, writ, writ, writ, fnst, writ, writ, writ, writ,writ,writ, writ ,wnst,none},//9
        };

        public int getToken() throws IOException {
                int estadoActual = 0;
                int caracterActual;
                int caracterValue;
                AccionSemantica as;
                while (estadoActual != -1) {
                        codigoFuente.mark(1);
                        caracterActual = codigoFuente.read();
                        caracterValue = decode(caracterActual);
                        as = matrizAS[estadoActual][caracterValue];
                        pointer = as.ejecutar(codigoFuente, (Lex) this, caracterActual, tablaSimbolos, tablaPRes);
                        estadoActual = matrizestados[estadoActual][caracterValue];
                        if (estadoActual==-2){
                                System.out.println("Error: linea N°"+this.line);
                                estadoActual=0;
                        }
                }
                return pointer.token;
        }

        public void setCadena(String c) {
                this.cadena = c;
        }

        public String getCadena() {
                return this.cadena;
        }

        private int decode(int caracterActual) {// A partir de un caracter devuelve su valor de matriz correspondiente
                int value;
                if (Character.isLetter((char) caracterActual)) {
                        value = 0;
                } else if (Character.isDigit((char) caracterActual)) {
                        value = 1;
                } else if (caracterActual == -1) {
                        value = 19;
                } else
                        switch ((char) caracterActual) {
                                case '/':
                                        value = 2;
                                        break;
                                case '*':
                                        value = 3;
                                        break;
                                case '+':
                                        value = 4;
                                        break;
                                case '-':
                                        value = 5;
                                        break;
                                case '=':
                                        value = 6;
                                        break;
                                case '<':
                                        value = 7;
                                        break;
                                case '>':
                                        value = 8;
                                        break;
                                case ':':
                                        value = 9;
                                        break;
                                case '"':
                                        value = 10;
                                        break;
                                case '@':
                                        value = 11;
                                        break;
                                case '(':
                                        value = 12;
                                        break;
                                case ')':
                                        value = 13;
                                        break;
                                case ',':
                                        value = 14;
                                        break;
                                case ';':
                                        value = 15;
                                        break;
                                case ' ':
                                case '\t':
                                        value = 17;
                                        break;
                                case '\n':
                                case '\r':
                                        value = 18;
                                        break;
                                default:
                                        value = 16;
                                        break;

                        }
                return value;
        }
}

