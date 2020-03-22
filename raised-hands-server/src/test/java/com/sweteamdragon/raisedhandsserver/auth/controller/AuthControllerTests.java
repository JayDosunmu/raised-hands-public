package com.sweteamdragon.raisedhandsserver.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweteamdragon.raisedhandsserver.RaisedHandsServerApplication;
import com.sweteamdragon.raisedhandsserver.auth.dto.RegisterRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes=RaisedHandsServerApplication.class)
@AutoConfigureMockMvc
class AuthControllerTests {

    private final String registerEndpoint = "/auth/register";

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnSuccessfullyWithValidUserEmailAndPassword() throws Exception {
        /*
            - user created successfully
            - response status correct
            - response structure correct
                - JWT
                - user
         */
        RegisterRequestDto userRegistrationDataObject = new RegisterRequestDto("test@email.com", "testPassw0rd!", "testPassw0rd!", "name");
        String json = new ObjectMapper().writeValueAsString(userRegistrationDataObject);
        this.mockMvc.perform(post(registerEndpoint).contentType("application/json").content(json)).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnErrorIfEmailTaken() throws Exception {
        RegisterRequestDto userRegistrationDataObject = new RegisterRequestDto("test@email.com", "pass", "pass", "name");
        String json = new ObjectMapper().writeValueAsString(userRegistrationDataObject);
        this.mockMvc.perform(post(registerEndpoint).contentType("application/json").content(json));
        this.mockMvc.perform(post(registerEndpoint).contentType("application/json").content(json)).andExpect(status().isConflict());
    }

    @Test
    public void shouldReturnErrorIfPasswordsDoNotMatch() throws Exception {
        RegisterRequestDto userRegistrationDataObject = new RegisterRequestDto("test@email.com", "one_password", "different_password", "name");
        String json = new ObjectMapper().writeValueAsString(userRegistrationDataObject);
        this.mockMvc.perform(post(registerEndpoint).contentType("application/json").content(json)).andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturnErrorIfPasswordIsInvalid() throws Exception {

    }

    @Test
    public void shouldReturnErrorIfNameIsInvalid() throws Exception {

    }

    @Test
    public void shouldReturnErrorIfUserAlreadyAuthenticated() throws Exception {

    }
}
