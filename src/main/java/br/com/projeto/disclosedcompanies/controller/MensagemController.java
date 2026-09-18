package br.com.projeto.disclosedcompanies.controller;

import br.com.projeto.disclosedcompanies.model.Mensagem;
import br.com.projeto.disclosedcompanies.service.MensagemService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller REST para o sistema de chat direto entre usuários.
 *
 * Mapeado em "/api/mensagens". Todas as operações recebem e devolvem JSON.
 * Delega ao {@link MensagemService} sem lógica adicional.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mensagens")
public class MensagemController {

    private final MensagemService service;

    /**
     * POST /api/mensagens
     * Envia uma mensagem de um usuário para outro.
     * O body JSON deve conter remetenteId, remetenteNome, destinatarioId,
     * conteudo e opcionalmente imagemBase64.
     * O timestamp é preenchido automaticamente pelo @PrePersist.
     */
    @PostMapping
    public Mensagem enviar(@RequestBody Mensagem msg) {
        return service.enviar(msg);
    }

    /**
     * GET /api/mensagens/conversa/{id1}/{id2}
     * Retorna o histórico completo de conversa entre dois usuários, ordenado por data.
     * Inclui mensagens nas duas direções (id1→id2 e id2→id1).
     */
    @GetMapping("/conversa/{id1}/{id2}")
    public List<Mensagem> carregarConversa(@PathVariable Long id1, @PathVariable Long id2) {
        return service.carregarConversa(id1, id2);
    }

    /**
     * GET /api/mensagens/usuario/{id}
     * Retorna todas as mensagens onde o usuário participou (enviadas ou recebidas).
     * Usado para montar a lista lateral de chats ativos no dashboard.
     */
    @GetMapping("/usuario/{id}")
    public List<Mensagem> listarChatsDoUsuario(@PathVariable Long id) {
        return service.listarChatsDoUsuario(id);
    }
}
