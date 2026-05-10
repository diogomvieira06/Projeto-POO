package src.main.view;

import src.main.controller.DomusControl;
import src.main.model.*;
import src.main.Exceptions.*;
import java.time.*;

public class Main {
    public static void main(String[] args) {
        DomusControl domusControl = new DomusControl();

        // 1. Perguntar ao utilizador a preferência de arranque
        System.out.println("CONFIGURAÇÃO DE ARRANQUE:");
        System.out.println("1. Carregar estado anterior (Ficheiro Binário)");
        System.out.println("2. Iniciar novo Cenário de Teste (Fase 4)");
        System.out.print("Escolha: ");

        int escolhaArranque = InputValidator.lerInteiro();

        if (escolhaArranque == 1) {
            // Tenta carregar do ficheiro
            DomusControl carregado = null;
            try {
                carregado = DomusControl.carregarEstado("estado_projeto.dat");
            } catch (DomusControlException e) {
                carregado = null;
            }
            if (carregado != null) {
                domusControl = carregado;
                System.out.println("Estado carregado com sucesso.");
            } else {
                System.out.println("Aviso: Ficheiro não encontrado. A inicializar dados padrão...");
                inicializarDadosTeste(domusControl);
            }
        } else {
            // Ignora o ficheiro e gera o cenário de teste da Fase 4
            System.out.println("A gerar cenário controlado para a Fase 4...");
            gerarCenarioReal(domusControl);
        }

        // 1. LOGIN COM DASHBOARD
        Utilizador utilizador_atual = null;
        while (utilizador_atual == null) {
            StringBuilder listaUtilizadores = new StringBuilder();
            for (Utilizador utilizador : domusControl.getUtilizadores()) {
                listaUtilizadores.append("[")
                        .append(utilizador.getId())
                        .append("] ")
                        .append(utilizador.getNome())
                        .append("\n");
            }

            String info = "Bem-vindo ao Sistema DomusControl.\nIdentifique-se para gerir a sua habitação inteligente.\n\nUtilizadores disponíveis:\n"
                    + (listaUtilizadores.length() > 0 ? listaUtilizadores.toString().stripTrailing() : "(Sem utilizadores)"); // Exibe a lista de utilizadores ou uma mensagem caso não haja nenhum
            String opts = "Introduza o seu ID de utilizador\n(Ou digite 0 para encerrar o programa)";

            ConsoleUI.desenharDashboard("LOGIN", info, opts);

            System.out.print("\nID: ");
            int idLogin = InputValidator.lerInteiro();

            if (idLogin == 0) return;

            try {
                utilizador_atual = domusControl.encontrarUtilizadorPorId(idLogin);
            } catch (UtilizadorNaoEncontradoException e) {
                utilizador_atual = null;
            }
            if (utilizador_atual == null) {
                System.out.println("Erro: Utilizador não encontrado. Prima Enter...");
                InputValidator.lerLinha();
            }
        }

        // 2. MENU PRINCIPAL
        boolean sair = false;
        while (!sair) {
            boolean temCasas = !utilizador_atual.getCasasUtilizador().isEmpty() || !utilizador_atual.getCasasAdministradas().isEmpty();
            String info = "Sessão: " + utilizador_atual.getNome() + "\nO que deseja fazer hoje?";
            String opts = "1. Gestão de Casas\n2. Criar Nova Casa\n3. Automações\n4. Estatisticas\n5. Mudar Utilizador\n6. Escalonamentos\n7. Cenários\n0. Sair e Gravar";

            ConsoleUI.desenharDashboard("MENU PRINCIPAL", info, opts);
            System.out.print("\nOpção: ");
            int opcao = InputValidator.lerInteiro();

            switch (opcao) {
                case 1 -> Menu.submenuCasas(utilizador_atual, domusControl);
                case 2 -> {
                    System.out.print("Alcunha da nova casa: ");
                    String alcunha = InputValidator.lerLinha();
                    Casa nova = domusControl.criarCasa(alcunha);
                    domusControl.adicionarCasaAAdministrador(utilizador_atual, nova);
                }
                case 3 -> Menu.menuAutomacao(utilizador_atual, domusControl);
                case 4 -> {
                    if (!temCasas) {
                        ConsoleUI.mostrarErro("Sem permissões: não tem casas associadas.");
                    } else {
                        Menu.menuEstatisticas(utilizador_atual, domusControl);
                    }
                }
                case 5 -> {
                    // Mudar de utilizador: volta ao ecrã de login
                    utilizador_atual = null;
                    while (utilizador_atual == null) {
                        StringBuilder listaUtilizadores = new StringBuilder();
                        for (Utilizador utilizador : domusControl.getUtilizadores()) {
                            listaUtilizadores.append("[")
                                    .append(utilizador.getId())
                                    .append("] ")
                                    .append(utilizador.getNome())
                                    .append("\n");
                        }

                        String info2 = "SELEÇÃO DE UTILIZADOR\n\nUtilizadores disponíveis:\n"
                                + (listaUtilizadores.length() > 0 ? listaUtilizadores.toString().stripTrailing() : "(Sem utilizadores)");
                        String opts2 = "Introduza o ID do utilizador para iniciar sessão\n(Ou digite 0 para encerrar o programa)";

                        ConsoleUI.desenharDashboard("MUDAR UTILIZADOR", info2, opts2);

                        System.out.print("\nID: ");
                        int idLogin = InputValidator.lerInteiro();

                        if (idLogin == 0) {
                            sair = true;
                            break;
                        }

                        try {
                            utilizador_atual = domusControl.encontrarUtilizadorPorId(idLogin);
                        } catch (UtilizadorNaoEncontradoException e) {
                            utilizador_atual = null;
                        }
                        if (utilizador_atual == null) {
                            System.out.println("Erro: Utilizador não encontrado. Prima Enter...");
                            InputValidator.lerLinha();
                        }
                    }
                }
                case 6 -> Menu.menuEscalonamentos(utilizador_atual, domusControl);
                case 7 -> Menu.menuCenarios(utilizador_atual, domusControl);
                case 0 -> sair = true;
                default -> { System.out.println("Opção inválida."); InputValidator.lerLinha(); }
            }
        }

        if (escolhaArranque == 1) {
            // Se o utilizador escolheu carregar do ficheiro, volta a guardar para manter as alterações
            domusControl.guardarEstado("estado_projeto.dat");
        }
    }

