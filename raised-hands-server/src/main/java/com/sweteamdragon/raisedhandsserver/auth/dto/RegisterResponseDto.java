package com.sweteamdragon.raisedhandsserver.auth.dto;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import lombok.*;
import org.modelmapper.ModelMapper;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RegisterResponseDto {

    private static final ModelMapper modelMapper = new ModelMapper();

    private long id;
    private String email;

    static public RegisterResponseDto from(Account account) {
        return modelMapper.map(account, RegisterResponseDto.class);
    }
}
