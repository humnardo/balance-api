package com.ebanx.balanceapi.service;

import com.ebanx.balanceapi.model.Account;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AccountService {

    // Mapeamento das contas, onde a chave é o ID da conta e o valor é a instância da conta.
    private final Map<String, Account> accounts = new HashMap<>();

    // Obtém uma conta pelo ID. Retorna null se a conta não existir.
    public Account getAccount(String id) {
        return accounts.get(id);
    }

    // Cria uma nova conta com o ID fornecido e o saldo inicial.
    public void createAccount(String id, int initialBalance) {
        accounts.put(id, new Account(id, initialBalance));
    }

    // Atualiza o saldo de uma conta existente adicionando o valor fornecido.
    // Se a conta não existir, não faz nada.
    public void updateAccountBalance(String id, int amount) {
        Account account = accounts.get(id);
        if (account != null) {
            // Atualiza o saldo.
            account.setBalance(account.getBalance() + amount);
        }
    }

    // Reseta o serviço, removendo todas as contas que existem.
    public void reset() {
        accounts.clear();
    }
}
