package src.automacao;

import src.controller.*;//DomusControl
import src.model.*;//Casa, Dispositivo, Utilizador

import java.io.Serializable;

public class Automacao implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private boolean ativa;
    private Condicao condicao;//deixar assim para ja
    private Acao acao;//deixar assim para ja
    private long ultimaExecucao;

    public Automacao(int id, String nome, boolean ativa, Condicao condicao, Acao acao) {
        this.id = id;
        this.nome = nome;
        this.ativa = ativa;
        this.condicao = condicao;
        this.acao = acao;
        this.ultimaExecucao = 0L; 
    }

    public Automacao() {
        this.id = 0;
        this.nome = "";
        this.ativa = false;
        this.condicao = null;
        this.acao = null;
        this.ultimaExecucao = 0L; 
    }

    public Automacao(Automacao a) {
        this.id = a.id;
        this.nome = a.nome;
        this.ativa = a.ativa;
        this.condicao = a.condicao; // Cópia rasa, pode ser necessário implementar clone() em Condicao e Acao para uma cópia profunda
        this.acao = a.acao; // Cópia rasa, pode ser necessário implementar clone() em Condicao e Acao para uma cópia profunda
        this.ultimaExecucao = a.ultimaExecucao;
    }

    //getters
    public int getId(){
        return this.id;
    }

    public String getNome(){
        return this.nome;
    }

    public boolean isAtiva(){
        return this.ativa;
    }

    public Condicao getCondicao(){
        return this.condicao;
    }

    public Acao getAcao(){
        return this.acao;
    }

    //setters
    public void setId(int id){
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setAtiva(boolean a){
        this.ativa = a;
    }

    public void setCondicao(Condicao c){
        this.condicao = c;
    }

    public void setAcao(Acao a){
        this.acao = a;
    }

    //metodos
    public void ativar(){
        this.ativa = true;
    }

    public void desativar(){
        this.ativa = false;
    }

    //executar
    //deveExcutar

    @Override
    public String toString(){
        return "Automação{" +
                "id= " + id +
                ", nome= '" + nome + '\'' +
                ", ativa= " + ativa +
                "ultimaExecucao= " + ultimaExecucao +
                '}';
    }

    
}
