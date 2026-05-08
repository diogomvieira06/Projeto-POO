package src.main.Exceptions;


/**
 * Exceção lançada quando uma divisão não é encontrada no sistema.
 * Essa exceção é utilizada para indicar que a operação solicitada não pode ser concluída porque a divisão especificada não existe ou não foi localizada.
 */
public class DivisaoNaoEncontradaException extends DomusControlException {
    public DivisaoNaoEncontradaException() {
        super("Divisão não encontrada.");
    }

    public DivisaoNaoEncontradaException(String message) {
        super(message);
    }
}
