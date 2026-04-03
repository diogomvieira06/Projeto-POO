import java.util.ArrayList;

public class Divisao {
    private String nome;
    private int id;
    private ArrayList<Dispositivo> dispositivos;

    public Divisao(String nome, int id) {
        this.nome = nome;
        this.id = id;
        this.dispositivos = new ArrayList<>();
    }
    public Divisao(Divisao d) {
        this.nome = d.nome;
        this.id = d.id;
        this.dispositivos = new ArrayList<>();
        for (Dispositivo disp : d.dispositivos) {
            this.dispositivos.add(disp.clone());
        }
    }
    public Divisao() {
        this.nome = "";
        this.id = 0;
        this.dispositivos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Dispositivo> getDispositivos() {
        return dispositivos;
    }
    public void setDispositivos(ArrayList<Dispositivo> dispositivos) {
        this.dispositivos = dispositivos;
    }

    public void adicionarDispositivo(Dispositivo d) {
        this.dispositivos.add(d);
    }
    public void removerDispositivo(Dispositivo d) {
        this.dispositivos.remove(d);
    }

    public void listarDispositivos() {
        for (Dispositivo d : dispositivos) {
            System.out.println(d.getTipo() + " - " + d.getMarca() + " " + d.getModelo());
        }
    }

}