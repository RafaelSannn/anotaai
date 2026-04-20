package br.com.projeto.anotaai.controller;

import br.com.projeto.anotaai.model.Usuario;
import br.com.projeto.anotaai.repository.UsuarioRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/php") // Mantém a compatibilidade com as chamadas do script2.js
public class UsuarioController {

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder; // Injeta o motor de criptografia BCrypt

    // ROTA: Retorna todas as empresas cadastradas (usada pelo Explorar)
    @GetMapping("/usuarios/empresas")
    public List<Usuario> listarEmpresas() {
        return repository.findByTipo("empresa");
    }

    // ROTA: Filtra empresas por categoria diretamente no MariaDB
    @GetMapping("/usuarios/empresa/{categoria}")
    public List<Usuario> listarEmpresaPorCategoria(@PathVariable String categoria) {
        return repository.findByTipoAndCategoria("empresa", categoria);
    }

    // ROTA PRINCIPAL: Cadastro de novos usuários
    @PostMapping("/register.php")
    public ResponseEntity<Map<String, String>> cadastrar(
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam String senha,
            @RequestParam String tipo,
            @RequestParam(required = false) String localizacao,
            @RequestParam(required = false) String cnpj,
            @RequestParam(required = false) String categoria) {

        Map<String, String> response = new HashMap<>();

        try {
            // 1. VALIDAÇÃO DE SENHA: Barra senhas curtas antes de gerar o Hash
            if (senha == null || senha.trim().length() < 6) {
                response.put("status", "erro");
                response.put("mensagem", "A palavra-passe deve ter pelo menos 6 caracteres.");
                return ResponseEntity.badRequest().body(response);
            }

            // 2. VALIDAÇÃO DE E-MAIL: Impede emails repetidos
            if (repository.findByEmail(email).isPresent()) {
                response.put("status", "erro");
                response.put("mensagem", "E-mail já registado. Tente outro.");
                return ResponseEntity.badRequest().body(response);
            }

            // 3. VALIDAÇÃO DE CNPJ: Se for empresa, impede CNPJs repetidos
            if ("empresa".equals(tipo) && cnpj != null && !cnpj.trim().isEmpty()) {
                if (repository.findByCnpj(cnpj).isPresent()) {
                    response.put("status", "erro");
                    response.put("mensagem", "Este CNPJ já está registado em outra empresa.");
                    return ResponseEntity.badRequest().body(response);
                }
            }

            // 4. MONTAGEM DO OBJETO:
            Usuario novoUsuario = new Usuario();
            novoUsuario.setNome(nome);
            novoUsuario.setEmail(email);

            // CRIPTOGRAFIA: Salva apenas o Hash no banco (Segurança técnica)
            novoUsuario.setSenha(passwordEncoder.encode(senha));
            novoUsuario.setTipo(tipo);

            if ("empresa".equals(tipo)) {
                novoUsuario.setLocalizacao(localizacao);
                novoUsuario.setCnpj(cnpj);
                novoUsuario.setCategoria(categoria);
            }

            // 5. PERSISTÊNCIA:
            repository.save(novoUsuario);

            response.put("status", "sucesso");
            response.put("mensagem", "Registo concluído com sucesso.");
            return ResponseEntity.ok(response);

        } catch (ConstraintViolationException e) {
            // Captura erros automáticos da Entity (ex: formato de email inválido)
            response.put("status", "erro");
            response.put("mensagem", e.getConstraintViolations().iterator().next().getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    // ROTA: Login de usuários com verificação de Hash
    @PostMapping("/login.php")
    public ResponseEntity<Map<String, Object>> login(
            @RequestParam String email,
            @RequestParam String senha) {

        Map<String, Object> response = new HashMap<>();
        Optional<Usuario> usuarioOp = repository.findByEmail(email);

        // BCRYPT MATCHES: Compara a senha digitada com o Hash do banco
        if (usuarioOp.isEmpty() || !passwordEncoder.matches(senha, usuarioOp.get().getSenha())) {
            response.put("status", "erro");
            response.put("mensagem", "E-mail ou palavra-passe incorretos.");
            return ResponseEntity.badRequest().body(response);
        }

        Usuario u = usuarioOp.get();
        Map<String, Object> usuarioFormatado = new HashMap<>();
        usuarioFormatado.put("id", u.getId());
        usuarioFormatado.put("nome", u.getNome());
        usuarioFormatado.put("email", u.getEmail());
        usuarioFormatado.put("tipo", u.getTipo());
        usuarioFormatado.put("profileImage", u.getProfileImage());

        // Se for empresa, monta o objeto de detalhes exigido pelo script2.js
        if ("empresa".equals(u.getTipo())) {
            Map<String, Object> companyDetails = new HashMap<>();
            companyDetails.put("name", u.getNome());
            companyDetails.put("location", u.getLocalizacao());
            companyDetails.put("cnpj", u.getCnpj());
            companyDetails.put("category", u.getCategoria());
            companyDetails.put("description", u.getDescricao());
            companyDetails.put("views", 0);
            companyDetails.put("images", new java.util.ArrayList<>());

            usuarioFormatado.put("companyDetails", companyDetails);
        }

        response.put("status", "sucesso");
        response.put("mensagem", "Sessão iniciada com sucesso.");
        response.put("usuario", usuarioFormatado);

        return ResponseEntity.ok(response);
    }
}