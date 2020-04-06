package com.sweteamdragon.raisedhandsserver.session.controller;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.auth.security.AuthenticationFacade;
import com.sweteamdragon.raisedhandsserver.auth.service.AccountService;
import com.sweteamdragon.raisedhandsserver.session.dto.SessionCreateRequestDto;
import com.sweteamdragon.raisedhandsserver.session.dto.SessionResponseDto;
import com.sweteamdragon.raisedhandsserver.session.message.UserJoinedSessionMessage;
import com.sweteamdragon.raisedhandsserver.session.model.Session;
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
    public List<SessionResponseDto> getSessions(Authentication authentication) throws ResponseStatusException{
        Account user = accountService.findByEmail((String) authentication.getPrincipal());

        List<Session> sessions = sessionService.getSessionsByUser(user);
        SessionResponseDto[] sessionsArray = modelMapper.map(sessions, SessionResponseDto[].class);
        return Arrays.asList(sessionsArray);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionResponseDto create(@RequestBody SessionCreateRequestDto sessionCreateRequestDto, Authentication authentication) throws ResponseStatusException{
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
    public SessionResponseDto join() throws ResponseStatusException {
        Authentication authentication = authenticationFacade.getAuthentication();
        Account user = accountService.findByEmail((String) authentication.getPrincipal());

        template.convertAndSend(
                "/topic/joinSession",
                new UserJoinedSessionMessage(
                        user.getName(),
                        user.getEmail(),
                        user.getAccountId()
                )
        );
        throw new ResponseStatusException(HttpStatus.OK, "Message sent");
    }
}
