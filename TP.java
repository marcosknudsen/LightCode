import java.io.IOException;
import java.util.HashMap;

public class TP {
    public static void main(String[] args) throws IOException {
        Lex lex = new Lex(args[0]);
        int Token=0;
        HashMap<Integer,String> tokens = new HashMap<>();
        tokens.put(10,":=");
        tokens.put(16,"<>");
        tokens.put(61, "=");
        tokens.put(62, ">");
        tokens.put(24, ">=");
        tokens.put(60, "<");
        tokens.put(26, "<=");
        tokens.put(45, "-");
        tokens.put(47, "/");
        tokens.put(42, "*");
        tokens.put(43, "+");
        tokens.put(59, ";");
        tokens.put(40, "(");
        tokens.put(41, ")");
        tokens.put(50, "IDENTIFICADOR");
        tokens.put(51, "PALABRA RESERVADA");
        tokens.put(54, "CADENA");
        tokens.put(80, "if");
        tokens.put(81, "then");
        tokens.put(82, "else");
        tokens.put(83, "begin");
        tokens.put(84, "end");
        tokens.put(85, "end_id");
        tokens.put(86,"print");
        tokens.put(87, "while");
        tokens.put(88, "do");
        tokens.put(89, "fun");
        tokens.put(90, "return");
        tokens.put(91, "uinteger");
        tokens.put(92, "longint");
        Token=lex.getToken();
        while (Token!=-1){
            System.out.println(Token+": "+tokens.get(Token));
            Token=lex.getToken();
        };

        for (String name: lex.tablaSimbolos.keySet()) {
            String key = name.toString();
            String value = lex.tablaSimbolos.get(name).tipo.toString()+" "+lex.tablaSimbolos.get(name).uso.toString();
            System.out.println(key + " " + value);
        }
    }
}