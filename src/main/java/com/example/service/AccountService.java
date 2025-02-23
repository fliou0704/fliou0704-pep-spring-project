package com.example.service;

import com.example.entity.Account;
import com.example.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;

    public Account registerAccount(Account account) {

        // Check that username is not blank
        if (account.getUsername() == null || account.getUsername().length() == 0) {
            return null;
        }

        // Check that password is not less than four characters
        if (account.getPassword() == null || account.getPassword().length() < 4) {
            return null;
        }
        
        // Check that no other user exists with same username
        if (accountRepository.findByUsername(account.getUsername()) != null) {
            return new Account();
        }

        return accountRepository.save(account);
    }

    public Account login(String username, String password) {

        Account account = accountRepository.findByUsername(username);
        
        // Check that user with username exists and that the password matches
        if (account == null || !account.getPassword().equals(password)) {
            return null;
        }
    
        return account;
    }
}
