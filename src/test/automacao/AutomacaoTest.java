package src.test.automacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.automacao.Acao;
import src.main.automacao.Automacao;
import src.main.automacao.Condicao;
import src.main.controller.DomusControl;
import src.main.model.*;

import static org.junit.jupiter.api.Assertions.*;

class AutomacaoTest {

    private DomusControl dc;
    private Casa casa;
    private Divisao sala;
    private Lampada lampada;
    private SensorLuz sensorLuz;
    private SensorAgua sensorAgua;

    @BeforeEach
    void setUp() {
        // Inicializa um controlador com uma casa e sensores para testar automações e condições.
        dc = new DomusControl();
        Utilizador util = dc.criarUtilizador("Ana");
        casa = dc.criarCasa("Casa Automacao");
        dc.adicionarCasaAAdministrador(util, casa);
        dc.criarDivisao(casa, "Sala");
        sala = casa.obterDivisaoPorId(1);

        lampada = new Lampada(dc.aumentarIdDispositivo(), "Philips", "Hue", 9.5, "Branco");
        sensorLuz = new SensorLuz(dc.aumentarIdDispositivo(), "Aqara", "Lux", 0.5, 10.0);
        sensorAgua = new SensorAgua(dc.aumentarIdDispositivo(), "Bosch", "Rain", 0.3, 0, false);

        dc.adicionarDispositivo(sala, lampada);
        dc.adicionarDispositivo(sala, sensorLuz);
        dc.adicionarDispositivo(sala, sensorAgua);
    }

    @Test
    void testAcaoLigarEDesligarLuzesCasa() {
        // Valida se a ação liga e depois desliga as lâmpadas da casa.
        Acao.ligarLuzesCasa(casa.getId()).executar(dc);
        assertEquals("LIGADO", lampada.getEstado());

        Acao.desligarLuzesCasa(casa.getId()).executar(dc);
        assertEquals("DESLIGADO", lampada.getEstado());
    }

    @Test
    void testAcaoDefinirCorEIntensidadeLampadasCasa() {
        // Valida que as ações de cor e intensidade afetam todas as lâmpadas da casa.
        Acao.definirIntensidadeLampadasCasa(casa.getId(), 35).executar(dc);
        Acao.definirCorLampadasCasa(casa.getId(), "Azul").executar(dc);
        // Removido: intensidade não existe mais
        assertEquals("Azul", lampada.getCor_Luz());
    }

    @Test
    void testCondicaoLuminosidadeBaixaCasa() {
        // Valida a condição de luminosidade baixa.
        sensorLuz.setNivelLuz(5.0);
        assertTrue(Condicao.luminosidadeBaixaCasa(casa.getId()).verificar(dc));
        sensorLuz.setNivelLuz(100.0);
        assertFalse(Condicao.luminosidadeBaixaCasa(casa.getId()).verificar(dc));
    }

    @Test
    void testCondicaoChuvaCasa() {
        // Valida a condição de detecção de chuva na casa.
        sensorAgua.setEmChuva(true);
        assertTrue(Condicao.detetarChuvaCasa(casa.getId()).verificar(dc));
        assertFalse(Condicao.naoEstaAChoverCasa(casa.getId()).verificar(dc));
    }

    @Test
    void testAutomacaoExecutaQuandoCondicaoEverdadeira() {
        // Valida que a automação executa quando a condição é verdadeira.
        sensorLuz.setNivelLuz(5.0);
        lampada.desligarDispositivo();

        Automacao automacao = new Automacao(
                1,
                "Modo Noite",
                true,
                Condicao.luminosidadeBaixaCasa(casa.getId()),
                Acao.ligarLuzesCasa(casa.getId()),
                casa.getId()
        );

        assertTrue(automacao.executar(dc));
        assertEquals("LIGADO", lampada.getEstado());
    }

    @Test
    void testAutomacaoNaoExecutaQuandoInativa() {
        // Valida que uma automação inativa não é executada.
        sensorLuz.setNivelLuz(5.0);
        Automacao automacao = new Automacao(
                2,
                "Modo Noite",
                false,
                Condicao.luminosidadeBaixaCasa(casa.getId()),
                Acao.ligarLuzesCasa(casa.getId()),
                casa.getId()
        );

        assertFalse(automacao.executar(dc));
        assertEquals("DESLIGADO", lampada.getEstado());
    }

    @Test
    void testAutomacaoAtivacao() {
        // Valida que uma automação pode ser ativada e desativada.
        Automacao automacao = new Automacao(
                3,
                "Teste Ativação",
                true,
                Condicao.luminosidadeBaixaCasa(casa.getId()),
                Acao.ligarLuzesCasa(casa.getId()),
                casa.getId()
        );

        assertTrue(automacao.isAtiva());
        automacao.desativar();
        assertFalse(automacao.isAtiva());
        automacao.ativar();
        assertTrue(automacao.isAtiva());
    }

    @Test
    void testAutomacaoDeveExecutarTrue() {
        // Valida que a automação reconhece quando deve executar.
        sensorLuz.setNivelLuz(5.0);
        
        Automacao automacao = new Automacao(
                4,
                "Luz Noite",
                true,
                Condicao.luminosidadeBaixaCasa(casa.getId()),
                Acao.ligarLuzesCasa(casa.getId()),
                casa.getId()
        );

        assertTrue(automacao.deveExecutar(dc));
    }

