package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }


    public Optional<Account> getAccountById(int id) {
        return accountRepository.findById(id);
    }


    public ResponseEntity<Void> createAccount(Account account) {
        if(account.getUsername().length() == 0 || account.getPassword().length() < 4) return ResponseEntity.status(400).build();
        for(Account a : getAllAccounts()){
            if(a.getUsername().equals(account.getUsername())) return ResponseEntity.status(409).build();
        }
        accountRepository.save(account);
        return ResponseEntity.status(200).build();
    }

    public Account loginAccount(Account account) {
        for(Account a : getAllAccounts()){
            if(a.getUsername().equals(account.getUsername()) && a.getPassword().equals(account.getPassword())) return a;
        }
        return null;
    }
}
