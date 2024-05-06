package com.veerajk.demo.controller;

import com.veerajk.demo.model.User;
import com.veerajk.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@CrossOrigin("http://localhost:3000/")
public class UserController {

    @Autowired
    UserService userService;
    @GetMapping("/api/users")
    public ResponseEntity<List<User>> getUsers(){
        return userService.getAllUsers();
    }
}
