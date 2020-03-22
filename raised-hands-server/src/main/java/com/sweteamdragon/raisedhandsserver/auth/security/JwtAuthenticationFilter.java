package com.sweteamdragon.raisedhandsserver.auth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweteamdragon.raisedhandsserver.auth.dto.AuthResponseDto;
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
                        creds.getPassword()
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
        Date expireTime = new Date(System.currentTimeMillis() + securityProperties.getExpirationTime());
        Account account = (Account) auth.getPrincipal();

        String token = JWT.create()
                .withSubject(account.getEmail())
                .withArrayClaim("authorities", account.getAuthorities().stream().toArray(String[]::new))
                .withExpiresAt(expireTime)
                .sign(Algorithm.HMAC512(securityProperties.getSecretKey()));
        response.addHeader(
                securityProperties.getHeaderString(),
                securityProperties.formatTokenWithPrefix(token)
        );
        response.addHeader(
                "Content-type",
                "application/json"
        );
        AuthResponseDto authResponseDto = new AuthResponseDto(account, token);
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.writeValue(response.getWriter(), authResponseDto);
        chain.doFilter(request, response);
    }
}