    private static void inicializarDadosTeste(DomusControl dc) {
        Utilizador ana = dc.criarUtilizador("Ana");
        Utilizador bruno = dc.criarUtilizador("Bruno");
        Utilizador carla = dc.criarUtilizador("Carla");

        Casa casa1 = dc.criarCasa("Casa Jardim");
        Casa casa2 = dc.criarCasa("Casa Centro");
        Casa casa3 = dc.criarCasa("Casa Praia");

        dc.adicionarCasaAAdministrador(ana, casa1);
        dc.adicionarCasaAUtilizador(ana, casa2);

        dc.adicionarCasaAAdministrador(bruno, casa2);
        dc.adicionarCasaAUtilizador(bruno, casa3);

        dc.adicionarCasaAAdministrador(carla, casa3);
        dc.adicionarCasaAUtilizador(carla, casa1);

        Divisao sala1 = criarDivisaoEObter(dc, casa1, "Sala");
        Divisao quarto1 = criarDivisaoEObter(dc, casa1, "Quarto");

        Lampada l1 = new Lampada(dc.aumentarIdDispositivo(), "Philips", "Hue", 9.5, "Branco");
        Cortina c1 = new Cortina(dc.aumentarIdDispositivo(), "Somfy", "Smart", 18.0, 50);
        SensorAgua sACasa1 = new SensorAgua(dc.aumentarIdDispositivo(), "Bosch", "RainSensor", 0.3, 0, false);
        dc.adicionarDispositivo(sala1, l1);
        dc.adicionarDispositivo(sala1, c1);
        dc.adicionarDispositivo(sala1, sACasa1);

        Lampada l2 = new Lampada(dc.aumentarIdDispositivo(), "Osram", "Bed", 7.5, "Quente");
        Tomada t1 = new Tomada(dc.aumentarIdDispositivo(), "TP-Link", "Plug", 5.0);
        dc.adicionarDispositivo(quarto1, l2);
        dc.adicionarDispositivo(quarto1, t1);

        Divisao sala2 = criarDivisaoEObter(dc, casa2, "Sala");
        Divisao cozinha2 = criarDivisaoEObter(dc, casa2, "Cozinha");

        Lampada l3 = new Lampada(dc.aumentarIdDispositivo(), "IKEA", "Tradfri", 8.0, "Branco");
        SensorLuz s1 = new SensorLuz(dc.aumentarIdDispositivo(), "Aqara", "Sensor", 0.5, 40.0);
        dc.adicionarDispositivo(sala2, l3);
        dc.adicionarDispositivo(sala2, s1);

        Lampada l4 = new Lampada(dc.aumentarIdDispositivo(), "Philips", "Kitchen", 8.2, "Frio");
        SensorAgua s2 = new SensorAgua(dc.aumentarIdDispositivo(), "Bosch", "Rain", 0.3, 0, false);
        dc.adicionarDispositivo(cozinha2, l4);
        dc.adicionarDispositivo(cozinha2, s2);

        Divisao sala3 = criarDivisaoEObter(dc, casa3, "Sala");
        Divisao garagem3 = criarDivisaoEObter(dc, casa3, "Garagem");

        Lampada l5 = new Lampada(dc.aumentarIdDispositivo(), "Nanoleaf", "Color", 9.0, "Azul");
        Cortina c2 = new Cortina(dc.aumentarIdDispositivo(), "Somfy", "Premium", 16.0, 60);
        dc.adicionarDispositivo(sala3, l5);
        dc.adicionarDispositivo(sala3, c2);

        PortaoGaragem p1 = new PortaoGaragem(dc.aumentarIdDispositivo(), "Nice", "Gate", 50.0, 0);
        dc.adicionarDispositivo(garagem3, p1);

        l1.ligarDispositivo();
        l3.ligarDispositivo();
        l5.ligarDispositivo();

        for (int i = 0; i < 3; i++) {
            t1.desligarDispositivo();
            t1.ligarDispositivo();
        }

        dc.passarTempoGlobal(1.0);

        dc.criarEscalonamentoAbrirCortinas(casa1.getId());
        dc.criarEscalonamentoModoNoturno(casa2.getId());

        dc.criarCenariosObrigatorios(casa1.getId());
    }

