package src.controller;
import src.model.*;
import src.automacao.*;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.io.Serializable;

public class DomusControl implements Serializable {

    private static final long serialVersionUID = 1L;

    private HashMap<Integer, Utilizador> utilizadores = new HashMap<>();
    private HashMap<Integer, Casa> casas = new HashMap<>();
    private HashMap<Integer, Automacao> automacoes = new HashMap<>();

    private int proximoIdUtilizador = 1;
    private int proximoIdCasa = 1;
    private int proximoIdDivisao = 1;
    private int proximoIdDispositivo = 1;
    private int proximoIdAutomacao = 1;

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
    public void removerUtilizador(Utilizador u) {
        if (u == null) return;
        // Remove o utilizador do registo central do sistema
        utilizadores.remove(u.getId()); //
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

    // Método para garantir que o HashMap de automações é inicializado mesmo que a classe seja carregada de um estado serializado antigo
    private void readObject(java.io.ObjectInputStream ois) throws java.io.IOException, ClassNotFoundException {
        ois.defaultReadObject();
        if (automacoes == null) automacoes = new HashMap<>();
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

    // Casa que mais consome (Soma consumos de dispositivos ligados)
    public Casa casaQueMaisConsome() {
        Casa vencedora = null;
        double maxConsumo = -1;

        for (Casa c : casas.values()) {
            double consumoAtual = 0;
            for (Divisao d : c.getDivisoes().values()) {
                for (Dispositivo disp : d.getDispositivos().values()) {
                    if (disp.getEstado().equals("LIGADO")) {
                        consumoAtual += disp.getConsumo_Por_Hora_Wh();
                    }
                }
            }
            if (consumoAtual > maxConsumo) {
                maxConsumo = consumoAtual;
                vencedora = c;
            }
        }
        return vencedora;
    }

    // Top 3 Divisões com mais dispositivos
    public List<String> gettop3DivisoesComMaisDispositivos() {
        class DivInfo { String nome; int total; DivInfo(String n, int t) { nome = n; total = t; } }
        List<DivInfo> lista = new ArrayList<>();

        for (Casa c : casas.values()) {
            for (Divisao d : c.getDivisoes().values()) {
                lista.add(new DivInfo(c.getAlcunha() + " -> " + d.getNome(), d.getDispositivos().size()));
            }
        }
        lista.sort((a, b) -> b.total - a.total);
        return lista.stream().limit(3).map(di -> di.nome + " (" + di.total + " disp.)").toList();
    }

    // 3. Top 3 Dispositivos (por ativações ou tempo)
    public List<Dispositivo> getTop3Dispositivos(boolean porTempo) {
        List<Dispositivo> todos = new ArrayList<>();
        for (Casa c : casas.values()) {
            for (Divisao d : c.getDivisoes().values()) {
                todos.addAll(d.getDispositivos().values());
            }
        }
        if (porTempo) todos.sort((a, b) -> Double.compare(b.getTempoUsoHoras(), a.getTempoUsoHoras()));
        else todos.sort((a, b) -> b.getNumAtivacoes() - a.getNumAtivacoes());

        return todos.stream().limit(3).toList();
    }

    // Para satisfazer o requisito: "listar dispositivos por casa" devolvendo String
    public String listarDispositivosCasaDashboard(Casa casa) {
        if (casa == null) return "Casa não encontrada.";
        StringBuilder sb = new StringBuilder();
        for (Divisao d : casa.getDivisoes().values()) {
            sb.append("[").append(d.getNome()).append("]\n");
            for (Dispositivo disp : d.getDispositivos().values()) {
                sb.append(String.format(" > ID: %d | %-12s | %s\n",
                        disp.getId(), disp.getModelo(), disp.getEstado()));
            }
        }
        return sb.toString();
    }

    // Método para simular a passagem de tempo em todo o sistema
    public void passarTempoGlobal(double horas) {
        for (Casa c : casas.values()) {
            for (Divisao d : c.getDivisoes().values()) {
                for (Dispositivo disp : d.getDispositivos().values()) {
                    disp.adicionarTempoUso(horas); //
                }
            }
        }
    }


    //AUTOMACAO
    public void criarAutomacaoFecharCortinasChuva(int idCasa, int idDivisao, int idSensor) {
    int id = proximoIdAutomacao++;
    Automacao auto = new Automacao(
        id,
        "Fechar Cortinas Quando Chover",
        true,
        Condicao.detetarChuva(idCasa, idDivisao, idSensor),
        Acao.fecharCortinas(idCasa)
    );
    automacoes.put(id, auto);
}

    public void executarAutomacoes() {
        for (Automacao a : automacoes.values()) {
            a.executar(this);
        }
    }

    public Collection<Automacao> getAutomacoes(){
        return automacoes.values();
    }

    public Automacao encontrarAutomacaoPorId(int id) {
        return automacoes.get(id).clone();//ns se vale a pena meter clone, mas tmb n esta mal(VER DPS)
    }


}