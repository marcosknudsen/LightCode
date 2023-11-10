public class Terceto {
    String operando;
    ParserVal a,b;

    public Terceto(String operando,ParserVal a,ParserVal b){
        this.operando=operando;
        this.a=a;
        this.b=b;
    }

    @Override
    public String toString() {
        return this.operando+" "+this.a+" "+this.b;
    }
}
