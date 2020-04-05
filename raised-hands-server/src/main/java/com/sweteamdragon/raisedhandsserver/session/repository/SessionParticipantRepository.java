package com.sweteamdragon.raisedhandsserver.session.repository;

import com.sweteamdragon.raisedhandsserver.session.model.SessionParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionParticipantRepository extends JpaRepository<SessionParticipant, Long> {
}
