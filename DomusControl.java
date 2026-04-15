import java.util.*;

public class DomusControl {
    
    private static ArrayList<Utilizador> utilizadores = new ArrayList<>();
    private static ArrayList<Casa> casas = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args){
        boolean sair = false;
        
        while(!sair){
            System.out.println("\n--- DomusControl ---");
            System.out.println("1. Criar utilizador");
            System.out.println("2. Criar casa");
            System.out.println("3. Criar divisão");
            System.out.println("4. Adicionar dispositivo");
            System.out.println("5. Listar utilizadores");
            System.out.println("6. Listar casas");
            System.out.println("7. Ligar/desligar dispositivo");
            System.out.println("0. Sair");
            System.out.print("Opção: ");

            //int opçao = sc.parseInt(sc.nextInt());


        }
    }
}
