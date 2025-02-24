package com.example.GrowChild.entity.request;

import lombok.Getter;

@Getter
public class LoginRequest {
    String usernameOrEmail;
    String password;
}
