package src.main.controller;

import src.main.model.*;
import src.main.automacao.*;
import src.main.Exceptions.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;
import java.io.Serializable;
import java.time.*;

public class DomusControl implements Serializable {

    private static final long serialVersionUID = 1L;

    private HashMap<Integer, Utilizador> utilizadores = new HashMap<>();
    private HashMap<Integer, Casa> casas = new HashMap<>();
    private HashMap<Integer, Automacao> automacoes = new HashMap<>();
    private HashMap<Integer, Escalonamento> escalonamentos = new HashMap<>();// Para armazenar os escalonamentos criados
    private HashMap<Integer, Cenario> cenarios = new HashMap<>();

    private int proximoIdUtilizador = 1;
    private int proximoIdCasa = 1;
    private int proximoIdDivisao = 1;
    private int proximoIdDispositivo = 1;
    private int proximoIdAutomacao = 1;
    private int proximoIdEscalonamento = 1; // Para gerar IDs únicos para escalonamentos
    private int proximoIdCenario = 1;

    private LocalDateTime tempoAtual = LocalDateTime.now(); // Para simular a passagem de tempo

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
        // Criar automações base
        criarAutomacaoFecharCortinasChuva(id);
        criarAutomacaoAbrirCortinasParouChuva(id);
        criarAutomacaoModoNoite(id);
        criarAutomacaoModoDia(id);
        return casa;
    }

    public void removerUtilizador(Utilizador u) {
        if (u == null) return;
        // Remove o utilizador do registo central do sistema
        utilizadores.remove(u.getId()); //
    }

    public Casa encontrarCasaPorId(int id) {
        Casa casa = casas.get(id);
        if (casa == null) throw new CasaNaoEncontradaException();
        return casa;
    }

    public Divisao encontrarDivisaoPorId(Casa casa, int id) {
        if (casa == null) return null;
        return casa.obterDivisaoPorId(id);
    }

    public Dispositivo encontrarDispositivoPorId(Divisao divisao, int id) {
        if (divisao == null) return null;
        return divisao.obterDispositivoPorId(id);
    }

    public Divisao criarDivisao(Casa casa, String nomeDivisao) {
        if (casa == null)
            return null;
        int idDivisao = proximoIdDivisao++;
        Divisao divisao = new Divisao(nomeDivisao, idDivisao);
        casa.adicionarDivisao(divisao);
        System.out.println("Divisão criada com sucesso! ID atribuído: " + idDivisao);
        return divisao;
    }

    public void adicionarDispositivo(Divisao divisao, Dispositivo dispositivo) {
        if (divisao == null || dispositivo == null)
            return;
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
        Utilizador u = utilizadores.get(id);
        if (u == null)
            throw new UtilizadorNaoEncontradoException();
        return u;
    }

    public Collection<Utilizador> getUtilizadores() {
        return utilizadores.values();
    }

    public Collection<Casa> getCasas() {
        return casas.values();
    }

    public void adicionarCasaAAdministrador(Utilizador administrador, Casa casa) {
        administrador.adicionarCasaAdministrada(casa);
    }

    public void removerPermissoesAdmin(Utilizador administrador, Casa casa) {
        if (contarAdministradoresCasa(casa) <= 1 && administrador.serAdmin(casa)) {
            throw new PermissaoNegadaException();
        }
        administrador.removerCasaAdministrada(casa);
    }

    public void adicionarCasaAUtilizador(Utilizador utilizador, Casa casa) {
        utilizador.adicionarCasaUtilizador(casa);
    }

    public void removerCasaDeUtilizador(Utilizador utilizador, Casa casa) {
        utilizador.removerCasaUtilizador(casa);
    }

    public void listarCasasdeUtilizador(Utilizador utilizador) {
        System.out.println("Casas associadas ao utilizador " + utilizador.getNome() + ":");
        for (Casa c : utilizador.getCasasUtilizador().values()) {
            if (utilizador.getCasasAdministradas().containsKey(c.getId())) {
                continue; // Pula as casas onde o utilizador é administrador, para evitar duplicação
            }
            System.out.println("ID: " + c.getId() + " - Alcunha: " + c.getAlcunha());
        }
    }

    public void listarCasasdeAdministrador(Utilizador utilizador) {
        System.out.println("Casas administradas pelo utilizador " + utilizador.getNome() + ":");
        for (Casa c : utilizador.getCasasAdministradas().values()) {
            System.out.println("ID: " + c.getId() + " - Alcunha: " + c.getAlcunha());
        }
    }

    // Quero que so imprima os nomes das pessoas que tem acesso a essa casa, sem
    // diferenciar se são administradores ou utilizadores
    public void listarPessoasComAcessoACasa(Casa casa) {
        System.out.println("Pessoas com acesso à casa " + casa.getAlcunha() + ":");
        for (Utilizador u : utilizadores.values()) {
            if (u.serAdmin(casa)) {
                System.out.println("[ADMIN] " + u.getId() + " - " + u.getNome());
            }
        }
        for (Utilizador u : utilizadores.values()) {
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
            throw new DomusControlException("Erro ao carregar estado.", e);
        }
    }

    // Método para garantir que o HashMap de automações é inicializado mesmo que a
    // classe seja carregada de um estado serializado antigo
    private void readObject(java.io.ObjectInputStream ois) throws java.io.IOException, ClassNotFoundException {
        ois.defaultReadObject();
        if (automacoes == null)
            automacoes = new HashMap<>();
        if (escalonamentos == null)
            escalonamentos = new HashMap<>();// Garantir que o HashMap de escalonamentos também é inicializado
        if (cenarios == null)
            cenarios = new HashMap<>();
        if (tempoAtual == null)
            tempoAtual = LocalDateTime.now();
        if (proximoIdCenario <= 0)
            proximoIdCenario = 1;
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
        if (casa == null)
            return;

        // 1. Percorre todos os utilizadores para limpar as referências a esta casa
        for (Utilizador u : utilizadores.values()) {
            u.removerCasaAdministrada(casa); //
            u.removerCasaUtilizador(casa); //
        }

        // Remover automações, cenários e escalonamentos associados
        automacoes.values().removeIf(a -> a.getIdCasa() == casa.getId());
        escalonamentos.values().removeIf(e -> e.getIdCasa() == casa.getId());
        cenarios.values().removeIf(c -> c.getIdCasa() == casa.getId());

        // 2. Remove a casa do registo central do sistema
        casas.remove(casa.getId()); //
        System.out.println("Casa '" + casa.getAlcunha() + "' foi eliminada com sucesso.");
    }

    public String encontrarLocalizacaoDispositivo(Dispositivo dispProc) {
        for (Casa c : casas.values()) {
            for (Divisao d : c.getDivisoes().values()) {
                if (d.getDispositivos().containsValue(dispProc)) {
                    return c.getAlcunha() + " -> " + d.getNome();
                }
            }
        }
        return "Desconhecido";
    }

    // Casa que mais consome (Soma consumos de dispositivos ligados)
    public Casa casaQueMaisConsome() {
        Casa vencedora = null;
        double maxConsumo = -1;
        for (Casa c : casas.values()) {
            double consumoAtual = 0;
            for (Divisao d : c.getDivisoes().values()) {
                for (Dispositivo disp : d.getDispositivos().values()) {
                    if (disp.isLigado()) { // Corrigido para usar isLigado()
                        consumoAtual += disp.getConsumo_Por_Hora_Wh();
                    }
                }
            }
            if (consumoAtual > maxConsumo) { maxConsumo = consumoAtual; vencedora = c; }
        }
        return vencedora;
    }

    // Top 3 Divisões com mais dispositivos
    public List<String> gettop3DivisoesComMaisDispositivos() {
        class DivInfo {
            String nome;
            int total;

            DivInfo(String n, int t) {
                nome = n;
                total = t;
            }
        }
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
        if (porTempo)
            todos.sort((a, b) -> Double.compare(b.getTempoUsoHoras(), a.getTempoUsoHoras()));
        else
            todos.sort((a, b) -> b.getNumAtivacoes() - a.getNumAtivacoes());

        return todos.stream().limit(3).toList();
    }

    // Para satisfazer o requisito: "listar dispositivos por casa" devolvendo String
    public String listarDispositivosCasaDashboard(Casa casa) {
        if (casa == null)
            return "Casa não encontrada.";
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

    // AUTOMACAO MODE CHUVA
    public void criarAutomacaoFecharCortinasChuva(int idCasa) {
        int id = proximoIdAutomacao++;
        Automacao auto = new Automacao(
                id,
                "Fechar Cortinas Quando Chover",
                true,
                Condicao.detetarChuvaCasa(idCasa),
                Acao.fecharCortinas(idCasa),
                idCasa);
        automacoes.put(id, auto);
    }

    public void criarAutomacaoAbrirCortinasParouChuva(int idCasa) {
        int id = proximoIdAutomacao++;
        Automacao auto = new Automacao(
                id,
                "Abrir Cortinas Quando Parar de Chover",
                true,
                Condicao.naoEstaAChoverCasa(idCasa),
                Acao.abrirCortinas(idCasa),
                idCasa);
        automacoes.put(id, auto);
    }

    public void criarAutomacaoModoNoite(int idCasa) {
        int id = proximoIdAutomacao++;
        Automacao auto = new Automacao(
                id,
                "Modo Noite",
                true,
                Condicao.luminosidadeBaixaCasa(idCasa),
                Acao.ligarLuzesCasa(idCasa),
                idCasa);
        automacoes.put(id, auto);
    }

    public void criarAutomacaoModoDia(int idCasa) {
        int id = proximoIdAutomacao++;
        Automacao auto = new Automacao(
                id,
                "Modo Dia",
                true,
                Condicao.luminosidadeNormalCasa(idCasa),
                Acao.desligarLuzesCasa(idCasa),
                idCasa);
        automacoes.put(id, auto);
    }

    public void executarAutomacoes() {
        for (Automacao a : automacoes.values()) {
            a.executar(this);
        }
    }

    /**
     * Alterna o estado de chuva de todos os sensores da casa e dispara automações
     * automaticamente.
     */
    public void alternarChuvaCasa(int idCasa) {
        Casa casa = encontrarCasaPorId(idCasa);
        if (casa == null)
            return;

        // Determina o novo estado (inverte o primeiro sensor encontrado)
        boolean novoEstado = false;
        boolean encontrou = false;
        for (Divisao d : casa.getDivisoes().values()) {
            for (Dispositivo disp : d.getDispositivos().values()) {
                if (disp instanceof SensorAgua sensor) {
                    novoEstado = !sensor.isEmChuva();
                    encontrou = true;
                    break;
                }
            }
            if (encontrou)
                break;
        }

        // Aplica o novo estado a todos os sensores da casa
        for (Divisao d : casa.getDivisoes().values())
            for (Dispositivo disp : d.getDispositivos().values())
                if (disp instanceof SensorAgua sensor)
                    sensor.setEmChuva(novoEstado);

        // Trigger: executa automações automaticamente
        executarAutomacoes();
    }

    /**
     * Alterna a luminosidade de todos os sensores de luz da casa e dispara
     * automações automaticamente.
     */
    public void alternarLuminosidadeCasa(int idCasa) {
        Casa casa = encontrarCasaPorId(idCasa);
        if (casa == null)
            return;

        for (Divisao d : casa.getDivisoes().values())
            for (Dispositivo disp : d.getDispositivos().values())
                if (disp instanceof SensorLuz sensor)
                    sensor.setNivelLuz(sensor.isLuminosidadeBaixa()
                            ? sensor.getLimiarNoite() + 10
                            : sensor.getLimiarNoite() - 10);

        // Trigger: executa automações automaticamente
        executarAutomacoes();
    }

    public Collection<Automacao> getAutomacoes() {
        return automacoes.values();
    }

    public Automacao encontrarAutomacaoPorId(int id) {
        return automacoes.get(id);
    }

    public Collection<Cenario> getCenarios() {
        return cenarios.values();
    }

    public List<Cenario> getCenariosDaCasa(int idCasa) {
        List<Cenario> lista = new ArrayList<>();
        for (Cenario c : cenarios.values()) {
            if (c.getIdCasa() == idCasa)
                lista.add(c);
        }
        lista.sort(Comparator.comparingInt(Cenario::getId));
        return lista;
    }

    public Cenario encontrarCenarioPorId(int id) {
        Cenario c = cenarios.get(id);
        return c != null ? new Cenario(c) : null;
    }

    public Cenario criarCenario(Cenario cenario) {
        if (cenario == null)
            return null;

        for (Cenario c : cenarios.values()) {
            if (c.getIdCasa() == cenario.getIdCasa() && c.getNome().equalsIgnoreCase(cenario.getNome())) {
                throw new DomusControlException("Já existe um cenário com o nome '" + cenario.getNome() + "' nesta casa.");
            }
        }

        int id = proximoIdCenario++;
        Cenario copia = new Cenario(cenario);
        copia.setId(id);
        cenarios.put(id, copia);
        return copia;
    }

    public Cenario criarCenario(String nome, int idCasa, List<Acao> acoes) {
        return criarCenario(new Cenario(0, nome, idCasa, acoes));
    }

    /**
     * Remove um cenário do sistema pelo seu ID.
     */
    public void removerCenario(int id) {
        if (cenarios.containsKey(id)) {
            cenarios.remove(id);
        } else {
            throw new DomusControlException("Cenário não encontrado.");
        }
    }

    /**
     * Cria um cenário pré-definido individual baseado nos templates do sistema.
     */
    public void adicionarCenarioPreDefinido(int tipo, int idCasa) {
        Cenario novo = switch (tipo) {
            case 1 -> Cenario.sairDeCasa(0, idCasa);
            case 2 -> Cenario.jantarComAmigos(0, idCasa);
            case 3 -> Cenario.jantarRomantico(0, idCasa);
            case 4 -> Cenario.cinema(0, idCasa);
            case 5 -> Cenario.estudar(0, idCasa);
            case 6 -> Cenario.deitar(0, idCasa);
            case 7 -> Cenario.acordar(0, idCasa);
            default -> null;
        };
        if (novo != null) criarCenario(novo);
    }

    public void criarCenariosObrigatorios(int idCasa) {
        Set<String> existentes = new HashSet<>();
        for (Cenario c : cenarios.values()) {
            if (c.getIdCasa() == idCasa)
                existentes.add(c.getNome().toLowerCase());
        }

        try {
            if (!existentes.contains("Sair de casa".toLowerCase()))
                criarCenario(Cenario.sairDeCasa(0, idCasa));
            if (!existentes.contains("Jantar com amigos".toLowerCase()))
                criarCenario(Cenario.jantarComAmigos(0, idCasa));
            if (!existentes.contains("Jantar Romantico".toLowerCase()))
                criarCenario(Cenario.jantarRomantico(0, idCasa));
            if (!existentes.contains("Cinema".toLowerCase()))
                criarCenario(Cenario.cinema(0, idCasa));
            if (!existentes.contains("Estudar".toLowerCase()))
                criarCenario(Cenario.estudar(0, idCasa));
            if (!existentes.contains("Deitar".toLowerCase()))
                criarCenario(Cenario.deitar(0, idCasa));
            if (!existentes.contains("Acordar".toLowerCase()))
                criarCenario(Cenario.acordar(0, idCasa));
        } catch (DomusControlException ignored) {
            // Ignora a exceção se ocorrer conflito de nomes inesperado na inicialização
        }
    }

    public boolean executarCenario(int id) {
        Cenario cenario = cenarios.get(id);
        if (cenario == null)
            return false;
        cenario.executar(this);
        return true;
    }

    // ======================================================================
    // ESCALONAMENTOS
    // ======================================================================

    public LocalDateTime getTempoAtual() {
        return tempoAtual.withNano(0);
    }

    // metodo que avanca o tempo e verifica escalonamemtos
    public void avancaTempo(long minutos) {
        passarTempoGlobal(minutos / 60.0);// avanca tempo em todos os dispositivos
        for (long i = 0; i < minutos; i++) {
            tempoAtual = tempoAtual.plusMinutes(1);// avanca tempo 1 minuto de cada vez
            executarEscalonamentos();
        }
        System.out.println("Tempo avançado em " + minutos + " minutos. Tempo atual: " + tempoAtual);
    }

    public void executarEscalonamentos() {
        LocalTime horaAtual = tempoAtual.toLocalTime();
        LocalDate dataAtual = tempoAtual.toLocalDate();

        for (Escalonamento e : escalonamentos.values()) {
            e.verificarEExecutar(this, horaAtual, dataAtual);// verifica e executa cada escalonamento
        }
    }

    public Collection<Escalonamento> getEscalonamentos() {
        return escalonamentos.values();
    }

    public Escalonamento encontrarEscalonamentoPorId(int id) {
        Escalonamento e = escalonamentos.get(id);
        return e != null ? e.clone() : null;
    }

    // ESCALONAMENTO 1 -> Abrir cortinas 100% às 7:30
    public void criarEscalonamentoAbrirCortinas(int idCasa) {
        int id = proximoIdEscalonamento++;

        Escalonamento e = new Escalonamento(
                id,
                "Abrir Cortinas às 7:30",
                true,
                LocalTime.of(7, 30),
                null,
                Acao.abrirCortinas(idCasa),
                null,
                idCasa);
        escalonamentos.put(id, e);// mete na hashmap de escalonamentos do sistema
    }

    // ESCALONAMENTO 2 -> Desliga luzes e fecha cortinas às 23:00
    public void criarEscalonamentoModoNoturno(int idCasa) {
        int id = proximoIdEscalonamento++;

        Escalonamento e = new Escalonamento(
                id,
                "Modo Noturno às 23:00",
                true,
                LocalTime.of(23, 0),
                null,
                Acao.desligarLuzesEFecharCortinas(idCasa),
                null,
                idCasa);
        escalonamentos.put(id, e);
    }

    // ESCALONAMENTO 3 -> Ligar luzes às 19:00 e desligar às 23:00
    public void criarEscalonamentoLuzTarde(int idCasa) {
        int id = proximoIdEscalonamento++;

        Escalonamento e = new Escalonamento(
                id,
                "Luzes Tarde",
                true,
                LocalTime.of(19, 0),
                LocalTime.of(23, 0),
                Acao.ligarLuzesCasa(idCasa),
                Acao.desligarLuzesCasa(idCasa),
                idCasa);
        escalonamentos.put(id, e);
    }

    // ESCALONAMENTO 4 -> coluna de som ligas as 7:00 e desliga as 7:45
    public void criarEscalonamentoMusicaManha(int idCasa) {
        int id = proximoIdEscalonamento++;

        Escalonamento e = new Escalonamento(
                id,
                "Música Manhã",
                true,
                LocalTime.of(7, 0),
                LocalTime.of(7, 45),
                Acao.ligarColunaSomCasa(idCasa), // execucao inicial
                Acao.desligarColunaSomCasa(idCasa), // execucao final
                idCasa);
        escalonamentos.put(id, e);
    }
}