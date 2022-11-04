package com.example.demo.controller;

import com.example.demo.converter.UserConverter;
import com.example.demo.entity.User;
import com.example.demo.model.UserDTO;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "api/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Save a user")
    @PostMapping(path = "/save")
    public ResponseEntity<User> saveUser(@RequestBody @Valid UserDTO userDTO) {
        log.info("saveUser is called with: {}", userDTO);
        return new ResponseEntity<>(userService.saveUser(UserConverter.convertToEntity(userDTO)), HttpStatus.CREATED);
    }

    @Operation(summary = "Get user by Id")
    @GetMapping(path = "/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable String userId) {
        log.info("getUserById is called with: {}", userId);
        return new ResponseEntity<>(userService.getByUserId(userId), HttpStatus.OK);
    }

    @Operation(summary = "Delete user by Id")
    @DeleteMapping(path = "/{userId}")
    public ResponseEntity<Boolean> deleteUserById(@PathVariable String userId) {
        log.info("deleteUserById is called with: {}", userId);
        return new ResponseEntity<>(userService.deleteByUserId(userId), HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Update user by Id")
    @PutMapping(path = "/{userId}")
    public ResponseEntity<User> updateUserById(@RequestBody @Valid UserDTO userDTO, @PathVariable String userId) {
        log.info("updateUserById is called with body: {}, and id: {}", userDTO, userId);
        return new ResponseEntity<>(
                userService.updateByUserId(userId, UserConverter.convertToEntity(userDTO)), HttpStatus.ACCEPTED
        );
    }

    @Operation(summary = "get all users")
    @GetMapping(path = "/all")
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("getAllUsers is called");
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }
}