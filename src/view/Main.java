package src.view;

import src.controller.DomusControl;
import src.model.*;

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
            DomusControl carregado = DomusControl.carregarEstado("estado_projeto.dat");
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
            String info = "Bem-vindo ao Sistema DomusControl.\nIdentifique-se para gerir a sua habitação inteligente.";
            String opts = "Introduza o seu ID de utilizador\n(Ou digite 0 para encerrar o programa)";

            ConsoleUI.desenharDashboard("LOGIN", info, opts);

            System.out.print("\nID: ");
            int idLogin = InputValidator.lerInteiro();

            if (idLogin == 0) return;

            utilizador_atual = domusControl.encontrarUtilizadorPorId(idLogin);
            if (utilizador_atual == null) {
                System.out.println("Erro: Utilizador não encontrado. Prima Enter...");
                InputValidator.lerLinha();
            }
        }

        // 2. MENU PRINCIPAL
        boolean sair = false;
        while (!sair) {
            String info = "Sessão: " + utilizador_atual.getNome() + "\nO que deseja fazer hoje?";
            String opts = "1. Gestão de Casas\n2. Criar Nova Casa\n3. Automações (Modo ECO)\n4. Ligar Tudo\n5. Estatisticas\n0. Sair e Gravar";

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
                case 4 -> Menu.menuLigarDispositivo(utilizador_atual, domusControl);
                case 5 -> Menu.menuEstatisticas(domusControl);
                case 0 -> sair = true;
                default -> { System.out.println("Opção inválida."); InputValidator.lerLinha(); }
            }
        }

        domusControl.guardarEstado("estado_projeto.dat");
    }

    private static void inicializarDadosTeste(DomusControl dc) {
        Utilizador u1 = dc.criarUtilizador("uti1");
        Casa c1 = dc.criarCasa("Vivenda Flores");
        dc.adicionarCasaAAdministrador(u1, c1);
        dc.criarDivisao(c1, "Sala");
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
            escritorio.adicionarDispositivo(new Lampada(dc.aumentarIdDispositivo(), "Xiaomi", "SmartLight V" + i, 8.5, 100, "Branco"));
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