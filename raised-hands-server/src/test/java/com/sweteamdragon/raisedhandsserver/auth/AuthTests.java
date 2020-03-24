package com.sweteamdragon.raisedhandsserver.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweteamdragon.raisedhandsserver.RaisedHandsServerApplication;
import com.sweteamdragon.raisedhandsserver.auth.dto.RegisterRequestDto;
import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.auth.repository.AccountRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = RaisedHandsServerApplication.class)
@AutoConfigureMockMvc
class AuthTests {

    private final String registerEndpoint = "/auth/register";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void shouldReturnSuccessfullyWithValidUserEmailAndPassword() throws Exception {
        /*
            - user created successfully
            - response status correct
            - response structure correct
                - JWT
                - user
         */
        Account account = accountRepository.findByEmail("test@email.com");
        if (account != null) {
            accountRepository.delete(account);
        }

        RegisterRequestDto userRegistrationDataObject = new RegisterRequestDto("test@email.com", "testPassw0rd!", "testPassw0rd!", "name");
        String json = new ObjectMapper().writeValueAsString(userRegistrationDataObject);
        this.mockMvc.perform(post(registerEndpoint).contentType("application/json").content(json)).andExpect(status().isOk());
        account = accountRepository.findByEmail("test@email.com");
        accountRepository.delete(account);
    }

    @Test
    public void shouldReturnErrorIfEmailTaken() throws Exception {
        RegisterRequestDto userRegistrationDataObject = new RegisterRequestDto("test@email.com", "pass", "pass", "name");
        String json = new ObjectMapper().writeValueAsString(userRegistrationDataObject);

        this.mockMvc.perform(post(registerEndpoint).contentType("application/json").content(json));
        this.mockMvc.perform(post(registerEndpoint).contentType("application/json").content(json)).andExpect(status().isConflict());

        Account account = accountRepository.findByEmail("test@email.com");
        accountRepository.delete(account);
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
}
