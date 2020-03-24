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

import static org.springframework.test.util.AssertionErrors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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
        this.mockMvc.perform(post(registerEndpoint).contentType("application/json").content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.email").isString())
                .andExpect(jsonPath("$.user.name").isString())
                .andExpect(jsonPath("$.user.id").isNumber())
                .andExpect(jsonPath("$.user.password").doesNotExist())
                .andExpect(jsonPath("$.jwt").isString());

        Account createdAccount = accountRepository.findByEmail("test@email.com");
        assertNotNull("Account registration must create a user", createdAccount);
        assertEquals("Created account email and input email must match!", createdAccount.getEmail(), "test@email.com");
        assertEquals("Created account name and input name must match!", createdAccount.getName(), "name");
        assertNotEquals("Encrypted password and input password must not match!", createdAccount.getPassword(), "testPassw0rd!");
        accountRepository.delete(createdAccount);
    }

    @Test
    public void shouldReturnErrorIfEmailTaken() throws Exception {
        RegisterRequestDto userRegistrationDataObject = new RegisterRequestDto("test@email.com", "pass", "pass", "name");
        String json = new ObjectMapper().writeValueAsString(userRegistrationDataObject);

        this.mockMvc.perform(post(registerEndpoint).contentType("application/json").content(json));
        this.mockMvc.perform(post(registerEndpoint).contentType("application/json").content(json))
                .andExpect(status().isConflict())
                .andExpect(content().string(""));

        Account account = accountRepository.findByEmail("test@email.com");
        accountRepository.delete(account);
    }

    @Test
    public void shouldReturnErrorIfPasswordsDoNotMatch() throws Exception {
        RegisterRequestDto userRegistrationDataObject = new RegisterRequestDto("test@email.com", "one_password", "different_password", "name");
        String json = new ObjectMapper().writeValueAsString(userRegistrationDataObject);
        this.mockMvc.perform(post(registerEndpoint).contentType("application/json").content(json))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    public void shouldReturnErrorIfPasswordIsInvalid() throws Exception {

    }

    @Test
    public void shouldReturnErrorIfNameIsInvalid() throws Exception {

    }
}
