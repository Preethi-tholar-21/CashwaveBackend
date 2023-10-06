package com.natwest.Cashwave.UserService.Service;

import com.natwest.Cashwave.UserService.Entity.User;
import com.natwest.Cashwave.UserService.Repository.UserRepository;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public User registerUser(User user) {
        // Perform validation and other business logic if needed
        return userRepository.save(user);
    }

    private String hashPassword(String password, String salt) {
        // You can use a secure hashing algorithm here (e.g., bcrypt)
        // For simplicity, let's assume you're using SHA-256
        String saltedPassword = password + salt;
        // Perform the hashing (you may want to use a more secure algorithm)
        return DigestUtils.sha256Hex(saltedPassword);
    }

    public ResponseEntity<User> loginUser(String emailid, String password) {
        User user = userRepository.findByEmailid(emailid);
        if (user != null) {
            String storedSalt = user.getSalt(); // Retrieve the salt from the user's record
            String storedHashedPIN = user.getSecurity_PIN();
            if (storedSalt != null && storedHashedPIN != null) {
                // Calculate the hash of the entered security PIN using the stored salt
                String calculatedHashedPIN = hashPassword(password, storedSalt);

                // Check if the calculated hash matches the stored hash
                if (calculatedHashedPIN.equals(storedHashedPIN)) {
                    return new ResponseEntity<>(user, HttpStatus.OK); // PIN is correct
                }
                else {
                    return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
                }
            }
            else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }


    public ResponseEntity<User> getUser(String emailID) {
        try {
            User user = userRepository.findByEmailid(emailID);

            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                // User not found, return an appropriate response, e.g., HttpStatus.NOT_FOUND
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
        } catch (Exception e) {
            // Handle any other exceptions that might occur, e.g., database errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}


