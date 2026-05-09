package src.main.model;

public class Cortina extends Dispositivo {
    private static final long serialVersionUID = 1L;
    private int nivel_abertura;

    public Cortina(int id, String marca, String modelo, double consumo, int nivel) {
        super(id, marca, modelo, consumo);
        this.nivel_abertura = nivel;
    }

    public Cortina() { super(); this.nivel_abertura = 0; }

    public Cortina(Cortina c) {
        super(c);
        this.nivel_abertura = c.nivel_abertura;
    }

    @Override
    public void desligarDispositivo() {
        super.desligarDispositivo();
        this.nivel_abertura = 0; // Fecha a cortina
    }

    @Override
    public void ligarDispositivo() {
        super.ligarDispositivo();
        this.nivel_abertura = 100; // Abre a cortina
    }

    @Override
    public String getEstado() {
        if (this.nivel_abertura == 0) return "FECHADA";
        return "ABERTA (" + this.nivel_abertura + "%)";
    }

    @Override
    public Cortina clone() { return new Cortina(this); }
    @Override
    public String getTipo() { return "Cortina"; }
    public int getNivelAbertura() { return nivel_abertura; }
    public void setNivelAbertura(int n) { this.nivel_abertura = Math.max(0, Math.min(100, n)); }
}