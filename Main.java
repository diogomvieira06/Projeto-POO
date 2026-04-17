import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in); // Scanner para ler a entrada do utilizador


    public static void main(String[] args){
        DomusControl domusControl = new DomusControl();
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
            sc.nextLine(); // Consumir o \n após ler o número
            switch (opcao) {
                case 1 -> {
                    System.out.print("Nome do Utilizador: ");
                    String nome = sc.nextLine(); 
                    domusControl.criarUtilizador(nome);
                }
                case 2 -> {
                    System.out.print("Alcunha da casa: ");
                    String alcunha = sc.nextLine();
                    domusControl.criarCasa(alcunha);
                }
                case 3 -> {
                    System.out.println("ID da casa onde quer criar a divisão: ");
                    int idCasa = sc.nextInt();
                    sc.nextLine(); // Consumir o \n após ler o número
                    Casa casa = domusControl.encontrarCasaPorId(idCasa);//ve se ja existe a casa
                    if (casa != null) {
                        System.out.print("Nome da divisão: ");
                        String nomeDivisao = sc.nextLine();
                        domusControl.criarDivisao(casa, nomeDivisao);
                    }
                    else {
                        System.out.println("Casa não encontrada!");
                        System.out.println("Lista das suas casas:");
                        domusControl.listarCasas();
                    }
                }
                case 4 -> {
                    System.out.print("ID da casa onde quer adicionar o dispositivo: ");
                    int idCasa = sc.nextInt();
                    sc.nextLine(); // Consumir o \n após ler o número
                    Casa casa = domusControl.encontrarCasaPorId(idCasa);

                    if (casa != null) {
                        System.out.print("ID da divisão onde quer adicionar o dispositivo: ");
                        int idDivisao = sc.nextInt();
                        sc.nextLine(); // Consumir o \n após ler o número
                        Divisao divisao = domusControl.encontrarDivisaoPorId(casa, idDivisao);

                        if (divisao != null) {
                            System.out.println("Tipos de dispositivo:");
                            System.out.println("1. Lâmpada");
                            System.out.println("2. Tomada");
                            System.out.println("3. Cortina");
                            System.out.println("4. Coluna de Som");
                            System.out.print("Escolha o tipo de dispositivo: ");
                            int tipoDispositivo = sc.nextInt();
                            sc.nextLine(); // Consumir o \n após ler o número

                            if(tipoDispositivo < 1 || tipoDispositivo > 4){
                                System.out.println("Tipo inválido");
                                return;//volta ao menu principal
                            }                                            

                            System.out.print("Marca do dispositivo: ");
                            String marca = sc.next();
                            System.out.print("Modelo do dispositivo: ");
                            String modelo = sc.next();
                            System.out.print("Consumo por hora (Wh): ");
                            double consumo = sc.nextDouble();
            
                            Dispositivo dispositivo = null;
                            int idDispositivo = domusControl.aumentarIdDispositivo(); // Talvez provisorio
            
                            //Criar o dispositivo com base no tipo escolhido
                            switch(tipoDispositivo){
                                //LAMPADA
                                case 1 : {
                                    System.out.print("Intensidade de luminosidade (0-100: ");
                                    int intensidade = sc.nextInt();
                                    System.out.print("Cor da luz: ");
                                    String corLuz = sc.next();
                                    dispositivo = new Lampada(idDispositivo, marca, modelo, consumo, intensidade, corLuz);
                                    break;
                                }
            
                                //TOMADA
                                case 2 : {
                                    dispositivo = new Tomada(idDispositivo, marca, modelo, consumo);
                                    break;
                                }
            
                                //CORTINA
                                case 3 : {
                                    System.out.print("Nivel de abertura (0-100): ");
                                    int nivelAbertura = sc.nextInt();
                                    dispositivo = new Curtina(idDispositivo, marca, modelo, consumo, nivelAbertura);
                                    break;
                                }
            
                                //COLUNA DE SOM
                                case 4 : {
                                    System.out.print("Intensidade do volume (0-100: ");
                                    int intensidadeVolume = sc.nextInt();
                                    dispositivo = new ColunaSom(idDispositivo, marca, modelo, consumo, intensidadeVolume);
                                    break;
                                }
            
                                //PORTAO DE GARAGEM
                                case 5 : {
                                    System.out.print("Nivel de abertura (0-100): ");
                                    int nivelAbertura = sc.nextInt();
                                    dispositivo = new PortaoGaragem(idDispositivo, marca, modelo, consumo, nivelAbertura);
                                    break;
                                }
                            }
                            //Aqu adicionamos o dispositivo à divisão escolhida
                            divisao.adicionarDispositivo(dispositivo);
                            System.out.println("Dispositivo adicionado com sucesso! Id atribuido: " + idDispositivo);
                        
                        } else {
                            System.out.println("Divisão não encontrada!");
                        }
                    } else {
                        System.out.println("Casa não encontrada!");
                        System.out.println("Lista das suas casas:");
                        domusControl.listarCasas();
                    }
                }
                case 5 -> {
                    domusControl.listarUtilizadores();
    
                }
                case 6 -> {
                    domusControl.listarCasas();
                }
                case 7 -> {
                    System.out.print("Id da casa onde está o dispositivo: ");
                    int idCasa = sc.nextInt();
                    sc.nextLine(); // Consumir o \n após ler o número
                    Casa casa = domusControl.encontrarCasaPorId(idCasa);
            
                    if(casa != null){
                        System.out.print("Id da divisão onde está o dispositivo: ");
                        int idDivisao = sc.nextInt();
                        sc.nextLine(); // Consumir o \n após ler o número
                        Divisao divisao = domusControl.encontrarDivisaoPorId(casa, idDivisao);
            
                        if(divisao != null){
                            System.out.print("Id do dispositivo que quer controlar: ");
                            int idDispositivo = sc.nextInt();
                            sc.nextLine(); // Consumir o \n após ler o número
                            Dispositivo dispositivo = domusControl.encontrarDispositivoPorId(divisao, idDispositivo);//ver melhor dps, falta o consumo por hora e tal
            
                            if(dispositivo != null){
                                System.out.println("1. Ligar dispositivo");
                                System.out.println("2. Desligar dispositivo");
                                System.out.print("Opção: ");
                                int opcao2 = sc.nextInt();
            
                                if(opcao2 == 1){
                                    dispositivo.ligarDispositivo();
                                    System.out.println("Dispositivo ligado com sucesso!");
                                } else if(opcao2 == 2){
                                    dispositivo.desligarDispositivo();
                                    System.out.println("Dispositivo desligado com sucesso!");
                                } else {
                                    System.out.println("Opção inválida");
                                }
                            } else {
                                System.out.println("Dispositivo não encontrado");
                            }
                        } else {
                            System.out.println("Divisão não encontrada");
                        }
                    } else {
                        System.out.println("Casa não encontrada");
                    }
                }// ver melhor dps
                case 0 -> sair = true;
                default -> System.out.println("Opção inválida ");
            }
        }
        sc.close(); // Fechar o Scanner ao final do programa
    }
}
