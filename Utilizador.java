import java.util.*;

public class Utilizador {
    private int id;
    private String nome;
    private HashMap<Integer, Casa> casasAdministradas;
    private HashMap<Integer, Casa> casasUtilizador;

    public Utilizador(int id, String nome){
        this.id = id;
        this.nome = nome;
        this.casasAdministradas = new HashMap<>();
        this.casasUtilizador = new HashMap<>();
    }

    public Utilizador(){
        this.id = 0;
        this.nome = "";
        this.casasAdministradas = new HashMap<>();
        this.casasUtilizador = new HashMap<>();
    }

    public Utilizador(Utilizador u){
        this.id = u.id;
        this.nome = u.nome;
        this.casasAdministradas = new HashMap<>(u.casasAdministradas);
        this.casasUtilizador = new HashMap<>(u.casasUtilizador);
    }

    //getters
    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public HashMap<Integer, Casa> getCasasAdmistradas(){
        return new HashMap<>(casasAdministradas);
    }

    public HashMap<Integer, Casa> getCasasUtilizador(){
        return new HashMap<>(casasUtilizador);
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setNome(String n){
        this.nome = n;
    }

    public void setCasasAdmistradas(HashMap<Integer, Casa> casasAdministradas){
        this.casasAdministradas = new HashMap<>(casasAdministradas);
    }

    public void setCasasUtilizador(HashMap<Integer, Casa> casasUtilizador){
        this.casasUtilizador = new HashMap<>(casasUtilizador);
    }



    //adicionar casa admistrada
    public void adicionarCasaAdministrada(Casa c){
        this.casasAdministradas.put(c.getId(), c);
        this.casasUtilizador.put(c.getId(), c); //um utilizador que é administrador de uma casa também é um utilizador dessa casa
    }

    //adicionar casa utilizador
    public void adicionarCasaUtilizador(Casa c){
        this.casasUtilizador.put(c.getId(), c);
    }

    //remover casa admistrada
    public void removerCasaAdmistrada(Casa c){
        this.casasAdministradas.remove(c.getId());
    }

    //remover casa Utilizador
    public void removerCasaUtilizador(Casa c){
        this.casasUtilizador.remove(c.getId());
    }

    //ver se um utilizador pode admistrar uma dada casa
    public boolean podeAdmistrarCasa(Casa c){
        return this.casasAdministradas.containsKey(c.getId());
    }

    //ver se um utilizador tem acesso a uma dada casa
    public boolean podeUsarCasa(Casa c){
        return (this.casasUtilizador.containsKey(c.getId()) || this.casasAdministradas.containsKey(c.getId()));//um utilizador pode usar uma casa se for um utilizador ou um admistrador dessa casa
    }


    @Override
    public String toString() {
        return "Utilizador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", casasAdministradas=" + casasAdministradas.size() +  // Mostra só o número para não poluir
                ", casasUtilizador=" + casasUtilizador.size() +
                '}';
    }
}
