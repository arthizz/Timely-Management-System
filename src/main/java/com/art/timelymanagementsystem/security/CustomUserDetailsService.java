package com.art.timelymanagementsystem.security;

import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.exceptions.ResourceNotFoundException;
import com.art.timelymanagementsystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){

        User user = userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail()).password(user.getPassword()).authorities("USER").build();

    }

}
