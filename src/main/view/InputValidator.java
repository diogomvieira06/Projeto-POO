package src.main.view;

import java.util.Scanner;

public class InputValidator {

    private static Scanner sc = new Scanner(System.in);

    // --- VALIDAÇÃO DE INPUTS (ROBUSTEZ - REQUISITO 8.0) ---

    public static int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Erro: Introduza um número inteiro: ");
            }
        }
    }

    public static double lerDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Erro: Introduza um valor decimal (ex: 15.5): ");
            }
        }
    }

    public static String lerLinha() {
        return sc.nextLine();
    }

}