package src.main.view;
import src.main.controller.DomusControl;
import src.main.model.*;
import src.main.Exceptions.DomusControlException;
import src.main.automacao.*;
import java.util.*;

public class Menu {

    private static Casa obterCasa(DomusControl dc, int id) {
        try { return dc.encontrarCasaPorId(id); }
        catch (DomusControlException e) { ConsoleUI.mostrarErro(e.getMessage()); return null; }
    }

    private static Utilizador obterUtilizador(DomusControl dc, int id) {
        try { return dc.encontrarUtilizadorPorId(id); }
        catch (DomusControlException e) { ConsoleUI.mostrarErro(e.getMessage()); return null; }
    }

    private static Divisao obterDivisao(DomusControl dc, Casa casa, int id) {
        try { return dc.encontrarDivisaoPorId(casa, id); }
        catch (DomusControlException e) { ConsoleUI.mostrarErro(e.getMessage()); return null; }
    }

    private static Dispositivo obterDispositivo(DomusControl dc, Divisao divisao, int id) {
        try { return dc.encontrarDispositivoPorId(divisao, id); }
        catch (Exception e) { ConsoleUI.mostrarErro("Dispositivo não encontrado."); return null; }
    }

    public static void submenuCasas(Utilizador u, DomusControl dc) {
        while (true) {
            StringBuilder info = new StringBuilder("AS MINHAS CASAS:\n\n");

            info.append("[ADMINISTRADOR]\n");
            if (u.getCasasAdministradas().isEmpty())
                info.append(" > (Vazio)\n");
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
            if (!temUser)
                info.append(" > (Vazio)\n");

            // Atualizamos as opções para incluir a remoção
            String opts = "ID da casa para aceder\n" +
                    "1. Aceder a uma Casa\n" +
                    "9. Remover uma Casa (Apenas Admin)\n" +
                    "0. Voltar";

            ConsoleUI.desenharDashboard("GESTÃO DE CASAS", info.toString(), opts);

            System.out.print("\nEscolha opção: ");
            int id = InputValidator.lerInteiro();
            int id2;
            if (id == 0)
                break;

            else if (id == 1) {
                System.out.print("ID da casa para aceder: ");
                id2 = InputValidator.lerInteiro();
                // Acesso normal à casa
                Casa casa = obterCasa(dc, id2);
                if (casa != null && u.podeUsarCasa(casa)) {
                    menuInternoCasa(casa, u, dc);
                }
            }

            // Lógica de remoção
            else if (id == 9) {
                System.out.print("ID da casa que deseja eliminar: ");
                int idEliminar = InputValidator.lerInteiro();
                Casa casaAEliminar = obterCasa(dc, idEliminar);

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
            } else {
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
                info.append(String.format(" [%d] %-15s (%d dispositivos)\n", d.getId(), d.getNome(),
                        d.getDispositivos().size()));
            }

            String opts = "1. Ver Estado Global\n2. Gestão de Utilizadores\n3. Entrar em Divisão\n" +
                    (eAdmin ? "4. Criar Divisão\n5. Remover Divisão\n" : "") + "0. Voltar";

            ConsoleUI.desenharDashboard("PAINEL DA CASA", info.toString(), opts);
            System.out.print("\nOpção: ");
            int opt = InputValidator.lerInteiro();
            if (opt == 0)
                break;

            switch (opt) {
                case 1 -> {
                    ConsoleUI.desenharDashboard(
                            "ESTADO GLOBAL",
                            montarEstadoGlobalCasa(casa),
                            "Prima Enter para voltar");
                    InputValidator.lerLinha();
                }
                // --- CORRECÇÃO: Agora chama o novo submenu ---
                case 2 -> {
                    if (submenuGestaoUtilizadores(casa, u, dc)) return;
                }
                case 3 -> {
                    System.out.print("ID Divisão: ");
                    Divisao d = obterDivisao(dc, casa, InputValidator.lerInteiro());
                    if (d != null)
                        menuDispositivos(d, eAdmin, dc);
                }
                case 4 -> {
                    if (eAdmin) {
                        System.out.print("Nome: ");
                        dc.criarDivisao(casa, InputValidator.lerLinha());
                    }
                }
                case 5 -> {
                    if (eAdmin) {
                        System.out.print("ID Divisão para remover: ");
                        Divisao rem = obterDivisao(dc, casa, InputValidator.lerInteiro());
                        if (rem != null)
                            dc.removerDivisao(casa, rem);
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
                        .append(") -> ")
                        .append(dispositivo.mostrarEstadoBase() ? "ESTADO: " + dispositivo.getEstado() : "")
                        .append(dispositivo.getDetalhesEspecificos())
                        .append("\n");
                temDispositivos = true;
            }
        }

        if (!temDispositivos) {
            estado.append("Esta casa ainda não possui dispositivos instalados. (Sem dispositivos)");
        }

        return estado.toString().stripTrailing();
    }

    public static boolean submenuGestaoUtilizadores(Casa casa, Utilizador u_sessao, DomusControl dc) {
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
            if (opt == 0)
                break;


            // Atualiza o estado de admin a cada iteração
            eAdmin = u_sessao.serAdmin(casa);
            if (!eAdmin) {
                // Se perdeu permissões de admin, expulsa do menu
                ConsoleUI.mostrarErro("Deixou de ser administrador desta casa. A voltar ao menu anterior.");
                return true;
            }

            switch (opt) {
                case 1 -> {
                    System.out.print("Nome: ");
                    String nome = InputValidator.lerLinha();
                    if (nome.equals("0"))
                        break;
                    Utilizador novo = dc.criarUtilizador(nome);
                    dc.adicionarCasaAUtilizador(novo, casa);
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
                        ConsoleUI.desenharDashboard("ADICIONAR UTILIZADOR", lista.toString(),
                                "ID do Utilizador a adicionar\n0. Voltar");
                        System.out.print("ID do Utilizador a adicionar: ");
                        int idAdd = InputValidator.lerInteiro();
                        if (idAdd == 0)
                            break;
                        Utilizador add = obterUtilizador(dc, idAdd);
                        if (add != null && !add.podeUsarCasa(casa)) {
                            dc.adicionarCasaAUtilizador(add, casa);
                        }
                    }
                }
                case 3 -> {
                    System.out.print("ID a remover: ");
                    int idRem = InputValidator.lerInteiro();
                    Utilizador rem = obterUtilizador(dc, idRem);
                    if (rem != null && rem.podeUsarCasa(casa)) {
                        // Impede a remoção se for o último administrador
                        if (dc.contarAdministradoresCasa(casa) <= 1 && rem.serAdmin(casa)) {
                            ConsoleUI.mostrarErro("Não pode remover o último administrador.");
                        } else {
                            dc.removerCasaDeUtilizador(rem, casa);
                            if (rem.getId() == u_sessao.getId())
                                return true;
                        }
                    }
                    // Se utilizador nao tiver casas removemos o utilizador
                    if (rem != null && rem.getCasasUtilizador().isEmpty() && rem.getCasasAdministradas().isEmpty()) {
                        dc.removerUtilizador(rem);
                    }
                }
                case 4 -> {
                    System.out.print("ID para tornar Administrador: ");
                    Utilizador adm = obterUtilizador(dc, InputValidator.lerInteiro());
                    if (adm != null && !adm.podeUsarCasa(casa)) {
                        dc.adicionarCasaAUtilizador(adm, casa); // Se não tinha acesso, damos acesso normal primeiro
                        dc.adicionarCasaAAdministrador(adm, casa); // Depois concedemos privilégios de admin
                    } else if (adm != null && adm.podeUsarCasa(casa) && !adm.serAdmin(casa)) {
                        dc.adicionarCasaAAdministrador(adm, casa);
                    } else if (adm != null && adm.serAdmin(casa)) {
                        ConsoleUI.mostrarErro("Utilizador já é administrador desta casa.");
                    } else {
                        ConsoleUI.mostrarErro("Utilizador não encontrado.");
                    }
                }
                case 5 -> {
                    System.out.print("ID para retirar privilégios de Admin: ");
                    int idT = InputValidator.lerInteiro();
                    Utilizador tir = obterUtilizador(dc, idT);
                    if (tir != null && dc.contarAdministradoresCasa(casa) > 1) {
                        dc.removerPermissoesAdmin(tir, casa);
                    } else if (tir != null && tir.serAdmin(casa)) {
                        ConsoleUI.mostrarErro("Não pode remover privilégios do último administrador.");
                    } else if (tir != null && !tir.podeUsarCasa(casa)) {
                        ConsoleUI.mostrarErro("Utilizador não encontrado.");
                    } else {
                        ConsoleUI.mostrarErro("Utilizador não é administrador desta casa.");
                    }
                }
            }
        }
        return false;
    }

