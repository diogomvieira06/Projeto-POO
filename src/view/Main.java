package src.view;

import java.util.*;
import src.controller.DomusControl;
import src.model.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        DomusControl domusControl = new DomusControl();

        // 1. CARREGAMENTO OU SEED
        DomusControl carregado = DomusControl.carregarEstado("estado_projeto.dat");
        if (carregado != null) {
            domusControl = carregado;
            System.out.println("Estado anterior carregado com sucesso!\n");
        } else {
            System.out.println("Iniciando de raiz. Criando configuração de teste...");
            inicializarDadosTeste(domusControl);
        }

        // 2. LOGIN COM CICLO INFINITO (ROBUSTEZ)
        Utilizador utilizador_atual = null;
        while (utilizador_atual == null) {
            System.out.println("Bem-vindo ao DomusControl!");
            System.out.print("Introduza o seu ID de Utilizador (ou 0 para encerrar): ");
            int idLogin = lerInteiro();

            if (idLogin == 0) {
                System.out.println("A encerrar o programa...");
                return;
            }

            utilizador_atual = domusControl.encontrarUtilizadorPorId(idLogin);

            if (utilizador_atual == null) {
                System.out.println("Erro: Utilizador com ID " + idLogin + " não encontrado. Tente novamente.");
            }
        }
        System.out.println("Sessão iniciada como: " + utilizador_atual.getNome());

        // 3. MENU PRINCIPAL
        boolean sair = false;
        while (!sair) {
            exibirMenuPrincipal();
            int opcao = lerInteiro();

            switch (opcao) {
                case 1 -> submenuCasas(utilizador_atual, domusControl);
                case 2 -> {
                    System.out.print("Alcunha da nova casa: ");
                    String alcunha = sc.nextLine();
                    Casa nova = domusControl.criarCasa(alcunha);
                    domusControl.adicionarCasaAAdministrador(utilizador_atual, nova);
                    System.out.println("Casa criada com sucesso!");
                }
                case 3 -> menuAutomacao(utilizador_atual, domusControl);
                case 0 -> sair = true;
                default -> System.out.println("Erro: Opção '" + opcao + "' inválida.");
            }
        }

        // 4. PERSISTÊNCIA
        try {
            domusControl.guardarEstado("estado_projeto.dat");
            System.out.println("Estado guardado com sucesso. Até à próxima!");
        } catch (Exception e) {
            System.out.println("Erro crítico ao gravar dados.");
        }
        sc.close();
    }

    // --- SUBMENUS ---

    private static void submenuCasas(Utilizador u, DomusControl dc) {
        while (true) {
            System.out.println("\n--- AS MINHAS CASAS ---");
            dc.listarCasasdeAdministrador(u);
            dc.listarCasasdeUtilizador(u);
            System.out.print("\nID da casa para aceder (0 para voltar): ");
            int id = lerInteiro();

            if (id == 0) break;

            Casa casa = dc.encontrarCasaPorId(id);
            if (casa != null && u.podeUsarCasa(casa)) {
                menuInternoCasa(casa, u, dc);
            } else {
                System.out.println("Erro: Acesso negado ou ID de casa inválido.");
            }
        }
    }

    private static void menuInternoCasa(Casa casa, Utilizador u, DomusControl dc) {
        boolean eAdmin = u.podeAdmistrarCasa(casa);
        while (true) {
            System.out.println("\n--- CASA: " + casa.getAlcunha() + " ---");
            System.out.println("Perfil: " + (eAdmin ? "ADMINISTRADOR" : "UTILIZADOR"));

            System.out.println("\nDivisões nesta casa:");
            casa.listarDivisoes();

            System.out.println("\n1. Selecionar Divisão (Entrar)");
            System.out.println("2. Ver Estado Global (Consumo e Dispositivos)");
            if (eAdmin) System.out.println("3. Adicionar Nova Divisão (Admin)");
            System.out.println("0. Voltar");

            System.out.print("Opção: ");
            int opt = lerInteiro();
            if (opt == 0) break;

            switch (opt) {
                case 1 -> {
                    System.out.print("ID da divisão para entrar: ");
                    int idDiv = lerInteiro();
                    Divisao div = dc.encontrarDivisaoPorId(casa, idDiv);
                    if (div != null) menuDispositivos(div, eAdmin, dc);
                    else System.out.println("Erro: Divisão não encontrada.");
                }
                case 2 -> dc.listarEstadoGlobalCasa(casa);
                case 3 -> {
                    if (eAdmin) {
                        System.out.print("Nome da nova divisão: ");
                        dc.criarDivisao(casa, sc.nextLine());
                    } else System.out.println("Erro: Permissões insuficientes.");
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuDispositivos(Divisao div, boolean eAdmin, DomusControl dc) {
        while (true) {
            System.out.println("\n--- DIVISÃO: " + div.getNome() + " ---");

            // VISUALIZAÇÃO DOS DISPOSITIVOS (REQUISITO 8.1 e 9.0)
            System.out.println("Dispositivos instalados:");
            div.listarDispositivos();

            System.out.println("\n1. Operar Dispositivo (Ligar/Desligar)");
            if (eAdmin) {
                System.out.println("2. Adicionar Dispositivo (Admin)");
                System.out.println("3. Remover Dispositivo (Admin)");
            }
            System.out.println("0. Voltar");

            System.out.print("Opção: ");
            int opt = lerInteiro();
            if (opt == 0) break;

            switch (opt) {
                case 1 -> {
                    System.out.print("ID do dispositivo: ");
                    int idD = lerInteiro();
                    Dispositivo d = div.obterDispositivoPorId(idD);
                    if (d != null) {
                        if (d.getEstado().equals("LIGADO")) d.desligarDispositivo();
                        else d.ligarDispositivo();
                        System.out.println("Novo estado de " + d.getModelo() + ": " + d.getEstado());
                    } else System.out.println("Erro: Dispositivo não encontrado.");
                }
                case 2 -> { if (eAdmin) adicionarDispositivoSubmenu(div, dc); }
                case 3 -> {
                    if (eAdmin) {
                        System.out.print("ID para remover: ");
                        int idR = lerInteiro();
                        Dispositivo r = div.obterDispositivoPorId(idR);
                        if (r != null) {
                            div.removerDispositivo(r);
                            System.out.println("Dispositivo removido.");
                        } else System.out.println("Erro: ID inválido.");
                    }
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    private static void adicionarDispositivoSubmenu(Divisao div, DomusControl dc) {
        System.out.println("\n1.Lâmpada | 2.Tomada | 3.Cortina | 4.Coluna | 5.Portão");
        System.out.print("Tipo: ");
        int tipo = lerInteiro();
        if (tipo < 1 || tipo > 5) { System.out.println("Tipo inválido."); return; }

        System.out.print("Marca: "); String marca = sc.nextLine();
        System.out.print("Modelo: "); String modelo = sc.nextLine();
        System.out.print("Consumo (Wh): "); double cons = lerDouble();

        int idNovo = dc.aumentarIdDispositivo();
        Dispositivo novo = null;

        switch (tipo) {
            case 1 -> novo = new Lampada(idNovo, marca, modelo, cons, 80, "Branco");
            case 2 -> novo = new Tomada(idNovo, marca, modelo, cons);
            case 3 -> novo = new Curtina(idNovo, marca, modelo, cons, 0);
            case 4 -> novo = new ColunaSom(idNovo, marca, modelo, cons, 50);
            case 5 -> novo = new PortaoGaragem(idNovo, marca, modelo, cons, 0);
        }
        if (novo != null) {
            div.adicionarDispositivo(novo);
            System.out.println("Sucesso: " + novo.getClass().getSimpleName() + " adicionada com ID " + idNovo);
        }
    }

    // --- VALIDAÇÃO DE INPUTS (ROBUSTEZ - REQUISITO 8.0) ---

    private static int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Erro: Introduza um número inteiro: ");
            }
        }
    }

    private static double lerDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Erro: Introduza um valor decimal (ex: 15.5): ");
            }
        }
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Gestão de Casas");
        System.out.println("2. Criar Nova Casa");
        System.out.println("3. Automações (Modo ECO)");
        System.out.println("0. Sair e Gravar");
        System.out.print("Escolha: ");
    }

    private static void menuAutomacao(Utilizador u, DomusControl dc) {
        System.out.println("\n--- MODO ECO ---");
        dc.listarCasasdeAdministrador(u);
        dc.listarCasasdeUtilizador(u);
        System.out.print("ID da casa: ");
        int id = lerInteiro();

        Casa casa = dc.encontrarCasaPorId(id);
        if (casa != null && u.podeUsarCasa(casa)) {
            for (Divisao d : casa.getDivisoes().values()) {
                for (Dispositivo disp : d.getDispositivos().values()) disp.desligarDispositivo();
            }
            System.out.println("Modo ECO aplicado em " + casa.getAlcunha());
        } else System.out.println("Erro: Acesso negado.");
    }

    private static void inicializarDadosTeste(DomusControl dc) {
        dc.criarUtilizador("uti1");
        dc.criarUtilizador("uti2");
        dc.criarCasa("Casa do uti1");
        dc.criarCasa("Casa do uti2");
        Casa c1 = dc.encontrarCasaPorId(1);
        Casa c2 = dc.encontrarCasaPorId(3);
        Utilizador u1 = dc.encontrarUtilizadorPorId(1);
        Utilizador u2 = dc.encontrarUtilizadorPorId(2);

        if (c1 != null) dc.criarDivisao(c1, "Sala");
        if (c2 != null) dc.criarDivisao(c2, "Quarto");

        dc.adicionarCasaAAdministrador(u1, c1);
        dc.adicionarCasaAUtilizador(u1, c2);
        dc.adicionarCasaAAdministrador(u2, c2);
        dc.adicionarCasaAUtilizador(u2, c1);
    }
}