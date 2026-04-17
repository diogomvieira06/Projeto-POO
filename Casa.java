import java.util.*;

public class Casa {
    private String alcunha; // Nome que o utilizador dá para diferenciar as casas
    private int id;
    //private ArrayList<Divisao> divisoes;
    private HashMap<Integer, Divisao> divisoes; // Mudar para HashMap para facilitar a procura por ID

    public Casa(String alcunha, int id) {
        this.alcunha = alcunha;
        this.id = id;
        this.divisoes = new HashMap<>();
    }
    public Casa(Casa c) {
        this.alcunha = c.alcunha;
        this.id = c.id;
        this.divisoes = new HashMap<>();
        for (Divisao div : c.divisoes.values()) {
            this.divisoes.put(div.getId(), new Divisao(div));
        }
    }
    public Casa() {
        this.alcunha = "";
        this.id = 0;
        this.divisoes = new HashMap<>();
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

    //retorna uma copia dos valores do HashMap para evitar que sejam alterados diretamente
    public HashMap<Integer, Divisao> getDivisoes() {
        return new HashMap<>(divisoes);//copia do HashMap para evitar que seja alterado diretamente
    }

    
    public void setDivisoes(HashMap<Integer, Divisao> divisoes) {
        this.divisoes = new HashMap<>(divisoes);
    }

    // Novo método para obter divisão por ID (muito mais rápido!)
    public Divisao obterDivisaoPorId(int idDivisao) {
        return divisoes.get(idDivisao);
    }

    public void adicionarDivisao(Divisao d) {
        this.divisoes.put(d.getId(), d);
    }
    public void removerDivisao(Divisao d) {
        this.divisoes.remove(d.getId());
    }

    public void listarDivisoes() {
        for (Divisao d : divisoes.values()) {
            System.out.println(d.getNome() + " - ID: " + d.getId());
        }
    }
}