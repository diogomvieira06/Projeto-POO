package src.main.Exceptions;

/**
 * Exceção lançada quando uma divisão não é encontrada no sistema.
 */
public class DivisaoNaoEncontradaException extends DomusControlException {
    public DivisaoNaoEncontradaException() {
        super("Divisão não encontrada.");
    }

    public DivisaoNaoEncontradaException(String message) {
        super(message);
    }
}
