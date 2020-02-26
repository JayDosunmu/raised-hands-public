package com.sweteamdragon.raisedhandsserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweteamdragon.raisedhandsserver.auth.models.RegisterRequestModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RaisedHandsServerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void shouldReturnSuccessfullyWithValidUserEmailAndPassword() throws Exception {
        RegisterRequestModel userRegistrationDataObject = new RegisterRequestModel("test@email.com", "testPassw0rd!");
        String json = new ObjectMapper().writeValueAsString(userRegistrationDataObject);
        this.mockMvc.perform(post("/register").contentType("application/json").content(json)).andExpect(status().isOk());
    }

}
