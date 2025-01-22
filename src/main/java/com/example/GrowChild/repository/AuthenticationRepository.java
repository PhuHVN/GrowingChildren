package com.example.GrowChild.repository;


import com.example.GrowChild.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthenticationRepository extends JpaRepository<User,String> {
    User findByUsername(String username);
}