    private static Divisao criarDivisaoEObter(DomusControl dc, Casa casa, String nomeDivisao) {
        return dc.criarDivisao(casa, nomeDivisao);
    }

    private static void gerarCenarioReal(DomusControl dc) {
        // 1. Criar Utilizador de Teste
        Utilizador u = dc.criarUtilizador("Admin_Teste");

        // 2. CENÁRIO PARA "TOP DIVISÕES": Criar uma casa com divisões muito povoadas
        Casa c1 = dc.criarCasa("Vivenda das Estatísticas");
        dc.adicionarCasaAAdministrador(u, c1);

        dc.criarDivisao(c1, "Escritório");
        Divisao escritorio = c1.getDivisoes().get(1); // Aceder pelo ID gerado

        // Adicionar 5 dispositivos no escritório para garantir o 1º lugar no Top
        for(int i = 0; i < 5; i++) {
            escritorio.adicionarDispositivo(new Lampada(dc.aumentarIdDispositivo(), "Xiaomi", "SmartLight V" + i, 8.5, "Branco"));
        }

        // 3. CENÁRIO PARA "CASA QUE MAIS CONSOME": Criar uma casa com consumo elevado
        Casa c2 = dc.criarCasa("Centro de Dados");
        dc.adicionarCasaAAdministrador(u, c2);
        dc.criarDivisao(c2, "Servidores");
        Divisao serverRoom = c2.getDivisoes().get(2);

        // Dispositivo com consumo massivo ligado
        Dispositivo server = new Tomada(dc.aumentarIdDispositivo(), "Dell", "PowerEdge", 4500.0);
        server.ligarDispositivo();
        serverRoom.adicionarDispositivo(server);

        // 4. CENÁRIO PARA "MAIS ATIVAÇÕES": Simular uso intensivo num dispositivo
        Dispositivo switchNet = new Tomada(dc.aumentarIdDispositivo(), "Cisco", "Catalyst", 50.0);
        serverRoom.adicionarDispositivo(switchNet);

        // Simular 15 ativações (ligar/desligar)
        for(int i = 0; i < 15; i++) {
            switchNet.ligarDispositivo();
            switchNet.desligarDispositivo();
        }
    }

    
}