package src.main.model;


public class Lampada extends Dispositivo {
    private static final long serialVersionUID = 1L;
    private String cor_Luz;

    public Lampada(int id, String marca, String modelo, double consumo_Por_Hora_Wh, String cor_Luz) {
        super(id, marca, modelo, consumo_Por_Hora_Wh);
        this.cor_Luz = cor_Luz;
    }

    public Lampada() {
        super();
        this.cor_Luz = "Branco"; // Cor padrão da luz
    }

    public Lampada(Lampada l) {
        super(l); // Chama o construtor de cópia da classe base
        this.cor_Luz = l.cor_Luz;
    }

    public String getCor_Luz() {
        return cor_Luz;
    }
    public void setCor_Luz(String cor_Luz) {
        this.cor_Luz = cor_Luz;
    }

    @Override
    public String getTipo() {
        return "Lampada";
    }

    @Override
    public Lampada clone() {
        return new Lampada(this);
    }

    @Override
    public String getDetalhesEspecificos(){
        if("LIGADO".equals(this.getEstado())) return " | Cor: " + this.cor_Luz;
        else return " | Cor: " + this.cor_Luz;
    }
}