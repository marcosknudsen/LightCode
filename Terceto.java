public class Terceto {
    String operando;
    ParserVal a,b;

    public Terceto(String operando,ParserVal a,ParserVal b){
        this.operando=operando;
        this.a=a;
        this.b=b;
    }

    public String showable(ParserVal a){
        if (a.sval==null){
            return String.valueOf(a.ival);
        }
        return a.sval;
    }

    @Override
    public String toString() {

        return this.operando+" "+showable(this.a)+" "+showable(this.b);
    }
}
