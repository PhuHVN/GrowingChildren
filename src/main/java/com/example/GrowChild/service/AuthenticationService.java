package com.example.GrowChild.service;

import com.example.GrowChild.entity.OTP;
import com.example.GrowChild.entity.Role;
import com.example.GrowChild.entity.User;
import com.example.GrowChild.repository.AuthenticationRepository;
import com.example.GrowChild.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AuthenticationService {
    @Autowired
    AuthenticationRepository authenticationRepository;
    @Autowired
    EmailSenderService senderService;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    RoleService roleService;

    BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthenticationService() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    //Register
    public User register(User user, long role_id) {
//        if (authenticationRepository.findByUsername(user.username) != null) {
//            throw new IllegalArgumentException("Username is taken!");
//        }
        if((user.username == null || user.username.isEmpty()) && !user.email.isEmpty()){
            String otp = senderService.generateCode();
            senderService.saveOTP(user.email, otp);
            senderService.sendEmailWithHtml(user.email,otp);
        }
        Role role = roleRepository.findById(role_id)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRole(role);

        PasswordEncoder hashPass = new BCryptPasswordEncoder(10); //password hash with hard level 10
        user.setPassword(hashPass.encode(user.password));

        return authenticationRepository.save(user); //create row in db
    }

    //Login
    public User loginByUsername(String username, String password) {

        User user = authenticationRepository.findByUsername(username);

        if(user != null && bCryptPasswordEncoder.matches(password, user.password)){
            return user;
        }; //pass string encode to match pass hash
        return null;
    }

    public User loginByEmail(String email, String password){
        User user =  authenticationRepository.findByEmail(email);
        if(user != null && bCryptPasswordEncoder.matches(password, user.password)){
            return user;
        }
        return null;
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
        if(userId == null || oldPassword == null || newPassword == null || confirmPassword == null ){ //check value not null
            throw new IllegalArgumentException("Input not null !");
        }
        User user = getUserById(userId); // find user by id
        if(user == null){
             throw new IllegalArgumentException("User not found !");
        }

        if(!new BCryptPasswordEncoder().matches(oldPassword,user.password)){ // check pass user to match with pass input when encode
            throw  new IllegalArgumentException("Old password incorrect");
        };

        if(!newPassword.equals(confirmPassword)){ //check new pass = confirm pass
            throw new IllegalArgumentException("New password and confirm password must same!");
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));  // update new pass
        authenticationRepository.save(user); // update row
        return true;
    }

    //Check role user by userId
    public String checkRole(String user_id){
        User user = getUserById(user_id);
        return user.role.roleName;
    }

    //Get User by RoleID
    public List<User> getUserByRole(long role_id){
        return authenticationRepository.findByRole_RoleId(role_id);

    }

    public List<User> getUserByRoleName(String roleName){
        return authenticationRepository.findByRole_RoleName(roleName);
    }
}

