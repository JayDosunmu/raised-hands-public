package com.sweteamdragon.raisedhandsserver.session.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SessionResponseDto {

    private Long id;
    private String name;
    private String passcode;
    private boolean active;
    private boolean distractionFree;
    private Date startDate;
    private Date endDate;
}
