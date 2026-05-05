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
                        if(dispositivo instanceof Cortina c){
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

    public static Acao definirIntensidadeLampadasCasa(int idCasa, int intensidade) {
        return new Acao("Definir Intensidade das Lampadas") {
            public void executar(DomusControl dc) {
                Casa casa = dc.encontrarCasaPorId(idCasa);
                if (casa == null) return;

                for (Divisao divisao : casa.getDivisoes().values()) {
                    for (Dispositivo dispositivo : divisao.getDispositivos().values()) {
                        if (dispositivo instanceof Lampada l) {
                            l.setIntensidade_Luminosidade(intensidade);
                        }
                    }
                }
            }
            public Acao clone() { return definirIntensidadeLampadasCasa(idCasa, intensidade); }
        };
    }

    public static Acao definirCorLampadasCasa(int idCasa, String cor) {
        return new Acao("Definir Cor das Lampadas") {
            public void executar(DomusControl dc) {
                Casa casa = dc.encontrarCasaPorId(idCasa);
                if (casa == null) return;

                for (Divisao divisao : casa.getDivisoes().values()) {
                    for (Dispositivo dispositivo : divisao.getDispositivos().values()) {
                        if (dispositivo instanceof Lampada l) {
                            l.setCor_Luz(cor);
                        }
                    }
                }
            }
            public Acao clone() { return definirCorLampadasCasa(idCasa, cor); }
        };
    }

    //ESCALONAMENTOS

    //abrir cortinas da casa a 100%
    public static Acao abrirCortinas(int idCasa) {
        return new Acao("Abrir Cortinas") {
            public void executar(DomusControl dc) {
                Casa casa = dc.encontrarCasaPorId(idCasa);
                if (casa == null) return;

                for (Divisao divisao : casa.getDivisoes().values()) {
                    for (Dispositivo dispositivo : divisao.getDispositivos().values()) {
                        if (dispositivo instanceof Cortina c) {
                            c.setNivelAbertura(100);
                        }
                    }
                }
            }
            public Acao clone() { return abrirCortinas(idCasa); }
        };
    }
    
    //desliga todas as luzes da casa
    public static Acao desligarLuzesCasa(int idCasa) {
        return new Acao("Desligar Luzes") {
            public void executar(DomusControl dc) {
                Casa casa = dc.encontrarCasaPorId(idCasa);
                if (casa == null) return;

                for (Divisao divisao : casa.getDivisoes().values()) {
                    for (Dispositivo dispositivo : divisao.getDispositivos().values()) {
                        if (dispositivo instanceof Lampada l) {
                            l.desligarDispositivo();//desliga
                        }
                    }
                }
            }
            public Acao clone() { return desligarLuzesCasa(idCasa); }
        };
    }

    //liga todas as colunas de som da casa
        public static Acao ligarColunaSomCasa(int idCasa) {
        return new Acao("Ligar Coluna de Som") {
            public void executar(DomusControl dc) {
                Casa casa = dc.encontrarCasaPorId(idCasa);
                if (casa == null) return;

                for (Divisao divisao : casa.getDivisoes().values()) {
                    for (Dispositivo dispositivo : divisao.getDispositivos().values()) {
                        if (dispositivo instanceof ColunaSom c) {
                            c.ligarDispositivo();
                        }
                    }
                }
            }
            public Acao clone() { return ligarColunaSomCasa(idCasa); }
        };
    }

    //desliga todas as colunas de som da casa
        public static Acao desligarColunaSomCasa(int idCasa) {
        return new Acao("Desligar Coluna de Som") {
            public void executar(DomusControl dc) {
                Casa casa = dc.encontrarCasaPorId(idCasa);
                if (casa == null) return;
                for (Divisao divisao : casa.getDivisoes().values()) {
                    for (Dispositivo dispositivo : divisao.getDispositivos().values()) {
                        if (dispositivo instanceof ColunaSom c) {
                            c.desligarDispositivo();
                        }
                    }
                }
            }
            public Acao clone() { return desligarColunaSomCasa(idCasa); }
        };
    }

    //desligar luzes e fechar cortinas da casa
    public static Acao desligarLuzesEFecharCortinas(int idCasa){
        return new Acao("Desligar Luzes e Fechar Cortinas"){
            //desliga as luzes e fecha as cortinas da casa
            public void executar(DomusControl dc){
                desligarLuzesCasa(idCasa).executar(dc);
                fecharCortinas(idCasa).executar(dc);
            }
            public Acao clone(){return desligarLuzesEFecharCortinas(idCasa);}
        };
    }
}
