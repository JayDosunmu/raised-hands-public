package com.sweteamdragon.raisedhandsserver.session.repository;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.session.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SessionRepository extends JpaRepository<Session, Long> {

    @Query("SELECT s FROM Session s, SessionParticipant sp WHERE sp.session = s AND sp.account = :account")
    List<Session> findByAccount(@Param("account") Account account);
}
