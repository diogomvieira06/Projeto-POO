import java.util.*;

public class Utilizador {
    private int id;
    private String nome;
    private ArrayList<Casa> casasAdmistradas;
    private ArrayList<Casa> casasUtilizador;

    public Utilizador(int id, String nome){
        this.id = id;
        this.nome = nome;
        this.casasAdmistradas = new ArrayList<>();
        this.casasUtilizador = new ArrayList<>();
    }

    public Utilizador(){
        this.id = 0;
        this.nome = "";
        this.casasAdmistradas = new ArrayList<>();
        this.casasUtilizador = new ArrayList<>();
    }

    public Utilizador(Utilizador u){
        this.id = u.id;
        this.nome = u.nome;
        this.casasAdmistradas = new ArrayList<>(u.casasAdmistradas);
        this.casasUtilizador = new ArrayList<>(u.casasUtilizador);
    }

    //getters
    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public ArrayList<Casa> getCasasAdmistradas(){
        return this.casasAdmistradas;//dps meter com clone
    }

    public ArrayList<Casa> getCasasUtilizador(){
        return this.casasUtilizador;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setNome(String n){
        this.nome = n;
    }

    public void setCasasAdmistradas(ArrayList<Casa> casasAdmistradas){
        this.casasAdmistradas = casasAdmistradas;
    }

    public void setCasasUtilizador(ArrayList<Casa> casasUtilizador){
        this.casasUtilizador = casasUtilizador;
    }



    //adicionar casa admistrada
    public void adicionarCasaAdmistrada(Casa c){
        if(!this.casasAdmistradas.contains(c)) this.casasAdmistradas.add(c);
    }

    //adicionar casa utilizador
    public void adicionarCasaUtilizador(Casa c){
        if(!this.casasUtilizador.contains(c)) this.casasUtilizador.add(c);
    }

    //remover casa admistrada
    public void removerCasaAdmistrada(Casa c){
        this.casasAdmistradas.remove(c);
    }

    //remover casa Utilizador
    public void removerCasaUtilizador(Casa c){
        this.casasUtilizador.remove(c);
    }

    //ver se um utilizador pode admistrar uma dada casa
    public boolean podeAdmistrarCasa(Casa c){
        return this.casasAdmistradas.contains(c);
    }

    //ver se um utilizador tem acesso a uma dada casa
    public boolean podeUsarCasa(Casa c){
        return (this.casasUtilizador.contains(c) || this.casasAdmistradas.contains(c));//um utilizador pode usar uma casa se for um utilizador ou um admistrador dessa casa
    }


    @Override
    public String toString() {
        return "Utilizador{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", casasAdministradas=" + casasAdmistradas.size() +  // Mostra só o número para não poluir
                ", casasUtilizador=" + casasUtilizador.size() +
                '}';
}


}
