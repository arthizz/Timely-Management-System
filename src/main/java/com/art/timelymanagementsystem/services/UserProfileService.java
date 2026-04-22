package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.dto.UserProfileDto;
import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.entities.UserProfile;
import com.art.timelymanagementsystem.mappers.UserProfileMapper;
import com.art.timelymanagementsystem.repositories.UserProfileRepository;
import com.art.timelymanagementsystem.request.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor

public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final UserProfileMapper userProfileMapper;

    public UserProfileDto CreateUserProfile(UserRequest userRequest, User newUser){

        var newUserProfile = new UserProfile();

        newUserProfile.setFirstName(userRequest.getFirstName());
        newUserProfile.setLastName(userRequest.getLastName());
        newUserProfile.setMiddleName(userRequest.getMiddleName());
        newUserProfile.setAddress(userRequest.getAddress());
        newUserProfile.setProfilePicture(userRequest.getProfilePicture());

        var createdUserProfile = userProfileRepository.save(newUserProfile);

        return userProfileMapper.toDto(createdUserProfile);

    }

}
