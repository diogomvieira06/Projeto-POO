public class Lampada extends Dispositivo {
    private int intesidade_Luminosidade;
    private String cor_Luz;

    public Lampada(int id, String marca, String modelo, double consumo_Por_Hora_Wh, int intesidade_Luminosidade, String cor_Luz) {
        super(id, marca, modelo, consumo_Por_Hora_Wh);
        this.intesidade_Luminosidade = intesidade_Luminosidade;
        this.cor_Luz = cor_Luz;
    }

    public Lampada() {
        super();
        this.intesidade_Luminosidade = 0;
        this.cor_Luz = "Branco"; // Cor padrão da luz
    }

    public Lampada(Lampada l) {
        super(l); // Chama o construtor de cópia da classe base
        this.intesidade_Luminosidade = l.intesidade_Luminosidade;
        this.cor_Luz = l.cor_Luz;
    }

    public int getIntesidade_Luminosidade() {
        return intesidade_Luminosidade;
    }
    public void setIntesidade_Luminosidade(int intesidade_Luminosidade) {
        if (intesidade_Luminosidade < 0) {
            this.intesidade_Luminosidade = 0; // Define a intensidade mínima como 0
        } else if (intesidade_Luminosidade > 100) {
            this.intesidade_Luminosidade = 100; // Define a intensidade máxima como 100
        } else {
            this.intesidade_Luminosidade = intesidade_Luminosidade;
        }
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
}