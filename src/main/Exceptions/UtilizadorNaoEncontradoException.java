package src.main.Exceptions;

/**
 * Exceção lançada quando um utilizador não é encontrado no sistema.
 * Essa exceção é utilizada para indicar que a operação solicitada não pode ser concluída porque o utilizador especificado não existe ou não foi localizado.
 */
public class UtilizadorNaoEncontradoException extends DomusControlException {
    public UtilizadorNaoEncontradoException() {
        super("Utilizador não encontrado.");
    }

    public UtilizadorNaoEncontradoException(String message) {
        super(message);
    }
}
