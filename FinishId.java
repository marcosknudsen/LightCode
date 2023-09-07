import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class FinishId extends AccionSemantica {

    @Override
    public Pointer ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos, HashMap<String,Integer> tablaPRes) throws IOException {
        Simbolo value = tablaSimbolos.get(lex.getCadena());
        boolean isExists = tablaPRes.containsKey(lex.getCadena());
        int Token;
        lex.setCadena(lex.getCadena() + String.valueOf((char) caracterActual));
        if (!isExists) {//Si no existe lo creo
            value = new Simbolo("String", "Var");
            tablaSimbolos.put(lex.getCadena(), value);
        }
        if (value.uso=="Var")
            Token = 50;//Identificador
        else //Palabra Reservada
            Token=tablaPRes.get(lex.getCadena());   
             
    return new Pointer(Token, lex.getCadena());
    }

}
