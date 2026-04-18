import java.util.*;

public class Main {
    private static Scanner sc = new Scanner(System.in); // Scanner para ler a entrada do utilizador


    public static void main(String[] args){
        DomusControl domusControl = new DomusControl();
        //Criação de alguns objetos para teste
        Lampada lampada1 = new Lampada(1, "Philips", "Hue", 10, 80, "Branco");
        Tomada tomada1 = new Tomada(2, "TP-Link", "Kasa", 5);
        Curtina cortina1 = new Curtina(3, "Ikea", "Tradfri", 8, 50);
        ColunaSom coluna1 = new ColunaSom(4, "Bose", "SoundLink", 15, 70);
        Divisao sala = new Divisao("Sala", 1);
        sala.adicionarDispositivo(lampada1);
        sala.adicionarDispositivo(tomada1);
        sala.adicionarDispositivo(cortina1);
        sala.adicionarDispositivo(coluna1);
        Lampada lampada2 = new Lampada(5, "Philips", "Hue", 10, 60, "Amarelo");
        Tomada tomada2 = new Tomada(6, "TP-Link", "Kasa", 5);
        Divisao quarto = new Divisao("Quarto", 2);
        quarto.adicionarDispositivo(lampada2);
        quarto.adicionarDispositivo(tomada2);
        domusControl.criarCasa("Casa do uti1-a");
        domusControl.criarCasa("Casa do uti1-b");
        domusControl.criarCasa("Casa do uti2-a");
        domusControl.criarCasa("Casa do uti2-b");

        Casa casa1 = domusControl.encontrarCasaPorId(1);
        Casa casa2 = domusControl.encontrarCasaPorId(2);
        Casa casa3 = domusControl.encontrarCasaPorId(3);
        Casa casa4 = domusControl.encontrarCasaPorId(4);

        if (casa1 != null) {
            casa1.adicionarDivisao(sala);
            casa1.adicionarDivisao(quarto);
        }

        Lampada lampada3 = new Lampada(7, "Xiaomi", "Yeelight", 9, 75, "Neutro");
        Tomada tomada3 = new Tomada(8, "Meross", "MSS310", 6);
        Divisao cozinha = new Divisao("Cozinha", 3);
        cozinha.adicionarDispositivo(lampada3);
        cozinha.adicionarDispositivo(tomada3);
        if (casa2 != null) {
            casa2.adicionarDivisao(cozinha);
        }

        ColunaSom coluna2 = new ColunaSom(9, "JBL", "Flip", 12, 55);
        Divisao escritorio = new Divisao("Escritório", 4);
        escritorio.adicionarDispositivo(coluna2);
        if (casa3 != null) {
            casa3.adicionarDivisao(escritorio);
        }

        Utilizador uti1 = domusControl.criarUtilizador("uti1");
        Utilizador uti2 = domusControl.criarUtilizador("uti2");

        if (casa1 != null) {
            domusControl.adicionarCasaAAdministrador(uti1, casa1);
            domusControl.adicionarCasaAUtilizador(uti2, casa1);
        }
        if (casa2 != null) {
            domusControl.adicionarCasaAAdministrador(uti1, casa2);
            domusControl.adicionarCasaAUtilizador(uti2, casa2);
        }
        if (casa3 != null) {
            domusControl.adicionarCasaAAdministrador(uti2, casa3);
            domusControl.adicionarCasaAUtilizador(uti1, casa3);
        }
        if (casa4 != null) {
            domusControl.adicionarCasaAAdministrador(uti2, casa4);
            domusControl.adicionarCasaAUtilizador(uti1, casa4);
        }


        boolean sair = false;
        System.out.println("Bem-vindo ao DomusControl!");
        boolean administrador_bool = false;

        Utilizador utilizador_atual = null;
        

        System.out.println("Nome de Utilizador: ");
        String nomeUtilizador = sc.nextLine();
        if(nomeUtilizador.equals("uti1")){
            utilizador_atual = uti1;// Teste, depois implementar um método para encontrar o utilizador por nome
            System.out.println("Bem-vindo, uti1!");
        }
        else if(nomeUtilizador.equals("uti2")){
            utilizador_atual = uti2;// Teste, depois implementar um método para encontrar o utilizador por nome
            System.out.println("Bem-vindo, uti2!");
        }
        else {
            System.out.println("Utilizador não reconhecido.");
            return; // Encerra o programa se o utilizador não for reconhecido
        }
        
        while(!sair){
            if (administrador_bool) {
                
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
                        System.out.print("Nome do utilizador: ");
                        String nomeNovoUtilizador = sc.nextLine();
                        while (true) {
                            System.out.println("Quer associar uma casa a este utilizador?");
                            System.out.println("1. Sim (Administrador)");
                            System.out.println("2. Sim (Utilizador Normal)");
                            System.out.println("3. Não");
                            System.out.print("Opção: ");
                            int tipoUtilizador = sc.nextInt();
                            sc.nextLine(); // Consumir o \n após ler o número
                            if (tipoUtilizador == 3) {
                                domusControl.criarUtilizador(nomeNovoUtilizador);
                                break; // Sai do loop se a opção for voltar
                            } else if (tipoUtilizador == 1) {
                                System.out.println("Que casa quer associar?");
                                domusControl.listarCasas();
                                System.out.print("ID da casa a adicionar: ");
                                int idCasa = sc.nextInt();
                                sc.nextLine(); // Consumir o \n após ler o número
                                Casa casa = domusControl.encontrarCasaPorId(idCasa);
                                if (casa != null) {
                                    Utilizador utilizador = domusControl.criarUtilizador(nomeNovoUtilizador);
                                    domusControl.adicionarCasaAAdministrador(utilizador, casa);
                                } else {
                                    System.out.println("Casa não encontrada!");
                                }
                            } else if (tipoUtilizador == 2) {
                                System.out.println("Que casa quer associar?");
                                domusControl.listarCasas();
                                System.out.print("ID da casa a adicionar: ");
                                int idCasa = sc.nextInt();
                                sc.nextLine(); // Consumir o \n após ler o número
                                Casa casa = domusControl.encontrarCasaPorId(idCasa);
                                if (casa != null) {
                                    Utilizador utilizador = domusControl.criarUtilizador(nomeNovoUtilizador);
                                    domusControl.adicionarCasaAUtilizador(utilizador, casa);
                                } else {
                                    System.out.println("Casa não encontrada!");
                                }
                            } else {
                                System.out.println("Opção inválida");
                            }
                        }
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
                                        System.out.print("Intensidade de luminosidade (0-100): ");
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
                                        System.out.print("Intensidade do volume (0-100): ");
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
                        System.out.println("Lista dos utilizadores:");
                        domusControl.listarUtilizadores();
        
                    }
                    case 6 -> {
                        System.out.println("Lista das suas casas:");
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
            else {
                System.out.println("\n--- DomusControl ---");
                System.out.println("1. Gestão de Casas");
                System.out.println("2. Criar Casa");
                System.out.println("3. Automações a todas as casas disponíveis");
                System.out.println("0. Sair");
                System.out.print("Opção: ");
    
                int opcao = sc.nextInt();
                sc.nextLine(); // Consumir o \n após ler o número
                switch (opcao) {
                    case 1 -> {
                        while(true){
                            domusControl.listarCasasdeAdministrador(utilizador_atual);
                            System.out.println("");
                            domusControl.listarCasasdeUtilizador(utilizador_atual);
                            System.out.println("0. Para voltar");
                            System.out.print("Id da casa que quer gerir: ");
                            int idCasa = sc.nextInt();
                            sc.nextLine(); // Consumir o \n após ler o número
                            if(idCasa == 0) break;
                            Casa casa = domusControl.encontrarCasaPorId(idCasa);
                            if (casa != null) {
                                System.out.println("Casa selecionada: " + casa.getAlcunha());
                                // Aqui pode adicionar mais opções para gerir a casa, como listar divisões, controlar dispositivos
                                while (true) {
                                    System.out.println("Divisões: ");
                                    casa.listarDivisoes();
                                    System.out.println("0. Para voltar");
                                    System.out.print("Id da divisão que quer gerir : ");
                                    int idDivisao = sc.nextInt();
                                    sc.nextLine(); // Consumir o \n após ler o número
                                    if(idDivisao == 0) break;
                                    if (idDivisao != 0) System.out.println("Ainda não implementamos");
                                }
                            } else {
                                System.out.println("Casa não encontrada!");
                            }
                        }
                        
                    }
                    case 2 -> {
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
                    }
                    case 0 -> sair = true;
                    default -> System.out.println("Opção inválida ");
                }

            }
        }    
        sc.close(); // Fechar o Scanner ao final do programa
    }
}
