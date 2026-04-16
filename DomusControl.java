import java.util.*;

public class DomusControl {
    
    private static ArrayList<Utilizador> utilizadores = new ArrayList<>();
    private static ArrayList<Casa> casas = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    private static int proximoIdUtilizador = 1;
    private static int proximoIdCasa = 1;
    private static int proximoIdDivisao = 1;
    private static int proximoIdDispositivo = 1;

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
        System.out.print("Nome do utilizador: ");
        String nome = sc.next();
        int id = proximoIdUtilizador++;
        utilizadores.add(new Utilizador(id, nome));
        System.out.println("Utilizador criado com sucesso! Id atribuido: " + id);
    }

    private static void criarCasa(){
        System.out.print("Alcunha da casa: ");
        int id = proximoIdCasa++;
        String alcunha = sc.next();
        casas.add(new Casa(alcunha, id));
        System.out.println("Casa criada com sucesso, Id atribuido: " + id);
    }

    private static Casa encontrarCasaPorId(int id){
        for(Casa c : casas){
            if(c.getId() == id) return c;
        }
        return null;
    }

    private static void criarDivisao(){
        System.out.print("ID da casa onde quer criar a divisão: ");
        int idCasa = sc.nextInt();
        Casa casa = encontrarCasaPorId(idCasa);//ve se ja existe a casa
        
        if(casa != null){
            System.out.print("Nome da divisao: ");
            String nomeDivisao = sc.next();
            int idDivisao = proximoIdDivisao++;
            casa.adicionarDivisao(new Divisao(nomeDivisao, idDivisao));
            System.out.println("Divisão criada com sucesso! Id atribuido: " + idDivisao);
        } else {
            System.out.println("Casa não encontrada");
        }
    }

    private static Divisao encontrarDivisaoPorId (Casa casa, int id){
        for(Divisao d : casa.getDivisoes()){
            if(d.getId() == id) return d;
        }
        return null;
    }

    private static void adicionarDispositivo(){
        System.out.print("ID da casa onde quer adicionar o dispositivo: ");
        int idCasa = sc.nextInt();
        Casa casa = encontrarCasaPorId(idCasa);

        if(casa != null){
            System.out.print("ID da divisao onde quer adicionar o dispositivo: ");
            int idDivisao = sc.nextInt();
            Divisao divisao = encontrarDivisaoPorId(casa, idDivisao);

            if(divisao != null){
                System.out.println("Tipos de dispositivo:");
                System.out.println("1. Lâmpada");
                System.out.println("2. Tomada");
                System.out.println("3. Cortina");
                System.out.println("4. Coluna de Som");
                System.out.println("5. Portão de Garagem");
                System.out.print("Escolha o tipo: ");
                int tipo = sc.nextInt();

                if(tipo < 1 || tipo > 5){
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
                int idDispositivo = proximoIdDispositivo++;

                //Criar o dispositivo com base no tipo escolhido
                switch(tipo){
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
            }
        }

    }

    private static void listarUtilizadores(){
        for(Utilizador u : utilizadores){
            System.out.println("ID: " + u.getId() + " - Nome: " + u.getNome());
        }
    }

    private static void listarCasas(){
        for(Casa c : casas){
            System.out.println("ID: " + c.getId() + " - Alcunha: " + c.getAlcunha() + " - Nº Divisões: " + c.getDivisoes().size() + "Nº Dispositivos: "+ c.getDivisoes().stream().mapToInt(d -> d.getDispositivos().size()).sum());
        }
    }

    private static Dispositivo encontrarDispositivoPorId(Divisao divisao, int id){
        for(Dispositivo d : divisao.getDispositivos()){
            if(d.getId() == id) return d;
        }
        return null;
    }

    private static void controlarDispositivo(){
        System.out.print("ID da casa onde está o dispositivo: ");
        int idCasa = sc.nextInt();
        Casa casa = encontrarCasaPorId(idCasa);

        if(casa != null){
            System.out.print("ID da divisao onde está o dispositivo: ");
            int idDivisao = sc.nextInt();
            Divisao divisao = encontrarDivisaoPorId(casa, idDivisao);

            if(divisao != null){
                System.out.print("ID do dispositivo que quer controlar: ");
                int idDispositivo = sc.nextInt();
                Dispositivo dispositivo = encontrarDispositivoPorId(divisao, idDispositivo);//ver melhor dps, falta o consumo por hora e tal

                if(dispositivo != null){
                    System.out.println("1. Ligar dispositivo");
                    System.out.println("2. Desligar dispositivo");
                    System.out.print("Opção: ");
                    int opcao = sc.nextInt();

                    if(opcao == 1){
                        dispositivo.ligarDispositivo();
                        System.out.println("Dispositivo ligado com sucesso!");
                    } else if(opcao == 2){
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
}
