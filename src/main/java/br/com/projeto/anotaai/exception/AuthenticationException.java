package br.com.projeto.anotaai.exception;

/**
 * Exceção lançada quando há erro de autenticação ou autorização.
 * 
 * Exemplo: credenciais inválidas, token expirado, acesso negado.
 */
public class AuthenticationException extends RuntimeException {
    
    public AuthenticationException(String message) {
        super(message);
    }
}
