package com.fgreau.offertechtest.controllers;

import com.fgreau.offertechtest.dtos.UserDto;
import com.fgreau.offertechtest.mappers.UserMapper;
import com.fgreau.offertechtest.models.User;
import com.fgreau.offertechtest.repositories.UserRepository;
import com.fgreau.offertechtest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @PostMapping(consumes = "application/json")
    public ResponseEntity<Object> createUser(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.registerUser(userDto));
    }

    @GetMapping(path = "/{username}", produces = "application/json")
    public ResponseEntity<Object> getUser(@PathVariable String username) {
        final Optional<User> user = userRepository.findByUsernameAndDeletedFalse(username);
        final UserDto dto = user.map(userMapper::map).orElse(null);
        if (dto != null) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
