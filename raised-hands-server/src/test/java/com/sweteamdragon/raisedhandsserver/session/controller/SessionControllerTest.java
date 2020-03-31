package com.sweteamdragon.raisedhandsserver.session.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sweteamdragon.raisedhandsserver.config.TestConfiguration;
import com.sweteamdragon.raisedhandsserver.session.dto.SessionCreateRequestDto;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Date;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SessionController.class)
@AutoConfigureMockMvc
@ContextConfiguration(classes = TestConfiguration.class)
public class SessionControllerTest {

    // TODO: make these configured values
    private final String sessionEndpoint = "/session";
    private final String testSessionName = "testSession";
    private final boolean testSessionActive = true;
    private final boolean testSessionInactive = false;
    private final boolean testSessionDistractionFree = true;
    private final boolean testSessionNotDistractionFree = false;
    private final Date testStartDate = new Date();
    private final Date testEndDate = new Date();


    @InjectMocks
    SessionController sessionControllerUnderTest;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value="test@email.com", userDetailsServiceBeanName="testUserDetailsService")
    public void anAuthenticatedUserCanCreateASession() throws Exception {
        SessionCreateRequestDto sessionCreateDto = new SessionCreateRequestDto(
                "testSession",
                testStartDate,
                testEndDate,
                testSessionDistractionFree
        );
        String json = new ObjectMapper().writeValueAsString(sessionCreateDto);
        this.mockMvc.perform(post(sessionEndpoint).contentType("application/json").content(json))
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
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(""));
    }
}
