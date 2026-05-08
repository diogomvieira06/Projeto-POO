package src.main.Exceptions;

/**
 * Exceção lançada quando uma casa não é encontrada no sistema.
 * Essa exceção é utilizada para indicar que a operação solicitada não pode ser concluída porque a casa especificada não existe ou não foi localizada.
 */
public class CasaNaoEncontradaException extends DomusControlException {
    public CasaNaoEncontradaException() {
        super("Casa não encontrada.");
    }

    public CasaNaoEncontradaException(String message) {
        super(message);
    }
}
