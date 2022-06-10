package com.kocirfan.jwtauth.repository;

import com.kocirfan.jwtauth.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