    public static void menuDispositivos(Divisao div, boolean eAdmin, DomusControl dc) {
        while (true) {
            StringBuilder info = new StringBuilder("DIVISÃO: " + div.getNome().toUpperCase() + "\n\n");
            for (Dispositivo d : div.getDispositivos().values()) {
                info.append(String.format(" ID:%-3d | %-12s | %s %s | %s%s\n",
                        d.getId(), d.getTipo(), d.getMarca(), d.getModelo(), d.getEstado(), d.getDetalhesEspecificos()));
            }
            String opts = "1. Alternar Estado\n2. Configurar (Volume/Intensidade/Cor/Abertura)\n" + (eAdmin ? "3. Adicionar\n4. Remover\n" : "") + "0. Voltar";
            ConsoleUI.desenharDashboard("DISPOSITIVOS", info.toString(), opts);
            int opt = InputValidator.lerInteiro();
            if (opt == 0) break;
            if (opt == 1) {
                System.out.print("ID: ");
                Dispositivo d = obterDispositivo(dc, div, InputValidator.lerInteiro());
                if (d != null) { if (d.isLigado()) d.desligarDispositivo(); else d.ligarDispositivo(); }
            } else if (opt == 2) {
                System.out.print("ID do Dispositivo: ");
                Dispositivo d = obterDispositivo(dc, div, InputValidator.lerInteiro());
                if (d != null && d.isLigado()) {
                    if (d instanceof Lampada lamp) {
                        System.out.print("Intensidade (0-100): ");
                        // Removido: intensidade não existe mais
                        System.out.print("Cor: ");
                        lamp.setCor_Luz(InputValidator.lerLinha());
                    } else if (d instanceof ColunaSom coluna) {
                        System.out.print("Volume (0-100): ");
                        coluna.setIntensidadeVolume(InputValidator.lerInteiro());
                    } else if (d instanceof Cortina cortina) {
                        System.out.print("Nível de Abertura (0-100): ");
                        cortina.setNivelAbertura(InputValidator.lerInteiro());
                    } else {
                        ConsoleUI.mostrarErro("Este dispositivo não possui configurações adicionais.");
                    }
                } else if (d != null) {
                    ConsoleUI.mostrarErro("O dispositivo precisa estar ligado para ser configurado.");
                }
            } else if (opt == 3 && eAdmin) {
                adicionarDispositivoSubmenu(div, dc);
            } else if (opt == 4 && eAdmin) {
                System.out.print("ID para remover: ");
                Dispositivo r = null;
                try {
                    r = div.obterDispositivoPorId(InputValidator.lerInteiro());
                } catch (Exception e) {
                }
                if (r != null)
                    div.removerDispositivo(r);
            }
        }
    }

