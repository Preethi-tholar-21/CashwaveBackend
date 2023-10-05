package com.natwest.Cashwave.Adminservice.Service;

import com.natwest.Cashwave.Adminservice.Entity.User;
import com.natwest.Cashwave.Adminservice.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> createUsers(List<User> users) {
        return userRepository.saveAll(users);
    }

    public User getUserByaccountNumber(String accountNumber) {
        return userRepository.findByaccountNumber(accountNumber);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
