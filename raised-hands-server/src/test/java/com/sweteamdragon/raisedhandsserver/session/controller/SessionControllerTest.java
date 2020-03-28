package com.sweteamdragon.raisedhandsserver.session.controller;

import com.sweteamdragon.raisedhandsserver.RaisedHandsServerApplication;
import org.mockito.InjectMocks;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = RaisedHandsServerApplication.class)
@AutoConfigureMockMvc
public class SessionControllerTest {

    @InjectMocks
    SessionController sessionController;


}
