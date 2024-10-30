package com.springBoot.Bank;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
    public void testLoginWithoutParams() throws Exception {
        mockMvc.perform(get("/login"))
            .andExpect(status().isOk())
            .andExpect(view().name("login"))
            .andExpect(model().attributeDoesNotExist("error"))
            .andExpect(model().attributeDoesNotExist("msg"));
    }

    @Test
    public void testLoginWithError() throws Exception {
        mockMvc.perform(get("/login").param("error", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("login"))
            .andExpect(model().attributeExists("error"))
            .andExpect(model().attribute("error", "Invalid username and/or password!"))
            .andExpect(model().attributeDoesNotExist("msg"));
    }

    @Test
    public void testLoginWithLogout() throws Exception {
        mockMvc.perform(get("/login").param("logout", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("login"))
            .andExpect(model().attributeExists("msg"))
            .andExpect(model().attribute("msg", "You have been logged out successfully."))
            .andExpect(model().attributeDoesNotExist("error"));
    }

    @Test
    public void testLoginWithErrorAndLogout() throws Exception {
        mockMvc.perform(get("/login").param("error", "").param("logout", ""))
            .andExpect(status().isOk())
            .andExpect(view().name("login"))
            .andExpect(model().attributeExists("error"))
            .andExpect(model().attribute("error", "Invalid username and/or password!"))
            .andExpect(model().attributeExists("msg"))
            .andExpect(model().attribute("msg", "You have been logged out successfully."));
    }
	
}
