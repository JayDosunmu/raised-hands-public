package com.sweteamdragon.raisedhandsserver.auth.models;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String email;

    private String password;

    public Account() {}

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
