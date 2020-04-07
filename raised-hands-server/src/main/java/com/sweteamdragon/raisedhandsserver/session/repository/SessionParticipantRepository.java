package com.sweteamdragon.raisedhandsserver.session.repository;

import com.sweteamdragon.raisedhandsserver.session.model.SessionParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SessionParticipantRepository extends JpaRepository<SessionParticipant, Long> {

    Optional<SessionParticipant> findBySessionSessionIdAndAccountAccountId(long sessionId, long accountId);
}
