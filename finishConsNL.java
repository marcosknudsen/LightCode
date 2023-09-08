import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

public class FinishConsNL extends AccionSemantica {

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

                        if (Integer.parseInt(lex.getCadena()) > 65535)
                                return new Pointer(92);
                        else
                                return new Pointer(91);

                } catch (NumberFormatException e) {
                        System.out.println("Error: El valor ingresado no se encuentra dentro del rango aceptado");
                        return new Pointer(-1);
                }
        }

}
