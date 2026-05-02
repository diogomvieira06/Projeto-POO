package src.automacao;
import src.controller.*;
import java.io.Serializable;
import java.time.*;

public class Escalonamento implements Serializable{
    private static final long serialVersionUID = 1L;

    private int id;
    private String nome;
    private boolean ativo;
    private LocalTime horaInicio; //horas
    private LocalTime horaFim;
    private Acao acaoInicio;
    private Acao acaoFim;
    private LocalDate ultimaExecucaoInicio; //dia
    private LocalDate ultimaExecucaoFim;

    public Escalonamento(int id, String nome, boolean ativo, LocalTime horaInicio, LocalTime horaFim, Acao acaoInicio, Acao acaoFim) {
        this.id = id;
        this.nome = nome;
        this.ativo = ativo;
        this.horaInicio = horaInicio;
        this.horaFim = horaFim;
        this.acaoInicio = acaoInicio;
        this.acaoFim = acaoFim;
        this.ultimaExecucaoInicio = null; // Inicialmente sem execução
        this.ultimaExecucaoFim = null; // Inicialmente sem execução
    }

    public Escalonamento(){
        this.id = 0;
        this.nome = "";
        this.ativo = false;
        this.horaInicio = null;
        this.horaFim = null;
        this.acaoInicio = null;
        this.acaoFim = null;
        this.ultimaExecucaoInicio = null; // Inicialmente sem execução
        this.ultimaExecucaoFim = null; // Inicialmente sem execução
    }

    public Escalonamento(Escalonamento e){
        this.id = e.id;
        this.nome = e.nome;
        this.ativo = e.ativo;
        this.horaInicio = e.horaInicio;
        this.horaFim = e.horaFim;
        this.acaoInicio = e.acaoInicio != null ? e.acaoInicio.clone() : null; // Clona a ação de início, se não for nula
        this.acaoFim = e.acaoFim != null ? e.acaoFim.clone() : null;
        this.ultimaExecucaoInicio = e.ultimaExecucaoInicio;
        this.ultimaExecucaoFim = e.ultimaExecucaoFim;
    }

    //getters
    public int getId(){return this.id;}
    public String getNome(){return this.nome;}
    public boolean isAtivo(){return this.ativo;}
    public LocalTime getHoraInicio(){return this.horaInicio;}
    public LocalTime getHoraFim(){return this.horaFim;}
    public Acao getAcaoInicio(){return this.acaoInicio;}
    public Acao getAcaoFim(){return this.acaoFim;}
    public LocalDate getUltimaExecucaoInicio(){return this.ultimaExecucaoInicio;}
    public LocalDate getUltimaExecucaoFim(){return this.ultimaExecucaoFim;}

    //setters
    public void setId(int id){this.id = id;}
    public void setNome(String nome){this.nome = nome;}
    public void setAtivo(boolean ativo){this.ativo = ativo;}
    public void setHoraInicio(LocalTime horaInicio){this.horaInicio = horaInicio;}
    public void setHoraFim(LocalTime horaFim){this.horaFim = horaFim;}
    public void setAcaoInicio(Acao acaoInicio){this.acaoInicio = acaoInicio;}
    public void setAcaoFim(Acao acaoFim){this.acaoFim = acaoFim;}
    public void setUltimaExecucaoInicio(LocalDate ultimaExecucaoInicio){this.ultimaExecucaoInicio = ultimaExecucaoInicio;}
    public void setUltimaExecucaoFim(LocalDate ultimaExecucaoFim){this.ultimaExecucaoFim = ultimaExecucaoFim;}


    public void ativar(){
        this.ativo = true;
    }

    public void desativar(){
        this.ativo = false;
    }

    public boolean isIntervalo(){
        return horaFim != null;
    }

    /**
     * Verifica e executa o escalonamento com base no tempo atual simulado.
     * Garante que cada ação só dispara uma vez por dia.
     */
    public void verificarEExecutar(DomusControl dc, LocalTime horaAtual, LocalDate dataAtual){
        if(!ativo || horaInicio == null || acaoInicio == null)return; // Se não estiver ativo ou faltar hora ou ação, não faz nada
    

        if(!isIntervalo()){
            //pontual, executa apenas a ação de início
            if(!horaAtual.isBefore(horaInicio) && (ultimaExecucaoInicio == null || ultimaExecucaoInicio.isBefore(dataAtual))){
                acaoInicio.executar(dc);
                ultimaExecucaoInicio = dataAtual; // Atualiza a última execução para hoje
            }
        } else {
            //intervalo
            if(!horaAtual.isBefore(horaInicio) && (ultimaExecucaoInicio == null || ultimaExecucaoInicio.isBefore(dataAtual))){
                acaoInicio.executar(dc);
                ultimaExecucaoInicio = dataAtual; // Atualiza a última execução para hoje
            }

            if(acaoFim != null && !horaAtual.isBefore(horaFim) &&(ultimaExecucaoFim == null || ultimaExecucaoFim.isBefore(dataAtual))){
                acaoFim.executar(dc);
                ultimaExecucaoFim = dataAtual; // Atualiza a última execução para hoje
            }
        }
    }

    @Override
    public Escalonamento clone(){
        return new Escalonamento(this);
    }

    @Override
    public String toString() {
        String tipo = isIntervalo() ? "Intervalo [" + horaInicio + " - " + horaFim + "]"
                                    : "Pontual [" + horaInicio + "]";
        return "Escalonamento{id=" + id + ", nome='" + nome + "', ativo=" + ativo + ", " + tipo + "}";
    }

}
