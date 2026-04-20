package src.view;

import java.io.InputStreamReader;
import java.io.BufferedReader;

public class ConsoleUI {

    private static final String CLEAR_ALL = "\033[2J\033[H\033[3J";
    private static final String RESET = "\u001B[0m";
    private static final String BOLD = "\u001B[1m";
    private static final String PURPLE = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String WHITE = "\u001B[37m";
    private static final String BG_PURPLE = "\u001B[45m";

    /**
     * Obtém as dimensões atuais do terminal (Colunas e Linhas).
     */
    private static int[] getTerminalDimensions() {
        try {
            // Comando para Linux/WSL/macOS
            Process p = Runtime.getRuntime().exec(new String[]{"sh", "-c", "stty size < /dev/tty"});
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String size = reader.readLine();
            if (size != null) {
                String[] parts = size.split(" ");
                return new int[]{Integer.parseInt(parts[1]), Integer.parseInt(parts[0])};
            }
        } catch (Exception e) {
            // Fallback se o comando falhar
        }
        return new int[]{85, 24};
    }

    public static void desenharDashboard(String titulo, String info, String opcoes) {
        int[] dims = getTerminalDimensions();
        int largura = dims[0] - 2; // Margem pequena
        int alturaTotal = dims[1];

        // Calculamos alturas dinâmicas (ex: 50% para info, 25% para opções)
        int alturaInfo = Math.max(5, (alturaTotal - 10) / 2);
        int alturaOpts = Math.max(3, (alturaTotal - alturaInfo - 8));

        System.out.print(CLEAR_ALL);
        System.out.flush();

        // 1. TOPO
        System.out.print(PURPLE);
        imprimirLinha(largura, "╔", "═", "╗");

        // 2. TÍTULO
        int larguraInterna = largura - 4;
        String tit = centerString(titulo.toUpperCase(), larguraInterna);
        System.out.printf("║ %s%s%s%s ║\n", BG_PURPLE + WHITE + BOLD, tit, RESET, PURPLE);
        imprimirLinha(largura, "╠", "═", "╣");

        // 3. ÁREA DE INFORMAÇÃO
        System.out.print(CYAN);
        String[] linhasInfo = (info == null ? "" : info).split("\n");
        for (int i = 0; i < alturaInfo; i++) {
            String conteudo = (i < linhasInfo.length) ? linhasInfo[i] : "";
            System.out.printf("║  %-" + (larguraInterna - 2) + "s  ║\n", truncar(conteudo, larguraInterna - 4));
        }

        // 4. DIVISÓRIA E OPÇÕES
        System.out.print(PURPLE);
        imprimirLinha(largura, "╠", "═", "╣");
        System.out.print(WHITE);
        String[] linhasOpt = (opcoes == null ? "" : opcoes).split("\n");
        for (int i = 0; i < alturaOpts; i++) {
            String opt = (i < linhasOpt.length) ? linhasOpt[i] : "";
            System.out.printf("║  %-" + (larguraInterna - 2) + "s  ║\n", truncar(opt, larguraInterna - 4));
        }

        // 5. BASE
        System.out.print(PURPLE);
        imprimirLinha(largura, "╚", "═", "╝");
        System.out.print(RESET);
        System.out.flush();
    }

    private static void imprimirLinha(int largura, String inicio, String meio, String fim) {
        System.out.print(inicio);
        for (int i = 0; i < largura - 2; i++) System.out.print(meio);
        System.out.println(fim);
    }

    private static String truncar(String s, int max) {
        if (s.length() <= max) return s;
        return s.substring(0, max - 3) + "...";
    }

    private static String centerString(String s, int width) {
        if (s.length() >= width) return s.substring(0, width);
        int paddingTotal = width - s.length();
        int pL = paddingTotal / 2;
        int pR = paddingTotal - pL;
        return " ".repeat(pL) + s + " ".repeat(pR);
    }
}