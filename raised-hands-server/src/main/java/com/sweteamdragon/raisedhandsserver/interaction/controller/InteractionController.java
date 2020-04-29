package com.sweteamdragon.raisedhandsserver.interaction.controller;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.auth.service.AccountService;
import com.sweteamdragon.raisedhandsserver.interaction.dto.InteractionCreateRequestDto;
import com.sweteamdragon.raisedhandsserver.interaction.dto.InteractionResponseDto;
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

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/interaction")
public class InteractionController {

    @Autowired
    AccountService accountService;

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

    @GetMapping("/session/{sessionId:^\\d+}")
    @ResponseStatus(HttpStatus.OK)
    public List<InteractionResponseDto> getInteractionsBySession(
            @PathVariable long sessionId,
            Authentication authentication) throws ResponseStatusException {
        Account account = accountService.findByEmail((String) authentication.getPrincipal());
        sessionParticipantRepository.findBySessionSessionIdAndAccountAccountId(
                sessionId,
                account.getAccountId()
        ).orElseThrow(() -> new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not a participant of this session"));

        Session session = sessionService.findById(sessionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find a session with this Id"));
        List<Interaction> interactions = interactionService.getInteractionsBySession(session);

        InteractionResponseDto[] interactionsResponse = new InteractionResponseDto[interactions.size()];

        for (int i = 0; i<interactions.size(); i++) {
            // TODO: this should be done using modelMapper
            Interaction interaction = interactions.get(i);

            InteractionResponseDto interactionResponseDto = new InteractionResponseDto();
            interactionResponseDto.setInteractionId(interaction.getInteractionId());
            interactionResponseDto.setSessionId(interaction.getSession().getSessionId());
            interactionResponseDto.setSessionParticipantId(
                    interaction.getSessionParticipant().getSessionParticipantId());
            interactionResponseDto.setMessage(interaction.getMessage());
            interactionResponseDto.setTimestamp(interaction.getTimestamp());
            interactionResponseDto.setVote(interaction.getVote());

            interactionsResponse[i] = interactionResponseDto;
        }
        return Arrays.asList(interactionsResponse);
    }

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
