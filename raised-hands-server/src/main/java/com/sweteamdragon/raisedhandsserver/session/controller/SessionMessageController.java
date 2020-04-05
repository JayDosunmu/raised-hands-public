package com.sweteamdragon.raisedhandsserver.session.controller;

import com.sweteamdragon.raisedhandsserver.session.dto.JoinSessionResponseDto;
import com.sweteamdragon.raisedhandsserver.session.message.UserJoinedSessionMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class SessionMessageController {

    @MessageMapping
    @SendTo("/topic/joinSession")
    public JoinSessionResponseDto join(UserJoinedSessionMessage message) throws Exception {
        return new JoinSessionResponseDto(
                HtmlUtils.htmlEscape(message.getName()),
                HtmlUtils.htmlEscape(message.getEmail()),
                message.getId()
        );
    }
}
