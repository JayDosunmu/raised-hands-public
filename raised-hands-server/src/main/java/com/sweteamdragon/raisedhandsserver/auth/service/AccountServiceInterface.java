package com.sweteamdragon.raisedhandsserver.auth.services;

import com.sweteamdragon.raisedhandsserver.auth.models.Account;

public interface AccountServiceInterface {

    Account signup(Account account);

    void save(Account account);

    Account findByEmail(String email);
}
