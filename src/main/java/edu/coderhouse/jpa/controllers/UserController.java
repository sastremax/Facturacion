package edu.coderhouse.jpa.controllers;

import edu.coderhouse.jpa.entities.UserEntity;
import edu.coderhouse.jpa.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserEntity[]> getExternalUsers() {
        ResponseEntity<UserEntity[]> users = userService.getUsers();
        return users;
    }

}
