import java.io.BufferedReader;
import java.io.IOException;
import java.util.Hashtable;

public class StartString extends AccionSemantica{

    @Override
    public int ejecutar(BufferedReader codigoFuente,Lex lex, int caracterActual,
            Hashtable<String, Integer> tablaSimbolos) throws IOException {
                lex.setCadena(" ");
        return -5;
    }
    
}
