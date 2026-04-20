package br.com.projeto.anotaai.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "publicacoes")
public class Publicacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @Column(columnDefinition = "LONGTEXT") // Para suportar imagens em Base64
    private String imagens;

    private String tipoAutor; // "visitante" ou "empresa"
    private String nomeAutor;
    private Long usuarioId; // Relaciona com o ID do Usuario

    private LocalDateTime dataCriacao = LocalDateTime.now();
}