    public static void adicionarDispositivoSubmenu(Divisao div, DomusControl dc) {
        System.out
                .println("1.Lâmpada | 2.Tomada | 3.Cortina | 4.Coluna | 5.Portão | 6.Sensor de Água | 7.Sensor de Luz");
        int t = InputValidator.lerInteiro();
        System.out.print("Marca: ");
        String ma = InputValidator.lerLinha();
        System.out.print("Modelo: ");
        String mo = InputValidator.lerLinha();
        System.out.print("Consumo: ");
        double c = InputValidator.lerDouble();
        int id = dc.aumentarIdDispositivo();

        Dispositivo d = switch (t) {
            case 1 -> new Lampada(id, ma, mo, c, "Branco");
            case 2 -> new Tomada(id, ma, mo, c);
            case 3 -> new Cortina(id, ma, mo, c, 0);
            case 4 -> new ColunaSom(id, ma, mo, c, 50);
            case 5 -> new PortaoGaragem(id, ma, mo, c, 0);
            case 6 -> new SensorAgua(id, ma, mo, c, 0, false);
            case 7 -> new SensorLuz(id, ma, mo, c, 100.0);
            default -> null;
        };
        if (d != null)
            div.adicionarDispositivo(d);
    }

    public static void menuAutomacao(Utilizador u, DomusControl dc) {
        while (true) {
            // Corpo: lista casas e automações existentes
            StringBuilder info = new StringBuilder("CASAS DISPONÍVEIS:\n");
            for (Casa c : u.getCasasUtilizador().values()) {
                info.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));
            }
            info.append("\nAUTOMAÇÕES CRIADAS (Admin):\n");
            boolean temAutoUtilizador = false;
            for (Automacao a : dc.getAutomacoes()) {
                Casa c2 = obterCasa(dc, a.getIdCasa());
                if (c2 == null) continue;
                if (u.podeAdministrarCasa(c2)) {
                    info.append(String.format(" > [%d] %s | Casa: %s\n", a.getId(), a.getNome(), c2.getAlcunha()));
                    temAutoUtilizador = true;
                }
            }
            if (!temAutoUtilizador) {
                info.append(" > (Nenhuma)\n");
            }

