package org.icescrum.web.controller;

import org.icescrum.config.TestPostgresConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestPostgresConfig.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void home_authenticatedUser_returnsHomeView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }

    @Test
    void home_authenticatedUser_addsUsernameAttribute() throws Exception {
        mockMvc.perform(get("/").with(SecurityMockMvcRequestPostProcessors.user("testuser")))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attribute("username", "testuser"));
    }

    @Test
    void home_unauthenticatedUser_returnsHomeView() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"));
    }
}
