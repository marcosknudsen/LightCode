import java.io.IOException;

public class TP {
    public static void main(String[] args) throws IOException {
        Lex lex = new Lex(args[0]);
        int Token;
        do{
            Token=lex.getToken();
            System.out.println(Token);
        }while(Token!=-2);
    }
}