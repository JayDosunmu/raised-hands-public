package com.sweteamdragon.raisedhandsserver.session.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinSessionRequestDto {
    private String joinId;
    private String passcode;
}
