package com.sweteamdragon.raisedhandsserver.session.controller;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.auth.security.AuthenticationFacade;
import com.sweteamdragon.raisedhandsserver.auth.service.AccountService;
import com.sweteamdragon.raisedhandsserver.session.dto.JoinSessionRequestDto;
import com.sweteamdragon.raisedhandsserver.session.dto.SessionCreateRequestDto;
import com.sweteamdragon.raisedhandsserver.session.dto.SessionResponseDto;
import com.sweteamdragon.raisedhandsserver.session.message.UserJoinedSessionMessage;
import com.sweteamdragon.raisedhandsserver.session.model.Session;
import com.sweteamdragon.raisedhandsserver.session.model.SessionParticipant;
import com.sweteamdragon.raisedhandsserver.session.service.SessionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    private AuthenticationFacade authenticationFacade;

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
        SessionResponseDto[] sessionsArray = modelMapper.map(sessions, SessionResponseDto[].class);
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
        return modelMapper.map(session, SessionResponseDto.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponseDto create(
                @RequestBody SessionCreateRequestDto sessionCreateRequestDto,
                Authentication authentication) throws ResponseStatusException{
        try {
            Account user = accountService.findByEmail((String) authentication.getPrincipal());

            Session session = sessionService.create(
                    user,
                    sessionCreateRequestDto.getName(),
                    sessionCreateRequestDto.isDistractionFree(),
                    sessionCreateRequestDto.getStartDate(),
                    sessionCreateRequestDto.getEndDate()
            );

            return modelMapper.map(session, SessionResponseDto.class);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/join")
    public SessionResponseDto join(
                @RequestBody JoinSessionRequestDto joinSessionRequestDto,
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

            template.convertAndSend(
                String.format("/app/session/%d/join", session.getSessionId()),
                sessionParticipant
            );
            return modelMapper.map(session, SessionResponseDto.class);
        // TODO: Handle exceptions in a more granular fashion
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }
}
