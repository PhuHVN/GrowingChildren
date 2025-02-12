package com.example.GrowChild.api;

import com.example.GrowChild.dto.UserDTO;
import com.example.GrowChild.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthenticationAPI {

    @Autowired
    UserService userService;

    @PostMapping("login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password) {
        try {
            UserDTO user ;
            if(username.contains("@")){ //check is email?
                user = userService.loginByEmail(username,password);
            }else{
                user = userService.loginByUsername(username,password);
            }
            if(user == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username/email or password ");
            }
            return ResponseEntity.ok("Role: " + user.getRoleName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }

    }
}
