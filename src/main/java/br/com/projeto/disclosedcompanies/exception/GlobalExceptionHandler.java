package br.com.projeto.disclosedcompanies.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

/**
 * Manipulador global de exceções da API.
 * 
 * Captura todas as exceções lançadas pelos controllers e as converte
 * em respostas HTTP padronizadas (ErrorResponse).
 * 
 * Benefícios:
 * - Código DRY (não precisa try-catch em cada controller)
 * - Respostas consistentes
 * - Logs centralizados
 * - Mensagens amigáveis ao usuário
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Trata erros de recurso não encontrado (404)
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(
            ResourceNotFoundException ex, 
            HttpServletRequest request) {
        
        log.warn("Recurso não encontrado: {} - Path: {}", ex.getMessage(), request.getRequestURI());
        
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "NOT_FOUND",
            ex.getMessage(),
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Trata erros de validação de dados (400)
     */
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(
            ValidationException ex, 
            HttpServletRequest request) {
        
        log.warn("Erro de validação: {} - Path: {}", ex.getMessage(), request.getRequestURI());
        
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "VALIDATION_ERROR",
            ex.getMessage(),
            request.getRequestURI()
        );
        
        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Trata erros de autenticação (401)
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthentication(
            AuthenticationException ex, 
            HttpServletRequest request) {
        
        log.warn("Erro de autenticação: {} - Path: {}", ex.getMessage(), request.getRequestURI());
        
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.UNAUTHORIZED.value(),
            "UNAUTHORIZED",
            ex.getMessage(),
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
    }

    /**
     * Trata erros de validação JPA (@Valid, @NotNull, etc.)
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, 
            HttpServletRequest request) {
        
        String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn("Validação de argumento falhou: {} - Path: {}", message, request.getRequestURI());
        
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "VALIDATION_ERROR",
            message,
            request.getRequestURI()
        );
        
        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Trata erros de constraint do banco (unique, not null, etc.)
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex, 
            HttpServletRequest request) {
        
        String message = ex.getConstraintViolations().iterator().next().getMessage();
        log.warn("Violação de constraint: {} - Path: {}", message, request.getRequestURI());
        
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "CONSTRAINT_VIOLATION",
            message,
            request.getRequestURI()
        );
        
        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Trata erros de tipo de argumento inválido
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleTypeMismatch(
            MethodArgumentTypeMismatchException ex, 
            HttpServletRequest request) {
        
        String message = String.format("Parâmetro '%s' inválido. Esperado tipo: %s", 
            ex.getName(), ex.getRequiredType().getSimpleName());
        
        log.warn("Erro de tipo de argumento: {} - Path: {}", message, request.getRequestURI());
        
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "TYPE_MISMATCH",
            message,
            request.getRequestURI()
        );
        
        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Trata qualquer exceção não capturada (500)
     * 
     * IMPORTANTE: Em produção, não exponha detalhes técnicos ao usuário.
     * Esta mensagem é genérica para evitar vazamento de informações.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex, 
            HttpServletRequest request) {
        
        log.error("Erro inesperado: {} - Path: {}", ex.getMessage(), request.getRequestURI(), ex);
        
        ErrorResponse error = new ErrorResponse(
            LocalDateTime.now(),
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            "INTERNAL_SERVER_ERROR",
            "Erro interno do servidor. Por favor, contate o suporte.",
            request.getRequestURI()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
