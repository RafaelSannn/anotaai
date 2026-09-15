package br.com.projeto.anotaai.exception;

/**
 * Exceção lançada quando um recurso não é encontrado no banco de dados.
 * 
 * Exemplo: tentativa de buscar usuário por ID inexistente.
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resource, Long id) {
        super(String.format("%s com ID %d não encontrado", resource, id));
    }
    
    public ResourceNotFoundException(String resource, String field, String value) {
        super(String.format("%s com %s='%s' não encontrado", resource, field, value));
    }
}
