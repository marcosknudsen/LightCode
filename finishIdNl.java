import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class finishIdNl extends AccionSemantica{

    @Override
    public Pointer ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos, HashMap<String, Integer> tablaPRes) throws IOException {
        Simbolo value = tablaSimbolos.get(lex.getCadena());
        boolean isExists = tablaPRes.containsKey(lex.getCadena());
        int Token=tablaPRes.get(lex.getCadena());
        lex.setCadena(lex.getCadena() + String.valueOf((char) caracterActual));
        if (!isExists) {// Si no es PR reescribe el token
            if (value == null) {
                value = new Simbolo("String", "Var");
                tablaSimbolos.put(lex.getCadena(), value);
            }
            Token=50;
        }
        lex.line++;
        return new Pointer(Token, lex.getCadena());
    }

    
}
