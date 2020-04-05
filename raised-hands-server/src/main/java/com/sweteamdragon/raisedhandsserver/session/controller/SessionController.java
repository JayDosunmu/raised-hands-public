package com.sweteamdragon.raisedhandsserver.session.controller;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.auth.security.AuthenticationFacade;
import com.sweteamdragon.raisedhandsserver.auth.service.AccountService;
import com.sweteamdragon.raisedhandsserver.session.dto.JoinSessionResponseDto;
import com.sweteamdragon.raisedhandsserver.session.dto.SessionCreateRequestDto;
import com.sweteamdragon.raisedhandsserver.session.dto.SessionCreateResponseDto;
import com.sweteamdragon.raisedhandsserver.session.message.UserJoinedSessionMessage;
import com.sweteamdragon.raisedhandsserver.session.model.Session;
import com.sweteamdragon.raisedhandsserver.session.service.SessionService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.HtmlUtils;

import java.util.Calendar;
import java.util.Date;

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
    private SimpMessagingTemplate template;

    @GetMapping
    public SessionCreateResponseDto getSessions(Authentication authentication) throws ResponseStatusException{
        Account user = accountService.findByEmail((String) authentication.getPrincipal());

        throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented");
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionCreateResponseDto create(@RequestBody SessionCreateRequestDto sessionCreateRequestDto, Authentication authentication) throws ResponseStatusException{
        try {
            Account user = accountService.findByEmail((String) authentication.getPrincipal());

            Session session = sessionService.create(
                    user,
                    sessionCreateRequestDto.getName(),
                    sessionCreateRequestDto.isDistractionFree(),
                    sessionCreateRequestDto.getStartDate(),
                    sessionCreateRequestDto.getEndDate()
            );

            ModelMapper modelMapper = new ModelMapper();
            return modelMapper.map(session, SessionCreateResponseDto.class);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/join")
    public SessionCreateResponseDto join() throws ResponseStatusException {
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
