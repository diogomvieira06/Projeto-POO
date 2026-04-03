public class Casa {
    private String endereco;
    private int id;
    private ArrayList<Divisao> divisoes;

    public Casa(String endereco, int id) {
        this.endereco = endereco;
        this.id = id;
        this.divisoes = new ArrayList<>();
    }
    public Casa(Casa c) {
        this.endereco = c.endereco;
        this.id = c.id;
        this.divisoes = new ArrayList<>();
        for (Divisao div : c.divisoes) {
            this.divisoes.add(new Divisao(div));
        }
    }
    public Casa() {
        this.endereco = "";
        this.id = 0;
        this.divisoes = new ArrayList<>();
    }

    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
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