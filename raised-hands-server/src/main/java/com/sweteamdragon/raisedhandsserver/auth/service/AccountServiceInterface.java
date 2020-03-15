package com.sweteamdragon.raisedhandsserver.auth.service;

import com.sweteamdragon.raisedhandsserver.auth.dto.RegisterRequestDto;
import com.sweteamdragon.raisedhandsserver.auth.model.Account;

public interface AccountServiceInterface {

    Account signup(RegisterRequestDto registerRequestDto);

    void save(Account account);

    Account findByEmail(String email);
}
