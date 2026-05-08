package src.main.Exceptions;


/**
 * Exceção lançada quando um escalonamento não é encontrado no sistema.
 * Essa exceção é utilizada para indicar que a operação solicitada não pode ser concluída porque o escalonamento especificado não existe ou não foi localizado.
 */
public class EscalonamentoNaoEncontradoException extends DomusControlException {
    public EscalonamentoNaoEncontradoException() {
        super("Escalonamento não encontrado.");
    }

    public EscalonamentoNaoEncontradoException(String message) {
        super(message);
    }
}
