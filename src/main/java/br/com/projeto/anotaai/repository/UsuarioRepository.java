package br.com.projeto.anotaai.repository;

import br.com.projeto.anotaai.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * Repositório de acesso a dados para a entidade {@link Usuario}.
 *
 * Estende JpaRepository, que fornece automaticamente as operações básicas de CRUD
 * (save, findById, findAll, deleteById, etc.) sem necessidade de implementação manual.
 *
 * Os métodos de busca abaixo são gerados pelo Spring Data JPA a partir dos nomes
 * dos métodos — nenhuma query SQL precisa ser escrita.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /** Busca um usuário pelo e-mail. Usado na autenticação e na validação de duplicatas. */
    Optional<Usuario> findByEmail(String email);

    /** Retorna todos os usuários do tipo especificado. Usado para listar apenas empresas. */
    List<Usuario> findByTipo(String tipo);

    /** Retorna empresas filtradas por categoria. Alimenta o filtro da tela "Explorar Empresas". */
    List<Usuario> findByTipoAndCategoria(String tipo, String categoria);

    /** Verifica se já existe uma empresa com o CNPJ informado. Evita duplicatas no cadastro. */
    Optional<Usuario> findByCnpj(String cnpj);
}
