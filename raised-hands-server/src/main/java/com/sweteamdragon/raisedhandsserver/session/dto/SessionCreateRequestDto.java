package com.sweteamdragon.raisedhandsserver.session.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SessionCreateRequestDto {
    private String name;
    private Date startDate;
    private Date endDate;
    private boolean distractionFree;
}
