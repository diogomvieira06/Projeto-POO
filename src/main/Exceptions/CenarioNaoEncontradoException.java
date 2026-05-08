package src.main.Exceptions;


/**
 * Exceção lançada quando um cenário não é encontrado no sistema.
 * Essa exceção é utilizada para indicar que a operação solicitada não pode ser concluída porque o cenário especificado não existe ou não foi localizado.
 */
public class CenarioNaoEncontradoException extends DomusControlException {
    public CenarioNaoEncontradoException() {
        super("Cenário não encontrado.");
    }

    public CenarioNaoEncontradoException(String message) {
        super(message);
    }
}
