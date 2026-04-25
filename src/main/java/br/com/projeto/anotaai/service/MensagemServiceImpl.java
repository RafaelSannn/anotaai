package br.com.projeto.anotaai.service;

import br.com.projeto.anotaai.model.Mensagem;
import br.com.projeto.anotaai.repository.MensagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementação concreta de {@link MensagemService}.
 *
 * Delega todas as operações ao {@link MensagemRepository}.
 * O @PrePersist da entidade {@link br.com.projeto.anotaai.model.Mensagem}
 * cuida de preencher o timestamp automaticamente ao salvar.
 */
@Service
@RequiredArgsConstructor
public class MensagemServiceImpl implements MensagemService {

    private final MensagemRepository repository;

    /** Salva a mensagem no banco. O timestamp é definido pelo @PrePersist da entidade. */
    @Override
    public Mensagem enviar(Mensagem msg) {
        return repository.save(msg);
    }

    /** Delega ao repositório a query JPQL que busca mensagens nas duas direções entre os usuários. */
    @Override
    public List<Mensagem> carregarConversa(Long id1, Long id2) {
        return repository.findConversa(id1, id2);
    }

    /** Delega ao repositório a busca de todas as mensagens onde o usuário participou. */
    @Override
    public List<Mensagem> listarChatsDoUsuario(Long id) {
        return repository.findByRemetenteIdOrDestinatarioIdOrderByTimestampAsc(id, id);
    }
}
