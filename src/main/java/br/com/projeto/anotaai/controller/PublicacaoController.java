package br.com.projeto.anotaai.controller;

import br.com.projeto.anotaai.model.Publicacao;
import br.com.projeto.anotaai.repository.PublicacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/publicacoes")
public class PublicacaoController {

    @Autowired
    private PublicacaoRepository repository;

    // CREATE (Integridade: add)
    @PostMapping
    public Publicacao criar(@RequestBody Publicacao pub) {
        return repository.save(pub);
    }

    // READ (Integridade: read)
    @GetMapping("/usuario/{id}")
    public List<Publicacao> listarPorUsuario(@PathVariable Long id) {
        return repository.findByUsuarioId(id);
    }

    @GetMapping("/tipo/{tipo}")
    public List<Publicacao> listarPorTipo(@PathVariable String tipo) {
        return repository.findByTipoAutor(tipo);
    }

    // DELETE (Integridade: exc)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        repository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}