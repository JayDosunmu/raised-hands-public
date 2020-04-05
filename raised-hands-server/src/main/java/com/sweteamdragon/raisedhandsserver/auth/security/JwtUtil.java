package com.sweteamdragon.raisedhandsserver.auth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.config.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {

    @Autowired
    SecurityProperties securityProperties;

    public String createToken(Account account) {
        return JWT.create()
                .withSubject(account.getEmail())
                .withArrayClaim("authorities", account.getAuthorities().stream().map(auth -> auth.getAuthority()).toArray(String[]::new))
                .withExpiresAt(new Date(System.currentTimeMillis() + securityProperties.getExpirationTime()))
                .sign(Algorithm.HMAC512(securityProperties.getSecretKey()));
    }

    public String extractToken(String token) {
        return token.replace(securityProperties.getTokenPrefix() + " ", "");
    }

    public DecodedJWT decodeJwt(String token) {
        return JWT.require(Algorithm.HMAC512(securityProperties.getSecretKey()))
                .build()
                .verify(token);
    }

    public String formatTokenWithPrefix(String token) {
        return String.format("%s %s", securityProperties.getTokenPrefix(), token);
    }

    public String getHeaderString() {
        return securityProperties.getHeaderString();
    }

}
