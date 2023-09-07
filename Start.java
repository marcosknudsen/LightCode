import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class Start extends AccionSemantica{

    @Override
    public Pointer ejecutar(BufferedReader codigoFuente,Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos, HashMap<String,Integer> tablaPRes) throws IOException {
                lex.setCadena(String.valueOf((char)caracterActual));
        return new Pointer(-1);
    }
    
}
