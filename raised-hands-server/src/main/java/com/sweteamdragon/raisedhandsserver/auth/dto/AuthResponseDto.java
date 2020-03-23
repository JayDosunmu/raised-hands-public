package com.sweteamdragon.raisedhandsserver.auth.dto;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AuthResponseDto {
    private Account user;
    private String jwt;
}
