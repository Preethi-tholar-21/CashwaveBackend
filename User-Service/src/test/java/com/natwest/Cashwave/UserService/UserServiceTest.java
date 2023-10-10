package com.natwest.Cashwave.UserService;
import com.natwest.Cashwave.UserService.Entity.User;
import com.natwest.Cashwave.UserService.Repository.UserRepository;
import com.natwest.Cashwave.UserService.Service.UserService;
import jakarta.mail.internet.MimeMessage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MongoTemplate mongoTemplate;

    @Mock
    private JavaMailSender javaMailSender;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testRegisterUser() {
        User user = new User();
        // Set user properties as needed

        when(userRepository.save(user)).thenReturn(user);

        User registeredUser = userService.registerUser(user);

        verify(userRepository, times(1)).save(user);
        assertNotNull(registeredUser);
        // You can add more assertions based on your business logic
    }

//    @Test
//    public void testLoginUserWithValidCredentials() {
//        // Mock a user with valid credentials
//        User user = new User();
//        user.setEmailid("test@example.com");
//        user.setSecurity_PIN("hashed_password"); // Replace with the actual hashed password
//        user.setSalt("salt"); // Replace with the actual salt
//
//        when(userRepository.findByEmailid(eq("test@example.com"))).thenReturn(user);
//
//        ResponseEntity<User> response = userService.loginUser("test@example.com", "password");
//
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(user, response.getBody());
//    }

    @Test
    public void testLoginUserWithInvalidCredentials() {
        // Mock a user with invalid credentials
        when(userRepository.findByEmailid(anyString())).thenReturn(null);

        ResponseEntity<User> response = userService.loginUser("nonexistent@example.com", "password");

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals(null, response.getBody());
    }
}