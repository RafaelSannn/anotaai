package br.com.projeto.anotaai.repository;

import br.com.projeto.anotaai.model.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Repositório de acesso a dados para a entidade {@link Publicacao}.
 *
 * Herda as operações básicas de CRUD do JpaRepository.
 * Os métodos de busca são derivados automaticamente pelo Spring Data JPA
 * a partir dos nomes dos métodos.
 */
public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {

    /** Retorna todas as publicações criadas por um usuário específico. */
    List<Publicacao> findByUsuarioId(Long usuarioId);

    /**
     * Retorna publicações filtradas pelo tipo do autor.
     * Usado pela tela da empresa para exibir publicações de visitantes
     * (busca por tipoAutor = "visitante").
     */
    List<Publicacao> findByTipoAutor(String tipoAutor);
}
