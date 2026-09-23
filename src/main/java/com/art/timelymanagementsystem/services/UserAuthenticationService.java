package com.art.timelymanagementsystem.services;

import com.art.timelymanagementsystem.request.LoginRequest;
import com.art.timelymanagementsystem.security.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserAuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public String loginService(LoginRequest loginRequest) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        return jwtService.generateJwt(loginRequest.getEmail());

    }

}
