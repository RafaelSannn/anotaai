package br.com.projeto.anotaai.model;

import br.com.projeto.anotaai.repository.MensagemRepository;
import br.com.projeto.anotaai.repository.PublicacaoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PrePersistTest {

    @Autowired
    private PublicacaoRepository publicacaoRepository;

    @Autowired
    private MensagemRepository mensagemRepository;

    @Test
    void publicacaoDataCriacaoEhDefinidaNaPersistencia() {
        Publicacao pub = new Publicacao();
        pub.setTitulo("Teste");

        Publicacao salva = publicacaoRepository.save(pub);

        assertThat(salva.getDataCriacao()).isNotNull();
        assertThat(salva.getDataCriacao()).isBefore(LocalDateTime.now().plusSeconds(1));
    }

    @Test
    void mensagemTimestampEhDefinidoNaPersistencia() {
        Mensagem msg = new Mensagem();
        msg.setRemetenteId(1L);
        msg.setDestinatarioId(2L);
        msg.setConteudo("Olá");

        Mensagem salva = mensagemRepository.save(msg);

        assertThat(salva.getTimestamp()).isNotNull();
        assertThat(salva.getTimestamp()).isBefore(LocalDateTime.now().plusSeconds(1));
    }
}
