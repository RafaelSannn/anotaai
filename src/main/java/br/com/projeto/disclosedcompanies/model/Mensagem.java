package br.com.projeto.disclosedcompanies.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * Entidade JPA que representa uma mensagem trocada no chat direto entre usuários.
 *
 * O chat é point-to-point: cada mensagem tem um remetente e um destinatário.
 * O histórico da conversa entre dois usuários é obtido combinando ambas as direções
 * (remetente→destinatário e destinatário→remetente) via query JPQL no repositório.
 */
@Data
@Entity
@Table(name = "mensagens")
public class Mensagem {

    /** Identificador único gerado automaticamente pelo banco. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** ID do usuário que enviou a mensagem. */
    @Column(nullable = false)
    private Long remetenteId;

    /** Nome do remetente no momento do envio (desnormalizado para evitar JOIN ao exibir o chat). */
    private String remetenteNome;

    /** ID do usuário que deve receber a mensagem. */
    @Column(nullable = false)
    private Long destinatarioId;

    /** Texto da mensagem. Pode ser null se a mensagem for só de imagem. */
    @Column(columnDefinition = "TEXT")
    private String conteudo;

    /**
     * Imagem enviada no chat, codificada em Base64.
     * LONGTEXT suporta imagens de até ~16 MB.
     */
    @Column(columnDefinition = "LONGTEXT")
    private String imagemBase64;

    /** Momento do envio, preenchido automaticamente na persistência. */
    private LocalDateTime timestamp;

    /**
     * Define o timestamp no momento em que a mensagem é persistida pela primeira vez.
     * Garante que o valor seja o instante real da gravação no banco.
     */
    @PrePersist
    protected void prePersist() {
        timestamp = LocalDateTime.now();
    }
}
