package com.ebanx.balanceapi.service;

import com.ebanx.balanceapi.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EventService {

    private final AccountService accountService;

    @Autowired
    public EventService(AccountService accountService) {
        this.accountService = accountService;
    }

    public String handleEvent(String type, String origin, String destination, int amount) {
        switch (type) {
            case "deposit":
                return handleDeposit(destination, amount);
            case "withdraw":
                return handleWithdraw(origin, amount);
            case "transfer":
                return handleTransfer(origin, destination, amount);
            default:
                return "Invalid event type";
        }
    }

    private String handleDeposit(String destination, int amount) {
        Account account = accountService.getAccount(destination);
        if (account == null) {
            accountService.createAccount(destination, amount);
            account = accountService.getAccount(destination);
        } else {
            accountService.updateAccountBalance(destination, amount);
        }
        return "{\"destination\": {\"id\":\"" + account.getId() + "\", \"balance\":" + account.getBalance() + "}}";
    }

    private String handleWithdraw(String origin, int amount) {
        Account account = accountService.getAccount(origin);
        if (account == null || account.getBalance() < amount) {
            return "404 0";
        } else {
            accountService.updateAccountBalance(origin, -amount);
            return "{\"origin\": {\"id\":\"" + account.getId() + "\", \"balance\":" + account.getBalance() + "}}";
        }
    }

    private String handleTransfer(String origin, String destination, int amount) {
        Account originAccount = accountService.getAccount(origin);
        if (originAccount == null || originAccount.getBalance() < amount) {
            return "404 0";
        } else {
            accountService.updateAccountBalance(origin, -amount);
            handleDeposit(destination, amount);  // Reuse the handleDeposit logic
            Account destinationAccount = accountService.getAccount(destination);
            return "{\"origin\": {\"id\":\"" + originAccount.getId() + "\", \"balance\":" + originAccount.getBalance() + "}, " +
                    "\"destination\": {\"id\":\"" + destinationAccount.getId() + "\", \"balance\":" + destinationAccount.getBalance() + "}}";
        }
    }
}
