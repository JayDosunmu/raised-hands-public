package com.sweteamdragon.raisedhandsserver.session.controller;

import com.sweteamdragon.raisedhandsserver.session.dto.ShallowSessionParticipantDto;
import com.sweteamdragon.raisedhandsserver.session.model.SessionParticipant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
@MessageMapping("/session")
public class SessionMessageController {

    @Autowired
    ModelMapper modelMapper;

    @MessageMapping("/{sessionId}/join")
    @SendTo("/topic/session/{sessionId}")
    public ShallowSessionParticipantDto join(SessionParticipant message) throws Exception {
        return modelMapper.map(message, ShallowSessionParticipantDto.class);
    }
}
