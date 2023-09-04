import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class Start extends AccionSemantica{

    @Override
    public int ejecutar(BufferedReader codigoFuente,Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos) throws IOException {
<<<<<<< HEAD
                lex.setCadena(" ");
        return 33;
=======
                lex.setCadena("");
        return -1;
>>>>>>> 6c2d4169d12c13aa3e751656c723eb107da121f0
    }
    
}
