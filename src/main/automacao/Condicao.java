package src.main.automacao;
//import static src.main.automacao.Condicao.detetarChuva;
import static src.main.automacao.Condicao.detetarChuvaCasa;

import java.io.Serializable;
import src.main.controller.*;//DomusControl
import src.main.model.*;
import src.main.Exceptions.*;


/**
 * Interface Condicao representa uma condição que pode ser verificada em um sistema de automação residencial. Ela define um método verificar que recebe um objeto DomusControl como parâmetro e retorna um booleano indicando se a condição é satisfeita ou não. Além disso, a interface inclui um método clone para criar uma cópia da condição. A interface também fornece métodos estáticos para criar condições específicas, como detetarChuva e detetarChuvaCasa, que verificam se está chovendo em um sensor específico ou em toda a casa, respectivamente. Essas condições podem ser utilizadas em cenários de automação para tomar decisões com base nas informações dos sensores e dispositivos da casa inteligente.
 * A implementação da interface Condicao pode ser feita por meio de classes anônimas ou classes concretas que implementam a lógica específica de verificação da condição. A interface é projetada para ser flexível e extensível, permitindo que os desenvolvedores criem uma variedade de condições personalizadas para atender às necessidades específicas de automação residencial. A utilização de condições na automação permite que as ações sejam executadas de forma inteligente e adaptativa, respondendo às mudanças no ambiente da casa e proporcionando uma experiência mais personalizada e eficiente para os usuários.
 */
public interface Condicao extends Serializable {
    boolean verificar(DomusControl dc);
    Condicao clone();


    //metodo que deteta chuva, para ser usado na automacao de fechar as cortinas quando estiver a chover
    //ACHO Q JA NAO PRECISO DESTE METODO, PQ A AUTOMACAO AGR E PARA A CAS TODA, NAO PARA UM DIVISAO ESPECIFICA
    //static Condicao detetarChuva(int idCasa, int idDivisao, int idSensor){
    //    return new Condicao(){
    //        public boolean verificar(DomusControl dc){
    //            try {
    //                Casa casa = dc.encontrarCasaPorId(idCasa);
    //                Divisao divisao = dc.encontrarDivisaoPorId(casa, idDivisao); 
    //                if (divisao == null) return false;
    //                Dispositivo dispositivo = dc.encontrarDispositivoPorId(divisao, idSensor); 
    //                if (dispositivo instanceof SensorAgua sensor) return sensor.isEmChuva();
    //                return false;
    //            } catch (DomusControlException e) {
    //                return false;
    //            }
    //        }
    //        public Condicao clone(){
    //            return detetarChuva(idCasa, idDivisao, idSensor);
    //        }
    //    };
    //}

