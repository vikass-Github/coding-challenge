package com.coding.Java.Coding.Challenge2.controller;

import com.coding.Java.Coding.Challenge2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/{userId}/tasks")
    public void userAssignedTasks(@PathVariable Long userId) {
        userService.userAssignedTasks(userId);
    }
}
