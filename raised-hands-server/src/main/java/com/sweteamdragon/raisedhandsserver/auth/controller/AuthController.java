package com.sweteamdragon.raisedhandsserver.auth.controller;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.auth.dto.RegisterRequestDto;
import com.sweteamdragon.raisedhandsserver.auth.dto.RegisterResponseDto;
import com.sweteamdragon.raisedhandsserver.auth.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AccountService accountService;

    @PostMapping("/register")
    public RegisterResponseDto register(@RequestBody RegisterRequestDto registerRequestDto, Authentication authentication) throws ResponseStatusException {
        Account account;
        try {
            account = accountService.signup(registerRequestDto);
        } catch(Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }
        return RegisterResponseDto.from(account);
    }

}
