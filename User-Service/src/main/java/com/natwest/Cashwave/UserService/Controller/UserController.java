package com.natwest.Cashwave.UserService.Controller;

import com.natwest.Cashwave.UserService.Dto.logincred;
import com.natwest.Cashwave.UserService.Entity.User;
import com.natwest.Cashwave.UserService.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/userservice")
@CrossOrigin(origins="*")
public class UserController {

    @Autowired
    UserService userService;
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        try {
            User registeredUser = userService.registerUser(user);
            return new ResponseEntity<>(registeredUser, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody logincred loginreq) {
        String emailid = loginreq.getEmailid();
        String security_PIN = loginreq.getSecurity_PIN();
        return userService.loginUser(emailid, security_PIN);
    }

    @GetMapping("/getUser/{emailID}")
    public ResponseEntity<User> getUser(@PathVariable String emailID)
    {
        return userService.getUser(emailID);
    }

}
