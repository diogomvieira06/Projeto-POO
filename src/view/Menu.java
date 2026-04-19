package src.view;

import src.controller.DomusControl;
import src.model.*;

public class Menu {

    public static void exibirMenuPrincipal() {
        System.out.println("\n--- MENU PRINCIPAL ---");
        System.out.println("1. Gestão de Casas");
        System.out.println("2. Criar Nova Casa");
        System.out.println("3. Automações (Modo ECO)");
        System.out.println("4. Ligar Todos os Dispositivos de uma Casa");
        System.out.println("0. Sair e Gravar");
        System.out.print("Escolha: ");
    }

    // --- SUBMENUS ---

    public static void submenuCasas(Utilizador u_atual, DomusControl dc) {
        while (true) {
            System.out.println("\n--- AS MINHAS CASAS ---");
            dc.listarCasasdeAdministrador(u_atual);
            dc.listarCasasdeUtilizador(u_atual);
            System.out.print("\nID da casa para aceder (0 para voltar): ");
            int id = InputValidator.lerInteiro();

            if (id == 0) break;

            Casa casa = dc.encontrarCasaPorId(id);
            if (casa != null && u_atual.podeUsarCasa(casa)) {
                menuInternoCasa(casa, u_atual, dc);
            } else {
                System.out.println("Erro: Acesso negado ou ID de casa inválido.");
            }
        }
    }



    public static void menuInternoCasa(Casa casa, Utilizador u_atual, DomusControl dc) {
        boolean eAdmin = u_atual.podeAdministrarCasa(casa);
        while (true) {
            System.out.println("\n--- CASA: " + casa.getAlcunha() + " ---");
            System.out.println("Perfil: " + (eAdmin ? "ADMINISTRADOR" : "UTILIZADOR"));

            System.out.println("\nDivisões nesta casa:");
            casa.listarDivisoes();

            System.out.println("\n1. Ver Estado Global (Consumo e Dispositivos)");
            System.out.println("2. Ver Lista de Utilizadores");
            System.out.println("3. Selecionar Divisão (Entrar)");
            if (eAdmin) { 
                System.out.println("4. Adicionar Nova Divisão (Admin)");
                System.out.println("5. Remover Divisão (Admin)");
            }
            System.out.println("0. Voltar");

            System.out.print("Opção: ");
            int opt = InputValidator.lerInteiro();
            if (opt == 0) break;

            switch (opt) {
                case 1 -> dc.listarEstadoGlobalCasa(casa);
                case 2 -> {
                    while (true) {
                        dc.listarPessoasComAcessoACasa(casa);
                        if (eAdmin) {
                            System.out.println("\nOpções de gestão de utilizadores:");
                            System.out.println("1. Criar novo utilizador");
                            System.out.println("2. Adicionar Utilizador à Casa");
                            System.out.println("3. Remover Utilizador da Casa");
                            System.out.println("4. Adicionar Administrador à Casa");
                            System.out.println("5. Remover Administrador da Casa");
                            System.out.println("0. Voltar");

                            System.out.print("Escolha: ");
                            int userOpt = InputValidator.lerInteiro();
                            if (userOpt == 0) break;

                            switch (userOpt) {
                                case 1 -> {
                                    System.out.print("Nome do novo utilizador: ");
                                    String nome = InputValidator.lerLinha();
                                    Utilizador novo = dc.criarUtilizador(nome);
                                    System.out.println("Novo utilizador criado com sucesso.");
                                }
                                
                                case 2 -> {
                                    System.out.println("Lista de Utilizadores disponíveis:");
                                    dc.listarUtilizadoresDisponiveisAAdicionar(casa);
                                    System.out.print("ID do utilizador a adicionar: ");
                                    int idAdd = InputValidator.lerInteiro();
                                    Utilizador uAdd = dc.encontrarUtilizadorPorId(idAdd);
                                    if (uAdd != null && !uAdd.podeUsarCasa(casa)) {
                                        dc.adicionarCasaAUtilizador(uAdd, casa);
                                        System.out.println("Utilizador adicionado com sucesso.");
                                    } else {
                                        System.out.println("Erro: ID de utilizador inválido ou utilizador já tem acesso à casa.");
                                    }
                                }
                                case 3 -> {
                                    System.out.print("ID do utilizador a remover: ");
                                    int idRem = InputValidator.lerInteiro();
                                    Utilizador uRem = dc.encontrarUtilizadorPorId(idRem);

                                    int total_administadores = dc.contarAdministradoresCasa(casa);
                                    if (total_administadores <= 1 && u_atual.getId() == idRem) {
                                        System.out.println("Erro: Você é o único administrador desta casa. Adicione outro administrador antes de remover seu acesso.");
                                        continue; // Impede que o último administrador remova a si mesmo
                                    }
                                    if (uRem != null && uRem.podeUsarCasa(casa)) {
                                        dc.removerCasaDeUtilizador(uRem, casa);
                                        if (u_atual.getId() == idRem) {
                                            System.out.println("Aviso: Você removeu seu próprio acesso a esta casa. Voltando ao menu principal...");
                                            return; // Volta para o menu principal se o utilizador remover a si mesmo
                                        }
                                        else System.out.println("Utilizador removido com sucesso.");
                                    } else {
                                        System.out.println("Erro: ID de utilizador inválido ou utilizador não tem acesso à casa.");
                                    }

                                }
                                case 4 -> {
                                    System.out.println("Lista de Administradores disponíveis:");
                                    dc.listarAdministradoresDisponiveisAAdicionar(casa);
                                    System.out.print("ID do utilizador a tornar administrador: ");
                                    int idAdminAdd = InputValidator.lerInteiro();
                                    Utilizador uAdminAdd = dc.encontrarUtilizadorPorId(idAdminAdd);
                                    if (uAdminAdd != null && !uAdminAdd.serAdmin(casa)) {
                                        dc.adicionarCasaAAdministrador(uAdminAdd, casa);
                                        System.out.println("Administrador adicionado com sucesso.");
                                    } else {
                                        System.out.println("Erro: ID de utilizador inválido ou utilizador já é administrador da casa.");
                                    }
                                }
                                case 5 -> {
                                    System.out.print("ID do administrador a remover: ");
                                    int idAdminRem = InputValidator.lerInteiro();
                                    Utilizador uAdminRem = dc.encontrarUtilizadorPorId(idAdminRem);

                                    int total_administadores = dc.contarAdministradoresCasa(casa);
                                    if (total_administadores <= 1 && u_atual.getId() == idAdminRem) {
                                        System.out.println("Erro: Você é o único administrador desta casa. Adicione outro administrador antes de remover seu acesso.");
                                        continue; // Impede que o último administrador remova a si mesmo
                                    }
                                    if (uAdminRem != null && uAdminRem.serAdmin(casa)) {
                                        dc.removerPermissoesAdmin(uAdminRem, casa);
                                        if (u_atual.getId() == idAdminRem) {
                                            System.out.println("Aviso: Você removeu suas permissões de administrador desta casa. Voltando ao menu principal...");
                                            return; // Volta para o menu principal se o utilizador remover a si mesmo
                                        }
                                        else System.out.println("Administrador removido com sucesso.");
                                    }
                                    else {
                                        System.out.println("Erro: ID de utilizador inválido ou utilizador não é administrador da casa.");
                                    }
                                }
                                default -> System.out.println("Opção inválida.");
                            }
                        } else {
                            System.out.println("Pressione 0 para voltar.");
                            int backOpt = InputValidator.lerInteiro();
                            if (backOpt == 0) break;
                            else System.out.println("Opção inválida.");
                        }
                    }
                }
                case 3 -> {
                    System.out.print("ID da divisão para entrar: ");
                    int idDiv = InputValidator.lerInteiro();
                    Divisao div = dc.encontrarDivisaoPorId(casa, idDiv);
                    if (div != null) menuDispositivos(div, eAdmin, dc);
                    else System.out.println("Erro: Divisão não encontrada.");
                }
                case 4 -> {
                    if (eAdmin) {
                        System.out.print("Nome da nova divisão: ");
                        dc.criarDivisao(casa, InputValidator.lerLinha());
                    } else System.out.println("Erro: Permissões insuficientes.");
                }
                case 5 -> {
                    if (eAdmin) {
                        System.out.print("ID da divisão para remover: ");
                        int idRem = InputValidator.lerInteiro();
                        Divisao rem = dc.encontrarDivisaoPorId(casa, idRem);
                        if (rem != null) dc.removerDivisao(casa, rem);
                        else System.out.println("Erro: ID de divisão inválido.");
                    } else System.out.println("Erro: Permissões insuficientes.");
                }
                default -> System.out.println("Opção inválida.");
            }
        }
    }

