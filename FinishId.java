import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

public class FinishId extends AccionSemantica {

    @Override
    public Pointer ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
        HashMap<String, Simbolo> tablaSimbolos, HashMap<String, Integer> tablaPRes) throws IOException {
        lex.setCadena(lex.getCadena() + String.valueOf((char) caracterActual));
        String cad = lex.getCadena();
        if (cad.length()>25){// recorto cad a 25 c, 
            cad = cad.substring(0, 25);
            System.out.println("The string is shortened to 25 characters");
        }
        Simbolo value = tablaSimbolos.get(cad);
        boolean isExists = tablaPRes.containsKey(cad);
        int Token=tablaPRes.get(cad);
        
        if (!isExists) {// Si no es PR reescribe el token
            if (value == null) {
                value = new Simbolo("String", "Var");
                tablaSimbolos.put(cad, value);
            }
            Token=50;
        }

        return new Pointer(Token, cad);
    }

}
