package com.natwest.Cashwave.UserService;
import com.natwest.Cashwave.UserService.Controller.UserController;
import com.natwest.Cashwave.UserService.Dto.logincred;
import com.natwest.Cashwave.UserService.Entity.User;
import com.natwest.Cashwave.UserService.Repository.UserRepository;
import com.natwest.Cashwave.UserService.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class UserControllerTest {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser() {
        User user = new User();
        // Set user properties as needed

        when(userService.registerUser(user)).thenReturn(user);

        ResponseEntity<User> response = userController.registerUser(user);

        verify(userService, times(1)).registerUser(user);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testCheckEmail() {
        String email = "test@example.com";

        when(userService.isUserEmailUnique(email)).thenReturn(true);

        ResponseEntity<Boolean> response = userController.checkEmail(email);

        verify(userService, times(1)).isUserEmailUnique(email);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody());
    }

    @Test
    public void testLoginUser() {
        logincred loginreq = new logincred();
        // Set loginreq properties as needed

        User user = new User();
        // Set user properties as needed

        when(userService.loginUser(loginreq.getEmailid(), loginreq.getSecurity_PIN())).thenReturn(new ResponseEntity<>(user, HttpStatus.OK));

        ResponseEntity<User> response = userController.loginUser(loginreq);

        verify(userService, times(1)).loginUser(loginreq.getEmailid(), loginreq.getSecurity_PIN());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}