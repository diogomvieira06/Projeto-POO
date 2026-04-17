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

    public void criarUtilizador(String nome){
        int id = proximoIdUtilizador++;
        utilizadores.add(new Utilizador(id, nome));
        System.out.println("Utilizador criado com sucesso! Id atribuido: " + id);
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
}