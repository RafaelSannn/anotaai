package br.com.projeto.disclosedcompanies.service;

import br.com.projeto.disclosedcompanies.dto.LoginResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class UsuarioServiceTest {

    @Autowired
    private UsuarioService service;

    @Test
    void cadastroComEmailDuplicadoRetornaErro() {
        service.cadastrar("João", "joao@test.com", "senha123", "visitante", null, null, null);

        Map<String, String> resultado = service.cadastrar("João2", "joao@test.com", "senha123", "visitante", null, null, null);

        assertThat(resultado.get("status")).isEqualTo("erro");
        assertThat(resultado.get("mensagem")).contains("E-mail já registado");
    }

    @Test
    void cadastroComSenhaCurtaRetornaErro() {
        Map<String, String> resultado = service.cadastrar("Ana", "ana@test.com", "123", "visitante", null, null, null);

        assertThat(resultado.get("status")).isEqualTo("erro");
        assertThat(resultado.get("mensagem")).contains("6 caracteres");
    }

    @Test
    void cadastroComCnpjDuplicadoRetornaErro() {
        service.cadastrar("Empresa A", "empresaA@test.com", "senha123",
                "empresa", "SP", "11.111.111/0001-11", "ti");

        Map<String, String> resultado = service.cadastrar("Empresa B", "empresaB@test.com", "senha123",
                "empresa", "RJ", "11.111.111/0001-11", "ti");

        assertThat(resultado.get("status")).isEqualTo("erro");
        assertThat(resultado.get("mensagem")).contains("CNPJ");
    }

    @Test
    void cadastroSucessoRetornaStatusSucesso() {
        Map<String, String> resultado = service.cadastrar("Pedro", "pedro@test.com", "senha123",
                "visitante", null, null, null);

        assertThat(resultado.get("status")).isEqualTo("sucesso");
    }

    @Test
    void loginComSenhaErradaRetornaErro() {
        service.cadastrar("Maria", "maria@test.com", "correta123", "visitante", null, null, null);

        LoginResponse response = service.login("maria@test.com", "errada999");

        assertThat(response.getStatus()).isEqualTo("erro");
        assertThat(response.getUsuario()).isNull();
    }

    @Test
    void loginSucessoRetornaUsuarioSemSenha() {
        service.cadastrar("Carlos", "carlos@test.com", "senha456", "visitante", null, null, null);

        LoginResponse response = service.login("carlos@test.com", "senha456");

        assertThat(response.getStatus()).isEqualTo("sucesso");
        assertThat(response.getUsuario().getEmail()).isEqualTo("carlos@test.com");
    }
}
