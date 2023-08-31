import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

public class Lex {
        Hashtable<String, Integer> tablaSimbolos = new Hashtable<>();
        private BufferedReader codigoFuente = null;
        private String cadena;

        public Lex(String filename) throws FileNotFoundException {
                this.codigoFuente = new BufferedReader(
                                new FileReader(filename));
        }

        AccionSemantica non = new nu();
        AccionSemantica str = new StartString();// comienza la lectura de un string
        AccionSemantica wri = new WriteString();// continua la lectura de un string
        AccionSemantica fns = new FinishString();// finaliza la lectura de un string
        AccionSemantica may = new mayor();

        // L,D,/,*,+,-,=,<,>,:,",@,otro,bl/tab/nl,$(eof)
        int matrizestados[][] = {
                        { 1, 7, -1, 2, -1, -1, -1, 6, 5, 8, 9, 1, -1, 0, -1 }, // 0
                        { 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 1
                        { -1, -1, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 2
                        { 3, 3, 4, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 }, // 3
                        { 3, 3, 4, 0, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 }, // 4
                        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 5
                        { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 6
                        { -1, 7, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1 }, // 7
                        { 0, 0, 0, 0, 0, 0, -1, 0, 0, 0, 0, 0, 0, 0, 0 }, // 8
                        { 9, 9, 9, 9, 9, 9, 9, 9, 9, 9, -1, 9, 9, 9, 9 }// 9
        };

        AccionSemantica matrizAS[][] = {
                        { null, null, null, null, null, null, null, null, non, null, str, null, null, null, null }, // 0
                        { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null }, // 1
                        { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null }, // 2
                        { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null }, // 3
                        { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null }, // 4
                        { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null }, // 5
                        { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null }, // 6
                        { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null }, // 7
                        { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null }, // 8
                        { wri, wri, null, null, null, null, null, null, null, null, fns, null, wri, wri, null }// 9
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

        void setCadena(String c) {
                this.cadena = c;
        }

        String getCadena() {
                return this.cadena;
        }

        // L,D,/,*,+,-,=,<,>,:,",@,otro,bl/tab/nl,$(eof)
        private int decode(int caracterActual) {// A partir de un caracter devuelve su valor de matriz correspondiente
                int value;
                if (Character.isLetter((char) caracterActual)) {
                        value = 0;
                } else if (Character.isDigit((char) caracterActual)) {
                        value = 1;
                } else if (caracterActual == -1) {
                        value = 14;
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
                                case ' ':
                                case '\n':
                                        // case 'tab':
                                        value = 13;
                                        break;
                                default:
                                        value = 12;
                                        break;

                        }
                return value;
        }

}