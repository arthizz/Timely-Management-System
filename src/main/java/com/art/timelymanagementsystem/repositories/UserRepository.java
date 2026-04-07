package com.art.timelymanagementsystem.repositories;

import com.art.timelymanagementsystem.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
