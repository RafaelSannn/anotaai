package br.com.projeto.anotaai.repository;

import br.com.projeto.anotaai.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {

    // Procura o histórico completo de conversa entre dois utilizadores específicos
    @Query("SELECT m FROM Mensagem m WHERE (m.remetenteId = :id1 AND m.destinatarioId = :id2) OR (m.remetenteId = :id2 AND m.destinatarioId = :id1) ORDER BY m.timestamp ASC")
    List<Mensagem> findConversa(@Param("id1") Long id1, @Param("id2") Long id2);

    // Procura todos os chats onde o utilizador participou (para montar a lista lateral)
    List<Mensagem> findByRemetenteIdOrDestinatarioIdOrderByTimestampAsc(Long remetenteId, Long destinatarioId);
}