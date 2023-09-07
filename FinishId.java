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
            value = new Simbolo("String", "Var");
            tablaSimbolos.put(lex.getCadena(), value);
        }
        if (value.uso=="Var")
            Token = 50;//Identificador
        else
            Token = 51;//Palabra reservada
        return new Pointer(Token, lex.getCadena());
    }

}
