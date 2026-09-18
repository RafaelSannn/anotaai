package br.com.projeto.disclosedcompanies.dto;

import lombok.Builder;
import lombok.Data;

/**
 * DTO de resposta para o endpoint POST /php/login.php.
 *
 * Encapsula o resultado da tentativa de autenticação:
 * - Em sucesso: status="sucesso", mensagem e o objeto usuario preenchido.
 * - Em falha:   status="erro", mensagem descritiva e usuario=null.
 *
 * O campo "status" é verificado pelo script2.js para decidir o fluxo
 * (redirecionar para o dashboard ou exibir o alerta de erro).
 */
@Data
@Builder
public class LoginResponse {

    /** "sucesso" ou "erro". Verificado diretamente pelo frontend. */
    private String status;

    /** Mensagem legível exibida ao usuário (ex: "Sessão iniciada com sucesso."). */
    private String mensagem;

    /**
     * Dados do usuário autenticado. Null quando status = "erro".
     * Inclui companyDetails para usuários do tipo "empresa".
     */
    private UsuarioResponse usuario;
}
