package com.example.demo.service;

import com.example.demo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    boolean registration(User user);
    String login(String email, String password);
    boolean logout(String token);
    boolean deleteUser(String token);
}
