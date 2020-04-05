package com.sweteamdragon.raisedhandsserver.session.dto;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ShallowSessionParticipantDto {
    private long sessionParticipantId;
    private Account account;
    private boolean leader;
    private Date joinTimestamp;
}
