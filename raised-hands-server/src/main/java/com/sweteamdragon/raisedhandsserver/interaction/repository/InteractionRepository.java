package com.sweteamdragon.raisedhandsserver.interaction.repository;

import com.sweteamdragon.raisedhandsserver.interaction.model.Interaction;
import com.sweteamdragon.raisedhandsserver.session.model.Session;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {

    List<Interaction> findBySession(Session session);
}
