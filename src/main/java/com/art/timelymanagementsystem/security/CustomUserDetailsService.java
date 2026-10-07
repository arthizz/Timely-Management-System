package com.art.timelymanagementsystem.security;

import com.art.timelymanagementsystem.entities.User;
import com.art.timelymanagementsystem.entities.UserLevel;
import com.art.timelymanagementsystem.exceptions.ResourceNotFoundException;
import com.art.timelymanagementsystem.repositories.UserLevelRepository;
import com.art.timelymanagementsystem.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{

    private final UserRepository userRepository;
    private final UserLevelRepository userLevelRepository;

    @Override
    public UserDetails loadUserByUsername(String username){

        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        UserLevel userLevel = userLevelRepository.findById(user.getUserLevel().getId()).orElseThrow(() -> new UsernameNotFoundException("User Level Not Found"));

        return org.springframework.security.core.userdetails.User.
                withUsername(user.getEmail()).
                password(user.getPassword()).
                authorities(userLevel.getUserLevelName())
                .build();

    }

}
