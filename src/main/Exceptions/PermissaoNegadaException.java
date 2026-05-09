package src.main.Exceptions;

/**
 * Exceção lançada quando um usuário tenta acessar um recurso ou realizar uma ação para a qual não tem permissão. Esta exceção pode ser usada para indicar que o usuário não tem as credenciais necessárias ou que sua conta está desativada, entre outras situações relacionadas à segurança e controle de acesso.
 */
public class PermissaoNegadaException extends DomusControlException {
    public PermissaoNegadaException() {
        super("Permissão negada.");
    }

    public PermissaoNegadaException(String message) {
        super(message);
    }
}
