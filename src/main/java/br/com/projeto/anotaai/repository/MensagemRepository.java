package br.com.projeto.anotaai.repository;

import br.com.projeto.anotaai.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repositório de acesso a dados para a entidade {@link Mensagem}.
 *
 * Além das operações CRUD herdadas do JpaRepository, define queries específicas
 * para recuperar histórico de conversa e lista de chats do usuário.
 */
public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    /**
     * Retorna todas as mensagens trocadas entre dois usuários, em ordem cronológica.
     *
     * A query busca mensagens nas duas direções (A→B e B→A) para compor o histórico
     * completo da conversa. Usada ao abrir uma janela de chat.
     *
     * @param id1 ID de um dos participantes da conversa
     * @param id2 ID do outro participante
     */
    @Query("SELECT m FROM Mensagem m WHERE " +
           "(m.remetenteId = :id1 AND m.destinatarioId = :id2) OR " +
           "(m.remetenteId = :id2 AND m.destinatarioId = :id1) " +
           "ORDER BY m.timestamp ASC")
    List<Mensagem> findConversa(@Param("id1") Long id1, @Param("id2") Long id2);

    /**
     * Retorna todas as mensagens em que o usuário participou (como remetente ou destinatário),
     * ordenadas por data. Usada para montar a lista lateral de chats ativos.
     *
     * @param remetenteId   ID do usuário como remetente
     * @param destinatarioId ID do usuário como destinatário (mesmo valor — Spring Data duplica o parâmetro)
     */
    List<Mensagem> findByRemetenteIdOrDestinatarioIdOrderByTimestampAsc(Long remetenteId, Long destinatarioId);
}
