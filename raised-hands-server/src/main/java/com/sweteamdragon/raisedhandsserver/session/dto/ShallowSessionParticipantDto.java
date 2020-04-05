package com.sweteamdragon.raisedhandsserver.session.dto;

import com.sweteamdragon.raisedhandsserver.auth.dto.AccountDto;
import lombok.*;

import java.util.Date;

// TODO: Find a better way to configure JSON output for DTOs to hide sensitive data + control nesting
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class ShallowSessionParticipantDto {
    private long sessionParticipantId;
    private AccountDto account;
    private boolean leader;
    private Date joinTimestamp;
}
