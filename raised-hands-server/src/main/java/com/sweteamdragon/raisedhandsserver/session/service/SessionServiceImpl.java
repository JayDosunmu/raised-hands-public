package com.sweteamdragon.raisedhandsserver.session.service;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.session.model.Session;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SessionServiceImpl implements SessionService {

    @Override
    public Session create(Account user, String name, boolean isDistractionFree, Date startDate, Date endDate) {
        if (user == null) {
            throw new IllegalArgumentException("Account to be session leader not found");
        }
        return new Session();
    }
}
