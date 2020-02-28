package com.sweteamdragon.raisedhandsserver.auth;

import com.sweteamdragon.raisedhandsserver.auth.models.RegisterRequestModel;
import com.sweteamdragon.raisedhandsserver.auth.models.RegisterResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class AuthController {

    private final static String template = "%s";
    private final AtomicLong counter = new AtomicLong();

    @PostMapping("/register")
    public RegisterResponseModel register(@RequestBody RegisterRequestModel registerRequestModel) throws Exception {
        if (registerRequestModel.getPassword().equals("")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        return new RegisterResponseModel(counter.getAndIncrement(), String.format(template, registerRequestModel.getEmail()));
    }

}
