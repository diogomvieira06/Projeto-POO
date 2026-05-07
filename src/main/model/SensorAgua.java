package src.main.model;

public class SensorAgua extends Dispositivo {
    private static final long serialVersionUID = 1L;

    //private double nivelAgua;// litros
    //private double nivelAguaAnterior;
    //private double nivelAguaMaximo;//quando exceder esse valor, 
    private boolean emChuva;


    public SensorAgua() {
        super();
        //this.nivelAgua = 0.0;
        //this.nivelAguaAnterior = 0.0;
        //this.nivelAguaMaximo = 100.0; // Exemplo de valor máximo
        this.emChuva = false;
    }

    public SensorAgua(int id, String marca, String modelo, double consumo) {
        this(id, marca, modelo, consumo, 0, false);
    }

    public SensorAgua(int id, String marca, String modelo, double consumo, double nivelAgua, boolean emChuva) {
        super(id, marca, modelo, consumo);
        //this.nivelAgua = nivelAgua;
        //this.nivelAguaAnterior = 0.0; // Inicialmente, o nível anterior é 0
        //this.nivelAguaMaximo = 100.0; // Exemplo de valor máximo
        this.emChuva = emChuva;
    }

    public SensorAgua(SensorAgua s) {
        super(s);
        //this.nivelAgua = s.nivelAgua;
        //this.nivelAguaAnterior = s.nivelAguaAnterior;
        //this.nivelAguaMaximo = s.nivelAguaMaximo;
        this.emChuva = s.emChuva;
    }

    //getters
    //public double getNivelAgua() { return nivelAgua; }

    // public double getNivelAguaAnterior() { return nivelAguaAnterior; }

    public boolean isEmChuva() {
        return emChuva;
    }

    //setters
    //public void setNivelAgua(double n){
        //this.nivelAguaAnterior = this.nivelAgua; // Atualiza o nível anterior antes de definir o novo nível
        // this.nivelAgua = n;}

    public void setEmChuva(boolean emChuva) {
        this.emChuva = emChuva;
    }


    //metodos
    public boolean estaAChover(){
        return this.emChuva;
    }

    @Override
    public String getTipo(){
        return "SensorAgua";
    }

    @Override
    public SensorAgua clone(){
        return new SensorAgua(this);
    }

    @Override
    public String getDetalhesEspecificos(){
        return " | Chuva: " + (emChuva ? "Sim": "Não");
    }
}
