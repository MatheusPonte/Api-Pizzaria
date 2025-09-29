package com.user.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
@ActiveProfiles("test")
class ApiApplicationTests {

    @Autowired
    org.springframework.core.env.Environment env;

    @Test
    void contextLoads() {
        System.out.println("TEST spring.datasource.url = " + env.getProperty("spring.datasource.url"));
        System.out.println("TEST APPLICATION_NAME = " + env.getProperty("APPLICATION_NAME"));
    }
}

