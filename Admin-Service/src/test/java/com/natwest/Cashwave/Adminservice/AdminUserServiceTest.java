package com.Capstone.Admin;
import com.Capstone.Admin.Entity.User;
import com.Capstone.Admin.Repository.UserRepository;
import com.Capstone.Admin.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class AdminUserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = Arrays.asList(
                new User(),
                new User()
        );

        when(userRepository.findAll())
                .thenReturn(users);

        List<User> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void testGetUserById() {
        String userId = "123";
        User user = new User();
        user.setId(userId);

        when(userRepository.findById(userId))
                .thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());

        verify(userRepository, times(1)).findById(userId);
    }
}
