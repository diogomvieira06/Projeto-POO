package src.main.Exceptions;

public class EscalonamentoNaoEncontradoException extends DomusControlException {
    public EscalonamentoNaoEncontradoException() {
        super("Escalonamento não encontrado.");
    }

    public EscalonamentoNaoEncontradoException(String message) {
        super(message);
    }
}
