package com.sweteamdragon.raisedhandsserver.auth.security;


import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
    Authentication getAuthentication();
}
