package com.sweteamdragon.raisedhandsserver.interaction.controller;

import com.sweteamdragon.raisedhandsserver.interaction.dto.InteractionCreateRequestDto;
import com.sweteamdragon.raisedhandsserver.interaction.message.InteractionMessage;
import com.sweteamdragon.raisedhandsserver.interaction.model.Interaction;
import com.sweteamdragon.raisedhandsserver.interaction.service.InteractionService;
import com.sweteamdragon.raisedhandsserver.session.model.Session;
import com.sweteamdragon.raisedhandsserver.session.model.SessionParticipant;
import com.sweteamdragon.raisedhandsserver.session.repository.SessionParticipantRepository;
import com.sweteamdragon.raisedhandsserver.session.repository.SessionRepository;
import com.sweteamdragon.raisedhandsserver.session.service.SessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/interaction")
public class InteractionController {

    @Autowired
    InteractionService interactionService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    SessionRepository sessionRepository;

    @Autowired
    SessionParticipantRepository sessionParticipantRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private SimpMessagingTemplate template;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity create(
            @RequestBody InteractionCreateRequestDto interactionCreateRequestDto,
            Authentication authentication) throws ResponseStatusException {
        try {
            String userEmail = (String) authentication.getPrincipal();
            long sessionId = interactionCreateRequestDto.getSessionId();
            long sessionParticipantId = interactionCreateRequestDto.getSessionParticipantId();

            Session session = sessionRepository
                    .findById(sessionId).get();
            SessionParticipant sessionParticipant = sessionParticipantRepository
                    .findById(sessionParticipantId).get();

            if (!sessionParticipant.getAccount().getEmail().equals(userEmail) ||
                !session.getParticipants().contains(sessionParticipant)) {
                throw new ResponseStatusException(
                        HttpStatus.FORBIDDEN,
                        "User does not have permission to interact in the session");
            }

            Interaction interaction = interactionService.create(
                    session,
                    sessionParticipant,
                    interactionCreateRequestDto.getMessage()
            );

            // TODO: this should be done using modelMapper
            InteractionMessage interactionMessage = new InteractionMessage();
            interactionMessage.setInteractionId(interaction.getInteractionId());
            interactionMessage.setSessionId(sessionId);
            interactionMessage.setSessionParticipantId(sessionParticipantId);
            interactionMessage.setMessage(interaction.getMessage());
            interactionMessage.setTimestamp(interaction.getTimestamp());
            interactionMessage.setVote(interaction.getVote());
            template.convertAndSend(
                    sessionService.getSessionTopicUrl(session),
                    interactionMessage
            );
            return new ResponseEntity(HttpStatus.CREATED);

        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
