package com.sweteamdragon.raisedhandsserver.interaction.service;

import com.sweteamdragon.raisedhandsserver.interaction.model.Interaction;
import com.sweteamdragon.raisedhandsserver.interaction.repository.InteractionRepository;
import com.sweteamdragon.raisedhandsserver.session.model.Session;
import com.sweteamdragon.raisedhandsserver.session.model.SessionParticipant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InteractionServiceImpl implements InteractionService {

    @Autowired
    InteractionRepository interactionRepository;

    @Override
    public Interaction create(Session session, SessionParticipant sessionParticipant, String message) {
        if (session == null) {
            throw new IllegalArgumentException("Trying to create an interaction for an invalid session");
        } else if (sessionParticipant == null) {
            throw new IllegalArgumentException("Trying to create an interaction for an invalid session participant");
        }

        Interaction interaction = new Interaction(message, session, sessionParticipant, 1);
        interactionRepository.save(interaction);

        return interaction;
    }

    @Override
    public List<Interaction> getInteractionsBySession(Session session) {
        if (session == null) {
            throw new NullPointerException("Invalid session to retrieve interactions from");
        }

        return interactionRepository.findBySession(session);
    }

    @Override
    public boolean clearInteraction(long interactionId) {
        return false;
    }

    @Override
    public boolean clearMultipleInteractions(long[] interactionIds) {
        return false;
    }
}