            // meter no menu se existe chuva ou nao
            info.append("\nESTADO DE CHUVA POR CASA:\n");
            for (Casa c : u.getCasasUtilizador().values()) {
                boolean chuva = false;
                boolean temSensorAgua = false;
                for (Divisao d : c.getDivisoes().values()) {
                    for (Dispositivo disp : d.getDispositivos().values()) {
                        if (disp instanceof SensorAgua sensor) {
                            temSensorAgua = true;
                            if (sensor.isEmChuva())
                                chuva = true;
                        }
                    }
                }
                if (!temSensorAgua) {
                    info.append(String.format(" > %s: (Sem sensor de água)\n", c.getAlcunha()));
                } else {
                    info.append(String.format(" > %s: %s\n", c.getAlcunha(),
                            chuva ? "Está a chover" : "Não está a chover"));
                }
            }

            // meter no menu o estado das cortinas (abertas ou fechadas)
            info.append("\nESTADO DAS CORTINAS POR CASA:\n");
            for (Casa c : u.getCasasUtilizador().values()) {
                boolean temCortinas = false;
                for (Divisao d : c.getDivisoes().values()) {
                    for (Dispositivo disp : d.getDispositivos().values()) {
                        if (disp instanceof Cortina cortina) {
                            temCortinas = true;
                            info.append(String.format(
                                    " > %s: [%s] -> [%d] cortina %s (%d%%)\n",
                                    c.getAlcunha(),
                                    d.getNome(),
                                    cortina.getId(),
                                    cortina.getNivelAbertura() > 0 ? "Aberta" : "Fechada",
                                    cortina.getNivelAbertura()));
                        }
                    }
                }
                if (!temCortinas) {
                    info.append(String.format(" > %s: (Sem cortinas)\n", c.getAlcunha()));
                }
            }

            // meter no menu se existe luminosidade baixa ou nao
            info.append("\nLUMINOSIDADE POR CASA:\n");
            for (Casa c : u.getCasasUtilizador().values()) {
                boolean luminosidadeBaixa = false;
                boolean temSensorLuz = false;
                for (Divisao d : c.getDivisoes().values()) {
                    for (Dispositivo disp : d.getDispositivos().values()) {
                        if (disp instanceof SensorLuz sensor) {
                            temSensorLuz = true;
                            if (sensor.isLuminosidadeBaixa())
                                luminosidadeBaixa = true;
                        }
                    }
                }
                if (!temSensorLuz) {
                    info.append(String.format(" > %s: (Sem sensor de luz)\n", c.getAlcunha()));
                } else {
                    info.append(String.format(" > %s: %s\n", c.getAlcunha(),
                            luminosidadeBaixa ? "Luminosidade baixa" : "Luminosidade normal"));
                }
            }

            // meter no menu o estado das lampadas (ligadas ou apagadas)
            info.append("\nESTADO DAS LÂMPADAS POR CASA:\n");
            for (Casa c : u.getCasasUtilizador().values()) {
                boolean temLampadas = false;
                for (Divisao d : c.getDivisoes().values()) {
                    for (Dispositivo disp : d.getDispositivos().values()) {
                        if (disp instanceof Lampada lampada) {
                            temLampadas = true;
                            info.append(String.format(
                                    " > %s: [%s] -> [%d] Lâmpada %s\n",
                                    c.getAlcunha(),
                                    d.getNome(),
                                    lampada.getId(),
                                    lampada.getEstado().equals("LIGADO") ? "Ligada" : "Desligada"));
                        }
                    }
                }
                if (!temLampadas) {
                    info.append(String.format(" > %s: (Sem lâmpadas)\n", c.getAlcunha()));
                }
            }

            // Menu para exibir automações
            info.append("\nAUTOMAÇÕES EXISTENTES:\n");
            for (Automacao a : dc.getAutomacoes()) {
                Casa casaAuto = obterCasa(dc, a.getIdCasa());
                if (casaAuto == null) continue;
                if (!u.podeUsarCasa(casaAuto)) continue;
                String nomeCasa = String.format("[ID:%d] %s", casaAuto.getId(), casaAuto.getAlcunha());
                info.append(String.format(" > [%d] %s | Casa: %s | %s\n",
                        a.getId(), a.getNome(), nomeCasa, a.isAtiva() ? "ATIVA" : "INATIVA"));
            }

            String opts = "1. Simular/Parar chuva numa casa\n" +
                    "2. Simular luminosidade baixa numa casa\n" +
                    "3. Ativar/Desativar automação\n" +
                    "0. Voltar para trás\n";

