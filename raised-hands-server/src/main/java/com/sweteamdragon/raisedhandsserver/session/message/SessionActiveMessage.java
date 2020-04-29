package com.sweteamdragon.raisedhandsserver.session.message;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SessionActiveMessage {
    private final String type = "sessionActive";
    private boolean active;
}
