package src.automacao;
import java.io.Serializable;

import src.controller.*;//DomusControl
import src.model.*;

public abstract class Acao implements Serializable{
    private static final long serialVersionUID = 1L;

    private String nome;

    public Acao(String nome) {
        this.nome = nome;
    }

    public Acao() {
        this.nome = "";
    }

    public Acao(Acao a) {
        this.nome = a.nome;
    }

    //getter
    public String getNome() {
        return this.nome;
    }

    //setter
    public void setNome(String nome) {
        this.nome = nome;
    }

    public abstract void executar(DomusControl dc); // Método abstrato, cada tipo de ação terá sua própria implementação
    public abstract Acao clone(); // Método para criar uma cópia da ação, necessário para cópias profundas em Automacao


    @Override
    public String toString() {
        return "Acao{" +
                "nome='" + nome + '\'' +
                '}';
    }


    public static Acao fecharCortinas(int idCasa){
        return new Acao("Fechar Cortinas"){
            public void executar(DomusControl dc){
                Casa casa = dc.encontrarCasaPorId(idCasa);
                if(casa == null) return;
                for(Divisao divisao : casa.getDivisoes().values()){
                    for(Dispositivo dispositivo : divisao.getDispositivos().values()){
                        if(dispositivo instanceof Curtina c){
                            c.setNivelAbertura(0);//fechou
                        }
                    }
                }
            }
            public Acao clone(){
                return fecharCortinas(idCasa);
            }
        };

    }

    //liga as luzes de casa quando a luminosidade esta baixa
    public static Acao ligarLuzesCasa(int idCasa){
        return new Acao("Ligar Luzes"){
            public void executar(DomusControl dc){
                
                Casa casa = dc.encontrarCasaPorId(idCasa);
                if(casa == null)return;
                for(Divisao divisao : casa.getDivisoes().values()){
                    for(Dispositivo dispositivo : divisao.getDispositivos().values()){
                        if(dispositivo instanceof Lampada l){
                            l.ligarDispositivo();
                        }
                    }
                }
            }
            public Acao clone(){return ligarLuzesCasa(idCasa);}
        };
    }
}
