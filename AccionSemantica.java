import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;

public abstract class AccionSemantica {

    public abstract int ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            Hashtable<String, Integer> tablaSimbolos) throws IOException;
}