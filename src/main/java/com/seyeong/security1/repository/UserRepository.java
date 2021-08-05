package com.seyeong.security1.repository;

import com.seyeong.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    // findBy규칙 -> Username 문법
    // select * from user where username = 1?
    public User findByUsername(String username); // Jpa query method

}
