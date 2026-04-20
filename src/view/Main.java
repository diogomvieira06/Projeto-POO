package src.view;

import src.controller.DomusControl;
import src.model.*;

public class Main {
    public static void main(String[] args) {
        DomusControl domusControl = new DomusControl();

        // Carregamento do estado
        DomusControl carregado = DomusControl.carregarEstado("estado_projeto.dat");
        if (carregado != null) {
            domusControl = carregado;
        } else {
            inicializarDadosTeste(domusControl);
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
            String opts = "1. Gestão de Casas\n2. Criar Nova Casa\n3. Automações (Modo ECO)\n4. Ligar Tudo\n0. Sair e Gravar";

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
}