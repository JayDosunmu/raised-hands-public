package com.sweteamdragon.raisedhandsserver.auth.security;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.sweteamdragon.raisedhandsserver.config.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    JwtUtil jwtUtil;

    public JwtAuthorizationFilter(AuthenticationManager auth, SecurityProperties securityProperties, JwtUtil jwtUtil) {
        super(auth);
        this.securityProperties = securityProperties;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(securityProperties.getHeaderString());

        if (header == null || !header.startsWith(securityProperties.getTokenPrefix())) {
            chain.doFilter(request, response);
            return;
        }
        String token = jwtUtil.extractToken(header);
        UsernamePasswordAuthenticationToken authentication = this.getAuthentication(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(String token) {
        DecodedJWT jwt = jwtUtil.decodeJwt(token);
        String user = jwt.getSubject();

        GrantedAuthority[] authoritiesFromJwt = jwt.getClaim("authorities").asArray(GrantedAuthority.class);
        Set<GrantedAuthority> authorities = new HashSet<>(Arrays.asList(authoritiesFromJwt));

        if (user != null) {
            return new UsernamePasswordAuthenticationToken(user, null, authorities);
        }
        return null;
    }
}
