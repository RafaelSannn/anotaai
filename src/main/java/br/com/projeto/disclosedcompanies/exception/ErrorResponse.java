package br.com.projeto.disclosedcompanies.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * Objeto padronizado de resposta de erro da API.
 * 
 * Garante que todos os erros sigam o mesmo formato JSON,
 * facilitando o tratamento no frontend.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    
    /** Timestamp do erro */
    private LocalDateTime timestamp;
    
    /** Código HTTP do erro (400, 404, 500, etc.) */
    private int status;
    
    /** Nome do erro (BAD_REQUEST, NOT_FOUND, etc.) */
    private String error;
    
    /** Mensagem descritiva para o usuário */
    private String message;
    
    /** Path da requisição que gerou o erro */
    private String path;
    
    /**
     * Construtor simplificado para respostas de erro comuns
     */
    public ErrorResponse(int status, String error, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
    }
}
