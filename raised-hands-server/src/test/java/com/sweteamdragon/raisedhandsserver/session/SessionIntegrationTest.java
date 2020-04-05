package com.sweteamdragon.raisedhandsserver.session;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweteamdragon.raisedhandsserver.RaisedHandsServerApplication;
import com.sweteamdragon.raisedhandsserver.auth.model.Account;
import com.sweteamdragon.raisedhandsserver.auth.security.JwtUtil;
import com.sweteamdragon.raisedhandsserver.config.TestConfiguration;
import com.sweteamdragon.raisedhandsserver.session.dto.SessionCreateRequestDto;
import com.sweteamdragon.raisedhandsserver.util.TestAuth;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = RaisedHandsServerApplication.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestConfiguration.class)
public class SessionIntegrationTest {

    // TODO: make these configured values
    private final String sessionEndpoint = "/session";
    private final String testSessionName = "testSession";
    private final boolean testSessionActive = true;
    private final boolean testSessionInactive = false;
    private final boolean testSessionDistractionFree = true;
    private final boolean testSessionNotDistractionFree = false;
    private final Date testStartDate = new Date();
    private final Date testEndDate = new Date();

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    @WithUserDetails(value="test@email.com", userDetailsServiceBeanName="testUserDetailsService")
    public void anAuthenticatedUserCanCreateASession() throws Exception {
        Account testAccount = TestAuth.getAccountFromContext();
        String token = jwtUtil.createToken(testAccount);

        SessionCreateRequestDto sessionCreateDto = new SessionCreateRequestDto(
                "testSession",
                testStartDate,
                testEndDate,
                testSessionDistractionFree
        );
        String json = new ObjectMapper().writeValueAsString(sessionCreateDto);
        this.mockMvc.perform(post(sessionEndpoint)
                .header(jwtUtil.getHeaderString(), jwtUtil.formatTokenWithPrefix(token))
                .contentType("application/json").content(json))
                .andExpect(status().isCreated())
                .andExpect((ResultMatcher) jsonPath("$.session.name", is("testSession")))
                .andExpect(jsonPath("$.session.sessionId").isNumber())
                .andExpect(jsonPath("$.session.leader").isMap())
                .andExpect((ResultMatcher) jsonPath("$.session.active", is(testSessionDistractionFree)))
                .andExpect((ResultMatcher) jsonPath("$.session.startDate", is(testStartDate)))
                .andExpect((ResultMatcher) jsonPath("$.session.endDate", is(testEndDate)))
                .andExpect(jsonPath("$.session.passcode").isString());
    }

    @Test
    @WithAnonymousUser
    public void anUnAuthenticatedUserCannotCreateASession() throws Exception {
        SessionCreateRequestDto sessionCreateDto = new SessionCreateRequestDto(
                "testSession",
                testStartDate,
                testEndDate,
                testSessionDistractionFree
        );
        String json = new ObjectMapper().writeValueAsString(sessionCreateDto);
        this.mockMvc.perform(post(sessionEndpoint).contentType("application/json").content(json))
                .andExpect(status().isForbidden())
                .andExpect(content().string(""));
    }
}
