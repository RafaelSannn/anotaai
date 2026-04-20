package br.com.projeto.anotaai.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "mensagens")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long remetenteId;

    private String remetenteNome;

    @Column(nullable = false)
    private Long destinatarioId;

    @Column(columnDefinition = "TEXT")
    private String conteudo;

    @Column(columnDefinition = "LONGTEXT")
    private String imagemBase64;

    private LocalDateTime timestamp = LocalDateTime.now();
}