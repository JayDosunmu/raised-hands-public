package com.sweteamdragon.raisedhandsserver.util;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class TestAuth {

    public static Account getAccountFromContext() {
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return new Account(
                principal.getUsername(),
                principal.getPassword(),
                "test"
        );
    }
}
