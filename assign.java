import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class Assign extends AccionSemantica{

    @Override
    public Pointer ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos, HashMap<String,Integer> tablaPRes) throws IOException {
        return new Pointer(260);
    }    
}
