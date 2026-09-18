package br.com.projeto.disclosedcompanies.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuração de segurança da aplicação via Spring Security.
 *
 * Esta classe define duas coisas principais:
 * 1. As regras de acesso HTTP (quem pode acessar o quê)
 * 2. O algoritmo de hash de senha (BCrypt)
 *
 * A autenticação é feita manualmente nos controllers (não usa o mecanismo
 * de login do Spring Security), portanto todas as rotas são públicas e
 * o CSRF está desabilitado para permitir chamadas via fetch/AJAX do frontend.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Define a cadeia de filtros de segurança HTTP.
     *
     * - CSRF desabilitado: necessário para que o script2.js possa fazer
     *   requisições POST/PUT sem token CSRF (padrão de SPAs e clientes REST).
     * - anyRequest().permitAll(): todas as rotas são públicas, pois a
     *   autenticação é controlada pelo próprio frontend via localStorage.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
        return http.build();
    }

    /**
     * Registra o BCryptPasswordEncoder como bean disponível para injeção.
     *
     * BCrypt aplica um fator de custo adaptável (padrão: 10 rounds), tornando
     * ataques de força bruta impraticáveis mesmo com hardware moderno.
     * Usado em UsuarioServiceImpl para codificar senhas no cadastro
     * e compará-las no login.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
