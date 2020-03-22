package com.sweteamdragon.raisedhandsserver.auth.security;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.config.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    @Autowired
    SecurityProperties securityProperties;

    public String createToken(Account account, ) {

    }

    public String formatTokenWithPrefix(String token) {
        return String.format("%s %s", securityProperties.getTokenPrefix(), token);
    }

    public String extractToken(String token) {
        return token.replace(securityProperties.getTokenPrefix() + " ", "");
    }
}
