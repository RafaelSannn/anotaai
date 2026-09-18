package br.com.projeto.disclosedcompanies.controller;

import br.com.projeto.disclosedcompanies.service.PublicacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PublicacaoControllerTest {

    @Autowired
    private PublicacaoService service;

    @Test
    void deleteComIdInexistenteRetorna404() {
        assertThatThrownBy(() -> service.eliminar(99999L))
                .isInstanceOf(ResponseStatusException.class)
                .satisfies(ex -> assertThat(((ResponseStatusException) ex).getStatusCode())
                        .isEqualTo(HttpStatus.NOT_FOUND));
    }
}
