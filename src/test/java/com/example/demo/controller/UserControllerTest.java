package com.example.demo.controller;

import com.example.demo.controller.UserController;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void registration() throws Exception {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(userService.registration(user)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shop/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Registration complete!"));

        verify(userService, times(1)).registration(user);
    }

    @Test
    void deleteUserById() throws Exception {
        String token = "token";

        when(userService.deleteUser(token)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/shop/delete-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("User deleted"));

        verify(userService, times(1)).deleteUser(token);
    }

    @Test
    void login() throws Exception {
        String email = "test@example.com";
        String password = "password";
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        String expectedResponse = "Login successful";

        when(userService.login(email, password)).thenReturn(expectedResponse);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shop/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(user)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(expectedResponse));

        verify(userService, times(1)).login(email, password);
    }

    @Test
    void logout() throws Exception {
        String token = "token";

        when(userService.logout(token)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/shop/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("You successfully logged out"));

        verify(userService, times(1)).logout(token);
    }
}

