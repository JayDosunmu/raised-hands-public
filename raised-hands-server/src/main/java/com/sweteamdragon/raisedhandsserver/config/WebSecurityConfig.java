package com.sweteamdragon.raisedhandsserver.config;

import com.sweteamdragon.raisedhandsserver.auth.security.JwtAuthenticationFilter;
import com.sweteamdragon.raisedhandsserver.auth.security.JwtAuthorizationFilter;
import com.sweteamdragon.raisedhandsserver.auth.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountService accountService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    SecurityProperties securityProperties;

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .cors().and().csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST,"/auth/register").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilter(jwtAuthenticationFilter())
            .addFilter(new JwtAuthorizationFilter(authenticationManager(), securityProperties))
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(accountService).passwordEncoder(passwordEncoder);
    }

    private JwtAuthenticationFilter jwtAuthenticationFilter() throws Exception{
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter(authenticationManager(), securityProperties);
        jwtAuthenticationFilter.setFilterProcessesUrl("/auth/login");
        return jwtAuthenticationFilter;
    }
}
