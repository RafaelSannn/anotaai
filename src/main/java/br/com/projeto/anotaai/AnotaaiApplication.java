package br.com.projeto.anotaai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Ponto de entrada da aplicação Anotaai.
 *
 * @SpringBootApplication ativa três comportamentos do Spring Boot:
 * - @Configuration: esta classe pode declarar beans.
 * - @EnableAutoConfiguration: configura automaticamente datasource, JPA, Security, etc.
 * - @ComponentScan: varre o pacote atual e subpacotes em busca de @Component,
 *   @Service, @Repository, @Controller e @RestController.
 *
 * Para iniciar a aplicação:
 *   ./mvnw spring-boot:run   (requer MariaDB/MySQL na porta 3306)
 */
@SpringBootApplication
public class AnotaaiApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnotaaiApplication.class, args);
    }
}
