package com.art.timelymanagementsystem.repositories;

import com.art.timelymanagementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUserLevelId(Byte userLevelId);

    Boolean existsByEmail(String email);
}
