package com.sweteamdragon.raisedhandsserver.auth.controllers;

import com.sweteamdragon.raisedhandsserver.auth.models.Account;
import com.sweteamdragon.raisedhandsserver.auth.models.RegisterRequestModel;
import com.sweteamdragon.raisedhandsserver.auth.models.RegisterResponseModel;
import com.sweteamdragon.raisedhandsserver.auth.repositories.AccountRepository;
import com.sweteamdragon.raisedhandsserver.auth.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class AuthController {

    private final static String template = "%s";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    AccountService accountService;

    @PostMapping("/register")
    public RegisterResponseModel register(@RequestBody RegisterRequestModel registerRequestModel) throws ResponseStatusException {
        if (!registerRequestModel.getPassword().equals(registerRequestModel.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }
        accountService.save(new Account(
                registerRequestModel.getEmail(),
                registerRequestModel.getPassword()
        ));
        return new RegisterResponseModel(counter.getAndIncrement(), String.format(template, registerRequestModel.getEmail()));
    }

}
