package com.art.timelymanagementsystem.controllers;

import com.art.timelymanagementsystem.dto.UserDto;
import com.art.timelymanagementsystem.entities.TimeLog;
import com.art.timelymanagementsystem.entities.UserProfile;
import com.art.timelymanagementsystem.mappers.UserMapper;
import com.art.timelymanagementsystem.repositories.UserProfileRepository;
import com.art.timelymanagementsystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers(@RequestParam(required = false, defaultValue = "id") String sort){

        userRepository.findAll().forEach(user -> {
            TimeLog timeLog = user.getTimeLogs().getFirst();

            if(timeLog != null){

                System.out.println("Time In: " + timeLog.getTimeIn());
                System.out.println("Time Out: " + timeLog.getTimeOut());

            }

        });

        return userRepository.findAll(Sort.by(sort))
                .stream().map(userMapper::toDto)
                .toList();

    }

    @PostMapping
    public UserDto createNewUser(@RequestBody UserDto data){

        return data;

    }

}
