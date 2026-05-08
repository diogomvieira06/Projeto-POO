package src.main.Exceptions;


/**
 * Classe base para todas as exceções personalizadas do sistema DomusControl.
 * Essa classe estende RuntimeException, permitindo que as exceções sejam lançadas sem a necessidade de serem declaradas ou capturadas explicitamente. Ela serve como uma superclasse para outras exceções específicas, como CasaNaoEncontradaException, DivisaoNaoEncontradaException, DispositivoNaoEncontradoException e CenarioNaoEncontradoException, proporcionando uma hierarquia de exceções organizada e consistente para o sistema.
 */
public class DomusControlException extends RuntimeException {
    public DomusControlException() {
        super();
    }

    public DomusControlException(String message) {
        super(message);
    }

    public DomusControlException(String message, Throwable cause) {
        super(message, cause);
    }
}

