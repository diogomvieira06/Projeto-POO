package src.test.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.model.Divisao;
import src.main.model.Lampada;

import static org.junit.jupiter.api.Assertions.*;

class DivisaoTest {

    private Divisao divisao;
    private Divisao divisaoMesmoId;
    private Divisao divisaoOutroId;
    private Lampada lampada;

    @BeforeEach
    void setUp() {
        divisao = new Divisao("Sala", 1);
        divisaoMesmoId = new Divisao("Quarto", 1);
        divisaoOutroId = new Divisao("Cozinha", 2);
        lampada = new Lampada(10, "Philips", "Hue", 9.5, "Branco");
    }

    @Test
    void testConstrutorGettersESetters() {
        // Valida construtor vazio, getters e setters da Divisão.
        Divisao d = new Divisao();
        d.setNome("Escritório");
        d.setId(7);

        assertEquals("Escritório", d.getNome());
        assertEquals(7, d.getId());
        assertTrue(d.getDispositivos().isEmpty());
    }

    @Test
    void testAdicionarERemoverDispositivo() {
        // Valida a adição e remoção de dispositivos na Divisão.
        divisao.adicionarDispositivo(lampada);
        assertSame(lampada, divisao.obterDispositivoPorId(10));

        divisao.removerDispositivo(lampada);
        assertThrows(Divisao.DispositivoNaoEncontradoException.class, () -> divisao.obterDispositivoPorId(10));
    }

    @Test
    void testCopyConstructorFazCloneDosDispositivos() {
        // Valida que o construtor de cópia clona os dispositivos.
        divisao.adicionarDispositivo(lampada);
        Divisao copia = new Divisao(divisao);

        assertEquals(divisao, copia);
        assertNotSame(divisao, copia);
        assertEquals(1, copia.getDispositivos().size());
        assertNotSame(divisao.obterDispositivoPorId(10), copia.obterDispositivoPorId(10));
        assertEquals(divisao.obterDispositivoPorId(10), copia.obterDispositivoPorId(10));
    }

    @Test
    void testEqualsEHashCodeBaseadosNoId() {
        // Valida que equals/hashCode dependem apenas do ID.
        assertEquals(divisao, divisaoMesmoId);
        assertNotEquals(divisao, divisaoOutroId);
        assertEquals(divisao.hashCode(), divisaoMesmoId.hashCode());
    }
}

