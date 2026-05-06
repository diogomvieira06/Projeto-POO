package src.view;

import src.controller.DomusControl;
import src.model.*;
import java.util.*;
import src.automacao.*;

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
                        .append(") -> ")
                        .append(dispositivo.mostrarEstadoBase() ? "ESTADO: " + dispositivo.getEstado() : "")
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
                    String nome = InputValidator.lerLinha();
                    if (nome.equals("0")) break;
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
                    // Se utilizador nao tiver casas removemos o utilizador
                    if (rem != null && rem.getCasasUtilizador().isEmpty() && rem.getCasasAdministradas().isEmpty()) {
                        dc.removerUtilizador(rem);
                    }
                }
                case 4 -> {
                    System.out.print("ID para tornar Administrador: ");
                    Utilizador adm = dc.encontrarUtilizadorPorId(InputValidator.lerInteiro());
                    if (adm != null && !adm.podeUsarCasa(casa)) {
                        dc.adicionarCasaAUtilizador(adm, casa); // Se não tinha acesso, damos acesso normal primeiro
                        dc.adicionarCasaAAdministrador(adm, casa); // Depois concedemos privilégios de admin
                    }
                    else if (adm != null && adm.podeUsarCasa(casa) && !adm.serAdmin(casa)) {
                        dc.adicionarCasaAAdministrador(adm, casa);
                    } else if (adm != null && adm.serAdmin(casa)) {
                        ConsoleUI.mostrarErro("Utilizador já é administrador desta casa.");
                    }
                     else {
                        ConsoleUI.mostrarErro("Utilizador não encontrado.");
                    }   
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
        System.out.println("1.Lâmpada | 2.Tomada | 3.Cortina | 4.Coluna | 5.Portão | 6.Sensor de Água | 7.Sensor de Luz");
        int t = InputValidator.lerInteiro();
        System.out.print("Marca: "); String ma = InputValidator.lerLinha();
        System.out.print("Modelo: "); String mo = InputValidator.lerLinha();
        System.out.print("Consumo: "); double c = InputValidator.lerDouble();
        int id = dc.aumentarIdDispositivo();

        Dispositivo d = switch(t) {
            case 1 -> new Lampada(id, ma, mo, c, 80, "Branco");
            case 2 -> new Tomada(id, ma, mo, c);
            case 3 -> new Cortina(id, ma, mo, c, 0);
            case 4 -> new ColunaSom(id, ma, mo, c, 50);
            case 5 -> new PortaoGaragem(id, ma, mo, c, 0);
            case 6 -> new SensorAgua(id, ma, mo, c, 0, false);//VER MELHOR PARA VER SE VALE A PENA TER A OPÇAO DE ADICIONAR SENSOR DE AGUA AQUI, OU SE DEVE FICAR APENAS PARA AUTOMACOES
            case 7 -> new SensorLuz(id, ma, mo, c, 100.0);
            default -> null;
        };
        if (d != null) div.adicionarDispositivo(d);
    }

    public static void menuAutomacao(Utilizador u, DomusControl dc) {
        while (true) {
            // Corpo: lista casas e automações existentes
            StringBuilder info = new StringBuilder("CASAS DISPONÍVEIS:\n");
            for (Casa c : u.getCasasUtilizador().values()) {
                info.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));
            }
            info.append("\nAUTOMAÇÕES CRIADAS:\n");
            boolean temAutoUtilizador = dc.getAutomacoes().stream()
                .anyMatch(a -> u.podeAdministrarCasa(dc.encontrarCasaPorId(a.getIdCasa())));
            if(!temAutoUtilizador){
                info.append(" > (Nenhuma)\n");
            }

            //meter no menu se existe chuva ou nao
            info.append("\nESTADO DE CHUVA POR CASA:\n");
            for(Casa c : u.getCasasUtilizador().values()){
            boolean chuva = false;
            boolean temSensorAgua = false;
            for(Divisao d : c.getDivisoes().values()){
                for(Dispositivo disp : d.getDispositivos().values()){
                    if(disp instanceof SensorAgua sensor){
                        temSensorAgua = true;
                        if(sensor.isEmChuva()) chuva = true;
                    }
                }
            }
            if(!temSensorAgua){
                info.append(String.format(" > %s: (Sem sensor de água)\n", c.getAlcunha()));
            } else {
                info.append(String.format(" > %s: %s\n", c.getAlcunha(), chuva ? "Está a chover" : "Não está a chover"));
            }
            }

            //meter no menu o estado das cortinas (abertas ou fechadas)
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
                                    cortina.getNivelAbertura()
                            ));
                        }
                    }
                }
                if (!temCortinas) {
                    info.append(String.format(" > %s: (Sem cortinas)\n", c.getAlcunha()));
                }
            }

            //meter no menu se existe luminosidade baixa ou nao
            info.append("\nLUMINOSIDADE POR CASA:\n");
            for(Casa c : u.getCasasUtilizador().values()){
            boolean luminosidadeBaixa = false;
            boolean temSensorLuz = false;
            for(Divisao d : c.getDivisoes().values()){
                for(Dispositivo disp : d.getDispositivos().values()){
                    if(disp instanceof SensorLuz sensor){
                        temSensorLuz = true;
                        if(sensor.isLuminosidadeBaixa()) luminosidadeBaixa = true;
                    }
                }
            }
            if(!temSensorLuz){
                info.append(String.format(" > %s: (Sem sensor de luz)\n", c.getAlcunha()));
            } else {
                info.append(String.format(" > %s: %s\n", c.getAlcunha(), luminosidadeBaixa ? "Luminosidade baixa" : "Luminosidade normal"));
            }
            }

            //meter no menu o estado das lampadas (ligadas ou apagadas)
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
                                    lampada.getEstado().equals("LIGADO") ? "Ligada" : "Desligada"
                            ));
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
                Casa casaAuto = dc.encontrarCasaPorId(a.getIdCasa());
                if (casaAuto == null || !u.podeUsarCasa(casaAuto)) continue;// Só mostramos automações de casas que o utilizador pode usar
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
            if (opt == 0) break;
            else if (opt == 1) {
                // Simular/parar chuva — basta alternar o estado de chuva dos sensores de água da casa selecionada
                StringBuilder infoCasa = new StringBuilder("Escolha a casa:\n\n");
                for (Casa c : u.getCasasUtilizador().values())
                    infoCasa.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));
                ConsoleUI.desenharDashboard("SIMULAR CHUVA", infoCasa.toString(), "ID da Casa\n0. Cancelar");
                System.out.print("ID da Casa: ");
                int idCasa = InputValidator.lerInteiro();
                if (idCasa == 0) continue;
                Casa casa = dc.encontrarCasaPorId(idCasa);
                if (casa != null) {
                    dc.alternarChuvaCasa(idCasa); // atualiza sensores e dispara automações
                }
            } 
            else if (opt == 2) {
                // Simular luminosidade baixa — basta alternar o estado dos sensores de luz da casa selecionada
                StringBuilder infoCasa = new StringBuilder("Escolha a casa:\n\n");
                for (Casa c : u.getCasasUtilizador().values())
                    infoCasa.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));
                ConsoleUI.desenharDashboard("SIMULAR LUMINOSIDADE BAIXA", infoCasa.toString(), "ID da Casa\n0. Cancelar");
                System.out.print("ID da Casa: ");
                int idCasa = InputValidator.lerInteiro();
                if (idCasa == 0) continue;

                Casa casa = dc.encontrarCasaPorId(idCasa);
                if (casa != null) {
                    dc.alternarLuminosidadeCasa(idCasa); // atualiza sensores e dispara automações
                }
            }
            else if (opt == 3) {
                StringBuilder infoAuto = new StringBuilder("AUTOMAÇÕES EXISTENTES:\n\n");
                for (Automacao a : dc.getAutomacoes()) {
                    Casa casaAuto = dc.encontrarCasaPorId(a.getIdCasa());
                    if (casaAuto == null || !u.podeUsarCasa(casaAuto)) continue; // <-- filtro
                    String nomeCasa = String.format("[ID:%d] %s", casaAuto.getId(), casaAuto.getAlcunha());
                    infoAuto.append(String.format(" > ID: %d | %s | Casa: %s | %s\n",
                        a.getId(), a.getNome(), nomeCasa, a.isAtiva() ? "ATIVA" : "INATIVA"));
                }
                ConsoleUI.desenharDashboard("ATIVAR/DESATIVAR AUTOMAÇÃO", infoAuto.toString(), "ID da automação para alternar estado\n0. Cancelar");
                System.out.print("ID da automação: ");
                int idAuto = InputValidator.lerInteiro();
                if (idAuto == 0) continue;
                Automacao auto = dc.encontrarAutomacaoPorId(idAuto);
                if (auto != null) {
                    auto.setAtiva(!auto.isAtiva());
                    if (auto.isAtiva()) auto.executar(dc); // Se estiver a ser ativada, executa imediatamente para aplicar efeitos
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
        Casa casa = dc.encontrarCasaPorId(id);
        if (casa != null && u.podeUsarCasa(casa)) {
            for (Divisao d : casa.getDivisoes().values()) {
                for (Dispositivo disp : d.getDispositivos().values()) disp.ligarDispositivo();
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
        Casa casa = dc.encontrarCasaPorId(id);
        if (casa != null && u.podeUsarCasa(casa)) {
            for (Divisao d : casa.getDivisoes().values()) {
                for (Dispositivo disp : d.getDispositivos().values()) disp.desligarDispositivo();
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


            //meter no menu o estado das cortinas (abertas ou fechadas)
            info.append("\n\nESTADO DAS CORTINAS POR CASA:\n");
            for(Casa c : u.getCasasUtilizador().values()){
                boolean temCortinas = false;
                for(Divisao d : c.getDivisoes().values()){
                    for(Dispositivo disp : d.getDispositivos().values()){
                        if(disp instanceof Cortina cortina){
                            temCortinas = true;
                            info.append(String.format(
                                    " > %s: [%s] -> [%d] cortina %s (%d%%)\n",
                                    c.getAlcunha(),
                                    d.getNome(),
                                    cortina.getId(),
                                    cortina.getNivelAbertura() > 0 ? "Aberta" : "Fechada",
                                    cortina.getNivelAbertura()
                            ));
                        }
                    }
                }
                if(!temCortinas){
                    info.append(String.format(" > %s: (Sem cortinas)\n", c.getAlcunha()));
                }
            }

            //meter no menu o estado das luzes (ligadas ou apagadas)
            info.append("\nESTADO DAS LUZES POR CASA:\n");
            for(Casa c : u.getCasasUtilizador().values()){
                boolean temLampadas = false;
                for(Divisao d : c.getDivisoes().values()){
                    for(Dispositivo disp : d.getDispositivos().values()){
                        if(disp instanceof Lampada lampada){
                            temLampadas = true;
                            info.append(String.format(
                                    " > %s: [%s] -> [%d] Lâmpada %s\n",
                                    c.getAlcunha(),
                                    d.getNome(),
                                    lampada.getId(),
                                    lampada.getEstado().equals("LIGADO") ? "Ligada" : "Desligada"
                            ));
                        }
                    }
                }
                if(!temLampadas){
                    info.append(String.format(" > %s: (Sem lâmpadas)\n", c.getAlcunha()));
                }
            }

            //meter no menu informacao sobre a coluna de som (ligada ou desligada e volume)
            info.append("\nESTADO DAS COLUNAS DE SOM POR CASA:\n");
            for(Casa c : u.getCasasUtilizador().values()){
                boolean temColuna = false;
                for(Divisao d : c.getDivisoes().values()){
                    for(Dispositivo disp : d.getDispositivos().values()){
                        if(disp instanceof ColunaSom coluna){
                            temColuna = true;
                            info.append(String.format(
                                    " > %s: [%s] -> [%d] Coluna de Som %s (Volume: %d%%)\n",
                                    c.getAlcunha(),
                                    d.getNome(),
                                    coluna.getId(),
                                    coluna.getEstado().equals("LIGADO") ? "Ligada" : "Desligada",
                                    coluna.getIntensidadeVolume()
                            ));
                        }
                    }
                }
                if(!temColuna){
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
                    Casa casaEsc = dc.encontrarCasaPorId(e.getIdCasa());
                    String nomeCasaEsc = casaEsc != null ? String.format("[ID:%d] %s", casaEsc.getId(), casaEsc.getAlcunha()) : "?";
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

            if (opcao == 0) break;

            if (opcao >= 1 && opcao <= 4) {
                StringBuilder infoCasa = new StringBuilder("Escolha a casa:\n\n");
                for (Casa c : u.getCasasUtilizador().values())
                    infoCasa.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));
                ConsoleUI.desenharDashboard("CRIAR ESCALONAMENTO", infoCasa.toString(), "ID da Casa\n0. Cancelar");
                System.out.print("ID da Casa: ");
                int idCasa = InputValidator.lerInteiro();
                if (idCasa == 0) continue;
                if (dc.encontrarCasaPorId(idCasa) == null) {
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
                        if (e.isAtivo()) e.desativar(); else e.ativar();
                        System.out.println("Estado alterado para: " + (e.isAtivo() ? "ATIVO" : "INATIVO"));
                        encontrado = true;
                        break;
                    }
                }
                if (!encontrado) ConsoleUI.mostrarErro("Escalonamento não encontrado.");
                InputValidator.lerLinha();
            }
        }
    }

    public static void menuCenarios(Utilizador u, DomusControl dc) {
        while (true) {
            StringBuilder info = new StringBuilder("CASAS COM CENÁRIOS:\n\n");
            for (Casa c : u.getCasasUtilizador().values()) {
                info.append(String.format(" > ID: %d | %s\n", c.getId(), c.getAlcunha()));
                List<Cenario> cenariosCasa = dc.getCenariosDaCasa(c.getId());
                if (cenariosCasa.isEmpty()) {
                    info.append("    (Sem cenários criados)\n");
                } else {
                    for (Cenario cen : cenariosCasa) {
                        info.append(String.format("    [%d] %s | %d ações\n", cen.getId(), cen.getNome(), cen.getAcoes().size()));
                    }
                }
            }

            String opts = "1. Criar cenários obrigatórios de uma casa\n" +
                          "2. Executar um cenário\n" +
                          "3. Executar todos os cenários de uma casa\n" +
                          "0. Voltar";

            ConsoleUI.desenharDashboard("CENÁRIOS", info.toString(), opts);
            System.out.print("\nEscolha a opção: ");
            int opcao = InputValidator.lerInteiro();

            if (opcao == 0) break;

            if (opcao == 1) {
                System.out.print("ID da Casa: ");
                int idCasa = InputValidator.lerInteiro();
                Casa casa = dc.encontrarCasaPorId(idCasa);
                if (casa != null && u.podeUsarCasa(casa)) {
                    dc.criarCenariosObrigatorios(idCasa);
                    System.out.println("Cenários obrigatórios criados com sucesso. Prima Enter...");
                    InputValidator.lerLinha();
                } else {
                    ConsoleUI.mostrarErro("Casa não encontrada ou sem permissões.");
                }
            } else if (opcao == 2) {
                System.out.print("ID do Cenário: ");
                int idCenario = InputValidator.lerInteiro();
                Cenario cenario = dc.encontrarCenarioPorId(idCenario);
                if (cenario != null) {
                    cenario.executar(dc);
                    System.out.println("Cenário executado: " + cenario.getNome() + ". Prima Enter...");
                    InputValidator.lerLinha();
                } else {
                    ConsoleUI.mostrarErro("Cenário não encontrado.");
                }
            } else if (opcao == 3) {
                System.out.print("ID da Casa: ");
                int idCasa = InputValidator.lerInteiro();
                Casa casa = dc.encontrarCasaPorId(idCasa);
                if (casa != null && u.podeUsarCasa(casa)) {
                    dc.criarCenariosObrigatorios(idCasa);
                    int executados = 0;
                    for (Cenario cenario : dc.getCenariosDaCasa(idCasa)) {
                        cenario.executar(dc);
                        executados++;
                    }
                    System.out.println(executados + " cenários executados com sucesso. Prima Enter...");
                    InputValidator.lerLinha();
                } else {
                    ConsoleUI.mostrarErro("Casa não encontrada ou sem permissões.");
                }
            } else {
                ConsoleUI.mostrarErro("Opção inválida.");
            }
        }
    }
}