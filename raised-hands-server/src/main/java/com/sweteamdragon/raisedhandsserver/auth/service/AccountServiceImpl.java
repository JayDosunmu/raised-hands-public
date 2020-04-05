package com.sweteamdragon.raisedhandsserver.auth.service;

import com.sweteamdragon.raisedhandsserver.auth.dto.RegisterRequestDto;
import com.sweteamdragon.raisedhandsserver.auth.exception.UserAlreadyExistAuthenticationException;
import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.auth.repository.AccountRepository;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class AccountServiceImpl implements AccountService {

    private final String template = "User with email %s not found";

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account signUp(RegisterRequestDto registerRequestDto) throws BadCredentialsException, UserAlreadyExistAuthenticationException, DataIntegrityViolationException{
        if (registerRequestDto.getPassword() == null || registerRequestDto.getPassword().length() < 1) {
            throw new BadCredentialsException("Password missing");
        }
        if (registerRequestDto.getConfirmPassword() == null || registerRequestDto.getConfirmPassword().length() < 1) {
            throw new BadCredentialsException("Confirm password missing");
        }
        if (registerRequestDto.getName() == null|| registerRequestDto.getName().length() < 1) {
            throw new BadCredentialsException("Name is missing");
        }
        if (!registerRequestDto.getPassword().equals(registerRequestDto.getConfirmPassword())) {
            throw new BadCredentialsException("Passwords do not match");
        }

        try {
            Account account = new Account(
                    registerRequestDto.getEmail(),
                    passwordEncoder.encode(registerRequestDto.getPassword()),
                    registerRequestDto.getName()
            );
            this.save(account);
            return account;
        } catch (DataIntegrityViolationException e) {
            String constraint = ((ConstraintViolationException) e.getCause()).getConstraintName();

            if (Pattern.compile("uk", Pattern.CASE_INSENSITIVE).matcher(constraint).find()) {
                throw new UserAlreadyExistAuthenticationException("An account with this email already exists");
            }
            throw e;
        }
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
