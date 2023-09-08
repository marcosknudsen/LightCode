import java.io.IOException;

public class TP {
    public static void main(String[] args) throws IOException {
        Lex lex = new Lex("./a.txt");
        int Token;
        for (int i = 0; i <7;i++){
            Token=lex.getToken();
            System.out.println(Token);
        }
    }
}