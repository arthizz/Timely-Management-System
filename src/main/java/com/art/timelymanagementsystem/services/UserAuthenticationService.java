package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.request.LoginRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAuthenticationService {

    private final AuthenticationManager authenticationManager;

    public String loginService(LoginRequest loginRequest){

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        return "Login Success";

    }

}
