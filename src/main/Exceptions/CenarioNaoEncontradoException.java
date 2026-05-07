package src.main.Exceptions;

public class CenarioNaoEncontradoException extends DomusControlException {
    public CenarioNaoEncontradoException() {
        super("Cenário não encontrado.");
    }

    public CenarioNaoEncontradoException(String message) {
        super(message);
    }
}