    public static void menuDispositivos(Divisao div, boolean eAdmin, DomusControl dc) {
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
            int opt = InputValidator.lerInteiro();
            if (opt == 0) break;

            switch (opt) {
                case 1 -> {
                    System.out.print("ID do dispositivo: ");
                    int idD = InputValidator.lerInteiro();
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
                        int idR = InputValidator.lerInteiro();
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

    public static void adicionarDispositivoSubmenu(Divisao div, DomusControl dc) {
        System.out.println("\n1.Lâmpada | 2.Tomada | 3.Cortina | 4.Coluna | 5.Portão");
        System.out.print("Tipo: ");
        int tipo = InputValidator.lerInteiro();
        if (tipo < 1 || tipo > 5) { System.out.println("Tipo inválido."); return; }

        System.out.print("Marca: "); String marca = InputValidator.lerLinha();
        System.out.print("Modelo: "); String modelo = InputValidator.lerLinha();
        System.out.print("Consumo (Wh): "); double cons = InputValidator.lerDouble();

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



    public static void menuAutomacao(Utilizador u, DomusControl dc) {
        System.out.println("\n--- MODO ECO ---");
        dc.listarCasasdeAdministrador(u);
        dc.listarCasasdeUtilizador(u);
        System.out.print("ID da casa: ");
        int id = InputValidator.lerInteiro();

        Casa casa = dc.encontrarCasaPorId(id);
        if (casa != null && u.podeUsarCasa(casa)) {
            for (Divisao d : casa.getDivisoes().values()) {
                for (Dispositivo disp : d.getDispositivos().values()) disp.desligarDispositivo();
            }
            System.out.println("Modo ECO aplicado em " + casa.getAlcunha());
        } else System.out.println("Erro: Acesso negado.");
    }

    public static void menuLigarDispositivo(Utilizador u, DomusControl dc){
            System.out.println("\n--- LIGAR DISPOSITIVO ---");
            dc.listarCasasdeAdministrador(u);
            dc.listarCasasdeUtilizador(u);
            System.out.print("ID da casa: ");
            int id = InputValidator.lerInteiro();
    
            Casa casa = dc.encontrarCasaPorId(id);
            if (casa != null && u.podeUsarCasa(casa)) {
                for (Divisao d : casa.getDivisoes().values()) {
                    for (Dispositivo disp : d.getDispositivos().values()) disp.ligarDispositivo();
                }
                System.out.println("Todos os dispositivos em " + casa.getAlcunha() + " foram ligados.");
            } else System.out.println("Erro: Acesso negado.");
    }
}