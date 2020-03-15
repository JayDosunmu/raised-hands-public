package com.sweteamdragon.raisedhandsserver.auth.dto;

import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@AllArgsConstructor
public class AccountDto {

    private static final ModelMapper modelMapper = new ModelMapper();

    private final String email;
    private final String password;

    private final String name;
}
