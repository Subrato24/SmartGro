package com.smartgro.smartgro.controller;

import com.smartgro.smartgro.dto.UserDto;
import com.smartgro.smartgro.entity.User;
import com.smartgro.smartgro.mapper.UserMapper;
import com.smartgro.smartgro.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private final UserRepository userRepository;

    @GetMapping
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    @PostMapping
    public User createUser(@RequestBody UserDto userDto){
        UserDto user = new UserDto();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPasswordHash(userDto.getPasswordHash());

        return userRepository.save(UserMapper.toEntity(user));
    }

    @PostMapping("/login")
    public User logIn(@RequestBody UserDto userDto){
        UserDto user = UserMapper.toDto(userRepository.findByEmail(userDto.getEmail()).orElseThrow(()->new RuntimeException("User not found")));
        if (!user.getPasswordHash().equals(userDto.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        return UserMapper.toEntity(user);
    }

}
