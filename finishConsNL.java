import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class finishConsNL extends AccionSemantica{

    @Override
    public Pointer ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos, HashMap<String, Integer> tablaPRes) throws IOException {
        Simbolo value= tablaSimbolos.get(lex.getCadena());
        if (value==null)
            tablaSimbolos.put(lex.getCadena(),new Simbolo("int", "Constante"));    
        lex.line++;         
        return new Pointer(55,lex.getCadena());
    }
    
}
