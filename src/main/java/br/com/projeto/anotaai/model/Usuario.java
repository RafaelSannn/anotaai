package br.com.projeto.anotaai.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome não pode estar vazio.")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Formato de e-mail inválido.")
    @Column(nullable = false, unique = true) // Garante que não existam emails repetidos
    private String email;

    @NotBlank(message = "A palavra-passe é obrigatória.")
    @Size(min = 6, message = "A palavra-passe deve ter pelo menos 6 caracteres.")
    @Column(nullable = false)
    private String senha;

    @NotBlank(message = "O tipo de usuário é obrigatório.")
    private String tipo;

    private String localizacao;

    @Column(unique = true) // Bloqueia CNPJs duplicados no banco de dados
    private String cnpj;

    private String categoria;

    @Column(columnDefinition = "TEXT") // 64KB é suficiente para uma descrição de texto
    private String descricao;

    @Column(columnDefinition = "LONGTEXT") // Essencial para fotos em Base64 não serem truncadas
    private String profileImage;
}