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
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
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

        newUserProfile = this.setUserProfileData(newUserProfile, userRequest);

        newUserProfile.setUser(newUser);
        newUser.setUserProfile(newUserProfile);

        User createdUser = userRepository.save(newUser);
        UserProfile createdUserProfile = userProfileRepository.save(newUserProfile);


        return userMapper.toDto(createdUser);

    }

    public ResponseEntity<UserDto> updateUserProfile(User user, UserRequest userRequest){

        var userProfile = user.getUserProfile();

        userProfile = this.setUserProfileData(userProfile, userRequest);

        User updatedUser = userRepository.save(user);
        UserProfile updatedUserProfile = userProfileRepository.save(userProfile);

        return ResponseEntity.ok(userMapper.toDto(updatedUser));

    }

    public UserProfile setUserProfileData(UserProfile userProfile, UserRequest userRequest){

        userProfile.setFirstName(userRequest.getFirstName());
        userProfile.setLastName(userRequest.getLastName());
        userProfile.setMiddleName(userRequest.getMiddleName());
        userProfile.setAddress(userRequest.getAddress());
        userProfile.setProfilePicture(userRequest.getProfilePicture());
        userProfile.setCreatedAt(LocalDateTime.now());

        return userProfile;

    }

}
