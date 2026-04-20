package src.controller;
import src.model.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.io.Serializable;

public class DomusControl implements Serializable {

    private static final long serialVersionUID = 1L;

    private HashMap<Integer, Utilizador> utilizadores = new HashMap<>();
    private HashMap<Integer, Casa> casas = new HashMap<>();

    private int proximoIdUtilizador = 1;
    private int proximoIdCasa = 1;
    private int proximoIdDivisao = 1;
    private int proximoIdDispositivo = 1;

    public int aumentarIdDispositivo() {
        return proximoIdDispositivo++;
    }
    public int aumentarIdDivisao() {
        return proximoIdDivisao++;
    }
    public int aumentarIdCasa() {
        return proximoIdCasa++;
    }
    public int aumentarIdUtilizador() {
        return proximoIdUtilizador++;
    }

    public Utilizador criarUtilizador(String nome) {
        int id = proximoIdUtilizador++;
        Utilizador u = new Utilizador(id, nome);
        utilizadores.put(id, u);
        System.out.println("Utilizador criado com sucesso! ID atribuído: " + id);
        return u;
    }

    public Casa criarCasa(String alcunha) {
        int id = proximoIdCasa++;
        Casa casa = new Casa(alcunha, id);
        casas.put(id, casa);
        System.out.println("Casa criada com sucesso, ID atribuído: " + id);
        return casa;
    }

    public Casa encontrarCasaPorId(int id) {
        return casas.get(id);
    }

    public Divisao encontrarDivisaoPorId(Casa casa, int id) {
        if (casa == null) return null;
        return casa.obterDivisaoPorId(id);
    }

    public Dispositivo encontrarDispositivoPorId(Divisao divisao, int id) {
        if (divisao == null) return null;
        return divisao.obterDispositivoPorId(id);
    }

    public void criarDivisao(Casa casa, String nomeDivisao) {
        if (casa == null) return;
        int idDivisao = proximoIdDivisao++;
        Divisao divisao = new Divisao(nomeDivisao, idDivisao);
        casa.adicionarDivisao(divisao);
        System.out.println("Divisão criada com sucesso! ID atribuído: " + idDivisao);
    }

    public void adicionarDispositivo(Divisao divisao, Dispositivo dispositivo) {
        if (divisao == null || dispositivo == null) return;
        divisao.adicionarDispositivo(dispositivo);
    }

    public void associarCasaAdministrador(int idUtilizador, int idCasa) {
        Utilizador u = encontrarUtilizadorPorId(idUtilizador);
        Casa casa = encontrarCasaPorId(idCasa);
        if (u != null && casa != null) {
            u.adicionarCasaAdministrada(casa);
            System.out.println("Casa associada como administrada.");
        }
    }

    public void associarCasaUtilizador(int idUtilizador, int idCasa) {
        Utilizador u = encontrarUtilizadorPorId(idUtilizador);
        Casa casa = encontrarCasaPorId(idCasa);
        if (u != null && casa != null) {
            u.adicionarCasaUtilizador(casa);
            System.out.println("Casa associada como utilizador.");
        }
    }

    public void listarUtilizadores() {
        for (Utilizador u : utilizadores.values()) {
            System.out.println("ID: " + u.getId() + " - Nome: " + u.getNome());
        }
    }

    public void listarCasas() {
        for (Casa c : casas.values()) {
            System.out.println("ID: " + c.getId() + " - Alcunha: " + c.getAlcunha());
        }
    }

    public Utilizador encontrarUtilizadorPorId(int id) {
        return utilizadores.get(id);
    }

    public Collection<Utilizador> getUtilizadores() {
        return utilizadores.values();
    }

    public Collection<Casa> getCasas() {
        return casas.values();
    }

    public void adicionarCasaAAdministrador(Utilizador administrador, Casa casa){
        administrador.adicionarCasaAdministrada(casa);
    }

    public void removerPermissoesAdmin(Utilizador administrador, Casa casa){
        administrador.removerCasaAdministrada(casa);
    }

    public void adicionarCasaAUtilizador(Utilizador utilizador, Casa casa){
        utilizador.adicionarCasaUtilizador(casa);
    }

    public void removerCasaDeUtilizador(Utilizador utilizador, Casa casa){
        utilizador.removerCasaUtilizador(casa);
    }

