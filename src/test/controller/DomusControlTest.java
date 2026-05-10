package src.test.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.Exceptions.DomusControlException;
import src.main.Exceptions.*;
import src.main.controller.DomusControl;
import src.main.model.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DomusControlTest {

    private DomusControl dc;
    private Utilizador ana;
    private Utilizador bruno;
    private Casa casaJardim;
    private Casa casaCentro;
    private Divisao salaJardim;
    private Divisao salaCentro;
    private Lampada lampJardim;
    private Lampada lampCentro;
    private Tomada tomada;

    @BeforeEach
    void setUp() {
        // Inicializa um controlador novo e dados de teste antes de cada teste.
        dc = new DomusControl();
        ana = dc.criarUtilizador("Ana");
        bruno = dc.criarUtilizador("Bruno");

        casaJardim = dc.criarCasa("Casa Jardim");
        casaCentro = dc.criarCasa("Casa Centro");

        dc.adicionarCasaAAdministrador(ana, casaJardim);
        dc.adicionarCasaAUtilizador(ana, casaCentro);
        dc.adicionarCasaAAdministrador(bruno, casaCentro);

        dc.criarDivisao(casaJardim, "Sala");
        dc.criarDivisao(casaCentro, "Sala");
        salaJardim = casaJardim.obterDivisaoPorId(1);
        salaCentro = casaCentro.obterDivisaoPorId(2);

        lampJardim = new Lampada(dc.aumentarIdDispositivo(), "Philips", "Hue", 9.5, "Branco");
        lampCentro = new Lampada(dc.aumentarIdDispositivo(), "IKEA", "Tradfri", 8.0, "Branco");
        tomada = new Tomada(dc.aumentarIdDispositivo(), "TP-Link", "Plug", 5.0);

        dc.adicionarDispositivo(salaJardim, lampJardim);
        dc.adicionarDispositivo(salaJardim, tomada);
        dc.adicionarDispositivo(salaCentro, lampCentro);

        lampJardim.ligarDispositivo();
        lampCentro.ligarDispositivo();
        tomada.ligarDispositivo();
        dc.passarTempoGlobal(2.0);
    }

    @Test
    void testCriarEEncontrarUtilizador() {
        // Valida o CRUD básico de utilizadores.
        Utilizador novo = dc.criarUtilizador("Carla");
        assertEquals(novo, dc.encontrarUtilizadorPorId(novo.getId()));
        assertEquals("Carla", novo.getNome());
    }

    @Test
    void testEncontrarUtilizadorInexistenteLancaExcecao() {
        // Valida que o controlador lança exceção quando o utilizador não existe.
        assertThrows(UtilizadorNaoEncontradoException.class, () -> dc.encontrarUtilizadorPorId(999));
    }

    @Test
    void testCriarCasaCriaAutomaçõesBase() {
        // Valida que criar uma casa gera as automações base automaticamente.
        DomusControl local = new DomusControl();
        local.criarCasa("Casa Teste");
        assertEquals(4, local.getAutomacoes().size());
    }

    @Test
    void testEliminarCasaTotalmenteRemoveAssociacoes() {
        // Valida que eliminar uma casa limpa as associações dos utilizadores.
        dc.eliminarCasaTotalmente(casaJardim);
        assertFalse(ana.podeUsarCasa(casaJardim));
        assertFalse(ana.podeAdministrarCasa(casaJardim));
        assertThrows(CasaNaoEncontradaException.class, () -> dc.encontrarCasaPorId(casaJardim.getId()));
    }

    @Test
    void testRemoverPermissoesAdminUltimoAdminLancaExcecao() {
        // Valida que não é possível remover o último administrador.
        assertThrows(PermissaoNegadaException.class,
                () -> dc.removerPermissoesAdmin(ana, casaJardim));
    }

    @Test
    void testCasaQueMaisConsome() {
        // Valida o cálculo da casa com maior consumo.
        assertEquals(casaJardim, dc.casaQueMaisConsome());
    }

    @Test
    void testTop3DispositivosPorAtivacoes() {
        // Valida a ordenação dos 3 dispositivos com mais ativações.
        for (int i = 0; i < 3; i++) {
            tomada.desligarDispositivo();
            tomada.ligarDispositivo();
        }
        List<Dispositivo> top = dc.getTop3Dispositivos(false);
        assertFalse(top.isEmpty());
        assertEquals(tomada.getId(), top.get(0).getId());
    }

    @Test
    void testTop3DispositivosPorTempoUso() {
        // Valida a ordenação dos 3 dispositivos por tempo de uso.
        lampCentro.adicionarTempoUso(5.0);
        List<Dispositivo> top = dc.getTop3Dispositivos(true);
        assertFalse(top.isEmpty());
        assertEquals(lampCentro.getId(), top.get(0).getId());
    }

    @Test
    void testGuardarECarregarEstado() throws Exception {
        // Valida a serialização e desserialização do estado do controlador.
        Path temp = Files.createTempFile("domuscontrol", ".dat");
        dc.guardarEstado(temp.toString());

        DomusControl carregado = DomusControl.carregarEstado(temp.toString());
        assertNotNull(carregado);
        assertEquals(dc.getUtilizadores().size(), carregado.getUtilizadores().size());
        assertEquals(dc.getCasas().size(), carregado.getCasas().size());

        Files.deleteIfExists(temp);
    }

    @Test
    void testCarregarEstadoFicheiroInexistenteLancaExcecao() {
        // Valida que carregar um ficheiro inexistente lança exceção do domínio.
        assertThrows(DomusControlException.class, () -> DomusControl.carregarEstado("ficheiro_inexistente.dat"));
    }
}

