package com.example.GrowChild.api;

import com.example.GrowChild.entity.User;
import com.example.GrowChild.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class AuthenticationAPI {
    @Autowired
    AuthenticationService authenticationService;


    @PostMapping("register/{role_id}")
    public ResponseEntity register(@RequestBody User user, @PathVariable long role_id) {
        User newUser = authenticationService.register(user, role_id);
        return ResponseEntity.ok(newUser); //.ok tra ve status 200-ok khi call api
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password) {
        try {
            User user = new User() ;
            if(username.contains("@gmail.com")){
                user = authenticationService.loginByEmail(username,password);
            }else{
                user = authenticationService.loginByUsername(username,password);
            }
            if(user == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username/email or password ");
            }
            return ResponseEntity.ok("Role: " + user.role.roleName);
        } catch (Exception e) {
            e.printStackTrace(); // Log the full stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }

    }

    @GetMapping("getUser")
    public List<User> getUser() {
        return authenticationService.getUser();
    }

    @GetMapping("getUserById/{userId}")
    public User getUserById(@PathVariable("userId") String userId) {
        return authenticationService.getUserById(userId);
    }

    //updateUserById
    @PutMapping("updateUser/{userId}")
    public User updateUser(@PathVariable("userId") String userId, @RequestBody User user) {
        return authenticationService.updateUser(userId, user);
    }

    //deleteBId
    @DeleteMapping("deleteUser/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {
        authenticationService.deleteUser(userId);
        return "Delete successful";
    }

    //change password
    @PutMapping("/changePass/{userId}")
    public ResponseEntity changePassword(@PathVariable("userId") String userId, @RequestParam String oldPassword, @RequestParam String newPas, @RequestParam String confirmPass) {
        if (!authenticationService.changePassword(userId, oldPassword, newPas, confirmPass)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Change Password false");

        }
        return ResponseEntity.ok("Successful!");

    }

    @GetMapping("roleCheck/{user_id}")
    public String checkRole(@PathVariable String user_id) {
        return authenticationService.checkRole(user_id);
    }

    @GetMapping("getUserByRoleId/{role_id}")
    public List<User> getUserByRole(@PathVariable long role_id) {
        return authenticationService.getUserByRole(role_id);
    }

    @GetMapping("getUserByRoleName/{role_name}")
    public List<User> getUserByRoleName(@PathVariable String role_name){
        return authenticationService.getUserByRoleName(role_name);
    }
}
