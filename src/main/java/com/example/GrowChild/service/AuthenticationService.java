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

    //change password
    public boolean changePassword(String userId, String oldPassword, String newPassword,String confirmPassword ){
        if(userId == null || oldPassword == null || newPassword == null || confirmPassword == null ){
            throw new IllegalArgumentException("Input not null !");
        }
        User user = getUserById(userId);
        if(user == null){
             throw new IllegalArgumentException("User not found !");
        }

        if(!new BCryptPasswordEncoder().matches(oldPassword,user.password)){
            throw  new IllegalArgumentException("Old password incorrect");
        };
        if(!newPassword.equals(confirmPassword)){
            throw new IllegalArgumentException("New password and confirm password must same!");
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));
        authenticationRepository.save(user);
        return true;
    }
}

