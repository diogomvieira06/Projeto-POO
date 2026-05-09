package src.main.Exceptions;

public class DivisaoNaoEncontradaException extends DomusControlException {
    public DivisaoNaoEncontradaException() {
        super("Divisão não encontrada.");
    }

    public DivisaoNaoEncontradaException(String message) {
        super(message);
    }
}
