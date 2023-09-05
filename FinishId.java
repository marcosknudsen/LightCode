import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class FinishId extends AccionSemantica {

    @Override
    public Pointer ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos) throws IOException {
        Simbolo value = tablaSimbolos.get(lex.getCadena());
        int Token;
        lex.setCadena(lex.getCadena() + String.valueOf((char) caracterActual));
        if (value == null) {
            tablaSimbolos.put(lex.getCadena(), new Simbolo("String", "Var"));
        }
        if (value.uso == "Var")
            Token = 45;//Identificador
        else
            Token = 46;//Palabra reservada
        return new Pointer(Token, lex.getCadena());
    }

}
