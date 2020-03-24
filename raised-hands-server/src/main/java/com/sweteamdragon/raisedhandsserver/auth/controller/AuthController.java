package com.sweteamdragon.raisedhandsserver.auth.controller;

import com.sweteamdragon.raisedhandsserver.auth.dto.AuthResponseDto;
import com.sweteamdragon.raisedhandsserver.auth.dto.RegisterRequestDto;
import com.sweteamdragon.raisedhandsserver.auth.exception.UserAlreadyExistAuthenticationException;
import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.auth.security.JwtUtil;
import com.sweteamdragon.raisedhandsserver.auth.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
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

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/register")
    public AuthResponseDto register(@RequestBody RegisterRequestDto registerRequestDto, Authentication authentication) throws ResponseStatusException {
        Account account;
        String token;
        try {
            account = accountService.signup(registerRequestDto);
            token = jwtUtil.createToken(account);
        } catch(BadCredentialsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (UserAlreadyExistAuthenticationException | DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return new AuthResponseDto(account, token);
    }

}
