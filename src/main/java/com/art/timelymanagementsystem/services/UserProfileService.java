package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.dto.UserDto;
import com.art.timelymanagementsystem.dto.UserProfileDto;
import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.entities.UserProfile;
import com.art.timelymanagementsystem.mappers.UserMapper;
import com.art.timelymanagementsystem.mappers.UserProfileMapper;
import com.art.timelymanagementsystem.repositories.UserProfileRepository;
import com.art.timelymanagementsystem.repositories.UserRepository;
import com.art.timelymanagementsystem.request.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor

public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto CreateUserProfile(UserRequest userRequest, User newUser){

        var newUserProfile = new UserProfile();

        newUserProfile.setFirstName(userRequest.getFirstName());
        newUserProfile.setLastName(userRequest.getLastName());
        newUserProfile.setMiddleName(userRequest.getMiddleName());
        newUserProfile.setAddress(userRequest.getAddress());
        newUserProfile.setProfilePicture(userRequest.getProfilePicture());
        newUserProfile.setCreatedAt(LocalDateTime.now());

        newUserProfile.setUser(newUser);
        newUser.setUserProfile(newUserProfile);

        User createdUser = userRepository.save(newUser);
        UserProfile createdUserProfile = userProfileRepository.save(newUserProfile);


        return userMapper.toDto(createdUser);

    }

}
