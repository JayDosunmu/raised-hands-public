package com.sweteamdragon.raisedhandsserver.session.controller;

import com.sweteamdragon.raisedhandsserver.auth.security.AuthenticationFacade;
import com.sweteamdragon.raisedhandsserver.session.dto.SessionResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Calendar;
import java.util.Date;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    AuthenticationFacade authenticationFacade;

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
}
