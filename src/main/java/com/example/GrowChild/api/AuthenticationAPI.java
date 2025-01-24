package com.example.GrowChild.api;

import com.example.GrowChild.entity.User;
import com.example.GrowChild.service.AuthenticationService;
import com.example.GrowChild.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class AuthenticationAPI {
    @Autowired
    AuthenticationService authenticationService;


    @PostMapping("register")
    public ResponseEntity register(@RequestBody User user){
        User newUser = authenticationService.register(user);
        return ResponseEntity.ok(newUser); //.ok tra ve status 200-ok khi call api
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password ){
        boolean login = authenticationService.login(username, password);
        if(!login){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }else{
            return ResponseEntity.ok("Login successful");
        }
    }

    @GetMapping("getUser")
    public List<User> getUser(){
        return authenticationService.getUser();
    }

    @GetMapping("/{userId}")
    public User getUserById(@PathVariable("userId") String userId){
        return authenticationService.getUserById(userId);
    }

    //updateUserById
    @PutMapping ("/{userId}")
    public User updateUser(@PathVariable("userId") String userId,@RequestBody User user){
        return  authenticationService.updateUser(userId,user);
    }

    //deleteBId
    @DeleteMapping ("/{userId}")
    public String deleteUser(@PathVariable("userId") String userId){
        authenticationService.deleteUser(userId);
        return "Delete successful";
    }

    //change password
    @PutMapping("/changePass/{userId}")
    public ResponseEntity changePassword(@PathVariable("userId") String userId, @RequestParam String oldPassword, @RequestParam String newPas,@RequestParam String confirmPass){
        if(!authenticationService.changePassword(userId,oldPassword,newPas,confirmPass)){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Change Password false");

        }
        return ResponseEntity.ok("Successful!");

    }





}
