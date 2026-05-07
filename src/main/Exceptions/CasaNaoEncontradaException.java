package src.main.Exceptions;

public class CasaNaoEncontradaException extends DomusControlException {
    public CasaNaoEncontradaException() {
        super("Casa não encontrada.");
    }

    public CasaNaoEncontradaException(String message) {
        super(message);
    }
}
