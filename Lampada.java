public class Lampada extends Dispositivo {
    private int intensidade_Luminosidade;
    private String cor_Luz;

    public Lampada(int id, String marca, String modelo, double consumo_Por_Hora_Wh, int intesidade_Luminosidade, String cor_Luz) {
        super(id, marca, modelo, consumo_Por_Hora_Wh);
        this.intensidade_Luminosidade = intesidade_Luminosidade;
        this.cor_Luz = cor_Luz;
    }

    public Lampada() {
        super();
        this.intensidade_Luminosidade = 0;
        this.cor_Luz = "Branco"; // Cor padrão da luz
    }

    public Lampada(Lampada l) {
        super(l); // Chama o construtor de cópia da classe base
        this.intensidade_Luminosidade = l.intensidade_Luminosidade;
        this.cor_Luz = l.cor_Luz;
    }

    public int getIntesidade_Luminosidade() {
        return intensidade_Luminosidade;
    }
    public void setIntensidade_Luminosidade(int intensidade_Luminosidade) {
        if (intensidade_Luminosidade < 0) {
            this.intensidade_Luminosidade = 0; // Define a intensidade mínima como 0
        } else if (intensidade_Luminosidade > 100) {
            this.intensidade_Luminosidade = 100; // Define a intensidade máxima como 100
        } else {
            this.intensidade_Luminosidade = intensidade_Luminosidade;
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