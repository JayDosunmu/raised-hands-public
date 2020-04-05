package com.sweteamdragon.raisedhandsserver.auth.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AccountDto {
    private long accountId;
    private String email;
    private String name;
}
