package src.test.automacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.automacao.Condicao;
import src.main.controller.DomusControl;
import src.main.model.*;

import static org.junit.jupiter.api.Assertions.*;

class CondicaoTest {

    private DomusControl dc;
    private Casa casa;
    private Divisao sala;
    private SensorAgua sensorAgua;
    private SensorLuz sensorLuz;

    @BeforeEach
    void setUp() {
        // Inicializa a casa com sensores para testar condições.
        dc = new DomusControl();
        Utilizador util = dc.criarUtilizador("Ana");
        casa = dc.criarCasa("Casa Condicao");
        dc.adicionarCasaAAdministrador(util, casa);
        dc.criarDivisao(casa, "Sala");
        sala = casa.obterDivisaoPorId(1);

        sensorAgua = new SensorAgua(dc.aumentarIdDispositivo(), "SensorH2O", "v1", 25.0);
        sensorLuz = new SensorLuz(dc.aumentarIdDispositivo(), "SensorLux", "v1", 10.0);

        dc.adicionarDispositivo(sala, sensorAgua);
        dc.adicionarDispositivo(sala, sensorLuz);
    }

    @Test
    void testCondicaoDetetarChuvaCasa() {
        // Valida que a condição detecta chuva na casa.
        sensorAgua.setEmChuva(false);
        Condicao condicao = Condicao.detetarChuvaCasa(casa.getId());
        assertFalse(condicao.verificar(dc));

        sensorAgua.setEmChuva(true);
        assertTrue(condicao.verificar(dc));
    }

    @Test
    void testCondicaoNaoEstaAChoverCasa() {
        // Valida que a condição verifica se não está a chover.
        sensorAgua.setEmChuva(true);
        Condicao condicao = Condicao.naoEstaAChoverCasa(casa.getId());
        assertFalse(condicao.verificar(dc));

        sensorAgua.setEmChuva(false);
        assertTrue(condicao.verificar(dc));
    }

    @Test
    void testCondicaoNaoEstaAChoverCasaSemSensor() {
        // Valida que sem sensor, a condição retorna false.
        Casa casaSemSensor = dc.criarCasa("Casa Sem Sensor");
        dc.criarDivisao(casaSemSensor, "Sala");

        Condicao condicao = Condicao.naoEstaAChoverCasa(casaSemSensor.getId());
        assertFalse(condicao.verificar(dc));
    }

    @Test
    void testCondicaoLuminosidadeBaixaCasa() {
        // Valida que a condição detecta luminosidade baixa.
        sensorLuz.setLuminosidadeBaixa(false);
        Condicao condicao = Condicao.luminosidadeBaixaCasa(casa.getId());
        assertFalse(condicao.verificar(dc));

        sensorLuz.setLuminosidadeBaixa(true);
        assertTrue(condicao.verificar(dc));
    }

    @Test
    void testCondicaoLuminosidadeNormalCasa() {
        // Valida que a condição detecta luminosidade normal.
        sensorLuz.setLuminosidadeBaixa(true);
        Condicao condicao = Condicao.luminosidadeNormalCasa(casa.getId());
        assertFalse(condicao.verificar(dc));

        sensorLuz.setLuminosidadeBaixa(false);
        assertTrue(condicao.verificar(dc));
    }

    @Test
    void testCondicaoLuminosidadeNormalCasaSemSensor() {
        // Valida que sem sensor, a condição retorna false.
        Casa casaSemSensor = dc.criarCasa("Casa Sem Sensor");
        dc.criarDivisao(casaSemSensor, "Sala");

        Condicao condicao = Condicao.luminosidadeNormalCasa(casaSemSensor.getId());
        assertFalse(condicao.verificar(dc));
    }

    //@Test
    //void testCondicaoDetetarChuvaComDivisaoESensor() {
    //    // Valida a condição de detectar chuva com IDs específicos.
    //    sensorAgua.setEmChuva(false);
    //    Condicao condicao = Condicao.detetarChuva(casa.getId(), sala.getId(), sensorAgua.getId());
    //    assertFalse(condicao.verificar(dc));
//
    //    sensorAgua.setEmChuva(true);
    //    assertTrue(condicao.verificar(dc));
    //}
//
    //@Test
    //void testCondicaoDetetarChuvaSensorInvalido() {
    //    // Valida que com sensor inválido, a condição retorna false.
    //    Condicao condicao = Condicao.detetarChuva(casa.getId(), sala.getId(), 99999);
    //    assertFalse(condicao.verificar(dc));
    //}

    @Test
    void testCondicaoDetetarChuvaCasaInvalida() {
        // Valida que com casa inválida, a condição retorna false.
        Condicao condicao = Condicao.detetarChuvaCasa(99999);
        assertFalse(condicao.verificar(dc));
    }

    @Test
    void testCondicaoLuminosidadeBaixaCasaInvalida() {
        // Valida que com casa inválida, a condição retorna false.
        Condicao condicao = Condicao.luminosidadeBaixaCasa(99999);
        assertFalse(condicao.verificar(dc));
    }

    @Test
    void testCondicaoClone() {
        // Valida que o clone funciona corretamente.
        Condicao condicao1 = Condicao.detetarChuvaCasa(casa.getId());
        Condicao condicao2 = condicao1.clone();

        assertNotSame(condicao1, condicao2);
        // Ambas devem ter o mesmo resultado
        assertEquals(condicao1.verificar(dc), condicao2.verificar(dc));
    }

    @Test
    void testCondicaoDetetarChuvaCasaWithMultipleSensors() {
        // Valida que a condição funciona com múltiplos sensores.
        dc.criarDivisao(casa, "Quarto");
        Divisao quarto = casa.obterDivisaoPorId(2);

        SensorAgua sensorAgua2 = new SensorAgua(dc.aumentarIdDispositivo(), "SensorH2O2", "v1", 25.0);
        dc.adicionarDispositivo(quarto, sensorAgua2);

        // Ambos os sensores não estão em chuva
        sensorAgua.setEmChuva(false);
        sensorAgua2.setEmChuva(false);
        Condicao condicao = Condicao.detetarChuvaCasa(casa.getId());
        assertFalse(condicao.verificar(dc));

        // Um sensor em chuva
        sensorAgua2.setEmChuva(true);
        assertTrue(condicao.verificar(dc));
    }

    @Test
    void testCondicaoLuminosidadeNormalCasaWithMultipleSensors() {
        // Valida que a condição funciona com múltiplos sensores de luz.
        dc.criarDivisao(casa, "Quarto");
        Divisao quarto = casa.obterDivisaoPorId(2);

        SensorLuz sensorLuz2 = new SensorLuz(dc.aumentarIdDispositivo(), "SensorLux2", "v1", 10.0);
        dc.adicionarDispositivo(quarto, sensorLuz2);

        // Ambos os sensores com luminosidade baixa
        sensorLuz.setLuminosidadeBaixa(true);
        sensorLuz2.setLuminosidadeBaixa(true);
        Condicao condicao = Condicao.luminosidadeNormalCasa(casa.getId());
        assertFalse(condicao.verificar(dc));

        // Um sensor com luminosidade normal (não baixa)
        sensorLuz2.setLuminosidadeBaixa(false);
        assertFalse(condicao.verificar(dc)); // Ainda há um com baixa luminosidade

        // Ambos com luminosidade normal
        sensorLuz.setLuminosidadeBaixa(false);
        assertTrue(condicao.verificar(dc));
    }
}
