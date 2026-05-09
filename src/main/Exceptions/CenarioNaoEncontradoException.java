package src.main.Exceptions;

/**
 * Exceção lançada quando um cenário não é encontrado no sistema.
 */
public class CenarioNaoEncontradoException extends DomusControlException {
    public CenarioNaoEncontradoException() {
        super("Cenário não encontrado.");
    }

    public CenarioNaoEncontradoException(String message) {
        super(message);
    }
}
