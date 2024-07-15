package com.ebanx.balanceapi.service;

import com.ebanx.balanceapi.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventService {

    // Serviço de contas sendo injetado
    private final AccountService accountService;

    @Autowired
    public EventService(AccountService accountService) {
        this.accountService = accountService;
    }

    // Método principal para tratar eventos de diferentes tipos
    public String handleEvent(String type, String origin, String destination, int amount) {
        switch (type) {
            case "deposit":
                //chama o metodo para lidar com depósitos
                return handleDeposit(destination, amount);
            case "withdraw":
                //Chama o métod para lidar com retiradas
                return handleWithdraw(origin, amount);
            case "transfer":
                //Chama o método para lidar com transferencias
                return handleTransfer(origin, destination, amount);
            default:
                //tipo de evento inválido
                return "Invalid event type";
        }
    }

    // Método para tratar depósitos
    private String handleDeposit(String destination, int amount) {
        Account account = accountService.getAccount(destination);
        if (account == null) {
            //cria a conta se não existir e adiciona o valor
            accountService.createAccount(destination, amount);
            account = accountService.getAccount(destination);
        } else {
            // Atualiza o saldo da conta existente
            accountService.updateAccountBalance(destination, amount);
        }
        // Retorna a nova informação da conta
        return "{\"destination\": {\"id\":\"" + account.getId() + "\", \"balance\":" + account.getBalance() + "}}";
    }

    // Método para tratar retiradas
    private String handleWithdraw(String origin, int amount) {
        Account account = accountService.getAccount(origin);
        if (account == null || account.getBalance() < amount) {
            // Retorna 404 se a conta não existir ou saldo insuficiente
            return "404 0";
        } else {
            // retira o valor do saldo da conta
            accountService.updateAccountBalance(origin, -amount);
            // Retorna a nova informação da conta
            return "{\"origin\": {\"id\":\"" + account.getId() + "\", \"balance\":" + account.getBalance() + "}}";
        }
    }

    // Método para tratar transferências
    private String handleTransfer(String origin, String destination, int amount) {
        Account originAccount = accountService.getAccount(origin);
        if (originAccount == null || originAccount.getBalance() < amount) {
            // Retorna 404 se a conta de origem não existir ou saldo insuficiente
            return "404 0";
        } else {
            // retira o valor do saldo da conta de origem
            accountService.updateAccountBalance(origin, -amount);
            // Reutiliza a lógica de depósito para adicionar o valor à conta de destino
            handleDeposit(destination, amount);  // Reuse the handleDeposit logic
            Account destinationAccount = accountService.getAccount(destination);
            // Retorna as novas informações das contas de origem e destino
            return "{\"origin\": {\"id\":\"" + originAccount.getId() + "\", \"balance\":" + originAccount.getBalance() + "}, " +
                    "\"destination\": {\"id\":\"" + destinationAccount.getId() + "\", \"balance\":" + destinationAccount.getBalance() + "}}";
        }
    }
}
