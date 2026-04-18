import java.util.*;

public class DomusControl {
    
    private static ArrayList<Utilizador> utilizadores = new ArrayList<>();
    private static ArrayList<Casa> casas = new ArrayList<>();

    private static int proximoIdUtilizador = 1;
    private static int proximoIdCasa = 1;
    private static int proximoIdDivisao = 1;
    private static int proximoIdDispositivo = 1;

    public int aumentarIdDispositivo() {
        return proximoIdDispositivo++;
    }
    public int aumentarIdDivisao() {
        return proximoIdDivisao++;
    }
    public int aumentarIdCasa() {
        return proximoIdCasa++;
    }
    public int aumentarIdUtilizador() {
        return proximoIdUtilizador++;
    }

    public Utilizador criarUtilizador(String nome){
        int id = proximoIdUtilizador++;
        Utilizador utilizador = new Utilizador(id, nome);
        utilizadores.add(utilizador);
        System.out.println("Utilizador criado com sucesso! Id atribuido: " + id);
        return utilizador;
    }

    public void criarCasa(String alcunha){
        int id = proximoIdCasa++;
        casas.add(new Casa(alcunha, id));
        System.out.println("Casa criada com sucesso, Id atribuido: " + id);
    }

    public Casa encontrarCasaPorId(int id){
        for(Casa c : casas){
            if(c.getId() == id) return c;
        }
        return null;
    }

    public void listarCasas() {
        for (Casa c : casas) {
            System.out.println("ID: " + c.getId() + " - Alcunha: " + c.getAlcunha());
        }
    }

    public void criarDivisao(Casa casa, String nomeDivisao){
        int idDivisao = proximoIdDivisao++;
        Divisao divisao = new Divisao(nomeDivisao, idDivisao);
        casa.adicionarDivisao(divisao);
        System.out.println("Divisão criada com sucesso! Id atribuido: " + idDivisao);
    }

    public Divisao encontrarDivisaoPorId(Casa casa, int id){
        for(Divisao d : casa.getDivisoes()){
            if(d.getId() == id) return d;
        }
        return null;
    }

    public void listarUtilizadores(){
        for(Utilizador u : utilizadores){
            System.out.println("ID: " + u.getId() + " - Nome: " + u.getNome());
        }
    }

    public Dispositivo encontrarDispositivoPorId(Divisao divisao, int id){
        for(Dispositivo d : divisao.getDispositivos()){
            if(d.getId() == id) return d;
        }
        return null;
    }

    public void adicionarCasaAAdministrador(Utilizador administrador, Casa casa){
        administrador.adicionarCasaAdmistrada(casa);
    }

    public void removerCasaDeAdministrador(Utilizador administrador, Casa casa){
        administrador.removerCasaAdmistrada(casa);
    }

    public void adicionarCasaAUtilizador(Utilizador utilizador, Casa casa){
        utilizador.adicionarCasaUtilizador(casa);
    }

    public void removerCasaDeUtilizador(Utilizador utilizador, Casa casa){
        utilizador.removerCasaUtilizador(casa);
    }

    public void listarCasasdeUtilizador(Utilizador utilizador){
        System.out.println("Casas associadas ao utilizador " + utilizador.getNome() + ":");
        for(Casa c : utilizador.getCasasUtilizador()){
            if (utilizador.getCasasAdmistradas().contains(c)) {
                continue; // Pula as casas onde o utilizador é administrador, para evitar duplicação
            }
            System.out.println("ID: " + c.getId() + " - Alcunha: " + c.getAlcunha());
        }
    }

    public void listarCasasdeAdministrador(Utilizador utilizador){
        System.out.println("Casas administradas pelo utilizador " + utilizador.getNome() + ":");
        for(Casa c : utilizador.getCasasAdmistradas()){
            System.out.println("ID: " + c.getId() + " - Alcunha: " + c.getAlcunha());
        }
    }
}