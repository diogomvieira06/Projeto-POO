package src.test.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.model.Dispositivo;
import src.main.model.Lampada;
import src.main.model.SensorAgua;
import src.main.model.SensorLuz;
import src.main.model.Tomada;

import static org.junit.jupiter.api.Assertions.*;

class DispositivoTest {

    private TestDispositivo dispositivo;
    private TestDispositivo mesmoId;
    private TestDispositivo outroId;

    @BeforeEach
    void setUp() {
        dispositivo = new TestDispositivo(1, "MarcaA", "ModeloA", 10.0);
        mesmoId = new TestDispositivo(1, "MarcaB", "ModeloB", 20.0);
        outroId = new TestDispositivo(2, "MarcaC", "ModeloC", 30.0);
    }

    @Test
    void testConstrutorGettersSettersEClone() {
        // Valida construtores, getters/setters, estado e clone do dispositivo base.
        assertEquals(1, dispositivo.getId());
        assertEquals("MarcaA", dispositivo.getMarca());
        assertEquals("ModeloA", dispositivo.getModelo());
        assertEquals(10.0, dispositivo.getConsumo_Por_Hora_Wh(), 0.0001);
        assertEquals("DESLIGADO", dispositivo.getEstado());

        dispositivo.setMarca("NovaMarca");
        dispositivo.setModelo("NovoModelo");
        dispositivo.setConsumo_Por_Hora_Wh(15.5);
        assertEquals("NovaMarca", dispositivo.getMarca());
        assertEquals("NovoModelo", dispositivo.getModelo());
        assertEquals(15.5, dispositivo.getConsumo_Por_Hora_Wh(), 0.0001);

        dispositivo.adicionarTempoUso(2.0);
        assertEquals(0.0, dispositivo.getTempoUsoHoras(), 0.0001);

        dispositivo.ligarDispositivo();
        dispositivo.adicionarTempoUso(2.5);
        assertEquals(1, dispositivo.getNumAtivacoes());
        assertEquals("LIGADO", dispositivo.getEstado());
        assertEquals(2.5, dispositivo.getTempoUsoHoras(), 0.0001);

        Dispositivo clone = dispositivo.clone();
        assertNotSame(dispositivo, clone);
        assertEquals(dispositivo, clone);
        assertEquals(dispositivo.getNumAtivacoes(), clone.getNumAtivacoes());
        assertEquals(dispositivo.getTempoUsoHoras(), clone.getTempoUsoHoras(), 0.0001);
    }

    @Test
    void testEqualsEHashCodeBaseadosNoId() {
        // Valida que equals/hashCode do Dispositivo dependem apenas do ID.
        assertEquals(dispositivo, mesmoId);
        assertNotEquals(dispositivo, outroId);
        assertEquals(dispositivo.hashCode(), mesmoId.hashCode());
    }

    @Test
    void testLampadaEspecifica() {
        // Valida intensidade/cor e o detalhe específico da lâmpada.
        Lampada lampada = new Lampada(10, "Philips", "Hue", 9.5, "Azul");
        // Teste de intensidade removido, pois não existe mais
        assertEquals("Azul", lampada.getCor_Luz());
        lampada.ligarDispositivo();
        //assertTrue(lampada.getDetalhesEspecificos().contains("Intensidade"));
    }

    @Test
    void testTomadaSensorAguaSensorLuz() {
        // Valida comportamentos específicos de tomada e sensores.
        Tomada tomada = new Tomada(20, "TP-Link", "Plug", 5.0);
        assertEquals("Tomada", tomada.getTipo());
        tomada.ligarDispositivo();
        assertTrue(tomada.getDetalhesEspecificos().contains("Consumo"));

        SensorAgua sensorAgua = new SensorAgua(30, "Bosch", "Rain", 0.3, 0, false);
        assertFalse(sensorAgua.isEmChuva());
        sensorAgua.setEmChuva(true);
        assertTrue(sensorAgua.estaAChover());

        SensorLuz sensorLuz = new SensorLuz(40, "Aqara", "Lux", 0.5, 20.0);
        assertTrue(sensorLuz.isLuminosidadeBaixa());
        sensorLuz.setNivelLuz(100.0);
        assertFalse(sensorLuz.isLuminosidadeBaixa());
    }

    private static class TestDispositivo extends Dispositivo {
        private static final long serialVersionUID = 1L;

        private TestDispositivo(int id, String marca, String modelo, double consumo) {
            super(id, marca, modelo, consumo);
        }

        @Override
        public Dispositivo clone() {
            return new TestDispositivo(this);
        }

        private TestDispositivo(TestDispositivo d) {
            super(d);
        }

        @Override
        public String getTipo() {
            return "TestDispositivo";
        }
    }
}