    @Test
    void testAutomacaoDeveExecutarFalse() {
        // Valida que a automação reconhece quando não deve executar.
        sensorLuz.setNivelLuz(100.0);
        
        Automacao automacao = new Automacao(
                5,
                "Luz Noite",
                true,
                Condicao.luminosidadeBaixaCasa(casa.getId()),
                Acao.ligarLuzesCasa(casa.getId()),
                casa.getId()
        );

        assertFalse(automacao.deveExecutar(dc));
    }

    @Test
    void testAutomacaoGettersSetters() {
        // Valida que os getters e setters funcionam corretamente.
        Automacao automacao = new Automacao();
        
        automacao.setId(10);
        assertEquals(10, automacao.getId());
        
        automacao.setNome("Automação Teste");
        assertEquals("Automação Teste", automacao.getNome());
        
        automacao.setAtiva(true);
        assertTrue(automacao.isAtiva());
        
        automacao.setIdCasa(5);
        assertEquals(5, automacao.getIdCasa());
        
        Condicao condicao = Condicao.luminosidadeBaixaCasa(casa.getId());
        automacao.setCondicao(condicao);
        assertEquals(condicao, automacao.getCondicao());
        
        Acao acao = Acao.ligarLuzesCasa(casa.getId());
        automacao.setAcao(acao);
        assertEquals(acao, automacao.getAcao());
    }

    @Test
    void testAutomacaoCopy() {
        // Valida que o construtor de cópia funciona corretamente.
        Automacao automacao1 = new Automacao(
                6,
                "Automação Cópia",
                true,
                Condicao.luminosidadeBaixaCasa(casa.getId()),
                Acao.ligarLuzesCasa(casa.getId()),
                casa.getId()
        );
        
        Automacao automacao2 = new Automacao(automacao1);

        assertEquals(automacao1.getId(), automacao2.getId());
        assertEquals(automacao1.getNome(), automacao2.getNome());
        assertEquals(automacao1.isAtiva(), automacao2.isAtiva());
        assertEquals(automacao1.getIdCasa(), automacao2.getIdCasa());
    }

    @Test
    void testAutomacaoClone() {
        // Valida que o método clone funciona corretamente.
        Automacao automacao1 = new Automacao(
                7,
                "Automação Clone",
                true,
                Condicao.luminosidadeBaixaCasa(casa.getId()),
                Acao.ligarLuzesCasa(casa.getId()),
                casa.getId()
        );
        
        Automacao automacao2 = automacao1.clone();

        assertEquals(automacao1.getId(), automacao2.getId());
        assertEquals(automacao1.getNome(), automacao2.getNome());
        assertEquals(automacao1.isAtiva(), automacao2.isAtiva());
    }

    @Test
    void testAutomacaoUltimaExecucao() {
        // Valida que a última execução é registrada.
        sensorLuz.setNivelLuz(5.0);
        lampada.desligarDispositivo();
        
        Automacao automacao = new Automacao(
                8,
                "Tempo de Execução",
                true,
                Condicao.luminosidadeBaixaCasa(casa.getId()),
                Acao.ligarLuzesCasa(casa.getId()),
                casa.getId()
        );

        assertEquals(0L, automacao.getUltimaExecucao());
        automacao.executar(dc);
        assertTrue(automacao.getUltimaExecucao() > 0);
    }

    @Test
    void testAutomacaoSemCondicao() {
        // Valida que a automação não executa sem condição.
        lampada.desligarDispositivo();
        
        Automacao automacao = new Automacao(
                9,
                "Sem Condição",
                true,
                null,  // Sem condição
                Acao.ligarLuzesCasa(casa.getId()),
                casa.getId()
        );

        assertFalse(automacao.deveExecutar(dc));
    }

    @Test
    void testAutomacaoSemAcao() {
        // Valida que a automação não executa sem ação.
        sensorLuz.setNivelLuz(5.0);
        
        Automacao automacao = new Automacao(
                10,
                "Sem Ação",
                true,
                Condicao.luminosidadeBaixaCasa(casa.getId()),
                null,  // Sem ação
                casa.getId()
        );

        assertFalse(automacao.executar(dc));
    }

    @Test
    void testAutomacaoEquals() {
        // Valida que automações com o mesmo ID são consideradas iguais.
        Automacao automacao1 = new Automacao(
                11,
                "Automação 1",
                true,
                Condicao.luminosidadeBaixaCasa(casa.getId()),
                Acao.ligarLuzesCasa(casa.getId()),
                casa.getId()
        );
        
        Automacao automacao2 = new Automacao(
                11,
                "Outro Nome",
                false,
                Condicao.luminosidadeNormalCasa(casa.getId()),
                Acao.desligarLuzesCasa(casa.getId()),
                casa.getId()
        );

        assertEquals(automacao1, automacao2);
    }

    @Test
    void testAutomacaoNotEquals() {
        // Valida que automações com IDs diferentes não são consideradas iguais.
        Automacao automacao1 = new Automacao(
                12,
                "Automação 1",
                true,
                Condicao.luminosidadeBaixaCasa(casa.getId()),
                Acao.ligarLuzesCasa(casa.getId()),
                casa.getId()
        );
        
        Automacao automacao2 = new Automacao(
                13,
                "Automação 1",
                true,
                Condicao.luminosidadeBaixaCasa(casa.getId()),
                Acao.ligarLuzesCasa(casa.getId()),
                casa.getId()
        );

        assertNotEquals(automacao1, automacao2);
    }
}

