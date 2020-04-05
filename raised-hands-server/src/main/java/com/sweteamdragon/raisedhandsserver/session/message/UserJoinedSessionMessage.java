package com.sweteamdragon.raisedhandsserver.session.message;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserJoinedSessionMessage {
    private String name;
    private String email;
    private long id;
}
