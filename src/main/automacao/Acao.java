package src.main.automacao;
import java.io.Serializable;
import src.main.controller.DomusControl;
import src.main.model.*;
import src.main.Exceptions.DomusControlException;

public abstract class Acao implements Serializable {
    private static final long serialVersionUID = 1L;
    private String nome;
    protected int idDivisaoAlvo = -1;

    public Acao(String nome) { this.nome = nome; }
    public Acao() { this.nome = ""; }
    public Acao(Acao a) { this.nome = a.nome; this.idDivisaoAlvo = a.idDivisaoAlvo; }

    public abstract void executar(DomusControl dc);
    public abstract Acao clone();

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public void setDivisaoAlvo(int id) { this.idDivisaoAlvo = id; }


    public static Acao fecharCortinas(int idCasa) {
        return new Acao("Fechar Cortinas") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao divisao : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && divisao.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : divisao.getDispositivos().values()) {
                            if (disp instanceof Cortina c) c.desligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = fecharCortinas(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    public static Acao abrirCortinas(int idCasa) {
        return new Acao("Abrir Cortinas") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao divisao : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && divisao.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : divisao.getDispositivos().values()) {
                            if (disp instanceof Cortina c) c.ligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = abrirCortinas(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    public static Acao ligarLuzesCasa(int idCasa) {
        return new Acao("Ligar Luzes") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof Lampada l) l.ligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = ligarLuzesCasa(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    public static Acao desligarLuzesCasa(int idCasa) {
        return new Acao("Desligar Luzes") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof Lampada l) l.desligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = desligarLuzesCasa(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    public static Acao definirIntensidadeLampadasCasa(int idCasa, int intensidade) {
        return new Acao("Definir Intensidade") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof Lampada l) l.setIntensidade_Luminosidade(intensidade);
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = definirIntensidadeLampadasCasa(idCasa, intensidade);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    public static Acao definirCorLampadasCasa(int idCasa, String cor) {
        return new Acao("Definir Cor") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof Lampada l) l.setCor_Luz(cor);
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = definirCorLampadasCasa(idCasa, cor);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    public static Acao ligarColunaSomCasa(int idCasa) {
        return new Acao("Ligar Coluna") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof ColunaSom c) c.ligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = ligarColunaSomCasa(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    public static Acao desligarColunaSomCasa(int idCasa) {
        return new Acao("Desligar Coluna") {
            public void executar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao d : casa.getDivisoes().values()) {
                        if (idDivisaoAlvo != -1 && d.getId() != idDivisaoAlvo) continue;
                        for (Dispositivo disp : d.getDispositivos().values()) {
                            if (disp instanceof ColunaSom c) c.desligarDispositivo();
                        }
                    }
                } catch (DomusControlException e) { }
            }
            public Acao clone() {
                Acao a = desligarColunaSomCasa(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }

    public static Acao desligarLuzesEFecharCortinas(int idCasa) {
        return new Acao("Desligar e Fechar") {
            public void executar(DomusControl dc) {
                Acao luzes = desligarLuzesCasa(idCasa).clone();
                luzes.setDivisaoAlvo(idDivisaoAlvo);
                luzes.executar(dc);

                Acao cortinas = fecharCortinas(idCasa).clone();
                cortinas.setDivisaoAlvo(idDivisaoAlvo);
                cortinas.executar(dc);
            }
            public Acao clone() {
                Acao a = desligarLuzesEFecharCortinas(idCasa);
                a.setDivisaoAlvo(this.idDivisaoAlvo);
                return a;
            }
        };
    }
}