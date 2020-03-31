package com.sweteamdragon.raisedhandsserver.config;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@Configuration
public class TestConfiguration {

    @Bean
    public UserDetailsService testUserDetailsService() {
        GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");
        UserDetails userDetails = new Account("test@email.com", "ram123", "test");
        return new InMemoryUserDetailsManager(Arrays.asList(userDetails));
    }
}
