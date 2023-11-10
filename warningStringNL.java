import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class WarningStringNL extends AccionSemantica{

    @Override
    public Pointer ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
            HashMap<String, Simbolo> tablaSimbolos, HashMap<String,Integer> tablaPRes) throws IOException {
        Simbolo value = tablaSimbolos.get(lex.getCadena());
        if (value==null){
            tablaSimbolos.put(lex.getCadena(), new Simbolo("String", "String"));
        }
        if (caracterActual=='\n')
            lex.line+=1;
        lex.yylval=lex.getCadena();
        System.out.println("Warning: La cadena no fue cerrada correctamente: linea "+lex.line);
        return new Pointer(271,lex.getCadena());
    }

}
