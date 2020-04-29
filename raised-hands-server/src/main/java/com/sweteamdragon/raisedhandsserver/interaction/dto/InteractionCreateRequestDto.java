package com.sweteamdragon.raisedhandsserver.interaction.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class InteractionCreateRequestDto {
    private String message;
    private long sessionId;
    private long sessionParticipantId;
}
