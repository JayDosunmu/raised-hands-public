package com.sweteamdragon.raisedhandsserver.session.controller;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.auth.service.AccountService;
import com.sweteamdragon.raisedhandsserver.session.dto.JoinSessionRequestDto;
import com.sweteamdragon.raisedhandsserver.session.dto.SessionCreateRequestDto;
import com.sweteamdragon.raisedhandsserver.session.dto.SessionResponseDto;
import com.sweteamdragon.raisedhandsserver.session.message.SessionActiveMessage;
import com.sweteamdragon.raisedhandsserver.session.message.SessionJoinMessage;
import com.sweteamdragon.raisedhandsserver.session.model.Session;
import com.sweteamdragon.raisedhandsserver.session.model.SessionParticipant;
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
import java.util.Map;

/*
 TODO: currently, messaging integration is clunky. Extract the correct functionality
        into a proper service, and improve the data models
*/

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    private SimpMessagingTemplate template;

    @GetMapping
    public List<SessionResponseDto> getSessionsOfAuthenticatedUser(Authentication authentication) throws ResponseStatusException{
        Account user = accountService.findByEmail((String) authentication.getPrincipal());

        List<Session> sessions = sessionService.findAllByAccount(user);
        SessionResponseDto[] sessionsArray = modelMapper.map(
                sessions.stream().map(
                        session -> sessionService.getSessionWithMessagingMetadata(session)
                ).toArray(),
                SessionResponseDto[].class);
        return Arrays.asList(sessionsArray);
    }

    @GetMapping("/{sessionId:^[0-9A-Z]*}")
    public SessionResponseDto getSessionById(
                @PathVariable String sessionId,
                @RequestParam(required = false) boolean isJoinId,
                Authentication authentication) throws ResponseStatusException {
        Account user = accountService.findByEmail((String) authentication.getPrincipal());

        Session session;
        if (isJoinId) {
            session = sessionService.findByJoinIdSecured(sessionId, user.getAccountId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No session with that joinID"));
        } else {
            session = sessionService.findByIdSecured(Long.parseLong(sessionId), user.getAccountId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "No session with that ID"));
        }
        return sessionService.getSessionWithMessagingMetadata(session);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponseDto create(
                @RequestBody SessionCreateRequestDto sessionCreateRequestDto,
                Authentication authentication) throws ResponseStatusException {
        try {
            Account user = accountService.findByEmail((String) authentication.getPrincipal());

            Session session = sessionService.create(
                    user,
                    sessionCreateRequestDto.getName(),
                    sessionCreateRequestDto.isDistractionFree(),
                    sessionCreateRequestDto.getStartDate(),
                    sessionCreateRequestDto.getEndDate()
            );

            return sessionService.getSessionWithMessagingMetadata(session);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/join")
    public SessionResponseDto join(
                @RequestBody(required = false) JoinSessionRequestDto joinSessionRequestDto,
                Authentication authentication) throws ResponseStatusException {
        Account user = accountService.findByEmail((String) authentication.getPrincipal());

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User authentication invalid");
        }

        try {
            Map<String, Object> sessionData = sessionService.join(
                    joinSessionRequestDto.getJoinId(),
                    joinSessionRequestDto.getPasscode(),
                    user
            );
            Session session = (Session) sessionData.get("session");
            SessionParticipant sessionParticipant = (SessionParticipant) sessionData.get("sessionParticipant");

            // TODO: This should be extracted into some component responsible for this app's messaging
            template.convertAndSend(
                sessionService.getSessionTopicUrl(session),
                modelMapper.map(
                    sessionParticipant,
                    SessionJoinMessage.class
                )
            );
            return sessionService.getSessionWithMessagingMetadata(session);
        // TODO: Handle exceptions in a more granular fashion
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/{sessionId:^\\d+}")
    public ResponseEntity changeSessionActive(
            @PathVariable long sessionId,
            @RequestBody Map<String, Boolean> activeState,
            Authentication authentication) throws ResponseStatusException {
        try {
            Account account = accountService.findByEmail((String) authentication.getPrincipal());
            Session session = sessionService.findById(sessionId)
                    .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Unable to find session with that ID")
                    );
            if (session.getLeader().getAccount().equals(account)) {
                session.setActive(false);
                sessionService.save(session);

                template.convertAndSend(
                        sessionService.getSessionTopicUrl(session),
                        new SessionActiveMessage(activeState.getOrDefault("active", true))
                );

                return new ResponseEntity(HttpStatus.OK);
            } else {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unable to modify session");
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Unable to modify session");
        }
    }
}
