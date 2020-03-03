package com.sweteamdragon.raisedhandsserver.auth.services;

import com.sweteamdragon.raisedhandsserver.auth.models.Account;
import com.sweteamdragon.raisedhandsserver.auth.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements AccountServiceInterface {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }
}
