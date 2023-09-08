import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class FinishConstante extends AccionSemantica {

    @Override
    public Pointer ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos, HashMap<String, Integer> tablaPRes) throws IOException {
        Simbolo symbol = tablaSimbolos.get(lex.getCadena());
        if (symbol == null)
            tablaSimbolos.put(lex.getCadena(),
                    new Simbolo(Integer.parseInt(lex.getCadena()) > 65535 ? "longint" : "uinteger", "Constante"));
        codigoFuente.reset();
        lex.line++;
        return new Pointer(55, lex.getCadena());
    }
}
