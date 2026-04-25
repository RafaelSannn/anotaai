package br.com.projeto.anotaai.controller;

import br.com.projeto.anotaai.model.Usuario;
import br.com.projeto.anotaai.service.UsuarioService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UsuarioControllerTest {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listagemDeEmpresasNaoExpoeSenha() throws Exception {
        usuarioService.cadastrar("Empresa Teste", "empresa2@test.com", "senha123",
                "empresa", "São Paulo", "98.765.432/0001-00", "tecnologia");

        List<Usuario> empresas = usuarioService.listarEmpresas();

        for (Usuario empresa : empresas) {
            String json = objectMapper.writeValueAsString(empresa);
            assertThat(json).doesNotContain("\"senha\"");
        }
    }
}