            ConsoleUI.desenharDashboard("AUTOMAÇÕES", info.toString(), opts);
            System.out.print("\nEscolha opção: ");
            int opt = InputValidator.lerInteiro();
            if (opt == 0)
                break;
            else if (opt == 1) {
                // Simular/parar chuva — basta alternar o estado de chuva dos sensores de água
                // da casa selecionada
                StringBuilder infoCasa = new StringBuilder("Escolha a casa:\n\n");
                for (Casa c : u.getCasasUtilizador().values())
                    infoCasa.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));
                ConsoleUI.desenharDashboard("SIMULAR CHUVA", infoCasa.toString(), "ID da Casa\n0. Cancelar");
                System.out.print("ID da Casa: ");
                int idCasa = InputValidator.lerInteiro();
                if (idCasa == 0)
                    continue;
                Casa casa = obterCasa(dc, idCasa);
                if (casa != null) {
                    dc.alternarChuvaCasa(idCasa); // atualiza sensores e dispara automações
                }
            } else if (opt == 2) {
                // Simular luminosidade baixa — basta alternar o estado dos sensores de luz da
                // casa selecionada
                StringBuilder infoCasa = new StringBuilder("Escolha a casa:\n\n");
                for (Casa c : u.getCasasUtilizador().values())
                    infoCasa.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));
                ConsoleUI.desenharDashboard("SIMULAR LUMINOSIDADE BAIXA", infoCasa.toString(),
                        "ID da Casa\n0. Cancelar");
                System.out.print("ID da Casa: ");
                int idCasa = InputValidator.lerInteiro();
                if (idCasa == 0)
                    continue;

                Casa casa = obterCasa(dc, idCasa);
                if (casa != null) {
                    dc.alternarLuminosidadeCasa(idCasa); // atualiza sensores e dispara automações
                }
            } else if (opt == 3) {
                StringBuilder infoAuto = new StringBuilder("AUTOMAÇÕES EXISTENTES:\n\n");
                for (Automacao a : dc.getAutomacoes()) {
                    Casa casaAuto = obterCasa(dc, a.getIdCasa());
                    if (casaAuto == null) continue;
                    if (!u.podeUsarCasa(casaAuto)) continue;
                    String nomeCasa = String.format("[ID:%d] %s", casaAuto.getId(), casaAuto.getAlcunha());
                    infoAuto.append(String.format(" > ID: %d | %s | Casa: %s | %s\n",
                            a.getId(), a.getNome(), nomeCasa, a.isAtiva() ? "ATIVA" : "INATIVA"));
                }
                ConsoleUI.desenharDashboard("ATIVAR/DESATIVAR AUTOMAÇÃO", infoAuto.toString(),
                        "ID da automação para alternar estado\n0. Cancelar");
                System.out.print("ID da automação: ");
                int idAuto = InputValidator.lerInteiro();
                if (idAuto == 0)
                    continue;
                Automacao auto = dc.encontrarAutomacaoPorId(idAuto);
                if (auto != null) {
                    auto.setAtiva(!auto.isAtiva());
                    if (auto.isAtiva())
                        auto.executar(dc); // Se estiver a ser ativada, executa imediatamente para aplicar efeitos
                }
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
        Casa casa = obterCasa(dc, id);
        if (casa != null && u.podeUsarCasa(casa)) {
            for (Divisao d : casa.getDivisoes().values()) {
                for (Dispositivo disp : d.getDispositivos().values())
                    disp.ligarDispositivo();
            }
        }
    }

    public static void menuDesligarDispositivo(Utilizador u, DomusControl dc) {
        StringBuilder info = new StringBuilder("DESLIGAR TUDO\n\nCasas disponíveis:\n");
        for (Casa c : u.getCasasUtilizador().values()) {
            info.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));
        }
        ConsoleUI.desenharDashboard("DESLIGAR TUDO", info.toString(), "ID da casa para desligar tudo\n0. Cancelar");

        int id = InputValidator.lerInteiro();
        Casa casa = obterCasa(dc, id);
        if (casa != null && u.podeUsarCasa(casa)) {
            for (Divisao d : casa.getDivisoes().values()) {
                for (Dispositivo disp : d.getDispositivos().values())
                    disp.desligarDispositivo();
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
            if (topAtiv.isEmpty())
                sb.append(" > (Sem dados de ativação)\n");
            for (Dispositivo d : topAtiv) {
                String loc = dc.encontrarLocalizacaoDispositivo(d);
                sb.append(String.format(" > ID:%-3d | %-12s | %-15s | %-25s | %d ativ.\n", d.getId(), d.getTipo(), d.getModelo(), loc, d.getNumAtivacoes()));
            }

            // 4. ADICIONAR: Top 3 Dispositivos por Tempo
            sb.append("\nTOP 3 DISPOSITIVOS (Por Tempo de Uso):\n");
            List<Dispositivo> topTempo = dc.getTop3Dispositivos(true);
            if (topTempo.isEmpty())
                sb.append(" > (Sem dados de tempo)\n");
            for (Dispositivo d : topTempo) {
                String loc = dc.encontrarLocalizacaoDispositivo(d);
                sb.append(String.format(" > ID:%-3d | %-12s | %-15s | %-25s | %.2f horas\n", d.getId(), d.getTipo(), d.getModelo(), loc, d.getTempoUsoHoras()));
            }

            // Menu de opções para o Dashboard
            String opts = "1. Simular Passagem de Tempo (1h)\n" +
                    "2. Consultar Dispositivos de uma Casa\n" +
                    "0. Voltar";

            ConsoleUI.desenharDashboard("CENTRAL DE ESTATÍSTICAS", sb.toString(), opts);
            System.out.print("\nEscolha opção: ");
            int opt = InputValidator.lerInteiro();

            if (opt == 0)
                break;

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
                Casa casa = obterCasa(dc, idC);
                if (casa != null) {
                    StringBuilder lista = new StringBuilder("DISPOSITIVOS EM " + casa.getAlcunha() + ":\n\n");
                    for (Divisao div : casa.getDivisoes().values()) {
                        lista.append("[").append(div.getNome()).append("]\n");
                        for (Dispositivo disp : div.getDispositivos().values()) {
                            lista.append(" - ").append(disp.getModelo()).append(" (ID: ").append(disp.getId())
                                    .append(")\n");
                        }
                    }
                    ConsoleUI.desenharDashboard("CONSULTA POR CASA", lista.toString(), "0. Voltar");
                    InputValidator.lerInteiro();
                }
            }
        }
    }

    public static void menuEscalonamentos(Utilizador u, DomusControl dc) {
        while (true) {
            StringBuilder info = new StringBuilder();

            info.append("TEMPO ATUAL: ")
                    .append(dc.getTempoAtual().toLocalDate())
                    .append(" ")
                    .append(dc.getTempoAtual().toLocalTime())
                    .append("\n\n");

            info.append("\nCASAS DISPONÍVEIS:\n");
            for (Casa c : u.getCasasUtilizador().values())
                info.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));

            // meter no menu o estado das cortinas (abertas ou fechadas)
            info.append("\n\nESTADO DAS CORTINAS POR CASA:\n");
            for (Casa c : u.getCasasUtilizador().values()) {
                boolean temCortinas = false;
                for (Divisao d : c.getDivisoes().values()) {
                    for (Dispositivo disp : d.getDispositivos().values()) {
                        if (disp instanceof Cortina cortina) {
                            temCortinas = true;
                            info.append(String.format(
                                    " > %s: [%s] -> [%d] cortina %s (%d%%)\n",
                                    c.getAlcunha(),
                                    d.getNome(),
                                    cortina.getId(),
                                    cortina.getNivelAbertura() > 0 ? "Aberta" : "Fechada",
                                    cortina.getNivelAbertura()));
                        }
                    }
                }
                if (!temCortinas) {
                    info.append(String.format(" > %s: (Sem cortinas)\n", c.getAlcunha()));
                }
            }

            // meter no menu o estado das luzes (ligadas ou apagadas)
            info.append("\nESTADO DAS LUZES POR CASA:\n");
            for (Casa c : u.getCasasUtilizador().values()) {
                boolean temLampadas = false;
                for (Divisao d : c.getDivisoes().values()) {
                    for (Dispositivo disp : d.getDispositivos().values()) {
                        if (disp instanceof Lampada lampada) {
                            temLampadas = true;
                            info.append(String.format(
                                    " > %s: [%s] -> [%d] Lâmpada %s\n",
                                    c.getAlcunha(),
                                    d.getNome(),
                                    lampada.getId(),
                                    lampada.getEstado().equals("LIGADO") ? "Ligada" : "Desligada"));
                        }
                    }
                }
                if (!temLampadas) {
                    info.append(String.format(" > %s: (Sem lâmpadas)\n", c.getAlcunha()));
                }
            }

            // meter no menu informacao sobre a coluna de som (ligada ou desligada e volume)
            info.append("\nESTADO DAS COLUNAS DE SOM POR CASA:\n");
            for (Casa c : u.getCasasUtilizador().values()) {
                boolean temColuna = false;
                for (Divisao d : c.getDivisoes().values()) {
                    for (Dispositivo disp : d.getDispositivos().values()) {
                        if (disp instanceof ColunaSom coluna) {
                            temColuna = true;
                            info.append(String.format(
                                    " > %s: [%s] -> [%d] Coluna de Som %s (Volume: %d%%)\n",
                                    c.getAlcunha(),
                                    d.getNome(),
                                    coluna.getId(),
                                    coluna.getEstado().equals("LIGADO") ? "Ligada" : "Desligada",
                                    coluna.getIntensidadeVolume()));
                        }
                    }
                }
                if (!temColuna) {
                    info.append(String.format(" > %s: (Sem colunas de som)\n", c.getAlcunha()));
                }
            }

            info.append("\n\nESCALONAMENTOS CRIADOS:\n");
            if (dc.getEscalonamentos().isEmpty()) {
                info.append(" > (Nenhum)\n");
            } else {
                for (Escalonamento e : dc.getEscalonamentos()) {
                    String tipo = e.isIntervalo()
                            ? e.getHoraInicio() + " - " + e.getHoraFim()
                            : e.getHoraInicio().toString();
                    Casa casaEsc = obterCasa(dc, e.getIdCasa());
                    String nomeCasaEsc = casaEsc != null
                            ? String.format("[ID:%d] %s", casaEsc.getId(), casaEsc.getAlcunha())
                            : "?";
                    info.append(String.format(" > [%d] %s | Casa: %s | %s | %s\n",
                            e.getId(), e.getNome(), nomeCasaEsc, tipo, e.isAtivo() ? "ATIVO" : "INATIVO"));
                }
            }

            String opts = "1. Criar: Abrir Cortinas (07:30)\n" +
                    "2. Criar: Desligar luzes e fechar cortinas (23:00)\n" +
                    "3. Criar: Ligar luzes (Intervalo 19:00-23:00)\n" +
                    "4. Criar: Ligar Coluna de Som (07:00-07:45)\n" +
                    "5. Avançar tempo (minutos)\n" +
                    "6. Ativar/Desativar Escalonamento\n" +
                    "0. Voltar";

            ConsoleUI.desenharDashboard("ESCALONAMENTOS", info.toString(), opts);
            System.out.print("\nEscolha a opção: ");
            int opcao = InputValidator.lerInteiro();

            if (opcao == 0)
                break;

            if (opcao >= 1 && opcao <= 4) {
                StringBuilder infoCasa = new StringBuilder("Escolha a casa:\n\n");
                for (Casa c : u.getCasasUtilizador().values())
                    infoCasa.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));
                ConsoleUI.desenharDashboard("CRIAR ESCALONAMENTO", infoCasa.toString(), "ID da Casa\n0. Cancelar");
                System.out.print("ID da Casa: ");
                int idCasa = InputValidator.lerInteiro();
                if (idCasa == 0)
                    continue;
                if (obterCasa(dc, idCasa) == null) {
                    ConsoleUI.mostrarErro("Casa não encontrada.");
                    continue;
                }
                switch (opcao) {
                    case 1 -> dc.criarEscalonamentoAbrirCortinas(idCasa);
                    case 2 -> dc.criarEscalonamentoModoNoturno(idCasa);
                    case 3 -> dc.criarEscalonamentoLuzTarde(idCasa);
                    case 4 -> dc.criarEscalonamentoMusicaManha(idCasa);
                }
                System.out.println("Escalonamento criado com sucesso. Prima Enter...");
                InputValidator.lerLinha();

            } else if (opcao == 5) {
                System.out.print("Quantos minutos deseja avançar? ");
                long minutos = InputValidator.lerInteiro();
                dc.avancaTempo(minutos);
                System.out.println("Prima Enter para continuar...");
                InputValidator.lerLinha();

            } else if (opcao == 6) {
                System.out.print("ID do Escalonamento: ");
                int idEscalonamento = InputValidator.lerInteiro();
                boolean encontrado = false;
                for (Escalonamento e : dc.getEscalonamentos()) {
                    if (e.getId() == idEscalonamento) {
                        if (e.isAtivo())
                            e.desativar();
                        else
                            e.ativar();
                        System.out.println("Estado alterado para: " + (e.isAtivo() ? "ATIVO" : "INATIVO"));
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado)
                    ConsoleUI.mostrarErro("Escalonamento não encontrado.");
                InputValidator.lerLinha();
            }
        }
    }

    public static void menuCenarios(Utilizador u, DomusControl dc) {
        while (true) {
            StringBuilder info = new StringBuilder("CENTRAL DE CENÁRIOS:\n\n");
            for (Casa c : u.getCasasUtilizador().values()) {
                info.append(String.format("┌─ CASA: %s (ID: %d)\n", c.getAlcunha(), c.getId()));
                info.append("│  DIVISÕES E DISPOSITIVOS:\n");
                for (Divisao d : c.getDivisoes().values()) {
                    info.append(String.format("│   • [%d] %s\n", d.getId(), d.getNome().toUpperCase()));
                    for (Dispositivo disp : d.getDispositivos().values()) {
                        info.append(String.format("│        - ID:%-3d %-12s %s %s -> %s%s\n",
                                disp.getId(), disp.getTipo(), disp.getMarca(), disp.getModelo(),
                                disp.getEstado(), disp.getDetalhesEspecificos()));
                    }
                }
                info.append("│\n│  CENÁRIOS CONFIGURADOS:\n");
                List<Cenario> lista = dc.getCenariosDaCasa(c.getId());
                if (lista.isEmpty()) info.append("│   (Sem cenários configurados)\n");
                for (Cenario cen : lista) {
                    List<String> nomes = new ArrayList<>();
                    for (Acao a : cen.getAcoes()) nomes.add(a.getNome());
                    info.append(String.format("│   [%d] %-18s | Ações: %s\n", cen.getId(), cen.getNome(), String.join(" -> ", nomes)));
                }
                info.append("└─────────────────────────────────\n\n");
            }

            String opts = "1. Executar Cenário\n2. Adicionar Template\n3. Criar Personalizado\n4. Remover Cenário\n0. Voltar";
            ConsoleUI.desenharDashboard("CENÁRIOS", info.toString(), opts);
            System.out.print("\nOpção: ");
            int op = InputValidator.lerInteiro();
            if (op == 0) break;

            try {
                switch (op) {
                    case 1 -> {
                        System.out.print("ID do Cenário: ");
                        Cenario c = dc.encontrarCenarioPorId(InputValidator.lerInteiro());
                        if (c != null) {
                            System.out.print("ID Divisão alvo (ou 0 para toda a casa): ");
                            int div = InputValidator.lerInteiro();
                            if (div == 0) c.executar(dc); else c.executarNaDivisao(dc, div);
                            System.out.println("Comando enviado!");
                        }
                    }
                    case 2 -> {
                        System.out.print("ID da Casa: ");
                        Casa casa = obterCasa(dc, InputValidator.lerInteiro());
                        if (casa != null && u.serAdmin(casa)) {
                            System.out.println("1.Sair 2.Jantar 3.Romântico 4.Cinema 5.Estudar 6.Deitar 7.Acordar");
                            dc.adicionarCenarioPreDefinido(InputValidator.lerInteiro(), casa.getId());
                        }
                    }
                    case 3 -> {
                        System.out.print("ID da Casa: ");
                        Casa casa = obterCasa(dc, InputValidator.lerInteiro());
                        if (casa != null && u.serAdmin(casa)) {
                            System.out.print("Nome: "); String nome = InputValidator.lerLinha();
                            List<Acao> acoes = new ArrayList<>();
                            while (true) {
                                System.out.println("1.Luz ON       2.Luz OFF      3.Cortina Open  4.Cortina Close");
                                System.out.println("5.Coluna ON    6.Coluna OFF   7.Tomada ON     8.Tomada OFF");
                                System.out.println("9.Portão Open 10.Portão Close 0.Gravar");
                                int ac = InputValidator.lerInteiro();
                                if (ac == 0) break;
                                switch(ac) {
                                    case 1 -> acoes.add(Acao.ligarLuzesCasa(casa.getId()));
                                    case 2 -> acoes.add(Acao.desligarLuzesCasa(casa.getId()));
                                    case 3 -> acoes.add(Acao.abrirCortinas(casa.getId()));
                                    case 4 -> acoes.add(Acao.fecharCortinas(casa.getId()));
                                    case 5 -> acoes.add(Acao.ligarColunaSomCasa(casa.getId()));
                                    case 6 -> acoes.add(Acao.desligarColunaSomCasa(casa.getId()));
                                    case 7 -> acoes.add(Acao.ligarTomadasCasa(casa.getId()));
                                    case 8 -> acoes.add(Acao.desligarTomadasCasa(casa.getId()));
                                    case 9 -> acoes.add(Acao.abrirPortoesCasa(casa.getId()));
                                    case 10 -> acoes.add(Acao.fecharPortoesCasa(casa.getId()));
                                }
                            }
                            if (!acoes.isEmpty()) dc.criarCenario(nome, casa.getId(), acoes);
                        }
                    }
                    case 4 -> {
                        System.out.print("ID Cenário: ");
                        int id = InputValidator.lerInteiro();
                        Cenario c = dc.encontrarCenarioPorId(id);
                        if (c != null && u.serAdmin(obterCasa(dc, c.getIdCasa()))) dc.removerCenario(id);
                    }
                }
            } catch (Exception e) { ConsoleUI.mostrarErro(e.getMessage()); }
        }
    }
}