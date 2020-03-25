package com.sweteamdragon.raisedhandsserver.session.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JoinSessionResponseDto {
    private String name;
    private String email;
    private long id;
}
