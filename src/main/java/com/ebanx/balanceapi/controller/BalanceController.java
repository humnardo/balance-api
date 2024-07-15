package com.ebanx.balanceapi.controller;

import com.ebanx.balanceapi.model.Account;
import com.ebanx.balanceapi.service.AccountService;
import com.ebanx.balanceapi.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class BalanceController {

    // Injeção de dependências dos serviços de conta e evento
    private final AccountService accountService;
    private final EventService eventService;

    @Autowired
    public BalanceController(AccountService accountService, EventService eventService) {
        this.accountService = accountService;
        this.eventService = eventService;
    }

    // Endpoint para obter o saldo de uma conta
    @GetMapping("/balance")
    public ResponseEntity<String> getBalance(@RequestParam String account_id) {
        Account account = accountService.getAccount(account_id);
        if (account == null) {
            // Retorna 404 se a conta nao existe
            return ResponseEntity.status(404).body("0");
        } else {
            // Retorna o saldo da conta como string
            return ResponseEntity.ok(String.valueOf(account.getBalance()));
        }
    }

    // Endpoint para tratar eventos de transação
    @PostMapping("/event")
    public ResponseEntity<String> handleEvent(@RequestBody Map<String, Object> payload) {
        // Extrai os dados do payload
        String type = (String) payload.get("type");
        String origin = (String) payload.get("origin");
        String destination = (String) payload.get("destination");
        int amount = (Integer) payload.get("amount");

        // Chama o serviço de eventos para processar o evento
        String response = eventService.handleEvent(type, origin, destination, amount);

        // Verifica se a resposta é um erro 404
        if (response.equals("404 0")) {
            // Retorna 404 se houve algum problema com o evento
            return ResponseEntity.status(404).body("0");
        } else {
            // Retorna 201 se o evento foi processado com sucesso
            return ResponseEntity.status(201).body(response);
        }
    }

    // Endpoint para resetar todos os dados
    @PostMapping("/reset")
    public ResponseEntity<String> reset() {
        // Reseta todas as contas
        accountService.reset();
        // Retorna 200 OK sem corpo
        return ResponseEntity.ok().build();
    }
}
