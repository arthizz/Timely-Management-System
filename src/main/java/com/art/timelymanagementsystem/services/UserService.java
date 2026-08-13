package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.dto.UserDto;
import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.entities.UserProfile;
import com.art.timelymanagementsystem.mappers.UserMapper;
import com.art.timelymanagementsystem.repositories.UserLevelRepository;
import com.art.timelymanagementsystem.repositories.UserRepository;
import com.art.timelymanagementsystem.request.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final UserLevelRepository userLevelRepository;
    private final UserProfileService userProfileService;
    private final UserRepository userRepository;

    public UserDto createUser(UserRequest userRequest){

//        var userLevel = userLevelRepository.findById(1L).orElseThrow(() -> new RuntimeException("User Level not exists"));
//        System.out.println(userLevel);
        var newUser = new User();

        newUser.setUserName(userRequest.getUserName());
        newUser.setEmail(userRequest.getEmail());
        newUser.setPassword(userRequest.getPassword());
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setUserLevelId(userRequest.getUserLevelId());

        return userProfileService.CreateUserProfile(userRequest, newUser);

    }

}
