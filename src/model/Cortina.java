package src.model;


public class Cortina extends Dispositivo {
    private static final long serialVersionUID = 1L;
    private int nivel_abertura;// 0 a 100, onde 0 é completamente fechada e 100 é completamente aberta

    public Cortina(int id, String marca, String modelo, double consumo_Por_Hora_Wh, int nivel_abertura) {
        super(id, marca, modelo, consumo_Por_Hora_Wh);
        this.nivel_abertura = nivel_abertura;
    }

    public Cortina() {
        super();
        this.nivel_abertura = 0;
    }

    public Cortina(Cortina c) {
        super(c); // Chama o construtor de cópia da classe base
        this.nivel_abertura = c.nivel_abertura;
    }

    public int getNivelAbertura() {
        return nivel_abertura;
    }
    public void setNivelAbertura(int nivel_abertura) {
        if(nivel_abertura < 0) {
            this.nivel_abertura = 0; // Define o nível mínimo de abertura como 0
        } else if (nivel_abertura > 100) {
            this.nivel_abertura = 100; // Define o nível máximo de abertura como 100
        } else {
            this.nivel_abertura = nivel_abertura;
        }
    }

    @Override
    public String getTipo() {
        return "Cortina";
    }

    @Override
    public Cortina clone() {
        return new Cortina(this);
    }

    @Override
    public String getEstado() {
        if (this.nivel_abertura == 0) return "FECHADA";
        if (this.nivel_abertura == 100) return "ABERTA";
        return "ABERTA " + this.nivel_abertura + "%";
    }

    @Override
    public boolean mostrarEstadoBase() { return true; }

    @Override
    public String getDetalhesEspecificos() { return ""; }

    @Override
    public void desligarDispositivo() {
        setNivelAbertura(0); // Fechar a cortina ao desligar
    }
    @Override
    public void ligarDispositivo() {
        setNivelAbertura(100); // Abrir a cortina ao ligar  
    }
}