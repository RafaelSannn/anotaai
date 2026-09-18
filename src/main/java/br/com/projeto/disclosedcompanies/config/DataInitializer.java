package br.com.projeto.disclosedcompanies.config;

import br.com.projeto.disclosedcompanies.model.Usuario;
import br.com.projeto.disclosedcompanies.model.Publicacao;
import br.com.projeto.disclosedcompanies.repository.UsuarioRepository;
import br.com.projeto.disclosedcompanies.repository.PublicacaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuração para popular o banco H2 com dados de demonstração.
 *
 * IMPORTANTE: Esta configuração executa APENAS no profile 'dev'
 * - No modo DEV (H2): carrega dados de demonstração automaticamente
 * - No modo PROD (MariaDB): não carrega dados, banco inicia vazio
 *
 * Executa automaticamente ao iniciar a aplicação e cria:
 * - 3 visitantes de exemplo
 * - 5 empresas de categorias diferentes
 * - 4 publicações de serviços
 *
 * Útil para demos e apresentações acadêmicas.
 */
@Slf4j
@Configuration
@Profile("dev") // 🔥 EXECUTA APENAS NO PERFIL DEV
@RequiredArgsConstructor
public class DataInitializer {

    private final PasswordEncoder passwordEncoder;

    @Bean
    CommandLineRunner initDatabase(UsuarioRepository usuarioRepo, PublicacaoRepository publicacaoRepo) {
        return args -> {
            log.info("🚀 Inicializando dados de demonstração...");

            // Senha padrão para todos: "123456"
            String senhaHash = passwordEncoder.encode("123456");

            // ==========================================
            // VISITANTES
            // ==========================================
            Usuario visitante1 = criarVisitante("João Silva", "joao@email.com", senhaHash);
            Usuario visitante2 = criarVisitante("Maria Santos", "maria@email.com", senhaHash);
            Usuario visitante3 = criarVisitante("Pedro Costa", "pedro@email.com", senhaHash);

            usuarioRepo.save(visitante1);
            usuarioRepo.save(visitante2);
            usuarioRepo.save(visitante3);

            // ==========================================
            // EMPRESAS
            // ==========================================
            Usuario empresa1 = criarEmpresa(
                "TechFix Informática", 
                "techfix@empresa.com", 
                senhaHash,
                "São Paulo, SP",
                "12345678000190",
                "Informática",
                "Especializada em manutenção de computadores, redes e suporte técnico. Atendemos residências e empresas."
            );

            Usuario empresa2 = criarEmpresa(
                "Eletro Master", 
                "eletro@empresa.com", 
                senhaHash,
                "Rio de Janeiro, RJ",
                "98765432000110",
                "Elétrica",
                "Instalações elétricas residenciais e comerciais. Emergências 24h."
            );

            Usuario empresa3 = criarEmpresa(
                "Obra & Arte Reformas", 
                "obra@empresa.com", 
                senhaHash,
                "Belo Horizonte, MG",
                "11122233000145",
                "Reformas",
                "Reformas completas, pintura, gesso e acabamentos. Projetos personalizados."
            );

            Usuario empresa4 = criarEmpresa(
                "Verde Vida Jardinagem", 
                "verde@empresa.com", 
                senhaHash,
                "Curitiba, PR",
                "44455566000178",
                "Jardinagem",
                "Manutenção de jardins, paisagismo e poda de árvores."
            );

            Usuario empresa5 = criarEmpresa(
                "Clean Pro Limpeza", 
                "clean@empresa.com", 
                senhaHash,
                "Porto Alegre, RS",
                "77788899000123",
                "Limpeza",
                "Limpeza residencial, comercial e pós-obra. Equipe treinada e produtos ecológicos."
            );

            usuarioRepo.save(empresa1);
            usuarioRepo.save(empresa2);
            usuarioRepo.save(empresa3);
            usuarioRepo.save(empresa4);
            usuarioRepo.save(empresa5);

            // ==========================================
            // PUBLICAÇÕES DE VISITANTES
            // ==========================================
            Publicacao pub1 = criarPublicacao(
                "Preciso de Eletricista Urgente",
                "Tomada queimou e disjuntor caindo. Preciso de atendimento hoje se possível.",
                "visitante",
                "João Silva",
                visitante1.getId()
            );

            Publicacao pub2 = criarPublicacao(
                "Reforma de Banheiro",
                "Gostaria de orçamentos para reforma completa de banheiro (5m²). Troca de piso, azulejos e louças.",
                "visitante",
                "Maria Santos",
                visitante2.getId()
            );

            // ==========================================
            // PUBLICAÇÕES DE EMPRESAS
            // ==========================================
            Publicacao pub3 = criarPublicacao(
                "Promoção: Manutenção de PC - 20% OFF",
                "Limpeza completa, troca de pasta térmica e otimização. De R$ 150 por R$ 120 até final do mês!",
                "empresa",
                "TechFix Informática",
                empresa1.getId()
            );

            Publicacao pub4 = criarPublicacao(
                "Serviço de Jardinagem Mensal",
                "Pacotes mensais de manutenção a partir de R$ 200. Inclui poda, adubação e limpeza.",
                "empresa",
                "Verde Vida Jardinagem",
                empresa4.getId()
            );

            publicacaoRepo.save(pub1);
            publicacaoRepo.save(pub2);
            publicacaoRepo.save(pub3);
            publicacaoRepo.save(pub4);

            log.info("✅ Dados de demonstração carregados com sucesso!");
            log.info("📧 Login de teste:");
            log.info("   Visitante: joao@email.com / 123456");
            log.info("   Empresa: techfix@empresa.com / 123456");
            log.info("🌐 Acesse: http://localhost:8080");
            log.info("🗄️  Console H2: http://localhost:8080/h2-console");
        };
    }

    private Usuario criarVisitante(String nome, String email, String senhaHash) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senhaHash);
        usuario.setTipo("visitante");
        return usuario;
    }

    private Usuario criarEmpresa(String nome, String email, String senhaHash, 
                                  String localizacao, String cnpj, String categoria, String descricao) {
        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senhaHash);
        usuario.setTipo("empresa");
        usuario.setLocalizacao(localizacao);
        usuario.setCnpj(cnpj);
        usuario.setCategoria(categoria);
        usuario.setDescricao(descricao);
        return usuario;
    }

    private Publicacao criarPublicacao(String titulo, String descricao, String tipoAutor, 
                                        String nomeAutor, Long usuarioId) {
        Publicacao pub = new Publicacao();
        pub.setTitulo(titulo);
        pub.setDescricao(descricao);
        pub.setTipoAutor(tipoAutor);
        pub.setNomeAutor(nomeAutor);
        pub.setUsuarioId(usuarioId);
        return pub;
    }
}
