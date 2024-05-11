package com.example.demo.service;

import com.example.demo.model.Session;
import com.example.demo.model.User;
import com.example.demo.repository.UsersRepository;
import com.example.demo.repository.SessionRepository;
import com.example.demo.repository.ShoppingCartRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UsersRepository usersRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private ShoppingCartRepo shoppingCartRepo;

    @InjectMocks
    private UserService userService;

    @Test
    void registration_Success() {
        User user = new User("test@example.com", "password");

        when(usersRepository.existsById(user.getEmail())).thenReturn(false);

        assertTrue(userService.registration(user));

        verify(usersRepository, times(1)).existsById(user.getEmail());
        verify(usersRepository, times(1)).save(user);
        verify(shoppingCartRepo, times(1)).save(any());
    }

    @Test
    void registration_Failure_UserExists() {
        User user = new User("test@example.com", "password");

        when(usersRepository.existsById(user.getEmail())).thenReturn(true);

        assertFalse(userService.registration(user));

        verify(usersRepository, times(1)).existsById(user.getEmail());
        verify(usersRepository, never()).save(any());
        verify(shoppingCartRepo, never()).save(any());
    }

    @Test
    void login_Success() {
        String email = "test@example.com";
        String password = "password";

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        when(usersRepository.findById(email)).thenReturn(Optional.of(user));

        String token = userService.login(email, password);

        assertNotNull(token);
        verify(sessionRepository, times(1)).save(any());
    }

    @Test
    void login_Failure_UserNotFound() {
        String email = "test@example.com";
        String password = "password";

        when(usersRepository.findById(email)).thenReturn(Optional.empty());

        String token = userService.login(email, password);

        assertEquals("no authorized", token);
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void login_Failure_IncorrectPassword() {
        String email = "test@example.com";
        String password = "password";
        User user = new User(email, "wrong_password");

        when(usersRepository.findById(email)).thenReturn(Optional.of(user));

        String token = userService.login(email, password);

        assertEquals("no authorized", token);
        verify(sessionRepository, never()).save(any());
    }

    @Test
    void logout_Success() {
        String token = "some_token";

        when(sessionRepository.findById(token)).thenReturn(Optional.of(new Session()));

        assertTrue(userService.logout(token));

        verify(sessionRepository, times(1)).delete(any());
    }

    @Test
    void logout_Failure_SessionNotFound() {
        String token = "some_token";

        when(sessionRepository.findById(token)).thenReturn(Optional.empty());

        assertFalse(userService.logout(token));

        verify(sessionRepository, never()).delete(any());
    }

    @Test
    void deleteUser_Success() {
        String token = "some_token";
        User user = new User("test@example.com", "password");
        Session session = new Session(token, user);

        when(sessionRepository.findById(token)).thenReturn(Optional.of(session));

        assertTrue(userService.deleteUser(token));

        verify(usersRepository, times(1)).delete(user);
    }

    @Test
    void deleteUser_Failure_SessionNotFound() {
        String token = "some_token";

        when(sessionRepository.findById(token)).thenReturn(Optional.empty());

        assertFalse(userService.deleteUser(token));

        verify(usersRepository, never()).delete(any());
    }
}

