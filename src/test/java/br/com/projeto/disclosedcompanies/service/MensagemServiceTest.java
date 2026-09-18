package br.com.projeto.disclosedcompanies.service;

import br.com.projeto.disclosedcompanies.model.Mensagem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MensagemServiceTest {

    @Autowired
    private MensagemService service;

    @Test
    void enviarPersisteMensagemComId() {
        Mensagem msg = new Mensagem();
        msg.setRemetenteId(10L);
        msg.setDestinatarioId(20L);
        msg.setConteudo("Teste de mensagem");

        Mensagem salva = service.enviar(msg);

        assertThat(salva.getId()).isNotNull();
        assertThat(salva.getTimestamp()).isNotNull();
    }

    @Test
    void carregarConversaRetornaMensagensEntreDoisUsuarios() {
        Mensagem m1 = new Mensagem();
        m1.setRemetenteId(30L);
        m1.setDestinatarioId(40L);
        m1.setConteudo("Oi");

        Mensagem m2 = new Mensagem();
        m2.setRemetenteId(40L);
        m2.setDestinatarioId(30L);
        m2.setConteudo("Olá");

        service.enviar(m1);
        service.enviar(m2);

        List<Mensagem> conversa = service.carregarConversa(30L, 40L);

        assertThat(conversa).hasSizeGreaterThanOrEqualTo(2);
        assertThat(conversa).extracting(Mensagem::getConteudo)
                .contains("Oi", "Olá");
    }
}
