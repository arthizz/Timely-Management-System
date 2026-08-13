package com.art.timelymanagementsystem.repositories;

import com.art.timelymanagementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByUserLevelId(Byte userLevelId);

}
