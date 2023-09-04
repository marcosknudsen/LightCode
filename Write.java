import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class Write extends AccionSemantica {

    @Override
    public int ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos) throws IOException {
        lex.setCadena(lex.getCadena() + String.valueOf((char) caracterActual));
        return -1;
    }

}
