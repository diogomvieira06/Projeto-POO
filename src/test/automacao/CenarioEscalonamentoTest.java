package src.test.automacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.automacao.Cenario;
import src.main.automacao.Escalonamento;
import src.main.automacao.Acao;
import src.main.controller.DomusControl;
import src.main.model.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class CenarioEscalonamentoTest {

    private DomusControl dc;
    private Casa casa;
    private Divisao sala;
    private Lampada lampada;
    private Cortina cortina;
    private ColunaSom colunaSom;

    @BeforeEach
    void setUp() {
        // Inicializa a casa com dispositivos para testar cenários e escalonamentos.
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
        assertEquals(100, cortina.getNivelAbertura());
    }

    @Test
    void testCriarCenariosObrigatoriosIncluiTodosOsEsperados() {
        // Valida que os cenários obrigatórios são criados pela casa.
        dc.criarCenariosObrigatorios(casa.getId());
        assertEquals(7, dc.getCenariosDaCasa(casa.getId()).size());
    }

    @Test
    void testEscalonamentoPontualExecutaAcao() {
        // Valida que um escalonamento pontual executa a ação no tempo correto.
        lampada.desligarDispositivo();
        Escalonamento escalonamento = new Escalonamento(
                1,
                "Liga Luz",
                true,
                LocalTime.now().minusMinutes(1),
                null,
                Acao.ligarLuzesCasa(casa.getId()),
                null,
                casa.getId()
        );

        escalonamento.verificarEExecutar(dc, LocalTime.now(), LocalDate.now());
        assertEquals("LIGADO", lampada.getEstado());
    }

    @Test
    void testEscalonamentoIntervaloExecutaInicioEFim() {
        // Valida um escalonamento em intervalo com ação de início e fim.
        lampada.desligarDispositivo();
        Escalonamento escalonamento = new Escalonamento(
                2,
                "Intervalo Luz",
                true,
                LocalTime.now().minusMinutes(10),
                LocalTime.now().minusMinutes(5),
                Acao.ligarLuzesCasa(casa.getId()),
                Acao.desligarLuzesCasa(casa.getId()),
                casa.getId()
        );

        escalonamento.verificarEExecutar(dc, LocalTime.now(), LocalDate.now());
        escalonamento.verificarEExecutar(dc, LocalTime.now(), LocalDate.now());
        assertEquals("DESLIGADO", lampada.getEstado());
    }
}

