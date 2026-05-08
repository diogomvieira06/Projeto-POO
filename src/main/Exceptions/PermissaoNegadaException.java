package src.main.Exceptions;

/**
 * Exceção lançada quando uma operação é negada devido à falta de permissões adequadas.
 * Essa exceção é utilizada para indicar que o usuário ou processo que está tentando realizar a operação não possui as permissões necessárias para executá-la, seja por restrições de segurança, políticas de acesso ou outras razões relacionadas à autorização. Ela serve para informar que a ação solicitada não pode ser concluída devido a limitações de acesso ou privilégios insuficientes.
 */
public class PermissaoNegadaException extends DomusControlException {
    public PermissaoNegadaException() {
        super("Permissão negada.");
    }

    public PermissaoNegadaException(String message) {
        super(message);
    }
}
