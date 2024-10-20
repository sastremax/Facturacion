package edu.coderhouse.jpa.controllers;

import edu.coderhouse.jpa.entities.UserEntity;
import edu.coderhouse.jpa.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserEntity[]> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserEntity> getUserById(@PathVariable int id) {
        UserEntity user = userService.getUserById(id);

        return ResponseEntity.ok(user);
    }

    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE}, consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserEntity> saveUser(@RequestBody UserEntity user) {
        UserEntity newUser = userService.saveUser(user);

        return ResponseEntity.ok(newUser);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserEntity> updateUser(@PathVariable int id, @RequestBody UserEntity user) {
        UserEntity updatedUser = userService.updateUser(id, user);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<UserEntity> deleteUser(@PathVariable int id) {
        UserEntity deletedUser = userService.deleteUser(id);

        return ResponseEntity.ok(deletedUser);
    }

}
