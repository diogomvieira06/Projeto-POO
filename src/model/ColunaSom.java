package src.model;


public class ColunaSom extends Dispositivo {
    private static final long serialVersionUID = 1L;
    private int intensidade_Volume;

    public ColunaSom(int id, String marca, String modelo, double consumo_Por_Hora_Wh, int intensidade_Volume) {
        super(id, marca, modelo, consumo_Por_Hora_Wh);
        this.intensidade_Volume = intensidade_Volume;
    }

    public ColunaSom() {
        super();
        this.intensidade_Volume = 0;
    }

    public ColunaSom(ColunaSom c) {
        super(c); // Chama o construtor de cópia da classe base
        this.intensidade_Volume = c.intensidade_Volume;
    }

    public int getIntensidadeVolume() {
        return intensidade_Volume;
    }
    public void setIntensidadeVolume(int intensidade_Volume) {
        if (intensidade_Volume < 0) {
            this.intensidade_Volume = 0; // Define a intensidade mínima como 0
        } else if (intensidade_Volume > 100) {
            this.intensidade_Volume = 100; // Define a intensidade máxima como 100
        } else {
            this.intensidade_Volume = intensidade_Volume;
        }
    }

    @Override
    public String getTipo() {
        return "ColunaSom";
    }

    @Override
    public ColunaSom clone() {
        return new ColunaSom(this);
    }
}