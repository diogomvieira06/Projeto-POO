package src.model;


public class Curtina extends Dispositivo {
    private int nivel_abertura;// 0 a 100, onde 0 é completamente fechada e 100 é completamente aberta

    public Curtina(int id, String marca, String modelo, double consumo_Por_Hora_Wh, int nivel_abertura) {
        super(id, marca, modelo, consumo_Por_Hora_Wh);
        this.nivel_abertura = nivel_abertura;
    }

    public Curtina() {
        super();
        this.nivel_abertura = 0;
    }

    public Curtina(Curtina c) {
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
        return "Curtina";
    }
    
    @Override
    public Curtina clone() {
        return new Curtina(this);
    }
}