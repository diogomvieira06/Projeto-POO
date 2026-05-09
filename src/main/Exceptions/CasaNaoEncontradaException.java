package src.main.Exceptions;

/**
 * Exceção lançada quando uma casa não é encontrada no sistema.
 */
public class CasaNaoEncontradaException extends DomusControlException {
    public CasaNaoEncontradaException() {
        super("Casa não encontrada.");
    }

    public CasaNaoEncontradaException(String message) {
        super(message);
    }
}
