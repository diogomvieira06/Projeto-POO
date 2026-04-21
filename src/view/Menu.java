package src.view;

import src.controller.DomusControl;
import src.model.*;
import java.util.*;

public class Menu {

    public static void submenuCasas(Utilizador u, DomusControl dc) {
        while (true) {
            StringBuilder info = new StringBuilder("AS MINHAS CASAS:\n\n");

            info.append("[ADMINISTRADOR]\n");
            if (u.getCasasAdministradas().isEmpty()) info.append(" > (Vazio)\n");
            for (Casa c : u.getCasasAdministradas().values()) {
                info.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));
            }

            info.append("\n[UTILIZADOR]\n");
            boolean temUser = false;
            for (Casa c : u.getCasasUtilizador().values()) {
                if (!u.serAdmin(c)) {
                    info.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));
                    temUser = true;
                }
            }
            if (!temUser) info.append(" > (Vazio)\n");

            // Atualizamos as opções para incluir a remoção
            String opts = "ID da casa para aceder\n" +
                    "1. Aceder a uma Casa\n" +
                    "9. Remover uma Casa (Apenas Admin)\n" +
                    "0. Voltar";

            ConsoleUI.desenharDashboard("GESTÃO DE CASAS", info.toString(), opts);

            System.out.print("\nEscolha opção: ");
            int id = InputValidator.lerInteiro();
            int id2;
            if (id == 0) break;

            else if (id == 1) {
                System.out.print("ID da casa para aceder: ");
                id2 = InputValidator.lerInteiro();
                            // Acesso normal à casa
                Casa casa = dc.encontrarCasaPorId(id2);
                if (casa != null && u.podeUsarCasa(casa)) {
                    menuInternoCasa(casa, u, dc);
            }
            }

            // Lógica de remoção
            else if (id == 9) {
                System.out.print("ID da casa que deseja eliminar: ");
                int idEliminar = InputValidator.lerInteiro();
                Casa casaAEliminar = dc.encontrarCasaPorId(idEliminar);

                // Verificação: a casa existe e eu sou o dono/admin?
                if (casaAEliminar != null && u.serAdmin(casaAEliminar)) {
                    System.out.print("Tem a certeza? Isto apagará todos os dispositivos! (s/n): ");
                    if (InputValidator.lerLinha().equalsIgnoreCase("s")) {
                        dc.eliminarCasaTotalmente(casaAEliminar);
                    }
                } else if (idEliminar == 0) { // Se não for 0, é porque tentou eliminar algo
                continue; // Volta ao menu sem mostrar erro
                } else {
                    ConsoleUI.mostrarErro("Casa não encontrada ou sem permissões de Admin.");
                }
            }
            else {
                ConsoleUI.mostrarErro("Opção inválida.");
                }
        }
    }

    public static void menuInternoCasa(Casa casa, Utilizador u, DomusControl dc) {
        boolean eAdmin = u.podeAdministrarCasa(casa);
        while (true) {
            StringBuilder info = new StringBuilder("HABITAÇÃO: " + casa.getAlcunha() + "\n");
            info.append("PERFIL: ").append(eAdmin ? "ADMINISTRADOR" : "UTILIZADOR").append("\n\n");
            info.append("DIVISÕES:\n");
            for (Divisao d : casa.getDivisoes().values()) {
                info.append(String.format(" [%d] %-15s (%d dispositivos)\n", d.getId(), d.getNome(), d.getDispositivos().size()));
            }

            String opts = "1. Ver Estado Global\n2. Gestão de Utilizadores\n3. Entrar em Divisão\n" +
                    (eAdmin ? "4. Criar Divisão\n5. Remover Divisão\n" : "") + "0. Voltar";

            ConsoleUI.desenharDashboard("PAINEL DA CASA", info.toString(), opts);
            System.out.print("\nOpção: ");
            int opt = InputValidator.lerInteiro();
            if (opt == 0) break;

            switch (opt) {
                case 1 -> {
                    ConsoleUI.desenharDashboard(
                            "ESTADO GLOBAL",
                            montarEstadoGlobalCasa(casa),
                            "Prima Enter para voltar"
                    );
                    InputValidator.lerLinha();
                }
                // --- CORRECÇÃO: Agora chama o novo submenu ---
                case 2 -> submenuGestaoUtilizadores(casa, u, dc);
                case 3 -> {
                    System.out.print("ID Divisão: ");
                    Divisao d = dc.encontrarDivisaoPorId(casa, InputValidator.lerInteiro());
                    if (d != null) menuDispositivos(d, eAdmin, dc);
                }
                case 4 -> { if(eAdmin) { System.out.print("Nome: "); dc.criarDivisao(casa, InputValidator.lerLinha()); } }
                case 5 -> {
                    if (eAdmin) {
                        System.out.print("ID Divisão para remover: ");
                        Divisao rem = dc.encontrarDivisaoPorId(casa, InputValidator.lerInteiro());
                        if (rem != null) dc.removerDivisao(casa, rem);
                    }
                }
            }
        }
    }

    private static String montarEstadoGlobalCasa(Casa casa) {
        StringBuilder estado = new StringBuilder("CASA: ").append(casa.getAlcunha()).append("\n\n");
        boolean temDispositivos = false;

        for (Divisao divisao : casa.getDivisoes().values()) {
            for (Dispositivo dispositivo : divisao.getDispositivos().values()) {
                estado.append("[")
                        .append(divisao.getNome())
                        .append("] (")
                        .append(dispositivo.getTipo())
                        .append(") ")
                        .append(dispositivo.getMarca())
                        .append(" ")
                        .append(dispositivo.getModelo())
                        .append(" (ID: ")
                        .append(dispositivo.getId())
                        .append(") -> ESTADO: ")
                        .append(dispositivo.getEstado())
                        .append(dispositivo.getDetalhesEspecificos())
                        .append("\n");
                temDispositivos = true;
            }
        }

        if (!temDispositivos) {
            estado.append("Esta casa ainda não possui dispositivos instalados.");
        }

        return estado.toString().stripTrailing();
    }

    public static void submenuGestaoUtilizadores(Casa casa, Utilizador u_sessao, DomusControl dc) {
        boolean eAdmin = u_sessao.serAdmin(casa); //
        while (true) {
            StringBuilder info = new StringBuilder("UTILIZADORES COM ACESSO A: " + casa.getAlcunha() + "\n\n");

            // Listagem de utilizadores no corpo do quadrado
            for (Utilizador uti : dc.getUtilizadores()) {
                if (uti.serAdmin(casa)) {
                    info.append(String.format(" [ADMIN]      ID: %d | %s\n", uti.getId(), uti.getNome()));
                } else if (uti.serUtilizador(casa)) {
                    info.append(String.format(" [UTILIZADOR] ID: %d | %s\n", uti.getId(), uti.getNome()));
                }
            }

            String opts;
            if (eAdmin) {
                opts = "1. Criar Novo Utilizador\n" +
                        "2. Adicionar Utilizador à Casa\n" +
                        "3. Remover Utilizador da Casa\n" +
                        "4. Tornar Administrador\n" +
                        "5. Remover Permissões de Administrador\n" +
                        "0. Voltar";
            } else {
                opts = "0. Voltar\n(Apenas Administradores podem gerir permissões)";
            }

            // Desenha o dashboard com as opções em lista vertical
            ConsoleUI.desenharDashboard("GESTÃO DE UTILIZADORES", info.toString(), opts);

            System.out.print("\nEscolha opção: ");
            int opt = InputValidator.lerInteiro(); //
            if (opt == 0) break;

            if (!eAdmin) continue;

            switch (opt) {
                case 1 -> {
                    System.out.print("Nome: ");
                    dc.criarUtilizador(InputValidator.lerLinha());
                }
                case 2 -> {
                    while (true) {
                        // Exibe apenas os utilizadores que não têm acesso à casa
                        StringBuilder lista = new StringBuilder("UTILIZADORES DISPONÍVEIS PARA ADIÇÃO:\n\n");
                        for (Utilizador uti : dc.getUtilizadores()) {
                            if (!uti.podeUsarCasa(casa)) {
                                lista.append(String.format("ID: %d | %s\n", uti.getId(), uti.getNome()));
                            }
                        }
                        ConsoleUI.desenharDashboard("ADICIONAR UTILIZADOR", lista.toString(), "ID do Utilizador a adicionar\n0. Voltar");
                        System.out.print("ID do Utilizador a adicionar: ");
                        int idAdd = InputValidator.lerInteiro();
                        if (idAdd == 0) break;
                        Utilizador add = dc.encontrarUtilizadorPorId(idAdd);
                        if (add != null && !add.podeUsarCasa(casa)) {
                            dc.adicionarCasaAUtilizador(add, casa);
                        }
                    }
                }
                case 3 -> {
                    System.out.print("ID a remover: ");
                    int idRem = InputValidator.lerInteiro();
                    Utilizador rem = dc.encontrarUtilizadorPorId(idRem);
                    if (rem != null && rem.podeUsarCasa(casa)) {
                        // Impede a remoção se for o último administrador
                        if (dc.contarAdministradoresCasa(casa) <= 1 && rem.serAdmin(casa)) {
                            ConsoleUI.mostrarErro("Não pode remover o último administrador.");
                        } else {
                            dc.removerCasaDeUtilizador(rem, casa);
                            if (rem.getId() == u_sessao.getId()) return;
                        }
                    }
                }
                case 4 -> {
                    System.out.print("ID para tornar Administrador: ");
                    Utilizador adm = dc.encontrarUtilizadorPorId(InputValidator.lerInteiro());
                    if (adm != null) dc.adicionarCasaAAdministrador(adm, casa);
                }
                case 5 -> {
                    System.out.print("ID para retirar privilégios de Admin: ");
                    int idT = InputValidator.lerInteiro();
                    Utilizador tir = dc.encontrarUtilizadorPorId(idT);
                    if (tir != null && dc.contarAdministradoresCasa(casa) > 1) {
                        dc.removerPermissoesAdmin(tir, casa);
                    } else if (tir != null && tir.serAdmin(casa)) {
                        ConsoleUI.mostrarErro("Não pode remover privilégios do último administrador.");
                    } else if (tir != null && !tir.podeUsarCasa(casa)) {
                        ConsoleUI.mostrarErro("Utilizador não encontrado.");
                    }
                    else {
                        ConsoleUI.mostrarErro("Utilizador não é administrador desta casa.");
                    }
                }
            }
        }
    }

    public static void menuDispositivos(Divisao div, boolean eAdmin, DomusControl dc) {
        while (true) {
            StringBuilder info = new StringBuilder("DIVISÃO: " + div.getNome().toUpperCase() + "\n\n");
            info.append(String.format("%-4s | %-12s | %-15s | %-10s | %s\n", "ID", "TIPO", "MODELO", "CONS.(Wh)", "ESTADO"));
            info.append("----------------------------------------------------------------------\n");

            for (Dispositivo d : div.getDispositivos().values()) {
                info.append(String.format("%-4d | %-12s | %-15s | %-10.2f | %s\n",
                        d.getId(), d.getTipo(), d.getModelo(), d.getConsumo_Por_Hora_Wh(), d.getEstado()));
            }

            String opts = "1. Alternar Estado (On/Off)\n" + (eAdmin ? "2. Adicionar\n3. Remover\n" : "") + "0. Voltar";
            ConsoleUI.desenharDashboard("DISPOSITIVOS", info.toString(), opts);

            System.out.print("\nEscolha opção: ");
            int opt = InputValidator.lerInteiro();
            if (opt == 0) break;

            if (opt == 1) {
                System.out.print("ID: ");
                Dispositivo d = div.obterDispositivoPorId(InputValidator.lerInteiro());
                if (d != null) {
                    if (d.getEstado().equals("LIGADO")) d.desligarDispositivo(); else d.ligarDispositivo();
                }
            } else if (opt == 2 && eAdmin) {
                adicionarDispositivoSubmenu(div, dc);
            } else if (opt == 3 && eAdmin) {
                System.out.print("ID para remover: ");
                Dispositivo r = div.obterDispositivoPorId(InputValidator.lerInteiro());
                if (r != null) div.removerDispositivo(r);
            }
        }
    }

    public static void adicionarDispositivoSubmenu(Divisao div, DomusControl dc) {
        System.out.println("1.Lâmpada | 2.Tomada | 3.Cortina | 4.Coluna | 5.Portão");
        int t = InputValidator.lerInteiro();
        System.out.print("Marca: "); String ma = InputValidator.lerLinha();
        System.out.print("Modelo: "); String mo = InputValidator.lerLinha();
        System.out.print("Consumo: "); double c = InputValidator.lerDouble();
        int id = dc.aumentarIdDispositivo();

        Dispositivo d = switch(t) {
            case 1 -> new Lampada(id, ma, mo, c, 80, "Branco");
            case 2 -> new Tomada(id, ma, mo, c);
            case 3 -> new Curtina(id, ma, mo, c, 0);
            case 4 -> new ColunaSom(id, ma, mo, c, 50);
            case 5 -> new PortaoGaragem(id, ma, mo, c, 0);
            default -> null;
        };
        if (d != null) div.adicionarDispositivo(d);
    }

    public static void menuAutomacao(Utilizador u, DomusControl dc) {
        StringBuilder info = new StringBuilder("MODO ECO\n\nCasas disponíveis para automação:\n");
        for (Casa c : u.getCasasUtilizador().values()) {
            info.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));
        }

        String opts = "Introduza o ID da casa para desligar tudo\n0. Cancelar";
        ConsoleUI.desenharDashboard("AUTOMAÇÕES", info.toString(), opts);

        int id = InputValidator.lerInteiro();
        Casa casa = dc.encontrarCasaPorId(id);
        if (casa != null && u.podeUsarCasa(casa)) {
            for (Divisao d : casa.getDivisoes().values()) {
                for (Dispositivo disp : d.getDispositivos().values()) disp.desligarDispositivo();
            }
        }
    }

    public static void menuLigarDispositivo(Utilizador u, DomusControl dc) {
        StringBuilder info = new StringBuilder("LIGAR TUDO\n\nCasas disponíveis:\n");
        for (Casa c : u.getCasasUtilizador().values()) {
            info.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));
        }
        ConsoleUI.desenharDashboard("LIGAR TUDO", info.toString(), "ID da casa para ligar tudo\n0. Cancelar");

        int id = InputValidator.lerInteiro();
        Casa casa = dc.encontrarCasaPorId(id);
        if (casa != null && u.podeUsarCasa(casa)) {
            for (Divisao d : casa.getDivisoes().values()) {
                for (Dispositivo disp : d.getDispositivos().values()) disp.ligarDispositivo();
            }
        }
    }

    public static void menuEstatisticas(Utilizador u, DomusControl dc) {
        while (true) {
            StringBuilder sb = new StringBuilder("RESUMO DE ESTATÍSTICAS\n\n");

            // 1. Casa que mais consome
            Casa topCasa = dc.casaQueMaisConsome();
            sb.append("CASA QUE MAIS CONSOME:\n");
            sb.append(topCasa != null ? " > " + topCasa.getAlcunha() : " > (Nenhuma)").append("\n\n");

            // 2. Top 3 Divisões com mais dispositivos
            sb.append("TOP 3 DIVISÕES (Mais Dispositivos):\n");
            for (String s : dc.gettop3DivisoesComMaisDispositivos()) {
                sb.append(" > ").append(s).append("\n");
            }

            // 3. ADICIONAR: Top 3 Dispositivos por Ativações
            sb.append("\nTOP 3 DISPOSITIVOS (Por Ativações):\n");
            List<Dispositivo> topAtiv = dc.getTop3Dispositivos(false);
            if (topAtiv.isEmpty()) sb.append(" > (Sem dados de ativação)\n");
            for (Dispositivo d : topAtiv) {
                sb.append(String.format(" > %-15s | %d ativ.\n", d.getModelo(), d.getNumAtivacoes()));
            }

            // 4. ADICIONAR: Top 3 Dispositivos por Tempo
            sb.append("\nTOP 3 DISPOSITIVOS (Por Tempo de Uso):\n");
            List<Dispositivo> topTempo = dc.getTop3Dispositivos(true);
            if (topTempo.isEmpty()) sb.append(" > (Sem dados de tempo)\n");
            for (Dispositivo d : topTempo) {
                sb.append(String.format(" > %-15s | %.2f horas\n", d.getModelo(), d.getTempoUsoHoras()));
            }

            // Menu de opções para o Dashboard
            String opts = "1. Simular Passagem de Tempo (1h)\n" +
                    "2. Consultar Dispositivos de uma Casa\n" +
                    "0. Voltar";

            ConsoleUI.desenharDashboard("CENTRAL DE ESTATÍSTICAS", sb.toString(), opts);
            System.out.print("\nEscolha opção: ");
            int opt = InputValidator.lerInteiro();

            if (opt == 0) break;

            if (opt == 1) {
                // Lógica para simular tempo nos dispositivos ligados
                for (Casa c : dc.getCasas()) {
                    for (Divisao d : c.getDivisoes().values()) {
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            disp.adicionarTempoUso(1.0);
                        }
                    }
                }
            } else if (opt == 2) {
                StringBuilder sb2 = new StringBuilder("CONSULTA POR CASA\n\nCasas disponíveis:\n");
                for (Casa c : u.getCasasUtilizador().values()) {
                    sb2.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));
                }
                ConsoleUI.desenharDashboard("CONSULTA POR CASA", sb2.toString(), "0. Voltar");
                System.out.print("ID da Casa para consultar: ");
                int idC = InputValidator.lerInteiro();
                Casa casa = dc.encontrarCasaPorId(idC);
                if (casa != null) {
                    StringBuilder lista = new StringBuilder("DISPOSITIVOS EM " + casa.getAlcunha() + ":\n\n");
                    for (Divisao div : casa.getDivisoes().values()) {
                        lista.append("[").append(div.getNome()).append("]\n");
                        for (Dispositivo disp : div.getDispositivos().values()) {
                            lista.append(" - ").append(disp.getModelo()).append(" (ID: ").append(disp.getId()).append(")\n");
                        }
                    }
                    ConsoleUI.desenharDashboard("CONSULTA POR CASA", lista.toString(), "0. Voltar");
                    InputValidator.lerInteiro();
                }
            }
        }
    }
}