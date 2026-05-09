package src.main.automacao;

import static src.main.automacao.Condicao.detetarChuva;
import static src.main.automacao.Condicao.detetarChuvaCasa;

import java.io.Serializable;
import src.main.controller.*;//DomusControl
import src.main.model.*;
import src.main.Exceptions.*;

public interface Condicao extends Serializable {
    boolean verificar(DomusControl dc);

    Condicao clone();

    // metodo que deteta chuva, para ser usado na automacao de fechar as cortinas
    // quando estiver a chover
    // ACHO Q JA NAO PRECISO DESTE METODO, PQ A AUTOMACAO AGR E PARA A CAS TODA, NAO
    // PARA UM DIVISAO ESPECIFICA
    static Condicao detetarChuva(int idCasa, int idDivisao, int idSensor) {
        return new Condicao() {
            public boolean verificar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    Divisao divisao = dc.encontrarDivisaoPorId(casa, idDivisao);
                    if (divisao == null)
                        return false;
                    Dispositivo dispositivo = dc.encontrarDispositivoPorId(divisao, idSensor);
                    if (dispositivo instanceof SensorAgua sensor)
                        return sensor.isEmChuva();
                    return false;
                } catch (DomusControlException e) {
                    return false;
                }
            }

            public Condicao clone() {
                return detetarChuva(idCasa, idDivisao, idSensor);
            }
        };
    }

    // novo metodo para detetar chuva na casa toda, para ser usado na automacao de
    // fechar as cortinas quando estiver a chover
    static Condicao detetarChuvaCasa(int idCasa) {
        return new Condicao() {
            public boolean verificar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    for (Divisao divisao : casa.getDivisoes().values()) {
                        for (Dispositivo dispositivo : divisao.getDispositivos().values()) {
                            if (dispositivo instanceof SensorAgua sensor && sensor.isEmChuva())
                                return true;
                        }
                    }
                    return false;
                } catch (DomusControlException e) {
                    return false;
                }
            }

            public Condicao clone() {
                return detetarChuvaCasa(idCasa);
            }
        };
    }

    // Condição inversa: não está a chover em nenhum sensor da casa
    static Condicao naoEstaAChoverCasa(int idCasa) {
        return new Condicao() {
            public boolean verificar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    boolean temSensor = false;
                    for (Divisao divisao : casa.getDivisoes().values()) {
                        for (Dispositivo dispositivo : divisao.getDispositivos().values()) {
                            if (dispositivo instanceof SensorAgua sensor) {
                                temSensor = true;
                                if (sensor.isEmChuva())
                                    return false; // ainda está a chover
                            }
                        }
                    }
                    return temSensor; // só abre se existir sensor E não estiver a chover
                } catch (DomusControlException e) {
                    return false;
                }
            }

            public Condicao clone() {
                return naoEstaAChoverCasa(idCasa);
            }
        };
    }

    // para a automacao modo Noite, verificar se a luminosidade esta baixa na casa
    static Condicao luminosidadeBaixaCasa(int idCasa) {
        return new Condicao() {
            public boolean verificar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);

                    for (Divisao divisao : casa.getDivisoes().values()) {
                        for (Dispositivo dispositivo : divisao.getDispositivos().values()) {
                            if (dispositivo instanceof SensorLuz sensor && sensor.isLuminosidadeBaixa())
                                return true;
                        }
                    }
                    return false;
                } catch (DomusControlException e) {
                    return false;
                }
            }

            public Condicao clone() {
                return luminosidadeBaixaCasa(idCasa);
            }
        };
    }

    static Condicao luminosidadeNormalCasa(int idCasa) {
        return new Condicao() {
            public boolean verificar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);

                    boolean temSensor = false;
                    for (Divisao divisao : casa.getDivisoes().values()) {
                        for (Dispositivo dispositivo : divisao.getDispositivos().values()) {
                            if (dispositivo instanceof SensorLuz sensor) {
                                temSensor = true;
                                if (sensor.isLuminosidadeBaixa())
                                    return false;
                            }
                        }
                    }
                    return temSensor;
                } catch (DomusControlException e) {
                    return false;
                }
            }

            public Condicao clone() {
                return luminosidadeNormalCasa(idCasa);
            }
        };
    }
}
