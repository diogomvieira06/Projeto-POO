package src.test.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.model.Casa;
import src.main.model.Divisao;

import static org.junit.jupiter.api.Assertions.*;

class CasaTest {

    private Casa casa;
    private Casa casaMesmoId;
    private Casa casaOutroId;
    private Divisao sala;

    @BeforeEach
    void setUp() {
        casa = new Casa("Casa A", 1);
        casaMesmoId = new Casa("Casa B", 1);
        casaOutroId = new Casa("Casa C", 2);
        sala = new Divisao("Sala", 10);
    }

    @Test
    void testConstrutorGettersESetters() {
        // Valida construtor vazio, getters e setters da Casa.
        Casa c = new Casa();
        c.setAlcunha("Nova Casa");
        c.setId(99);

        assertEquals("Nova Casa", c.getAlcunha());
        assertEquals(99, c.getId());
        assertTrue(c.getDivisoes().isEmpty());
    }

    @Test
    void testAdicionarERemoverDivisao() {
        // Valida a adição e remoção de divisões na Casa.
        casa.adicionarDivisao(sala);
        assertSame(sala, casa.obterDivisaoPorId(10));

        casa.removerDivisao(sala);
        assertThrows(Divisao.DivisaoNaoEncontradaException.class, () -> casa.obterDivisaoPorId(10));
    }

    @Test
    void testCopyConstructorFazCopiaProfunda() {
        // Valida que o construtor de cópia preserva os dados e cria uma cópia
        // independente.
        casa.adicionarDivisao(sala);
        Casa copia = new Casa(casa);

        assertEquals(casa, copia);
        assertNotSame(casa, copia);
        assertEquals(1, copia.getDivisoes().size());
        assertNotSame(casa.getDivisoes(), copia.getDivisoes());
    }

    @Test
    void testEqualsEHashCodeBaseadosNoId() {
        // Valida que equals/hashCode dependem apenas do ID.
        assertEquals(casa, casaMesmoId);
        assertNotEquals(casa, casaOutroId);
        assertEquals(casa.hashCode(), casaMesmoId.hashCode());
    }
}
