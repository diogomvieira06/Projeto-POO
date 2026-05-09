package src.main.Exceptions;

/**
 * Exceção lançada quando um dispositivo não é encontrado no sistema.
 */
public class DispositivoNaoEncontradoException extends DomusControlException {
    public DispositivoNaoEncontradoException() {
        super("Dispositivo não encontrado.");
    }

    public DispositivoNaoEncontradoException(String message) {
        super(message);
    }
}
