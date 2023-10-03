package com.natwest.Cashwave.UserService.Controller;

import com.natwest.Cashwave.UserService.Entity.User;
import com.natwest.Cashwave.UserService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userservice")
@CrossOrigin(origins="*")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/addUser")
    public User addUser(@RequestBody User user)
    {
        return userService.addUser(user);
    }

    @GetMapping("/getUser/{user_id}")
    public ResponseEntity<?> getUser(@PathVariable String user_id)
    {
        return userService.getUser(user_id);
    }
}
