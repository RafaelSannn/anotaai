package br.com.projeto.anotaai.repository;

import br.com.projeto.anotaai.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);

    // Busca apenas as empresas, ignorando visitantes
    List<Usuario> findByTipo(String tipo);

    // Busca as empresas por uma categoria específica (O filtro do frontend)
    List<Usuario> findByTipoAndCategoria(String tipo, String categoria);

   // Perguntar ao banco: "Ei, já existe esse CNPJ aí?".
    Optional<Usuario> findByCnpj(String cnpj);
}