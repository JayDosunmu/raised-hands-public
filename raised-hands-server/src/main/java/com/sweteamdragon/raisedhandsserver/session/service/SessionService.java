package com.sweteamdragon.raisedhandsserver.session.service;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.session.model.Session;
import org.springframework.stereotype.Service;

import java.util.Date;

public interface SessionService {

    Session create(Account user, String name, boolean isDistractionFree, Date startDate, Date endDate);
}
