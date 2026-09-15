package br.com.projeto.anotaai.exception;

/**
 * Exceção lançada quando há erro de validação de dados.
 * 
 * Exemplo: e-mail inválido, CNPJ duplicado, senha muito curta.
 */
public class ValidationException extends RuntimeException {
    
    public ValidationException(String message) {
        super(message);
    }
    
    public ValidationException(String field, String message) {
        super(String.format("Erro no campo '%s': %s", field, message));
    }
}
