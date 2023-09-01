import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class Lex {
        HashMap<String, Simbolo> tablaSimbolos = new HashMap<>();
        private BufferedReader codigoFuente = null;
        private String cadena;

        public Lex(String filename) throws FileNotFoundException {
                this.codigoFuente = new BufferedReader(
                                new FileReader(filename));
        }

        AccionSemantica none = new nu();
        AccionSemantica stst = new StartString();// comienza la lectura de un string
        AccionSemantica writ = new WriteString();// continua la lectura de un string
        AccionSemantica fnst = new FinishString();// finaliza la lectura de un string
        AccionSemantica meql = new mayorigual();
        AccionSemantica mayo = new mayor();
        AccionSemantica meno = new menor();
        AccionSemantica meoi = new menorigual();
        AccionSemantica dist = new distinto();
        AccionSemantica assi = new assign();
        AccionSemantica erro = new error();
        AccionSemantica stid = new StartId();
        AccionSemantica wrid = new WriteId();
        AccionSemantica fnid = new FinishId();
        AccionSemantica stct = new StartConstante();
        AccionSemantica wrct = new WriteConstante();
        AccionSemantica fnct = new FinishConstante();
        AccionSemantica sdiv = new SignoDivision();
        AccionSemantica aste = new Asterisco();
        AccionSemantica suma = new Suma();
        AccionSemantica rest = new Resta();
        AccionSemantica equa = new Igual();
        AccionSemantica oppa = new OpenParentesis();
        AccionSemantica clpa = new CloseParentesis();
        AccionSemantica coma = new Coma();
        AccionSemantica pycm = new PuntoYComa();
        AccionSemantica blan = new Blanco();
        AccionSemantica opcm = new OpenComment();
        AccionSemantica clcm = new CloseComment();

        // L 1
        // D 2
        // / 3
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
                        { 1, 7, -1, -1, -1, -1, -1, 6, 5, 8, 9, 1, -1, -1, -1, -1, -2, 0, -1 }, // 0
                        { 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 1
                        { -1, -1, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 2
                        { 3, 3, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 }, // 3
                        { -1, -1, 4, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 }, // 4
                        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 5
                        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 6
                        { -1, 7, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 7
                        { -2, -2, -2, -2, -2, -2, -1, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2, -2 }, // 8
                        { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, -1, 9, 9, 9, 9, 9, 9, 9, 9 }// 9
        };

        AccionSemantica matrizAS[][]={
                // L     D    /      *     +     -    =     <     >      :     "     @     (    )      ,    ;  otro  bl   eof
                {stid, stct, sdiv, none, suma, rest, equa, none, none, none, stst, stid, oppa, clpa, coma,pycm,null,blan,null},//0
                {wrid, wrid, fnid, fnid, fnid, fnid, fnid, fnid, fnid, fnid, fnid, fnid, fnid, fnid, fnid,fnid,fnid,fnid,fnid},//1
                {aste, aste, opcm, aste, aste, aste, aste, aste, aste, aste, aste, aste, aste, aste, aste,aste,aste,aste,aste},//2
                {none, none, none, none, none, none, none, none, none, none, none, none, none, none, none,none,none,none,none},//3
                {none, none, none, clcm, none, none, none, none, none, none, none, none, none, none, none,none,none,none,none},//4
                {mayo, mayo, mayo, mayo, mayo, mayo, meql, mayo, mayo, mayo, mayo, mayo, mayo, mayo, mayo,mayo,mayo,mayo,mayo},//5
                {meno, meno, meno, meno, meno, meno, meoi, meno, dist, meno, meno, meno, meno, meno, meno,meno,meno,meno,meno},//6
                {fnct, wrct, fnct, fnct, fnct, fnct, fnct, fnct, fnct, fnct, fnct, fnct, fnct, fnct, fnct,fnct,fnct,fnct,fnct},//7
                {erro, erro, erro, erro, erro, erro, assi, erro, erro, erro, erro, erro, erro, erro, erro,erro,erro,erro,erro},//8
                {writ, writ, writ, writ, writ, writ, writ, writ, writ, writ, fnst, writ, writ, writ, writ,writ,writ,writ,erro},//9
        };

        public int getToken() throws IOException {
                int tokenId = 0;
                int estadoActual = 0;
                int caracterActual;
                int caracterValue;
                AccionSemantica as;
                while (estadoActual != -1) {
                        codigoFuente.mark(1);
                        caracterActual = codigoFuente.read();
                        caracterValue = decode(caracterActual);
                        as = matrizAS[estadoActual][caracterValue];
                        tokenId = as.ejecutar(codigoFuente, (Lex) this, caracterActual, tablaSimbolos);
                        estadoActual = matrizestados[estadoActual][caracterValue];
                }
                return tokenId;
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
                        value = 18;
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
                                        value=12;
                                        break;
                                case ')':
                                        value=13;
                                        break;
                                case ',':
                                        value=14;
                                        break;
                                case ';':
                                        value=15;
                                        break;
                                case ' ':
                                case '\n':
                                        // case 'tab':
                                        value = 17;
                                        break;
                                default:
                                        value = 16;
                                        break;

                        }
                return value;
        }

}