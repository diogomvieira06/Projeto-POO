package src.model;


public class PortaoGaragem extends Dispositivo {
    private int nivel_abertura;

    public PortaoGaragem(int id, String marca, String modelo, double consumo_Por_Hora_Wh, int nivel_abertura) {
        super(id, marca, modelo, consumo_Por_Hora_Wh);
        this.nivel_abertura = nivel_abertura;
    }

    public PortaoGaragem() {
        super();
        this.nivel_abertura = 0;
    }

    public PortaoGaragem(PortaoGaragem p) {
        super(p); // Chama o construtor de cópia da classe base
        this.nivel_abertura = p.nivel_abertura;
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
        return "PortaoGaragem";
    }

    @Override
    public PortaoGaragem clone() {
        return new PortaoGaragem(this);
    }
}