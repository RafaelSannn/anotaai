package br.com.projeto.disclosedcompanies.controller;

import br.com.projeto.disclosedcompanies.dto.LoginResponse;
import br.com.projeto.disclosedcompanies.model.Usuario;
import br.com.projeto.disclosedcompanies.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller REST para operações de usuário.
 *
 * Mapeado em "/php" para manter compatibilidade com o script2.js legado,
 * que espera URLs no estilo "/php/login.php", "/php/register.php", etc.
 *
 * Responsabilidade: receber a requisição HTTP, delegar ao {@link UsuarioService}
 * e devolver o ResponseEntity adequado. Nenhuma lógica de negócio aqui.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/php")
public class UsuarioController {

    private final UsuarioService service;

    /**
     * GET /php/usuarios/empresas
     * Retorna todas as empresas cadastradas. Usado pela tela "Explorar Empresas".
     * O campo "senha" nunca aparece no JSON graças ao @JsonIgnore na entidade.
     */
    @GetMapping("/usuarios/empresas")
    public List<Usuario> listarEmpresas() {
        return service.listarEmpresas();
    }

    /**
     * GET /php/usuarios/empresa/{categoria}
     * Retorna empresas filtradas por categoria. Alimenta o select de filtro do frontend.
     */
    @GetMapping("/usuarios/empresa/{categoria}")
    public List<Usuario> listarEmpresaPorCategoria(@PathVariable String categoria) {
        return service.listarEmpresasPorCategoria(categoria);
    }

    /**
     * POST /php/register.php
     * Cadastra um novo usuário (visitante ou empresa).
     * Recebe os dados como form-data (@RequestParam) para compatibilidade com o script2.js.
     * Retorna 200 em sucesso ou 400 com mensagem de erro.
     */
    @PostMapping("/register.php")
    public ResponseEntity<Map<String, String>> cadastrar(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String tipo,
            @RequestParam(required = false) String localizacao,
            @RequestParam(required = false) String cnpj,
            @RequestParam(required = false) String categoria) {

        Map<String, String> response = service.cadastrar(nome, email, senha, tipo, localizacao, cnpj, categoria);

        if ("erro".equals(response.get("status"))) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    /**
     * PUT /php/perfil.php
     * Atualiza os dados de perfil de um usuário (nome, foto, localização, categoria, descrição).
     * Apenas os campos enviados são atualizados — campos ausentes são preservados.
     * Retorna 200 em sucesso ou 400 se o ID não existir.
     */
    @PutMapping("/perfil.php")
    public ResponseEntity<Map<String, String>> atualizarPerfil(
            @RequestParam Long id,
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String profileImage,
            @RequestParam(required = false) String localizacao,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) String descricao) {

        Map<String, String> response = service.atualizarPerfil(id, nome, profileImage, localizacao, categoria, descricao);
        if ("erro".equals(response.get("status"))) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }

    /**
     * POST /php/login.php
     * Autentica o usuário por e-mail e senha.
     * Retorna 200 com os dados do usuário em caso de sucesso,
     * ou 400 com mensagem genérica em caso de credenciais inválidas.
     */
    @PostMapping("/login.php")
    public ResponseEntity<LoginResponse> login(
            @RequestParam String email,
            @RequestParam String senha) {

        LoginResponse response = service.login(email, senha);
        if ("erro".equals(response.getStatus())) {
            return ResponseEntity.badRequest().body(response);
        }
        return ResponseEntity.ok(response);
    }
}
