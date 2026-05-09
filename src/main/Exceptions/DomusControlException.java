package src.main.Exceptions;

/**
 * Exceção base para erros relacionados ao sistema DomusControl. Todas as outras exceções específicas do sistema devem estender esta classe.
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

