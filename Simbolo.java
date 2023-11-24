public class Simbolo {
    public String tipo;
    public String uso;

    public Simbolo(String tipo,String uso){
        this.tipo=tipo;
        this.uso=uso;
    }

    public String toString(){
        return this.tipo+" "+this.uso;
    }
}
