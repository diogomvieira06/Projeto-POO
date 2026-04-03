import java.util.ArrayList;

public class Casa {
    private String alcunha; // Nome que o utilizador dá para diferenciar as casas
    private int id;
    private ArrayList<Divisao> divisoes;

    public Casa(String alcunha, int id) {
        this.alcunha = alcunha;
        this.id = id;
        this.divisoes = new ArrayList<>();
    }
    public Casa(Casa c) {
        this.alcunha = c.alcunha;
        this.id = c.id;
        this.divisoes = new ArrayList<>();
        for (Divisao div : c.divisoes) {
            this.divisoes.add(new Divisao(div));
        }
    }
    public Casa() {
        this.alcunha = "";
        this.id = 0;
        this.divisoes = new ArrayList<>();
    }

    public String getAlcunha() {
        return alcunha;
    }
    public void setAlcunha(String alcunha) {
        this.alcunha = alcunha;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Divisao> getDivisoes() {
        return divisoes;
    }
    public void setDivisoes(ArrayList<Divisao> divisoes) {
        this.divisoes = divisoes;
    }

    public void adicionarDivisao(Divisao d) {
        this.divisoes.add(d);
    }
    public void removerDivisao(Divisao d) {
        this.divisoes.remove(d);
    }

    public void listarDivisoes() {
        for (Divisao d : divisoes) {
            System.out.println(d.getNome() + " - ID: " + d.getId());
        }
    }
}