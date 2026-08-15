package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.dto.UserDto;
import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.entities.UserProfile;
import com.art.timelymanagementsystem.mappers.UserMapper;
import com.art.timelymanagementsystem.repositories.UserLevelRepository;
import com.art.timelymanagementsystem.repositories.UserRepository;
import com.art.timelymanagementsystem.request.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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

        newUser = this.setUserData(newUser, userRequest);

        return userProfileService.CreateUserProfile(userRequest, newUser);

    }

    public ResponseEntity<UserDto> updateUser(User user, UserRequest userRequest){

        user = this.setUserData(user, userRequest);

        return userProfileService.updateUserProfile(user, userRequest);

    }

    public User setUserData(User user, UserRequest userRequest){

        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());

        user.setCreatedAt(LocalDateTime.now());
        user.setUserLevelId(userRequest.getUserLevelId());

        if(userRequest.getPassword() != null && !userRequest.getPassword().isBlank()){

            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        }

        return user;

    }

}
