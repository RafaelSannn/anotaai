package br.com.projeto.anotaai.controller;

import br.com.projeto.anotaai.model.Mensagem;
import br.com.projeto.anotaai.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensagens")
public class MensagemController {

    @Autowired
    private MensagemRepository repository;

    @PostMapping
    public Mensagem enviar(@RequestBody Mensagem msg) {
        return repository.save(msg);
    }

    @GetMapping("/conversa/{id1}/{id2}")
    public List<Mensagem> carregarConversa(@PathVariable Long id1, @PathVariable Long id2) {
        return repository.findConversa(id1, id2);
    }

    @GetMapping("/usuario/{id}")
    public List<Mensagem> listarChatsDoUsuario(@PathVariable Long id) {
        return repository.findByRemetenteIdOrDestinatarioIdOrderByTimestampAsc(id, id);
    }
}