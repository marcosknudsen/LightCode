import java.io.IOException;

public class TP {
    public static void main(String[] args) throws IOException {
        Lex lex = new Lex(args[0]);
        int Token=0;
        HashMap<Integer,String> tokens = new HashMap<>();
        tokens.put(10,":=");
        tokens.put(16,"<>");
        tokens.put(22, "=");
        tokens.put(23, ">");
        tokens.put(24, ">=");
        tokens.put(25, "<");
        tokens.put(26, "<=");
        tokens.put(32, "-");
        tokens.put(32, "/");
        tokens.put(33, "*");
        tokens.put(34, "+");
        tokens.put(45, ";");
        tokens.put(47, "(");
        tokens.put(48, ")");
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
            System.out.println(Token);
            Token=lex.getToken();
        };
    }
}