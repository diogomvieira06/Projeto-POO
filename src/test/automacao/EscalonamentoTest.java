package src.test.automacao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.automacao.Escalonamento;
import src.main.automacao.Acao;
import src.main.controller.DomusControl;
import src.main.model.*;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class EscalonamentoTest {

    private DomusControl dc;
    private Casa casa;
    private Divisao sala;
    private Lampada lampada;
    private Cortina cortina;
    private ColunaSom colunaSom;

    @BeforeEach
    void setUp() {
        // Inicializa a casa com dispositivos para testar escalonamentos.
        dc = new DomusControl();
        Utilizador util = dc.criarUtilizador("Ana");
        casa = dc.criarCasa("Casa Escalonamento");
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

    @Test
    void testEscalonamentoPontualNaoExecutaSemAcao() {
        // Valida que um escalonamento sem ação não executa nada.
        lampada.ligarDispositivo();
        Escalonamento escalonamento = new Escalonamento(
                3,
                "Sem Acao",
                true,
                LocalTime.now().minusMinutes(1),
                null,
                null,
                null,
                casa.getId()
        );

        escalonamento.verificarEExecutar(dc, LocalTime.now(), LocalDate.now());
        assertEquals("LIGADO", lampada.getEstado());
    }

    @Test
    void testEscalonamentoPontualNaoExecutaSeDesativado() {
        // Valida que um escalonamento desativado não executa.
        lampada.desligarDispositivo();
        Escalonamento escalonamento = new Escalonamento(
                4,
                "Liga Luz Desativada",
                false,  // Desativado
                LocalTime.now().minusMinutes(1),
                null,
                Acao.ligarLuzesCasa(casa.getId()),
                null,
                casa.getId()
        );

        escalonamento.verificarEExecutar(dc, LocalTime.now(), LocalDate.now());
        assertEquals("DESLIGADO", lampada.getEstado());
    }

    @Test
    void testEscalonamentoPontualNaoExecutaSeFuturo() {
        // Valida que um escalonamento futuro não executa.
        lampada.desligarDispositivo();
        Escalonamento escalonamento = new Escalonamento(
                5,
                "Liga Luz Futura",
                true,
                LocalTime.now().plusMinutes(10),  // No futuro
                null,
                Acao.ligarLuzesCasa(casa.getId()),
                null,
                casa.getId()
        );

        escalonamento.verificarEExecutar(dc, LocalTime.now(), LocalDate.now());
        assertEquals("DESLIGADO", lampada.getEstado());
    }

    @Test
    void testEscalonamentoIsIntervalo() {
        // Valida que isIntervalo() retorna corretamente.
        Escalonamento pontual = new Escalonamento(
                1, "Pontual", true, LocalTime.now(), null,
                Acao.ligarLuzesCasa(casa.getId()), null, casa.getId()
        );
        assertFalse(pontual.isIntervalo());

        Escalonamento intervalo = new Escalonamento(
                2, "Intervalo", true, LocalTime.now(), LocalTime.now().plusHours(1),
                Acao.ligarLuzesCasa(casa.getId()), Acao.desligarLuzesCasa(casa.getId()), casa.getId()
        );
        assertTrue(intervalo.isIntervalo());
    }

    @Test
    void testEscalonamentoAtivacao() {
        // Valida que um escalonamento pode ser ativado e desativado.
        Escalonamento escalonamento = new Escalonamento(
                1, "Teste", true, LocalTime.now(), null,
                Acao.ligarLuzesCasa(casa.getId()), null, casa.getId()
        );
        assertTrue(escalonamento.isAtivo());

        escalonamento.desativar();
        assertFalse(escalonamento.isAtivo());

        escalonamento.ativar();
        assertTrue(escalonamento.isAtivo());
    }

    @Test
    void testEscalonamentoCopy() {
        // Valida que o construtor de cópia funciona corretamente.
        Acao acaoInicio = Acao.ligarLuzesCasa(casa.getId());
        Acao acaoFim = Acao.desligarLuzesCasa(casa.getId());

        Escalonamento escalonamento1 = new Escalonamento(
                1, "Teste", true, LocalTime.now(), LocalTime.now().plusHours(1),
                acaoInicio, acaoFim, casa.getId()
        );

        Escalonamento escalonamento2 = new Escalonamento(escalonamento1);

        assertEquals(escalonamento1.getId(), escalonamento2.getId());
        assertEquals(escalonamento1.getNome(), escalonamento2.getNome());
        assertEquals(escalonamento1.isAtivo(), escalonamento2.isAtivo());
        assertEquals(escalonamento1.getHoraInicio(), escalonamento2.getHoraInicio());
        assertEquals(escalonamento1.getHoraFim(), escalonamento2.getHoraFim());
    }

    @Test
    void testEscalonamentoIntervaloNaoExecutaAcaoFimAntesDeInicio() {
        // Valida que a ação de fim não executa antes da ação de início.
        lampada.desligarDispositivo();
        cortina.setNivelAbertura(50);

        Escalonamento escalonamento = new Escalonamento(
                6,
                "Intervalo",
                true,
                LocalTime.now().plusMinutes(10),  // Inicio no futuro
                LocalTime.now().plusMinutes(20),  // Fim no futuro
                Acao.ligarLuzesCasa(casa.getId()),
                Acao.fecharCortinas(casa.getId()),
                casa.getId()
        );

        escalonamento.verificarEExecutar(dc, LocalTime.now(), LocalDate.now());
        assertEquals("DESLIGADO", lampada.getEstado());
        assertEquals(50, cortina.getNivelAbertura());
    }

    @Test
    void testEscalonamentoMultiplasExecutaosPorDia() {
        // Valida que um escalonamento só executa uma vez por dia.
        lampada.desligarDispositivo();
        Escalonamento escalonamento = new Escalonamento(
                7,
                "Uma Vez Por Dia",
                true,
                LocalTime.now().minusMinutes(1),
                null,
                Acao.ligarLuzesCasa(casa.getId()),
                null,
                casa.getId()
        );

        // Primeira execução
        escalonamento.verificarEExecutar(dc, LocalTime.now(), LocalDate.now());
        assertEquals("LIGADO", lampada.getEstado());

        // Desliga novamente
        lampada.desligarDispositivo();

        // Tenta executar novamente no mesmo dia
        escalonamento.verificarEExecutar(dc, LocalTime.now(), LocalDate.now());
        assertEquals("DESLIGADO", lampada.getEstado()); // Não deve ligar novamente
    }

    @Test
    void testEscalonamentoGettersSetters() {
        // Valida que os getters e setters funcionam corretamente.
        Escalonamento escalonamento = new Escalonamento();

        escalonamento.setId(1);
        assertEquals(1, escalonamento.getId());

        escalonamento.setNome("Teste");
        assertEquals("Teste", escalonamento.getNome());

        escalonamento.setAtivo(true);
        assertTrue(escalonamento.isAtivo());

        LocalTime hora = LocalTime.of(10, 30);
        escalonamento.setHoraInicio(hora);
        assertEquals(hora, escalonamento.getHoraInicio());

        escalonamento.setIdCasa(5);
        assertEquals(5, escalonamento.getIdCasa());
    }
}

