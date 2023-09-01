import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public abstract class AccionSemantica {

    public abstract int ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos) throws IOException;
}