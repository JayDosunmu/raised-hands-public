package com.sweteamdragon.raisedhandsserver.auth.services;

import com.sweteamdragon.raisedhandsserver.auth.models.Account;

public interface AccountServiceInterface {

    void save(Account account);

    Account findByEmail(String email);
}
