package com.example.GrowChild.service;

import com.example.GrowChild.entity.User;
import com.example.GrowChild.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    @Autowired
    AuthenticationRepository authenticationRepository;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthenticationService() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    //Register
    public User register(User user) {
        if (authenticationRepository.findByUsername(user.username) != null) {
            throw new IllegalArgumentException("Username is taken!");
        }
        PasswordEncoder hashPass = new BCryptPasswordEncoder(10); //password hash with hard level 10
        user.setPassword(hashPass.encode(user.password));
        return authenticationRepository.save(user); //create row in sql
    }

    //Login
    public boolean login(String username, String password) {
        User user = authenticationRepository.findByUsername(username);
        return user != null && bCryptPasswordEncoder.matches(password, user.password); //pass string encode to match pass hash
    }

    //getAllUser
    public List<User> getUser() {
        return authenticationRepository.findAll();
    }

    //getUserByID
    public User getUserById(String userID) {
        return authenticationRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("User not found")); //return no found
    }

    //update user by ID
    public User updateUser(String userId, User user) {
        User user1 = getUserById(userId); //call fun getId to match user

        user1.setFullName(user.fullName);
        user1.setGender(user.gender);
        user1.setEmail(user.email);
        user1.setPhone(user.phone);

        return authenticationRepository.save(user1);
    }

    //Delete User
    public void deleteUser(String userId) {
        authenticationRepository.deleteById(userId);
    }
}

