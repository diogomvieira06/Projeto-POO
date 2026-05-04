package src.view;

import src.automacao.Escalonamento;
import src.controller.DomusControl;
import src.model.*;
import java.util.Iterator;
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

            utilizador_atual = domusControl.encontrarUtilizadorPorId(idLogin);
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
            String opts = "1. Gestão de Casas\n2. Criar Nova Casa\n3. Automações\n4. Ligar Tudo Numa Casa\n5. Desligar Tudo Numa Casa\n6. Estatisticas\n7. Mudar Utilizador\n8. Escalonamentos\n9. Cenários\n0. Sair e Gravar";

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
                case 5 -> Menu.menuDesligarDispositivo(utilizador_atual, domusControl);
                case 6 -> {
                    if (!temCasas) {
                        ConsoleUI.mostrarErro("Sem permissões: não tem casas associadas.");
                    } else {
                        Menu.menuEstatisticas(utilizador_atual, domusControl);
                    }
                }
                case 7 -> {
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

                        utilizador_atual = domusControl.encontrarUtilizadorPorId(idLogin);
                        if (utilizador_atual == null) {
                            System.out.println("Erro: Utilizador não encontrado. Prima Enter...");
                            InputValidator.lerLinha();
                        }
                    }
                }
                case 8 -> Menu.menuEscalonamentos(utilizador_atual, domusControl);
                case 9 -> Menu.menuCenarios(utilizador_atual, domusControl);
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
        Utilizador u1 = dc.criarUtilizador("Ana");
        Utilizador u2 = dc.criarUtilizador("Bruno");
        Utilizador u3 = dc.criarUtilizador("Carla");

        Casa c1 = dc.criarCasa("Casa Jardim");
        Casa c2 = dc.criarCasa("Casa Centro");
        Casa c3 = dc.criarCasa("Casa Praia");

        dc.adicionarCasaAAdministrador(u1, c1);
        dc.adicionarCasaAUtilizador(u2, c1);
        dc.adicionarCasaAUtilizador(u3, c1);

        dc.adicionarCasaAAdministrador(u2, c2);
        dc.adicionarCasaAUtilizador(u1, c2);
        dc.adicionarCasaAUtilizador(u3, c2);

        dc.adicionarCasaAAdministrador(u3, c3);
        dc.adicionarCasaAUtilizador(u1, c3);
        dc.adicionarCasaAUtilizador(u2, c3);

        dc.criarDivisao(c1, "Sala");
        dc.criarDivisao(c1, "Cozinha");
        dc.criarDivisao(c1, "Quarto");

        Iterator<Divisao> divisoesC1 = c1.getDivisoes().values().iterator();
        if (divisoesC1.hasNext()) dc.adicionarDispositivo(divisoesC1.next(), new Lampada(dc.aumentarIdDispositivo(), "Philips", "Hue", 10.0, 80, "Branco"));
        if (divisoesC1.hasNext()) dc.adicionarDispositivo(divisoesC1.next(), new Tomada(dc.aumentarIdDispositivo(), "Xiaomi", "Smart Plug", 5.0));
        if (divisoesC1.hasNext()) dc.adicionarDispositivo(divisoesC1.next(), new ColunaSom(dc.aumentarIdDispositivo(), "Sony", "SRS-XB13", 12.0, 40));

        dc.criarDivisao(c2, "Sala");
        dc.criarDivisao(c2, "Escritório");
        dc.criarDivisao(c2, "Garagem");

        Iterator<Divisao> divisoesC2 = c2.getDivisoes().values().iterator();
        if (divisoesC2.hasNext()) dc.adicionarDispositivo(divisoesC2.next(), new Lampada(dc.aumentarIdDispositivo(), "IKEA", "Tradfri", 9.0, 70, "Amarelo"));
        if (divisoesC2.hasNext()) dc.adicionarDispositivo(divisoesC2.next(), new Curtina(dc.aumentarIdDispositivo(), "Somfy", "Curtina Pro", 20.0, 50));
        if (divisoesC2.hasNext()) dc.adicionarDispositivo(divisoesC2.next(), new PortaoGaragem(dc.aumentarIdDispositivo(), "Nice", "Robus", 45.0, 0));

        dc.criarDivisao(c3, "Sala");
        dc.criarDivisao(c3, "Suite");
        dc.criarDivisao(c3, "Varanda");

        Iterator<Divisao> divisoesC3 = c3.getDivisoes().values().iterator();
        if (divisoesC3.hasNext()) dc.adicionarDispositivo(divisoesC3.next(), new ColunaSom(dc.aumentarIdDispositivo(), "JBL", "Flip", 15.0, 55));
        if (divisoesC3.hasNext()) dc.adicionarDispositivo(divisoesC3.next(), new Tomada(dc.aumentarIdDispositivo(), "TP-Link", "Tapo", 6.0));
        if (divisoesC3.hasNext()) dc.adicionarDispositivo(divisoesC3.next(), new Lampada(dc.aumentarIdDispositivo(), "Osram", "Smart+", 11.0, 90, "Branco Frio"));
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