import java.io.IOException;

public class TP {
    public static void main(String[] args) throws IOException {
        Lex lex = new Lex("./a.txt");
        int Token=0;
        Token=lex.getToken();
        while (Token!=-1){
            System.out.println(Token);
            Token=lex.getToken();
        };
    }
}