package src.main.automacao;

//import static src.main.automacao.Condicao.detetarChuva;
import static src.main.automacao.Condicao.detetarChuvaCasa;

import java.io.Serializable;
import src.main.controller.*;//DomusControl
import src.main.model.*;
import src.main.Exceptions.*;

/**
 * Interface que representa uma condição para a automação, que define um método para verificar se a condição é satisfeita com base no estado atual do sistema. A interface também inclui métodos estáticos para criar condições específicas, como detetar chuva em um sensor específico ou em toda a casa, e verificar a luminosidade baixa ou normal na casa. As condições são usadas para determinar quando as ações associadas a um cenário devem ser executadas, permitindo que o sistema de automação reaja a mudanças no ambiente ou no estado dos dispositivos de forma inteligente e personalizada.
 */
public interface Condicao extends Serializable {
    boolean verificar(DomusControl dc);

    Condicao clone();

    
    //static Condicao detetarChuva(int idCasa, int idDivisao, int idSensor) {
    //    return new Condicao() {
    //        public boolean verificar(DomusControl dc) {
    //            try {
    //                Casa casa = dc.encontrarCasaPorId(idCasa);
    //                Divisao divisao = dc.encontrarDivisaoPorId(casa, idDivisao);
    //                if (divisao == null)
    //                    return false;
    //                Dispositivo dispositivo = dc.encontrarDispositivoPorId(divisao, idSensor);
    //                if (dispositivo instanceof SensorAgua sensor)
    //                    return sensor.isEmChuva();
    //                return false;
    //            } catch (DomusControlException e) {
    //                return false;
    //            }
    //        }
//
    //        public Condicao clone() {
    //            return detetarChuva(idCasa, idDivisao, idSensor);
    //        }
    //    };
    //}

    // novo metodo para detetar chuva na casa toda, para ser usado na automacao de
    // fechar as cortinas quando estiver a chover
    /**
     * Método estático para criar uma condição que verifica se está a chover em algum sensor de água da casa, que retorna uma instância de Condicao. A condição é implementada como uma classe anônima que implementa o método verificar, onde o método percorre todas as divisões e dispositivos da casa para verificar se algum sensor de água indica que está a chover. Se encontrar um sensor de água em chuva, a condição retorna true; caso contrário, retorna false. O método clone é implementado para permitir a criação de cópias da condição, garantindo que cada cenário possa ter sua própria instância da condição sem interferir em outras instâncias.
     * @param idCasa
     * @return Condicao
     */
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
    /**
     * Método estático para criar uma condição que verifica se não está a chover em nenhum sensor de água da casa, que retorna uma instância de Condicao. A condição é implementada como uma classe anônima que implementa o método verificar, onde o método percorre todas as divisões e dispositivos da casa para verificar se algum sensor de água indica que está a chover. Se encontrar um sensor de água em chuva, a condição retorna false; caso contrário, retorna true. O método clone é implementado para permitir a criação de cópias da condição, garantindo que cada cenário possa ter sua própria instância da condição sem interferir em outras instâncias.
     * @param idCasa
     * @return Condicao
     */
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
    /**
     * Método estático para criar uma condição que verifica se a luminosidade está baixa em algum sensor de luz da casa, que retorna uma instância de Condicao. A condição é implementada como uma classe anônima que implementa o método verificar, onde o método percorre todas as divisões e dispositivos da casa para verificar se algum sensor de luz indica que a luminosidade está baixa. Se encontrar um sensor de luz com luminosidade baixa, a condição retorna true; caso contrário, retorna false. O método clone é implementado para permitir a criação de cópias da condição, garantindo que cada cenário possa ter sua própria instância da condição sem interferir em outras instâncias.
     * @param idCasa
     * @return Condicao
     */
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

    /**
     * Método estático para criar uma condição que verifica se a luminosidade está normal em todos os sensores de luz da casa, que retorna uma instância de Condicao. A condição é implementada como uma classe anônima que implementa o método verificar, onde o método percorre todas as divisões e dispositivos da casa para verificar se algum sensor de luz indica que a luminosidade está baixa. Se encontrar um sensor de luz com luminosidade baixa, a condição retorna false; caso contrário, retorna true. O método clone é implementado para permitir a criação de cópias da condição, garantindo que cada cenário possa ter sua própria instância da condição sem interferir em outras instâncias.
     * @param idCasa
     * @return Condicao
     */
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
