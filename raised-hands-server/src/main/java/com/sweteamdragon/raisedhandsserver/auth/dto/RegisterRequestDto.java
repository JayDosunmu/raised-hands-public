package com.sweteamdragon.raisedhandsserver.auth.models;

public class RegisterRequestModel {

    private final String email;
    private final String password;
    private final String confirmPassword;

    private final String firstName;
    private final String lastName;

    public RegisterRequestModel(String email, String password, String confirmPassword, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

}
