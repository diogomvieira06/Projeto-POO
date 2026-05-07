package src.main.model;


import java.io.Serializable;
import java.util.*;

public class Utilizador implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public HashMap<Integer, Casa> getCasasAdministradas(){
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

    public void setCasasAdministradas(HashMap<Integer, Casa> casasAdministradas){
        this.casasAdministradas = new HashMap<>(casasAdministradas);
    }

    public void setCasasUtilizador(HashMap<Integer, Casa> casasUtilizador){
        this.casasUtilizador = new HashMap<>(casasUtilizador);
    }



    //adicionar casa administrada
    public void adicionarCasaAdministrada(Casa c){
        this.casasAdministradas.put(c.getId(), c);
        this.casasUtilizador.put(c.getId(), c); //um utilizador que é administrador de uma casa também é um utilizador dessa casa
    }

    //adicionar casa utilizador
    public void adicionarCasaUtilizador(Casa c){
        this.casasUtilizador.put(c.getId(), c);
    }

    //remover casa administrada
    public void removerCasaAdministrada(Casa c){
        this.casasAdministradas.remove(c.getId());
    }

    //remover casa Utilizador
    public void removerCasaUtilizador(Casa c){
        if (this.casasAdministradas.containsKey(c.getId())) {//se for administrador, ao remover como utilizador, também remove como administrador
            this.casasAdministradas.remove(c.getId());
        }
        this.casasUtilizador.remove(c.getId());
    }

    //ver se um utilizador pode administrar uma dada casa
    public boolean podeAdministrarCasa(Casa c){
        return this.casasAdministradas.containsKey(c.getId());
    }

    //ver se um utilizador tem acesso a uma dada casa
    public boolean podeUsarCasa(Casa c){
        return (this.casasUtilizador.containsKey(c.getId()) || this.casasAdministradas.containsKey(c.getId()));//um utilizador pode usar uma casa se for um utilizador ou um administrador dessa casa
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

    public boolean serAdmin(Casa c) { // Se este utilizador é administrador da casa c
        return this.casasAdministradas.containsKey(c.getId());
    }
    public boolean serUtilizador(Casa c) { // Se este utilizador é um utilizador normal da casa c (não administrador)
        return this.casasUtilizador.containsKey(c.getId()) && !this.serAdmin(c);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Utilizador that = (Utilizador) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }
}