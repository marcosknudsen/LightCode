import java.util.ArrayList;

public class Terceto {
    String operando;
    ParserVal a,b;
    ArrayList<String> errores;

    public Terceto(String operando,ParserVal a,ParserVal b){
        this.operando=operando;
        this.a=a;
        this.b=b;
        this.errores=new ArrayList<String>();
    }

    public Terceto(String operando,ParserVal a,ParserVal b,ArrayList<String> errores){
        this.operando=operando;
        this.a=a;
        this.b=b;
        this.errores=errores;
    }

    public String showable(ParserVal a){
        if (a.sval==null){
            return String.valueOf(a.ival);
        }
        return a.sval;
    }

    @Override
    public String toString() {

        return this.operando+" "+showable(this.a)+" "+showable(this.b)+" "+String.valueOf(this.errores.size()>0);
    }
}
