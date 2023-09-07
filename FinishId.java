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
        if (!isExists && value == null ) {
            value = new Simbolo("String", "Var");
            tablaSimbolos.put(lex.getCadena(), value);
        }
        if (value.uso=="Var")
            Token = 50;//Identificador
        else{ //Palabra Reservada
             
             if (!isExists)
                return null;
            else{
                //Integer valorPr = tablaPRes.obtenerElementoPorClave(lex.getCadena()); //
                Token=100; //puse 100 para q no de error, iria Token=valorPr
            }      
        }   
             
    return new Pointer(Token, lex.getCadena());
    }

}
