package src.test.automacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.automacao.Cenario;
import src.main.automacao.Acao;
import src.main.controller.DomusControl;
import src.main.model.*;

import static org.junit.jupiter.api.Assertions.*;

class CenarioTest {

    private DomusControl dc;
    private Casa casa;
    private Divisao sala;
    private Lampada lampada;
    private Cortina cortina;
    private ColunaSom colunaSom;

    @BeforeEach
    void setUp() {
        // Inicializa a casa com dispositivos para testar cenários.
        dc = new DomusControl();
        Utilizador util = dc.criarUtilizador("Ana");
        casa = dc.criarCasa("Casa Cenario");
        dc.adicionarCasaAAdministrador(util, casa);
        dc.criarDivisao(casa, "Sala");
        sala = casa.obterDivisaoPorId(1);

        lampada = new Lampada(dc.aumentarIdDispositivo(), "Philips", "Hue", 9.5, "Branco");
        cortina = new Cortina(dc.aumentarIdDispositivo(), "Somfy", "Smart", 18.0, 50);
        colunaSom = new ColunaSom(dc.aumentarIdDispositivo(), "Sonos", "Beam", 15.0, 40);

        dc.adicionarDispositivo(sala, lampada);
        dc.adicionarDispositivo(sala, cortina);
        dc.adicionarDispositivo(sala, colunaSom);
    }

    @Test
    void testCenarioCinemaExecutaAcoesEsperadas() {
        // Valida o cenário Cinema, incluindo luzes, cortinas e som.
        Cenario cenario = Cenario.cinema(1, casa.getId());
        cenario.executar(dc);

        assertEquals("DESLIGADO", lampada.getEstado());
        assertEquals("Azul", lampada.getCor_Luz());
        // Removido: intensidade não existe mais
        assertEquals(0, cortina.getNivelAbertura());
        assertEquals("LIGADO", colunaSom.getEstado());
    }

    @Test
    void testCenarioJantarRomanticoExecutaAcoesEsperadas() {
        // Valida o cenário Jantar Romântico.
        Cenario cenario = Cenario.jantarRomantico(2, casa.getId());
        cenario.executar(dc);

        assertEquals("LIGADO", lampada.getEstado());
        assertEquals("Amarelo Quente", lampada.getCor_Luz());
        // Removido: intensidade não existe mais
        assertEquals(100, cortina.getNivelAbertura());
    }

    @Test
    void testCenarioJantarComAmigosExecutaAcoesEsperadas() {
        // Valida o cenário Jantar com Amigos.
        Cenario cenario = Cenario.jantarComAmigos(3, casa.getId());
        cenario.executar(dc);

        assertEquals("LIGADO", lampada.getEstado());
        assertEquals(100, cortina.getNivelAbertura());
        assertEquals("LIGADO", colunaSom.getEstado());
    }

    @Test
    void testCenarioEstudarExecutaAcoesEsperadas() {
        // Valida o cenário Estudar.
        Cenario cenario = Cenario.estudar(4, casa.getId());
        cenario.executar(dc);

        assertEquals("LIGADO", lampada.getEstado());
        assertEquals("Branco Frio", lampada.getCor_Luz());
        // Removido: intensidade não existe mais
    }

    @Test
    void testCenarioDeitarExecutaAcoesEsperadas() {
        // Valida o cenário Deitar.
        Cenario cenario = Cenario.deitar(5, casa.getId());
        cenario.executar(dc);

        assertEquals("DESLIGADO", lampada.getEstado());
        assertEquals(0, cortina.getNivelAbertura());
        assertEquals("DESLIGADO", colunaSom.getEstado());
    }

    @Test
    void testCenarioAcordarExecutaAcoesEsperadas() {
        // Valida o cenário Acordar.
        Cenario cenario = Cenario.acordar(6, casa.getId());
        cenario.executar(dc);

        assertEquals("LIGADO", lampada.getEstado());
        assertEquals(100, cortina.getNivelAbertura());
        assertEquals("LIGADO", colunaSom.getEstado());
    }

    @Test
    void testCenarioSairDeCasaExecutaAcoesEsperadas() {
        // Valida o cenário Sair de Casa.
        Cenario cenario = Cenario.sairDeCasa(7, casa.getId());
        cenario.executar(dc);

        assertEquals("DESLIGADO", lampada.getEstado());
        assertEquals(0, cortina.getNivelAbertura());
        assertEquals("DESLIGADO", colunaSom.getEstado());
    }

    @Test
    void testCriarCenariosObrigatoriosIncluiTodosOsEsperados() {
        // Valida que os cenários obrigatórios são criados pela casa.
        dc.criarCenariosObrigatorios(casa.getId());
        assertEquals(7, dc.getCenariosDaCasa(casa.getId()).size());
    }

    @Test
    void testCenarioAdicionarAcao() {
        // Valida que ações podem ser adicionadas a um cenário.
        Cenario cenario = new Cenario(1, "Teste", casa.getId(), null);
        assertEquals(0, cenario.getAcoes().size());

        cenario.adicionarAcao(Acao.ligarLuzesCasa(casa.getId()));
        assertEquals(1, cenario.getAcoes().size());
        
        cenario.adicionarAcao(Acao.abrirCortinas(casa.getId()));
        assertEquals(2, cenario.getAcoes().size());
    }

    @Test
    void testCenarioCopy() {
        // Valida que o construtor de cópia funciona corretamente.
        Cenario cenario1 = Cenario.cinema(1, casa.getId());
        Cenario cenario2 = new Cenario(cenario1);

        assertEquals(cenario1.getId(), cenario2.getId());
        assertEquals(cenario1.getNome(), cenario2.getNome());
        assertEquals(cenario1.getIdCasa(), cenario2.getIdCasa());
        assertEquals(cenario1.getAcoes().size(), cenario2.getAcoes().size());
    }

    @Test
    void testCenarioEquals() {
        // Valida que cenários com o mesmo ID são considerados iguais.
        Cenario cenario1 = new Cenario(1, "Cinema", casa.getId(), null);
        Cenario cenario2 = new Cenario(1, "Outro Nome", casa.getId(), null);

        assertEquals(cenario1, cenario2);
    }

    @Test
    void testCenarioNotEquals() {
        // Valida que cenários com IDs diferentes não são considerados iguais.
        Cenario cenario1 = new Cenario(1, "Cinema", casa.getId(), null);
        Cenario cenario2 = new Cenario(2, "Cinema", casa.getId(), null);

        assertNotEquals(cenario1, cenario2);
    }
}

