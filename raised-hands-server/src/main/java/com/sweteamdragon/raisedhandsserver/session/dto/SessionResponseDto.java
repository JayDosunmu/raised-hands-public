package com.sweteamdragon.raisedhandsserver.session.dto;

import com.sweteamdragon.raisedhandsserver.session.model.SessionParticipant;
import lombok.*;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SessionResponseDto {
    private Long sessionId;
    private String name;
    private String joinId;
    private String passcode;
    private boolean active;
    private boolean distractionFree;
    private Date startDate;
    private Date endDate;
    private Date createTimestamp;
    private ShallowSessionParticipantDto leader;
    private Set<ShallowSessionParticipantDto> participants;
}
