package com.natwest.Cashwave.UserService.Service;

import com.natwest.Cashwave.UserService.Entity.User;
import com.natwest.Cashwave.UserService.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User addUser(User user)
    {
        return userRepository.save(user);
    }

    public ResponseEntity<?> getUser(String userId) {
        User user = userRepository.findById(userId).orElse(null);

        if (user != null) {
            // Return the user as a ResponseEntity with HTTP status OK (200)
            return ResponseEntity.ok(user);
        } else {
            // If the user is not found, return a ResponseEntity with HTTP status NOT FOUND (404)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found with ID: " + userId);
        }
    }

}
