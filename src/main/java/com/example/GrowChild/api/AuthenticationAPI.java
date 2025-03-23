package com.example.GrowChild.api;

import com.example.GrowChild.dto.UserDTO;
import com.example.GrowChild.entity.request.LoginRequest;
import com.example.GrowChild.service.UserService;
import jakarta.validation.Valid;
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
    public ResponseEntity login(@Valid @RequestBody LoginRequest loginRequest) {

        try {
            UserDTO user;
            if (loginRequest.getUsernameOrEmail().contains("@")) { //check is email?
                user = userService.loginByEmail(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
            } else {
                user = userService.loginByUsername(loginRequest.getUsernameOrEmail(), loginRequest.getPassword());
            }
            if (user == null || user.isDelete()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username/email or password ");
            }
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }

    }

    @PostMapping("resetPassword")
    public ResponseEntity resetPassword(@RequestParam String userId,@RequestParam String email,@RequestParam String code,@RequestParam String newPassword,@RequestParam String confirmPassword){
        if(userService.resetPassword(userId, email, code, newPassword, confirmPassword)){
            return ResponseEntity.ok("Password reset successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password reset failed");
    }

    @PostMapping("forgotPasswordSender")
    public ResponseEntity sendEmail(@RequestParam String email,@RequestParam String userId){
        if(userService.resetPasswordEmailSender(userId,email)){
            return ResponseEntity.ok("Email sent successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email sent failed");
    }

}
