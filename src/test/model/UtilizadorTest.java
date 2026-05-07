package src.test.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.model.Casa;
import src.main.model.Utilizador;

import static org.junit.jupiter.api.Assertions.*;

class UtilizadorTest {

    private Utilizador utilizador;
    private Utilizador utilizadorMesmoId;
    private Utilizador utilizadorOutroId;
    private Casa casa1;
    private Casa casa2;

    @BeforeEach
    void setUp() {
        utilizador = new Utilizador(1, "Ana");
        utilizadorMesmoId = new Utilizador(1, "Ana Nova");
        utilizadorOutroId = new Utilizador(2, "Bruno");
        casa1 = new Casa("Casa Jardim", 10);
        casa2 = new Casa("Casa Centro", 20);
    }

    @Test
    void testConstrutorGettersESetters() {
        // Valida construtor vazio, getters e setters do Utilizador.
        Utilizador u = new Utilizador();
        u.setNome("Carla");
        u.setId(33);

        assertEquals("Carla", u.getNome());
        assertEquals(33, u.getId());
        assertTrue(u.getCasasAdministradas().isEmpty());
        assertTrue(u.getCasasUtilizador().isEmpty());
    }

    @Test
    void testAdicionarERemoverCasas() {
        // Valida gestão de casas administradas e de utilizador.
        utilizador.adicionarCasaAdministrada(casa1);
        utilizador.adicionarCasaUtilizador(casa2);

        assertTrue(utilizador.podeAdministrarCasa(casa1));
        assertTrue(utilizador.podeUsarCasa(casa1));
        assertTrue(utilizador.podeUsarCasa(casa2));
        assertTrue(utilizador.serAdmin(casa1));
        assertTrue(utilizador.serUtilizador(casa2));

        utilizador.removerCasaUtilizador(casa2);
        assertFalse(utilizador.podeUsarCasa(casa2));
    }

    @Test
    void testCopyConstructorPreservaAssociacoes() {
        // Valida que o construtor de cópia preserva os mapas associados.
        utilizador.adicionarCasaAdministrada(casa1);
        utilizador.adicionarCasaUtilizador(casa2);

        Utilizador copia = new Utilizador(utilizador);

        assertEquals(utilizador, copia);
        assertNotSame(utilizador, copia);
        assertEquals(1, copia.getCasasAdministradas().size());
        assertEquals(2, copia.getCasasUtilizador().size());
    }

    @Test
    void testEqualsEHashCodeBaseadosNoId() {
        // Valida que equals/hashCode dependem apenas do ID.
        assertEquals(utilizador, utilizadorMesmoId);
        assertNotEquals(utilizador, utilizadorOutroId);
        assertEquals(utilizador.hashCode(), utilizadorMesmoId.hashCode());
    }
}

