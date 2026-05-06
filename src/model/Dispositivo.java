package src.model;
import java.io.Serializable;


public abstract class Dispositivo implements Serializable { // Abstract porque não vamos instanciar objetos diretamente dessa classe, mas sim de suas subclasses (Lampada, Curtinas, etc...)
    private static final long serialVersionUID = 1L; // Para serializar o objeto, criado o ID de versao e garantir compatibilidade

    private int id;
    private String marca;
    private String modelo;
    private double consumo_Por_Hora_Wh;
    private enum Estado {
        LIGADO,
        DESLIGADO
    } // Escolhi enum para representar Ligado Desligado na base de um dispositivo,
    // achei que era overkill estar a criar uma classe para estado.
    private Estado estado;
    private int numAtivacoes = 0;
    private double tempoUsoHoras = 0;

    public Dispositivo(int id, String marca, String modelo, double consumo_Por_Hora_Wh) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.consumo_Por_Hora_Wh = consumo_Por_Hora_Wh;
        this.estado = Estado.DESLIGADO; // Inicialmente o dispositivo está desligado
    }
    public Dispositivo() {
        this.id = 0;
        this.marca = "";
        this.modelo = "";
        this.consumo_Por_Hora_Wh = 0.0;
        this.estado = Estado.DESLIGADO; // Inicialmente o dispositivo está desligado
    }
    public Dispositivo(Dispositivo d) {
        this.id = d.id;
        this.marca = d.marca;
        this.modelo = d.modelo;
        this.consumo_Por_Hora_Wh = d.consumo_Por_Hora_Wh;
        this.estado = d.estado; // Copia o estado do dispositivo original
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }
    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public double getConsumo_Por_Hora_Wh() {
        return consumo_Por_Hora_Wh;
    }
    public void setConsumo_Por_Hora_Wh(double consumo_Por_Hora_Wh) {
        this.consumo_Por_Hora_Wh = consumo_Por_Hora_Wh;
    }

    public String getEstado() {
        return estado.name(); // Retorna o nome do estado (LIGADO ou DESLIGADO)
    }
    public void ligarDispositivo() {
        if (this.estado != Estado.LIGADO) {
            this.numAtivacoes++; // Incrementa na ativação
            this.estado = Estado.LIGADO;
        }
    }
    public void desligarDispositivo() {
        this.estado = Estado.DESLIGADO;
    }

    public abstract Dispositivo clone(); // Método abstrato para clonar o dispositivo, cada subclasse vai implementar esse método para retornar uma cópia do seu tipo específico

    public abstract String getTipo(); // Método abstrato para obter o tipo do dispositivo (Lampada, Tomada, etc...)
    // Cada subclasse vai implementar esse método para retornar seu tipo específico

    //Para conseguir imprimir todos os atributos na classe DomusControl
    public String getDetalhesEspecificos(){
        return "";
    }

    public boolean mostrarEstadoBase() { return true; }
    public int getNumAtivacoes() { return numAtivacoes; }
    public double getTempoUsoHoras() { return tempoUsoHoras; }

    // Método para simular passagem de tempo (útil para testes)
    public void adicionarTempoUso(double horas) {
        if (this.getEstado().equals("LIGADO")) this.tempoUsoHoras += horas;
    }

    //metodo equals
    @Override
    public boolean equals(Object o){
        if(this == o)return true;
        if(o == null || this.getClass() != o.getClass()) return false;
        Dispositivo d = (Dispositivo) o;
        return this.id == d.id &&
                this.marca.equals(d.marca) &&
                this.modelo.equals(d.modelo) &&
                this.consumo_Por_Hora_Wh == d.consumo_Por_Hora_Wh &&
                this.estado == d.estado &&
                this.numAtivacoes == d.numAtivacoes &&
                this.tempoUsoHoras == d.tempoUsoHoras;
    }

    // Método hashCode para garantir que objetos iguais tenham o mesmo hash code, baseado no ID do dispositivo
    //retorna um integer que representa o hash code do objeto, nesse caso usando o ID do dispositivo para gerar o hash code, garantindo que dispositivos com o mesmo ID tenham o mesmo hash code    
    @Override
    public int hashCode() {
        return Integer.hashCode(this.id);
    }

}