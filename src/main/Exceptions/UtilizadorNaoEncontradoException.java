package src.main.Exceptions;

/**
 * Exceção lançada quando um utilizador não é encontrado no sistema.
 */
public class UtilizadorNaoEncontradoException extends DomusControlException {
    public UtilizadorNaoEncontradoException() {
        super("Utilizador não encontrado.");
    }

    public UtilizadorNaoEncontradoException(String message) {
        super(message);
    }
}
