package src.main.model;


public class Tomada extends Dispositivo {
    private static final long serialVersionUID = 1L;

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

    @Override
    public String getDetalhesEspecificos(){
        if("LIGADO".equals(this.getEstado()))return " | Consumo por Hora: " + this.getConsumo_Por_Hora_Wh() + "Wh";
        else return "";
    }
}