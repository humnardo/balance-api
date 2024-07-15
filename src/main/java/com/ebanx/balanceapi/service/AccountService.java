package com.ebanx.balanceapi.service;

import com.ebanx.balanceapi.model.Account;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

    private final Map<String, Account> accounts = new HashMap<>();

    public Account getAccount(String id) {
        return accounts.get(id);
    }

    public void createAccount(String id, int initialBalance) {
        accounts.put(id, new Account(id, initialBalance));
    }

    public void updateAccountBalance(String id, int amount) {
        Account account = accounts.get(id);
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
        }
    }

    public void reset() {
        accounts.clear();
    }
}
