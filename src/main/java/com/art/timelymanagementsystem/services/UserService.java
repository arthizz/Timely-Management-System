package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.dto.MessageResponseDto;
import com.art.timelymanagementsystem.dto.UserDto;
import com.art.timelymanagementsystem.entities.Company;
import com.art.timelymanagementsystem.entities.CompanyRole;
import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.exceptions.BadRequestException;
import com.art.timelymanagementsystem.exceptions.ResourceNotFoundException;
import com.art.timelymanagementsystem.mappers.UserMapper;
import com.art.timelymanagementsystem.repositories.CompanyRepository;
import com.art.timelymanagementsystem.repositories.CompanyRoleRepository;
import com.art.timelymanagementsystem.repositories.UserRepository;
import com.art.timelymanagementsystem.request.CreateUserRequest;
import com.art.timelymanagementsystem.request.UpdateUserRequest;
import com.art.timelymanagementsystem.request.UserRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserProfileService userProfileService;
    private final PasswordEncoder passwordEncoder;
    private final CompanyRoleRepository companyRoleRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsersService(){

        return userRepository.findAll().stream().map(userMapper::toDto).toList();

    }

    public UserDto getSingleUserService(Long id){

        return userRepository.findById(id).map(userMapper::toDto).orElseThrow(() -> new ResourceNotFoundException("User Data Not Found"));

    }

    public List<UserDto> findByUserLevelService(Byte userLevelId){

        List<User> users;
        if(userLevelId != null){

            users = userRepository.findByUserLevelId(userLevelId);

        }else{

            users = userRepository.findAll();

        }

        return users.stream().map(userMapper::toDto).toList();

    }

    public UserDto createUser(CreateUserRequest request){

        var newUser = new User();

        newUser = this.setUserData(newUser, request);

        if(request.getPassword() != null && !request.getPassword().isBlank()){

            newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        }

        newUser = this.setCompanyAndRole(newUser, request);
        return userProfileService.CreateUserProfile(request, newUser);

    }

    public ResponseEntity<UserDto> updateUser(Long id, UpdateUserRequest request){

        var user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));

        user = this.setUserData(user, request);

        if(request.getCompanyId() != null || request.getRoleId() != null){

            user = this.setCompanyAndRole(user, request);

        }

        return userProfileService.updateUserProfile(user, request);

    }

    public MessageResponseDto deleteUserService(Long id){

        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        userRepository.delete(user);

        return new MessageResponseDto("Delete User Success!");

    }

    public User setUserData(User user, UserRequest userRequest){

        if(userRepository.existsByEmail(userRequest.getEmail())){

            throw new BadRequestException("Email already exists");

        }

        user.setUserName(userRequest.getUserName());
        user.setEmail(userRequest.getEmail());
        user.setUserLevelId(userRequest.getUserLevelId());

        if(userRequest.getHourlyRate() != null){

            user.setHourlyRate(userRequest.getHourlyRate());

        }

        return user;

    }

    public User setCompanyAndRole(User user, UserRequest userRequest){

        if((userRequest.getRoleId() != null) ^ (userRequest.getCompanyId() != null)){

            throw new BadRequestException("Company and Employee role must be provided together");

        }

        if(userRequest.getRoleId() != null){

            CompanyRole companyRole = companyRoleRepository.findById(userRequest.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("Company Role Not Found"));
            Company company = companyRepository.findById(userRequest.getCompanyId()).orElseThrow(() -> new ResourceNotFoundException("Company Not Found"));

            if(!companyRole.getCompany().getId().equals(company.getId())){

                throw new BadRequestException("Role does not belong to the selected company");

            }

            user.setRole(companyRole);
            user.setCompany(company);

        }

        return user;

    }

}
