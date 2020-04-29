package com.sweteamdragon.raisedhandsserver.interaction.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class InteractionResponseDto {
    private long interactionId;
    private long sessionId;
    private long sessionParticipantId;
    private String message;
    private Date timestamp;
    private int vote;
}
