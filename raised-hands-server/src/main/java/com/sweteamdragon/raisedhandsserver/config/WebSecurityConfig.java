package com.sweteamdragon.raisedhandsserver.config;

import com.sweteamdragon.raisedhandsserver.auth.security.JwtAuthenticationFilter;
import com.sweteamdragon.raisedhandsserver.auth.security.JwtAuthorizationFilter;
import com.sweteamdragon.raisedhandsserver.auth.security.JwtUtil;
import com.sweteamdragon.raisedhandsserver.auth.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Component
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    SecurityProperties securityProperties;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .cors()
                .and()
            .csrf()
                .disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .authorizeRequests()
                .antMatchers("/auth/register", "/auth/login", "/connect")
                    .permitAll()
                .anyRequest()
                    .authenticated()
                .and()
            .addFilter(jwtAuthenticationFilter())
            .addFilter(jwtAuthorizationFilter());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://raisedhands.io", "http://localhost"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), securityProperties, jwtUtil);
        jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
        return jwtAuthenticationFilter;
    }

    private  JwtAuthorizationFilter jwtAuthorizationFilter() throws Exception {
        return new JwtAuthorizationFilter(authenticationManager(), securityProperties, jwtUtil);
    }
}
