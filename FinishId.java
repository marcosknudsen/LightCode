import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class FinishId extends AccionSemantica {

    @Override
    public int ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos) throws IOException {
        Simbolo value = tablaSimbolos.get(lex.getCadena());
        int Token;
        lex.setCadena(lex.getCadena() + String.valueOf((char) caracterActual));
        if (value == null) {
            tablaSimbolos.put(lex.getCadena(), new Simbolo("String", "Var"));
            Token = 45;
        } else {
            if (value.uso == "Var")
                Token = 45;
            else
                Token = 46;
        }
        return Token;
    }

}
