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
    private int idCasa; // ID da casa associada à automação

    public Automacao(int id, String nome, boolean ativa, Condicao condicao, Acao acao, int idCasa) {
        this.id = id;
        this.nome = nome;
        this.ativa = ativa;
        this.condicao = condicao;
        this.acao = acao;
        this.ultimaExecucao = 0L; 
        this.idCasa = idCasa;
    }

    public Automacao() {
        this.id = 0;
        this.nome = "";
        this.ativa = false;
        this.condicao = null;
        this.acao = null;
        this.ultimaExecucao = 0L; 
        this.idCasa = 0;
    }

    public Automacao(Automacao a) {
        this.id = a.id;
        this.nome = a.nome;
        this.ativa = a.ativa;
        this.condicao = a.condicao; // Cópia rasa, pode ser necessário implementar clone() em Condicao e Acao para uma cópia profunda
        this.acao = a.acao; // Cópia rasa, pode ser necessário implementar clone() em Condicao e Acao para uma cópia profunda
        this.ultimaExecucao = a.ultimaExecucao;
        this.idCasa = a.idCasa;
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

    public int getIdCasa() {
        return this.idCasa;
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

    // Verifica se a automação deve ser executada com base na condição e no estado de ativação
    public boolean deveExecutar(DomusControl dc){
        return this.ativa && this.condicao != null && this.condicao.verificar(dc);
    }

    // Retorna true se a automação foi executada, false caso contrário
    public boolean executar(DomusControl dc){
        if(!deveExecutar(dc) || this.acao == null) return false;
        this.acao.executar(dc);
        this.ultimaExecucao = System.currentTimeMillis();
        return true;
    }

    public long getUltimaExecucao() {
        return this.ultimaExecucao;
    }

    @Override
    public Automacao clone() {
        return new Automacao(this);
    }

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
