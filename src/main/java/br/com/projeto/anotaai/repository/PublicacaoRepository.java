package br.com.projeto.anotaai.repository;

import br.com.projeto.anotaai.model.Publicacao;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PublicacaoRepository extends JpaRepository<Publicacao, Long> {
    // Busca publicações de um utilizador específico (Integridade: Read)
    List<Publicacao> findByUsuarioId(Long usuarioId);

    // Busca todas as publicações de um tipo (ex: para a empresa ver as de visitantes)
    List<Publicacao> findByTipoAutor(String tipoAutor);
}