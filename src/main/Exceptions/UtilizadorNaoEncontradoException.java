package src.main.Exceptions;

public class UtilizadorNaoEncontradoException extends DomusControlException {
    public UtilizadorNaoEncontradoException() {
        super("Utilizador não encontrado.");
    }

    public UtilizadorNaoEncontradoException(String message) {
        super(message);
    }
}
