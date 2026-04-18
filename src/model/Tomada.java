package src.model;


public class Tomada extends Dispositivo {

    public Tomada(int id, String marca, String modelo, double consumo_Por_Hora_Wh) {
        super(id, marca, modelo, consumo_Por_Hora_Wh);
    }

    public Tomada() {
        super();
    }

    public Tomada(Tomada t) {
        super(t); // Chama o construtor de cópia da classe base
    }


    @Override
    public String getTipo() {
        return "Tomada";
    }
    
    @Override
    public Tomada clone() {
        return new Tomada(this);
    }
}