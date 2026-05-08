package src.main.Exceptions;


/**
 * Exceção lançada quando um dispositivo não é encontrado no sistema.
 * Essa exceção é utilizada para indicar que a operação solicitada não pode ser concluída porque o dispositivo especificado não existe ou não foi localizado.
 */
public class DispositivoNaoEncontradoException extends DomusControlException {
    public DispositivoNaoEncontradoException() {
        super("Dispositivo não encontrado.");
    }

    public DispositivoNaoEncontradoException(String message) {
        super(message);
    }
}
