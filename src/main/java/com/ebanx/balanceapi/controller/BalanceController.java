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

    private final AccountService accountService;
    private final EventService eventService;

    @Autowired
    public BalanceController(AccountService accountService, EventService eventService) {
        this.accountService = accountService;
        this.eventService = eventService;
    }

    @GetMapping("/balance")
    public ResponseEntity<String> getBalance(@RequestParam String account_id) {
        Account account = accountService.getAccount(account_id);
        if (account == null) {
            return ResponseEntity.status(404).body("0");
        } else {
            return ResponseEntity.ok(String.valueOf(account.getBalance()));
        }
    }

    @PostMapping("/event")
    public ResponseEntity<String> handleEvent(@RequestBody Map<String, Object> payload) {
        String type = (String) payload.get("type");
        String origin = (String) payload.get("origin");
        String destination = (String) payload.get("destination");
        int amount = (Integer) payload.get("amount");
        String response = eventService.handleEvent(type, origin, destination, amount);
        if (response.equals("404 0")) {
            return ResponseEntity.status(404).body("0");
        } else {
            return ResponseEntity.status(201).body(response);
        }
    }

    @PostMapping("/reset")
    public ResponseEntity<String> reset() {
        accountService.reset();
        return ResponseEntity.ok().build();
    }
}
