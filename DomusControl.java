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

            int opcao = sc.nextInt();
            switch (opcao) {
                case 1 -> criarUtilizador();
                case 2 -> criarCasa();
                case 3 -> criarDivisao();
                case 4 -> adicionarDispositivo();
                case 5 -> listarUtilizadores();
                case 6 -> listarCasas();
                case 7 -> controlarDispositivo();// ver melhor dps
                case 0 -> sair = true;
                default -> System.out.println("Opção inválida ");
            }
        }
        sc.close(); // Fechar o Scanner ao final do programa
    }

    private static void criarUtilizador(){
        System.out.println("ID do utilizador: ");
        int id = sc.nextInt();
        System.out.print("Nome do utilizador: ");
        String nome = sc.next();
        utilizadores.add(new Utilizador(id, nome));
        System.out.println("Utilizador criado com sucesso!");
    }
}
