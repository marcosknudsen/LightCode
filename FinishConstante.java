import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class FinishConstante extends AccionSemantica{

    @Override
    public int ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos) throws IOException {
<<<<<<< HEAD
        tablaSimbolos.get(lex.getCadena());
        //
        return 18;
=======
        Simbolo value= tablaSimbolos.get(lex.getCadena());
        if (value==null)
                tablaSimbolos.put(lex.getCadena(),new Simbolo("int", "Constante"));
        return 40;
>>>>>>> 6c2d4169d12c13aa3e751656c723eb107da121f0
    }

}
