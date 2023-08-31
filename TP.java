import java.io.IOException;

public class TP {
    public static void main(String[] args) throws IOException {
        Lex lex = new Lex(args[0]);
        System.out.println(lex.getToken());
    }
}