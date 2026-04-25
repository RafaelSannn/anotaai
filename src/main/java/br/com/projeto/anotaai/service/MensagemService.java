package br.com.projeto.anotaai.service;

import br.com.projeto.anotaai.model.Mensagem;

import java.util.List;

/**
 * Contrato de negócio para operações de chat (mensagens diretas).
 *
 * Define envio de mensagens e consulta de histórico.
 * A implementação concreta é {@link MensagemServiceImpl}.
 */
public interface MensagemService {

    /** Persiste uma mensagem no banco e retorna o objeto salvo (com ID e timestamp preenchidos). */
    Mensagem enviar(Mensagem msg);

    /**
     * Retorna o histórico completo da conversa entre dois usuários, em ordem cronológica.
     * Inclui mensagens nas duas direções (A→B e B→A).
     */
    List<Mensagem> carregarConversa(Long id1, Long id2);

    /**
     * Retorna todas as mensagens em que o usuário participou (como remetente ou destinatário).
     * Usado para montar a lista lateral de chats ativos no dashboard.
     */
    List<Mensagem> listarChatsDoUsuario(Long id);
}
