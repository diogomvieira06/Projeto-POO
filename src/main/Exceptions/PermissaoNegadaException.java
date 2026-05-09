package src.main.Exceptions;

public class PermissaoNegadaException extends DomusControlException {
    public PermissaoNegadaException() {
        super("Permissão negada.");
    }

    public PermissaoNegadaException(String message) {
        super(message);
    }
}
