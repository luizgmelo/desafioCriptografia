package com.example.desafiocriptografia.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafiocriptografia.dtos.UserDto;
import com.example.desafiocriptografia.services.UserService;

@RestController
@RequestMapping("users")
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping("{id}")
  public ResponseEntity<UserDto> getOneUser(@PathVariable("id") Long userId) {
    UserDto user = userService.getUserWithDataSensitive(userId);
    return ResponseEntity.status(HttpStatus.OK).body(user);
  }

  @PostMapping
  public ResponseEntity<UserDto> createNewUser(@RequestBody UserDto userDto) {
    UserDto user = userService.createUserWithDataSensitive(userDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

  @PutMapping("{id}")
  public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId,
      @RequestBody UserDto userDto) {
    UserDto user = userService.updateUserWithDataSensitive(userId, userDto);
    return ResponseEntity.status(HttpStatus.OK).body(user);
  }

  @DeleteMapping("{id}")
  public ResponseEntity<Void> deleteUser(@PathVariable("id") Long userId) {
    userService.deleteUserWithDataSensitive(userId);
    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
