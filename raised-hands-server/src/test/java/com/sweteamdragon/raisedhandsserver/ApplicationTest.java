package com.sweteamdragon.raisedhandsserver;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest(classes = RaisedHandsServerApplication.class)
@TestPropertySource(locations="classpath:application.properties")
class ApplicationTest {

    @Test
    void applicationContextLoads() {
    }

}