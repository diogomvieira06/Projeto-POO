package src.main.Exceptions;

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

