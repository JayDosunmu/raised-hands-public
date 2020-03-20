package com.sweteamdragon.raisedhandsserver.auth.service;

import com.sweteamdragon.raisedhandsserver.auth.dto.RegisterRequestDto;
import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.auth.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements AccountServiceInterface {

    private final String template = "User with email %s not found";

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public Account signup(RegisterRequestDto registerRequestDto) {
        if (!registerRequestDto.getPassword().equals(registerRequestDto.getConfirmPassword())) {
        }
        Account account = new Account(
                registerRequestDto.getEmail(),
                passwordEncoder.encode(registerRequestDto.getPassword())
        );
        this.save(account);
        return account;
    }

    @Override
    public void save(Account account) {
        accountRepository.save(account);
    }

    @Override
    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        try {
            return this.findByEmail(email);
        } catch(IllegalArgumentException e) {
            throw new UsernameNotFoundException(String.format(template, email));
        }
    }

    @Override
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        return this.loadUserByUsername(email);
    }
}
