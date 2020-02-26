package com.sweteamdragon.raisedhandsserver.auth.models;

public class RegisterRequestModel {

    private final String email;
    private final String password;

    public RegisterRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
