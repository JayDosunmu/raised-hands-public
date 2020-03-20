package com.sweteamdragon.raisedhandsserver.auth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.config.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, SecurityProperties securityProperties) {
        this.authenticationManager = authenticationManager;
        this.securityProperties = securityProperties;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Account creds = new ObjectMapper().readValue(request.getInputStream(), Account.class);

            return authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword(),
                        creds.getAuthorities()
                )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {
        Long expireDiff = securityProperties.getExpirationTime();
        Date expireTime = new Date(System.currentTimeMillis() + expireDiff);
        String token = JWT.create()
                .withSubject(((Account) auth.getPrincipal()).getEmail())
                .withExpiresAt(expireTime)
                .sign(Algorithm.HMAC512(securityProperties.getSecretKey()));
        response.addHeader(
                securityProperties.getHeaderString(),
                securityProperties.getTokenPrefix() + token
        );
    }
}
