package com.example.GrowChild.service;

import com.example.GrowChild.entity.User;
import com.example.GrowChild.repository.AuthenticationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationService {
    @Autowired
    AuthenticationRepository authenticationRepository;

    //getAllUser
    public List<User> getUser(){
        return authenticationRepository.findAll();
    }

}
