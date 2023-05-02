package com.codej.controller;

import com.codej.models.User;
import com.codej.services.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/users")
    public ResponseEntity<?> save(@RequestBody User user){
        return userService.save(user);
    }

    @GetMapping("/users/{id}")
    public User getId(@PathVariable Integer id){
        return userService.findById(id);
    }
    @PutMapping("/users/{id}")
    public User update( @RequestBody User user){
        return userService.update(user);
    }

    @DeleteMapping("/users/{id}")
    public void delete(@PathVariable Integer id){
        userService.delete(id);
    }
}
