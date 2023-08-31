import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;

public class mayor extends AccionSemantica {

    @Override
    public int ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            Hashtable<String, Integer> tablaSimbolos) throws IOException {
        codigoFuente.reset();
        return 30;
    }
}
