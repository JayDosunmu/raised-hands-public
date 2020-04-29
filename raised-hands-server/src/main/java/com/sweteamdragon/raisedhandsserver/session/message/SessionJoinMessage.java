package com.sweteamdragon.raisedhandsserver.session.message;

import com.sweteamdragon.raisedhandsserver.auth.dto.AccountDto;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SessionJoinMessage {
    private final String type = "sessionJoin";
    private long sessionParticipantId;
    private AccountDto account;
    private boolean leader;
    private Date joinTimestamp;
}
