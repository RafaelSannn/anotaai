package br.com.projeto.anotaai.service;

import br.com.projeto.anotaai.model.Publicacao;

import java.util.List;

/**
 * Contrato de negócio para operações sobre publicações.
 *
 * Define criação, listagem e exclusão de publicações.
 * A implementação concreta é {@link PublicacaoServiceImpl}.
 */
public interface PublicacaoService {

    /** Persiste uma nova publicação no banco e retorna o objeto salvo (com ID preenchido). */
    Publicacao criar(Publicacao pub);

    /** Retorna todas as publicações de um usuário específico. */
    List<Publicacao> listarPorUsuario(Long usuarioId);

    /** Retorna todas as publicações de um tipo de autor ("visitante" ou "empresa"). */
    List<Publicacao> listarPorTipo(String tipo);

    /**
     * Remove uma publicação pelo ID.
     * Lança {@link org.springframework.web.server.ResponseStatusException} com status 404
     * se o ID não existir, evitando que o cliente receba um erro 500 genérico.
     */
    void eliminar(Long id);
}
