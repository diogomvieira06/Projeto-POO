package src.main.Exceptions;

/**
 * Exceção lançada quando um escalonamento não é encontrado no sistema.
 */
public class EscalonamentoNaoEncontradoException extends DomusControlException {
    public EscalonamentoNaoEncontradoException() {
        super("Escalonamento não encontrado.");
    }

    public EscalonamentoNaoEncontradoException(String message) {
        super(message);
    }
}
