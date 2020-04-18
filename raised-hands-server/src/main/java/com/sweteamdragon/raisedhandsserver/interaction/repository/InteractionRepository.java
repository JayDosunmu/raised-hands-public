package com.sweteamdragon.raisedhandsserver.interaction.repository;

import com.sweteamdragon.raisedhandsserver.session.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends JpaRepository<Session, Long> {
}