    //novo metodo para detetar chuva na casa toda, para ser usado na automacao de fechar as cortinas quando estiver a chover
    /**
     * Método estático para criar uma condição de detecção de chuva em toda a casa. Este método recebe o ID da casa como parâmetro e retorna uma nova condição que verifica se algum sensor de água na casa está detectando chuva. A condição percorre todas as divisões da casa e verifica os dispositivos em cada divisão para identificar se há um sensor de água que esteja detectando chuva. Se encontrar um sensor de água que esteja detectando chuva, a condição retorna true; caso contrário, retorna false. Este método é útil para criar uma condição que pode ser utilizada em cenários de automação para tomar decisões com base na presença de chuva em toda a casa, como fechar as cortinas ou desligar dispositivos sensíveis à água.
     * @param idCasa
     * @return condição de detecção de chuva na casa
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
            public Condicao clone() { return detetarChuvaCasa(idCasa); }
        };
    }
    

    // Condição inversa: não está a chover em nenhum sensor da casa
    /**
     * Método estático para criar uma condição que verifica se não está chovendo em nenhum sensor de água da casa. Este método recebe o ID da casa como parâmetro e retorna uma nova condição que percorre todas as divisões da casa e verifica os dispositivos em cada divisão para identificar se há um sensor de água que esteja detectando chuva. Se encontrar um sensor de água que esteja detectando chuva, a condição retorna false; caso contrário, retorna true. Este método é útil para criar uma condição que pode ser utilizada em cenários de automação para tomar decisões com base na ausência de chuva em toda a casa, como abrir as cortinas ou ligar dispositivos sensíveis à água.
     * @param idCasa
     * @return condição de ausência de detecção de chuva na casa
     */
    static Condicao naoEstaAChuverCasa(int idCasa) {
        return new Condicao() {
            public boolean verificar(DomusControl dc) {
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);
                    boolean temSensor = false;
                    for (Divisao divisao : casa.getDivisoes().values()) {
                        for (Dispositivo dispositivo : divisao.getDispositivos().values()) {
                            if (dispositivo instanceof SensorAgua sensor) {
                                temSensor = true;
                                if (sensor.isEmChuva()) return false; // ainda está a chover
                            }
                        }
                    }
                    return temSensor; // só abre se existir sensor E não estiver a chover
                } catch (DomusControlException e) {
                    return false;
                }
            }
            public Condicao clone() { return naoEstaAChuverCasa(idCasa); }
        };
    }

    //para a automacao modo Noite, verificar se a luminosidade esta baixa na casa
    /**
     * Método estático para criar uma condição que verifica se a luminosidade está baixa em algum sensor de luz da casa. Este método recebe o ID da casa como parâmetro e retorna uma nova condição que percorre todas as divisões da casa e verifica os dispositivos em cada divisão para identificar se há um sensor de luz que esteja detectando luminosidade baixa. Se encontrar um sensor de luz que esteja detectando luminosidade baixa, a condição retorna true; caso contrário, retorna false. Este método é útil para criar uma condição que pode ser utilizada em cenários de automação para tomar decisões com base na luminosidade da casa, como ligar as luzes ou ajustar a intensidade das lâmpadas.
     * @param idCasa
     * @return condição de detecção de luminosidade baixa na casa
     */
    static Condicao luminosidadeBaixaCasa(int idCasa){
        return new Condicao(){
            public boolean verificar(DomusControl dc){
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);

                    for(Divisao divisao : casa.getDivisoes().values()){
                        for(Dispositivo dispositivo : divisao.getDispositivos().values()){
                            if(dispositivo instanceof SensorLuz sensor && sensor.isLuminosidadeBaixa())
                                return true;
                        }
                    } return false;
                } catch (DomusControlException e) {
                    return false;
                }
            }
            public Condicao clone(){
                return luminosidadeBaixaCasa(idCasa);
            }
        };
    }

    /**
     * Método estático para criar uma condição que verifica se a luminosidade está normal (não baixa) em todos os sensores de luz da casa. Este método recebe o ID da casa como parâmetro e retorna uma nova condição que percorre todas as divisões da casa e verifica os dispositivos em cada divisão para identificar se há um sensor de luz que esteja detectando luminosidade baixa. Se encontrar um sensor de luz que esteja detectando luminosidade baixa, a condição retorna false; caso contrário, retorna true. Este método é útil para criar uma condição que pode ser utilizada em cenários de automação para tomar decisões com base na luminosidade da casa, como desligar as luzes ou ajustar a intensidade das lâmpadas quando a luminosidade estiver normal.
     * @param idCasa
     * @return condição de detecção de luminosidade normal na casa
     */
    static Condicao luminosidadeNormalCasa(int idCasa){
        return new Condicao(){
            public boolean verificar(DomusControl dc){
                try {
                    Casa casa = dc.encontrarCasaPorId(idCasa);

                    boolean temSensor = false;
                    for(Divisao divisao : casa.getDivisoes().values()){
                        for(Dispositivo dispositivo : divisao.getDispositivos().values()){
                            if(dispositivo instanceof SensorLuz sensor){
                                temSensor = true;
                                if(sensor.isLuminosidadeBaixa()) return false;
                            }
                        }
                    }
                    return temSensor;
                } catch (DomusControlException e) {
                    return false;
                }
            }
            public Condicao clone(){
                return luminosidadeNormalCasa(idCasa);
            }
        };
    }
}
