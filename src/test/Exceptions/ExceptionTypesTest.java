package src.test.Exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.Exceptions.*;

import static org.junit.jupiter.api.Assertions.*;

class ExceptionTypesTest {

    @Test
    void testDomusControlExceptionGuardaMensagem() {
        // Valida a mensagem da exceção base do domínio.
        DomusControlException e = new DomusControlException("Erro genérico");
        assertEquals("Erro genérico", e.getMessage());
        assertTrue(e instanceof RuntimeException);
    }

    @Test
    void testCasaNaoEncontradaException() {
        // Valida a mensagem padrão e customizada da exceção de Casa.
        assertEquals("Casa não encontrada.", new CasaNaoEncontradaException().getMessage());
        assertEquals("Casa inválida", new CasaNaoEncontradaException("Casa inválida").getMessage());
    }

    @Test
    void testUtilizadorNaoEncontradoException() {
        // Valida a mensagem padrão e customizada da exceção de Utilizador.
        assertEquals("Utilizador não encontrado.", new UtilizadorNaoEncontradoException().getMessage());
        assertEquals("Utilizador inválido", new UtilizadorNaoEncontradoException("Utilizador inválido").getMessage());
    }

    @Test
    void testDivisaoEDispositivoNaoEncontradoException() {
        // Valida as exceções de Divisão e Dispositivo.
        assertEquals("Divisão não encontrada.", new DivisaoNaoEncontradaException().getMessage());
        assertEquals("Dispositivo não encontrado.", new DispositivoNaoEncontradoException().getMessage());
    }

    @Test
    void testCenarioEscalonamentoEPermissaoException() {
        // Valida as exceções de Cenário, Escalonamento e Permissão.
        assertEquals("Cenário não encontrado.", new CenarioNaoEncontradoException().getMessage());
        assertEquals("Escalonamento não encontrado.", new EscalonamentoNaoEncontradoException().getMessage());
        assertEquals("Permissão negada.", new PermissaoNegadaException().getMessage());
    }
}

