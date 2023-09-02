import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class FinishConstante extends AccionSemantica{

    @Override
    public int ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos) throws IOException {
        tablaSimbolos.get(lex.getCadena());
        //
        return 40;
    }

}