import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class finishConsNL extends AccionSemantica {

        @Override
        public Pointer ejecutar(BufferedReader codigoFuente, Lex lex, int caracterActual,
                        HashMap<String, Simbolo> tablaSimbolos, HashMap<String, Integer> tablaPRes) throws IOException {
                        lex.line++;
                try {
                        Simbolo symbol = tablaSimbolos.get(lex.getCadena());
                        if (symbol == null)
                                tablaSimbolos.put(lex.getCadena(),
                                                new Simbolo(Integer.parseInt(lex.getCadena()) > 65535 ? "longint"
                                                                : "uinteger", "Constante"));
                        codigoFuente.reset();
                        lex.yylval=lex.getCadena();
                        if (Integer.parseInt(lex.getCadena()) > 65535)
                                return new Pointer(273);
                        else
                                return new Pointer(272);

                } catch (NumberFormatException e) {
                        System.out.println("ERROR on line "+lex.line+": El valor ingresado no se encuentra dentro del rango aceptado");
                        return new Pointer(-1);
                }
        }

}