    public void listarCasasdeUtilizador(Utilizador utilizador){
        System.out.println("Casas associadas ao utilizador " + utilizador.getNome() + ":");
        for(Casa c : utilizador.getCasasUtilizador().values()){
            if (utilizador.getCasasAdministradas().containsKey(c.getId())) {
                continue; // Pula as casas onde o utilizador é administrador, para evitar duplicação
            }
            System.out.println("ID: " + c.getId() + " - Alcunha: " + c.getAlcunha());
        }
    }

    public void listarCasasdeAdministrador(Utilizador utilizador){
        System.out.println("Casas administradas pelo utilizador " + utilizador.getNome() + ":");
        for(Casa c : utilizador.getCasasAdministradas().values()){
            System.out.println("ID: " + c.getId() + " - Alcunha: " + c.getAlcunha());
        }
    }

    //Quero que so imprima os nomes das pessoas que tem acesso a essa casa, sem diferenciar se são administradores ou utilizadores
    public void listarPessoasComAcessoACasa(Casa casa){
        System.out.println("Pessoas com acesso à casa " + casa.getAlcunha() + ":");
        for(Utilizador u : utilizadores.values()){
            if (u.serAdmin(casa)) {
                System.out.println("[ADMIN] " + u.getId() + " - " + u.getNome());
            }
        }
        for(Utilizador u : utilizadores.values()){
            if (u.serUtilizador(casa)) {
                System.out.println("[UTILIZADOR] " + u.getId() + " - " + u.getNome());
            }
        }
    }

    public void listarEstadoGlobalCasa(Casa casa) {
        System.out.println("\n===== ESTADO GLOBAL: " + casa.getAlcunha() + " =====");
        boolean temDispositivos = false;

        // Percorre todas as divisões associadas à casa [cite: 14]
        for (Divisao d : casa.getDivisoes().values()) {
            // Percorre todos os dispositivos de cada divisão [cite: 14]
            for (Dispositivo disp : d.getDispositivos().values()) {
                System.out.println("[" + d.getNome() + "] " + "(" + disp.getTipo() + ") " + disp.getMarca() + " " + disp.getModelo() +
                        " (ID: " + disp.getId() + ") -> ESTADO: " + disp.getEstado() +
                        disp.getDetalhesEspecificos());
                temDispositivos = true;
            }
        }

        if (!temDispositivos) {
            System.out.println("Esta casa ainda não possui dispositivos instalados.");
        }
    }

    public void guardarEstado(String nomeFicheiro) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new java.io.FileOutputStream(nomeFicheiro))) {
            oos.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DomusControl carregarEstado(String nomeFicheiro) {
        try (ObjectInputStream ois = new ObjectInputStream(new java.io.FileInputStream(nomeFicheiro))) {
            return (DomusControl) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void removerDivisao(Casa casa, Divisao divisao) {
        if (casa != null && divisao != null) {
            casa.removerDivisao(divisao);
            System.out.println("Divisão removida com sucesso.");
        }
    }

    public void listarUtilizadoresDisponiveisAAdicionar(Casa casa) {
        for (Utilizador u : utilizadores.values()) {
            if (!u.podeUsarCasa(casa)) {
                System.out.println("ID: " + u.getId() + " - Nome: " + u.getNome());
            }
        }
    }

    public void listarAdministradoresDisponiveisAAdicionar(Casa casa) {
        for (Utilizador u : utilizadores.values()) {
            if (!u.serAdmin(casa)) {
                System.out.println("ID: " + u.getId() + " - Nome: " + u.getNome());
            }
        }
    }

    public int contarAdministradoresCasa(Casa casa) {
        int count = 0;
        for (Utilizador u : utilizadores.values()) {
            if (u.serAdmin(casa)) {
                count++;
            }
        }
        return count;
    }

    public void eliminarCasaTotalmente(Casa casa) {
        if (casa == null) return;

        // 1. Percorre todos os utilizadores para limpar as referências a esta casa
        for (Utilizador u : utilizadores.values()) {
            u.removerCasaAdministrada(casa); //
            u.removerCasaUtilizador(casa);     //
        }

        // 2. Remove a casa do registo central do sistema
        casas.remove(casa.getId()); //
        System.out.println("Casa '" + casa.getAlcunha() + "' foi eliminada com sucesso.");
    }
}