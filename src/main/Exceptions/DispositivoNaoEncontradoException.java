package src.main.Exceptions;

public class DispositivoNaoEncontradoException extends DomusControlException {
    public DispositivoNaoEncontradoException() {
        super("Dispositivo não encontrado.");
    }

    public DispositivoNaoEncontradoException(String message) {
        super(message);
    }
}
