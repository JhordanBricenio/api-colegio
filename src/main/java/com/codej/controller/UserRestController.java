package com.codej.controller;

import com.codej.models.User;
import com.codej.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserRestController {
    private final IUserService userService;
    @GetMapping("/users")
    public List<User> get(){
        return userService.findAll();
    }
}
