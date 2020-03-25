package com.sweteamdragon.raisedhandsserver.auth.service;

import com.sweteamdragon.raisedhandsserver.auth.dto.RegisterRequestDto;
import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface AccountService extends UserDetailsService {

    Account signUp(RegisterRequestDto registerRequestDto);

    void save(Account account);

    Account findByEmail(String email);

    UserDetails loadUserByUsername(String email);

    UserDetails loadUserByEmail(String email);
}
