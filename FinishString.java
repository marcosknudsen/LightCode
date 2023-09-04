import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class FinishString extends AccionSemantica {

    @Override
    public int ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos) throws IOException {
        Simbolo value = tablaSimbolos.get(lex.getCadena());
        if (value==null){
            tablaSimbolos.put(lex.getCadena(), new Simbolo("String", "String"));
        }
        return 40;
    }

}
