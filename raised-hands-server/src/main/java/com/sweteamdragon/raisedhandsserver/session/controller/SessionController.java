package com.sweteamdragon.raisedhandsserver.session.controller;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.auth.security.AuthenticationFacade;
import com.sweteamdragon.raisedhandsserver.auth.service.AccountService;
import com.sweteamdragon.raisedhandsserver.session.dto.JoinSessionResponseDto;
import com.sweteamdragon.raisedhandsserver.session.dto.SessionResponseDto;
import com.sweteamdragon.raisedhandsserver.session.message.UserJoinedSessionMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
    private SimpMessagingTemplate template;

    @GetMapping
    public SessionResponseDto getSessions() throws ResponseStatusException{
        Authentication authentication = authenticationFacade.getAuthentication();


        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.MARCH, 17, 14, 45);
        Date startDate = calendar.getTime();
        calendar.set(2020, Calendar.MARCH, 17, 16, 15);
        Date endDate = calendar.getTime();

        if (authentication != null && authentication.getName() != null) {
            return new SessionResponseDto(
                    1L,
                    authentication.getName() + "'s Session",
                    "196875",
                    false,
                    false,
                    startDate,
                    endDate
            );
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Not authorized");
    }

    @PostMapping("/join")
    public SessionResponseDto joinSession() throws ResponseStatusException {
        Authentication authentication = authenticationFacade.getAuthentication();
        Account user = accountService.findByEmail((String) authentication.getPrincipal());

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.MARCH, 17, 14, 45);
        Date startDate = calendar.getTime();
        calendar.set(2020, Calendar.MARCH, 17, 16, 15);
        Date endDate = calendar.getTime();

        template.convertAndSend(
                "/topic/joinSession",
                new UserJoinedSessionMessage(
                        user.getName(),
                        user.getEmail(),
                        user.getId()
                )
        );
        return new SessionResponseDto(
                1L,
                user.getName() + "'s Session",
                "196875",
                false,
                false,
                startDate,
                endDate
        );
    }

    @MessageMapping("")
    @SendTo("/topic/joinSession")
    public JoinSessionResponseDto joinSession(UserJoinedSessionMessage message) throws Exception {
        return new JoinSessionResponseDto(
            HtmlUtils.htmlEscape(message.getName()),
            HtmlUtils.htmlEscape(message.getEmail()),
            message.getId()
        );
    }
}
