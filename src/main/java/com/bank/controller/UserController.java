package com.bank.controller;

import com.bank.service.UserService;
import com.bank.user.User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    //  constructor injection
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable String username) {
        return userService.getByUsername(username);
    }
}

