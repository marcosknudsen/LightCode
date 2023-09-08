import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class warningNL extends AccionSemantica {

    @Override
    public Pointer ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos, HashMap<String, Integer> tablaPRes) throws IOException {
        lex.line++;
        System.out.println("Error in line:"+ lex.line +"= expected");
        return new Pointer(-1);
    }
    
}
