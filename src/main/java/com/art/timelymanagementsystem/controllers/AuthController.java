package com.art.timelymanagementsystem.controllers;


import com.art.timelymanagementsystem.request.LoginRequest;
import com.art.timelymanagementsystem.services.UserAuthenticationService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserAuthenticationService userAuthenticationService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request){

        return ResponseEntity.ok(userAuthenticationService.loginService(request));

    }

}
