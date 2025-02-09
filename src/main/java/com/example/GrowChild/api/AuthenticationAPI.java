package com.example.GrowChild.api;

import com.example.GrowChild.dto.UserDTO;
import com.example.GrowChild.entity.User;
import com.example.GrowChild.service.AuthenticationService;
import com.example.GrowChild.service.EmailSenderService;
import com.example.GrowChild.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api")
public class AuthenticationAPI {
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    EmailSenderService senderService;

    @PostMapping("register/{role_id}")
    public String register(@Valid @RequestBody User user, @PathVariable long role_id) {
    try {
        User newUser = authenticationService.register(user, role_id);
        if(newUser != null){ // username return != null
            return "ok " + newUser.toString(); //.ok tra ve status 200-ok khi call api
        }
        return "user register by Gmail"; // return
    } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
    }

    }

    @PostMapping("login")
    public ResponseEntity login(@RequestParam String username, @RequestParam String password) {
        try {
            UserDTO user = new UserDTO() ;
            if(username.contains("@gmail.com")){
                user = authenticationService.loginByEmail(username,password);
            }else{
                user = authenticationService.loginByUsername(username,password);
            }
            if(user == null){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username/email or password ");
            }
            return ResponseEntity.ok("Role: " + user.getRoleName());
        } catch (Exception e) {
            e.printStackTrace(); // Log the full stack trace for debugging
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred: " + e.getMessage());
        }

    }

    @GetMapping("getUser")
    public List<UserDTO> getUser() {
        return authenticationService.getUser();
    }

    @GetMapping("getUserById/{userId}")
    public UserDTO getUserById(@PathVariable("userId") String userId) {
        return authenticationService.getUserById(userId);
    }

    //updateUserById
    @PutMapping("updateUser/{userId}")
    public UserDTO updateUser(@PathVariable("userId") String userId, @RequestBody User user) {
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
    public List<UserDTO> getUserByRole(@PathVariable long role_id) {
        return authenticationService.getUserByRole(role_id);
    }

    @GetMapping("getUserByRoleName/{role_name}")
    public List<UserDTO> getUserByRoleName(@PathVariable String role_name){
        return authenticationService.getUserByRoleName(role_name);
    }

    @PostMapping("verifyOtp")
    public String verifyOtp(@RequestParam String email, @RequestParam String enterCode){

            return authenticationService.verifyOtp(email, enterCode);
    }
}
