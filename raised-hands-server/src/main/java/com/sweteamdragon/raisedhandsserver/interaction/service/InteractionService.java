package com.sweteamdragon.raisedhandsserver.interaction.service;

import com.sweteamdragon.raisedhandsserver.interaction.model.Interaction;
import com.sweteamdragon.raisedhandsserver.session.model.Session;
import com.sweteamdragon.raisedhandsserver.session.model.SessionParticipant;

import java.util.List;

public interface InteractionService {

    Interaction create(Session session, SessionParticipant sessionParticipant, String message);

    List<Interaction> getInteractionsBySession(Session session);

    boolean clearInteraction(long interactionId);

    boolean clearMultipleInteractions(long[] interactionIds);
}
