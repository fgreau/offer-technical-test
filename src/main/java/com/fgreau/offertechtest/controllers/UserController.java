package com.fgreau.offertechtest.controllers;

import com.fgreau.offertechtest.dtos.UserDto;
import com.fgreau.offertechtest.mappers.UserMapper;
import com.fgreau.offertechtest.models.User;
import com.fgreau.offertechtest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping(path = "/{username}", produces = "application/json")
    public ResponseEntity<UserDto> getUser(@PathVariable String username) {
        final Optional<User> user = userRepository.findById(username);
        final UserDto dto = user.map(userMapper::map).orElse(null);
        return ResponseEntity.ok(dto);
    }
}
