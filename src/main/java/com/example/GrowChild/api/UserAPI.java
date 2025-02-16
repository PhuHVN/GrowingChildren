package com.example.GrowChild.api;

import com.example.GrowChild.dto.UserDTO;
import com.example.GrowChild.entity.User;
import com.example.GrowChild.service.UserService;
import com.example.GrowChild.service.EmailSenderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("api")
public class UserAPI {
    @Autowired
    UserService userService;
    @Autowired
    EmailSenderService senderService;

    @PostMapping("register/{role_id}")
    public String register(@Valid @RequestBody User user, @PathVariable long role_id) {
    try {
        User newUser = userService.register(user, role_id);

        if(newUser != null){ // username return != null
            return "ok " + newUser.toString(); //.ok tra ve status 200-ok khi call api
        }
        return "user register by Gmail"; // return
    } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
    }

    }


    @GetMapping("getByGmail")
    public User getUserByGmail(@RequestParam String email){
        return userService.getUserByGmail(email);
    }
    @GetMapping("getUser")
    public List<UserDTO> getUser() {
        return userService.getUser();
    }

    @GetMapping("getUserById/{userId}")
    public UserDTO getUserById(@PathVariable("userId") String userId) {
        return userService.getUserById(userId);
    }

    //updateUserById
    @PutMapping("updateUser/{userId}")
    public UserDTO updateUser(@PathVariable("userId") String userId, @RequestBody User user) {
        return userService.updateUser(userId, user);
    }

    //deleteBId

    @DeleteMapping("deleteUser/{userId}")
    public String deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
        return "Delete successful";
    }

    //change password
    @PutMapping("/changePass/{userId}")
    public ResponseEntity changePassword(@PathVariable("userId") String userId, @RequestParam String oldPassword, @RequestParam String newPas, @RequestParam String confirmPass) {
        if (!userService.changePassword(userId, oldPassword, newPas, confirmPass)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Change Password false");

        }
        return ResponseEntity.ok("Successful!");

    }

    @GetMapping("roleCheck/{user_id}")
    public String checkRole(@PathVariable String user_id) {
        return userService.checkRole(user_id);
    }

    @GetMapping("getUserByRoleId/{role_id}")
    public List<UserDTO> getUserByRole(@PathVariable long role_id) {
        return userService.getUserByRole(role_id);
    }

    @GetMapping("getUserByRoleName/{role_name}")
    public List<UserDTO> getUserByRoleName(@PathVariable String role_name){
        return userService.getUserByRoleName(role_name);
    }

    @PostMapping("verifyOtp")
    public String verifyOtp(@RequestParam String email, @RequestParam String enterCode){

            return userService.verifyOtp(email, enterCode);
    }

    @GetMapping("/findByChildren/{childrenId}")
    public ResponseEntity<?> getUserByChildrenId(@PathVariable Long childrenId) {
        try {
            User user = userService.getUserByChildrenId(childrenId);
            return ResponseEntity.ok(toDTO(user));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    private UserDTO toDTO(User user) {
        return UserDTO.builder()
                .user_id(user.getUser_id())  // UUID -> String
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }
}
