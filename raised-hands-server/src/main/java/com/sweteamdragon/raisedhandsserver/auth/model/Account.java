package com.sweteamdragon.raisedhandsserver.auth.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "email", unique = true)
    private String email;

    private String password;

    public Account(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
