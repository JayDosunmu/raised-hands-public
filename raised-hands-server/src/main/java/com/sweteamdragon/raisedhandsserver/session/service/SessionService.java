package com.sweteamdragon.raisedhandsserver.session.service;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.session.dto.SessionResponseDto;
import com.sweteamdragon.raisedhandsserver.session.model.Session;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface SessionService {

    Session create(Account user, String name, boolean isDistractionFree, Date startDate, Date endDate);

    Optional<Session> findById(long sessionId);

    Optional<Session> findByIdSecured(long sessionId, long accountId);

    Optional<Session> findByJoinIdSecured(String joinId, long accountId);

    List<Session> findAllByAccount(Account account);

    Map<String, Object> join(String joinId, String passcode, Account account);

    SessionResponseDto getSessionWithMessagingMetadata(Session session);

    String getSessionTopicUrl(Session session);

    String getSessionAppUrl(Session session);
}
