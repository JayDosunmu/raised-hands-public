package com.sweteamdragon.raisedhandsserver.session.service;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.session.model.Session;
import com.sweteamdragon.raisedhandsserver.session.model.SessionParticipant;
import com.sweteamdragon.raisedhandsserver.session.repository.SessionParticipantRepository;
import com.sweteamdragon.raisedhandsserver.session.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    SessionParticipantRepository sessionParticipantRepository;

    @Override
    public List<Session> getSessionsByUser(Account account) {
        return sessionRepository.findByAccount(account);
    }

    @Override
    public Session create(Account user, String name, boolean distractionFree, Date startDate, Date endDate) {
        if (user == null) {
            throw new IllegalArgumentException("User to set as session leader not found");
        }
        if (startDate != null && endDate != null && endDate.compareTo(startDate) < 0) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        SessionParticipant leader = new SessionParticipant(user, true);

        Session session = new Session(name, distractionFree, startDate, endDate);
        session.setLeader(leader);

        sessionRepository.save(session);

        return session;
    }
}
