package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.dto.UserDto;
import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.entities.UserProfile;
import com.art.timelymanagementsystem.mappers.UserMapper;
import com.art.timelymanagementsystem.repositories.UserLevelRepository;
import com.art.timelymanagementsystem.repositories.UserRepository;
import com.art.timelymanagementsystem.request.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {

    private final UserProfileService userProfileService;
    private final PasswordEncoder passwordEncoder;

    public UserDto createUser(UserRequest userRequest){

        var newUser = new User();

        newUser.setUserName(userRequest.getUserName());
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        System.out.println(newUser);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUserLevelId(userRequest.getUserLevelId());

        return userProfileService.CreateUserProfile(userRequest, newUser);

    }

}
