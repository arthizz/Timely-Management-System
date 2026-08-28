package com.art.timelymanagementsystem.controllers;

import com.art.timelymanagementsystem.dto.MessageResponseDto;
import com.art.timelymanagementsystem.dto.UserDto;
import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.exceptions.ResourceNotFoundException;
import com.art.timelymanagementsystem.mappers.UserMapper;
import com.art.timelymanagementsystem.repositories.UserProfileRepository;
import com.art.timelymanagementsystem.repositories.UserRepository;
import com.art.timelymanagementsystem.request.CreateUserRequest;
import com.art.timelymanagementsystem.request.UpdateUserRequest;
import com.art.timelymanagementsystem.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(required = false, defaultValue = "id") String sort){

        return ResponseEntity.ok(userService.getAllUsersService());

    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){

        return ResponseEntity.ok(userService.getSingleUserService(id));

    }

    @PostMapping
    public UserDto createNewUser(@Valid @RequestBody CreateUserRequest request){

        return userService.createUser(request);


    }

    @GetMapping("/userlevelid")
    public ResponseEntity<List<UserDto>> findUserByLevel(@RequestParam(required = false, name="userLevelId") Byte userLevelId){

        return ResponseEntity.ok(userService.findByUserLevelService(userLevelId));

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,@Valid @RequestBody UpdateUserRequest request){

        return userService.updateUser(id, request);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponseDto> DeleteUser(@PathVariable Long id){

        return ResponseEntity.ok(userService.deleteUserService(id));

    }

}
