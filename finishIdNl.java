import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class finishIdNl extends AccionSemantica {

    @Override
    public Pointer ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
        HashMap<String, Simbolo> tablaSimbolos, HashMap<String, Integer> tablaPRes) throws IOException {
        String cad = lex.getCadena();
        if (cad.length()>25){// recorto cad a 25 c, 
            cad = cad.substring(0, 25);
            System.out.println("The string is shortened to 25 characters");
        }
        Simbolo value = tablaSimbolos.get(cad);
        int Token=tablaPRes.getOrDefault(cad,-1);
        lex.yylval=lex.getCadena();
        if (Token==-1) {// Si no es PR reescribe el token
            if (value == null) {
                value = new Simbolo("String", "Var");
                tablaSimbolos.put(cad, value);
            }
            Token=259;
        }
        if (caracterActual=='\n')
            lex.line+=1;
        return new Pointer(Token, cad);
    }

}

