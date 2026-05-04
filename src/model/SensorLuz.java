package src.model;

public class SensorLuz extends Dispositivo {
    private static final long serialVersionUID = 1L;

    private double nivelLuz; // lux
    private double limiarNoite;//para considerar q e noite

    public SensorLuz() {
        super();
        this.nivelLuz = 0.0;
        this.limiarNoite = 30.0; // Exemplo de limiar para considerar que é noite
    }

    public SensorLuz(int id, String marca, String modelo, double consumo, double nivelLuz) {
        super(id, marca, modelo, consumo); 
        this.nivelLuz = nivelLuz;
        this.limiarNoite = 30.0; // Exemplo de limiar para considerar que é noite
    }

    public SensorLuz(SensorLuz s) {
        super(s);
        this.nivelLuz = s.nivelLuz;
        this.limiarNoite = s.limiarNoite;
    }

    //getters
    public double getNivelLuz() {
        return nivelLuz;
    }

    public double getLimiarNoite() {
        return limiarNoite;
    }

    //setters
    public void setLimiarNoite(double l) {
        this.limiarNoite = l;
    }

    public void setNivelLuz(double nivelLuz) {
        this.nivelLuz = nivelLuz;
    }

    public void setLimiarNoite(int limiarNoite) {
        this.limiarNoite = limiarNoite;
    }

    @Override
    public String getTipo(){
        return "SensorLuz";
    }

    @Override
    public SensorLuz clone(){
        return new SensorLuz(this);
    }

    @Override
    public String getDetalhesEspecificos(){
        return " | Luz: " + this.nivelLuz + " lux (limiar: " + this.limiarNoite + ")";
    }

    //metodos
    public boolean isLuminosidadeBaixa(){
        return this.nivelLuz < this.limiarNoite;
    }
    
}
