package com.sweteamdragon.raisedhandsserver.session.repository;

import com.sweteamdragon.raisedhandsserver.session.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackRepository extends JpaRepository<Session, Long> {

}