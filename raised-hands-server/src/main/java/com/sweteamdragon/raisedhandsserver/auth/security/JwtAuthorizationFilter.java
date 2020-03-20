package com.sweteamdragon.raisedhandsserver.auth.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sweteamdragon.raisedhandsserver.config.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    SecurityProperties securityProperties;

    public JwtAuthorizationFilter(AuthenticationManager auth, SecurityProperties securityProperties) {
        super(auth);
        this.securityProperties = securityProperties;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(securityProperties.getHeaderString());

        if (header == null || !header.startsWith(securityProperties.getTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = this.getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(securityProperties.getHeaderString()).replace(securityProperties.getTokenPrefix(), "");

        if (token != null) {
            String user = JWT.require(Algorithm.HMAC512(securityProperties.getSecretKey()))
                    .build()
                    .verify(token)
                    .getSubject();

            if (user != null) {
                return new UsernamePasswordAuthenticationToken(user, null);
            }
            return null;
        }
        return null;
    }
}
