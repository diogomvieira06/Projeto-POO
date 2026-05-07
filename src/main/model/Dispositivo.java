package src.main.model;
import java.io.Serializable;

public abstract class Dispositivo implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String marca;
    private String modelo;
    private double consumo_Por_Hora_Wh;
    private enum Estado { LIGADO, DESLIGADO }
    private Estado estado;
    private int numAtivacoes = 0;
    private double tempoUsoHoras = 0;

    public Dispositivo(int id, String marca, String modelo, double consumo_Por_Hora_Wh) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.consumo_Por_Hora_Wh = consumo_Por_Hora_Wh;
        this.estado = Estado.DESLIGADO;
    }

    public Dispositivo() {
        this.id = 0;
        this.marca = "";
        this.modelo = "";
        this.consumo_Por_Hora_Wh = 0.0;
        this.estado = Estado.DESLIGADO;
    }

    public Dispositivo(Dispositivo d) {
        this.id = d.id;
        this.marca = d.marca;
        this.modelo = d.modelo;
        this.consumo_Por_Hora_Wh = d.consumo_Por_Hora_Wh;
        this.estado = d.estado;
        this.numAtivacoes = d.numAtivacoes;
        this.tempoUsoHoras = d.tempoUsoHoras;
    }

    public boolean isLigado() {
        return this.estado == Estado.LIGADO;
    }

    public void ligarDispositivo() {
        if (this.estado != Estado.LIGADO) {
            this.numAtivacoes++;
            this.estado = Estado.LIGADO;
        }
    }

    public void desligarDispositivo() {
        this.estado = Estado.DESLIGADO;
    }

    public void adicionarTempoUso(double horas) {
        if (this.isLigado()) this.tempoUsoHoras += horas;
    }

    public String getEstado() {
        return estado.name();
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public double getConsumo_Por_Hora_Wh() { return consumo_Por_Hora_Wh; }
    public void setConsumo_Por_Hora_Wh(double consumo) { this.consumo_Por_Hora_Wh = consumo; }

    public int getNumAtivacoes() { return numAtivacoes; }
    public double getTempoUsoHoras() { return tempoUsoHoras; }

    public abstract Dispositivo clone();
    public abstract String getTipo();
    public String getDetalhesEspecificos() { return ""; }
    public boolean mostrarEstadoBase() { return true; }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        Dispositivo d = (Dispositivo) o;
        return this.id == d.id;
    }

    @Override
    public int hashCode() { return Integer.hashCode(this.id); }
}