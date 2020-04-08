package com.sweteamdragon.raisedhandsserver.session.service;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.session.dto.SessionMessagingMetadataDto;
import com.sweteamdragon.raisedhandsserver.session.dto.SessionResponseDto;
import com.sweteamdragon.raisedhandsserver.session.model.Session;
import com.sweteamdragon.raisedhandsserver.session.model.SessionParticipant;
import com.sweteamdragon.raisedhandsserver.session.repository.SessionParticipantRepository;
import com.sweteamdragon.raisedhandsserver.session.repository.SessionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    SessionParticipantRepository sessionParticipantRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public Optional<Session> findById(long sessionId) {
        return sessionRepository.findById(sessionId);
    }


    //  TODO: determine better approach to checking if user is a member of this session
    @Override
    public Optional<Session> findByIdSecured(long sessionId, long accountId) {
        SessionParticipant participant = sessionParticipantRepository
                .findBySessionSessionIdAndAccountAccountId(sessionId, accountId)
                .orElse(null);

        return sessionRepository.findById(sessionId)
                .map((session) -> {
                    if (participant == null) {
                        session.setParticipants(null);
                        session.setPasscode(null);
                        session.setJoinId(null);
                    }
                    return session;
                });
    }

    @Override
    public Optional<Session> findByJoinIdSecured(String joinId, long accountId) {
        return Optional.empty();
    }

    @Override
    public List<Session> findAllByAccount(Account account) {
        return sessionRepository.findByAccount(account);
    }

    @Override
    public Session create(Account account, String name, boolean distractionFree, Date startDate, Date endDate) {
        if (account == null) {
            throw new IllegalArgumentException("User to set as session leader not found");
        }
        if (startDate != null && endDate != null && endDate.compareTo(startDate) < 0) {
            throw new IllegalArgumentException("End date must be after start date");
        }

        SessionParticipant leader = new SessionParticipant(account, true);

        Session session = new Session(name, distractionFree, startDate, endDate);
        session.setLeader(leader);

        sessionRepository.save(session);

        return session;
    }

    @Override
    public Map<String, Object> join(String joinId, String passcode, Account account) throws IllegalArgumentException {
        Session session = sessionRepository.findByJoinId(joinId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        SessionParticipant participant = sessionParticipantRepository
                .findBySessionSessionIdAndAccountAccountId(session.getSessionId(), account.getAccountId())
                .orElseGet(() -> new SessionParticipant(account));


        if (!session.equals(participant.getSession())) {
            if (!session.getPasscode().equals(passcode)) {
                throw new IllegalArgumentException("Passcode provided is incorrect for session");
            }
            session.addParticipant(participant);
            sessionRepository.save(session);
        }

        Map<String, Object> sessionData = new HashMap<>();
        sessionData.put("session", session);
        sessionData.put("sessionParticipant", participant);

        return sessionData;
    }

    @Override
    public SessionResponseDto getSessionWithMessagingMetadata(Session session) {
        SessionResponseDto sessionResponseDto = modelMapper.map(session, SessionResponseDto.class);
        sessionResponseDto.setWebsocketData(
                new SessionMessagingMetadataDto(
                        "/api/connect",
                        this.getSessionTopicUrl(session),
                        this.getSessionAppUrl(session)
                )
        );

        return sessionResponseDto;
    }

    @Override
    public String getSessionTopicUrl(Session session) {
        return String.format("/topic/session/%d", session.getSessionId());
    }

    @Override
    public String getSessionAppUrl(Session session) {
        return String.format("/app/session/%d", session.getSessionId());
    }
}
