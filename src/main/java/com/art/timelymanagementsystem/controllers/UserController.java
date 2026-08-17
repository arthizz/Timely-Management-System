package com.art.timelymanagementsystem.controllers;

import com.art.timelymanagementsystem.dto.ErrorResponseDto;
import com.art.timelymanagementsystem.dto.UserDto;
import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.exceptions.UserNotFoundException;
import com.art.timelymanagementsystem.mappers.UserMapper;
import com.art.timelymanagementsystem.repositories.UserProfileRepository;
import com.art.timelymanagementsystem.repositories.UserRepository;
import com.art.timelymanagementsystem.request.UpdateUserRequest;
import com.art.timelymanagementsystem.request.UserRequest;
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

        var userList =  userRepository.findAll(Sort.by(sort))
                .stream().map(userMapper::toDto)
                .toList();

        return ResponseEntity.ok(userList);

    }


    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id){

        var user = userRepository.findById(id).map(userMapper::toDto).orElseThrow(() -> new UserNotFoundException("User Not Found"));

        return ResponseEntity.ok(user);

    }

    @PostMapping
    public UserDto createNewUser(@Valid @RequestBody UserRequest request){
//        For Later
//        var user = userMapper.toEntity(request);
//        System.out.println(user);

        return userService.createUser(request);


    }

    @GetMapping("/userlevelid")
    public ResponseEntity<List<UserDto>> findUserByLevel(@RequestParam(required = false, name="userLevelId") Byte userLevelId){

        List<User> users;
        System.out.println("this is test");
        if(userLevelId != null){

            users = userRepository.findByUserLevelId(userLevelId);

        }else{

            users = userRepository.findAll();

        }

        return ResponseEntity.ok(users.stream().map(userMapper::toDto).toList());

    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable Long id,@Valid @RequestBody UserRequest userRequest){

        var user = userRepository.findById(id).orElse(null);

        if(user == null){

            return ResponseEntity.notFound().build();

        }

        return userService.updateUser(user, userRequest);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> DeleteUser(@PathVariable Long id){

        User user = userRepository.findById(id).orElse(null);

        if(user == null){

            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponseDto(404, "User Not Found"));

        }

        userRepository.delete(user);

        return ResponseEntity.status(HttpStatus.OK).body("User Successfully Deleted");

    }

}
