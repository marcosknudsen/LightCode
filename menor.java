import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class menor extends AccionSemantica {

    @Override
    public int ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos) throws IOException {
        codigoFuente.reset();

        return 25;
    }

}
