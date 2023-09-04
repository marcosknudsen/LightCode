import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class Start extends AccionSemantica{

    @Override
    public int ejecutar(BufferedReader codigoFuente,Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos) throws IOException {
                lex.setCadena(" ");
        return 33;
    }
    
}
