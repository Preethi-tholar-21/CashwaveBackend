package com.natwest.Cashwave.Adminservice.Controller;

import com.natwest.Cashwave.Adminservice.Entity.User;
import com.natwest.Cashwave.Adminservice.Service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/users")
public class AdminController {
    private final UserService userService;
    public AdminController(UserService userService) {
        this.userService = userService;
    }



    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping("/createUsers")
    public List<User> createUsers(@RequestBody Map<String, List<User>> request) {
        List<User> users = request.get("users");
        // Process and save the list of users
        return userService.createUsers(users);
    }
//    @PostMapping
//    public List<User> createUsers(@RequestBody List<User> users) {
//        // Process and save the list of users
//        return userService.createUsers(users);
//    }

    @GetMapping("/{accountNumber}")
    public User getUserByaccountNumber(@PathVariable String accountNumber) {
        return userService.getUserByaccountNumber(accountNumber);

    }


    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }


}





