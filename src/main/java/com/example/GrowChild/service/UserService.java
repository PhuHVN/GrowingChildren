package com.example.GrowChild.service;

import com.example.GrowChild.dto.UserDTO;
import com.example.GrowChild.entity.enumStatus.MembershipType;
import com.example.GrowChild.entity.respone.OTP;
import com.example.GrowChild.entity.respone.Role;
import com.example.GrowChild.entity.respone.User;
import com.example.GrowChild.mapstruct.toDTO.UserToDTO;
import com.example.GrowChild.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailSenderService senderService;
    @Autowired
    RoleService roleService;
    @Autowired
    UserToDTO userToDTO;
    @Autowired
    MembershipService membershipService;


    BCryptPasswordEncoder bCryptPasswordEncoder;

    Map<String, User> storeUser = new HashMap<>(); //store User with key email

    public UserService() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    //Register
    public User register(User user, long role_id) {
        if(userRepository.findByUsername(user.getUsername()) != null){
            throw new IllegalArgumentException("Username: " + user.getUsername() + " is exist!");
        }
        if(user.getEmail() != null && userRepository.existsByEmail(user.getEmail())){
            throw new IllegalArgumentException("Email: "+user.getEmail()+" is exist!");
        }

        user.setDelete(false);

        //check role exist
        Role role = roleService.getRoleExisted(role_id);
        if (role == null) {
            throw new RuntimeException("Role not found");
        }
        user.setRole(role);

        //hash password
        PasswordEncoder hashPass = new BCryptPasswordEncoder(10); //password hash with hard level 10
        user.setPassword(hashPass.encode(user.getPassword()));
        if(user.getRole().getRoleName().equals("Parent")){
            user.setMembership(membershipService.getMembershipByType(MembershipType.DEFAULT));
        }
        // username field not null
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            return userRepository.save(user); //create row in db
        }
        //email not null
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            String otp = senderService.generateCode(); // create OTP code
            saveOTP(user.getEmail(), otp); // save otp with key email
            senderService.sendEmailWithHtml(user.getEmail(), otp); // send mail
            storeUser.put(user.getEmail(), user); // return obj user = null
            return null; //save in map so use null
        }

        throw new RuntimeException("Invalid register !");
    }

    private Map<String, OTP> otpStore = new HashMap<>();

    public void saveOTP(String email, String code) {
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(5); //time now + 5min = expiration time
        OTP otp = new OTP(code, expirationTime);
        otpStore.put(email, otp); // add in map
    }


    public String verifyOtp(String email, String code) {
        OTP otp = otpStore.get(email); //get otp from key
        if (otp == null) {
            return "OTP not found!?";
        }
        if (!otp.getExpirationTime().isAfter(LocalDateTime.now())) { // time now <= time expiration
            otpStore.remove(email, otp);
            return "OTP expiration...";
        }
        if (!otp.getCode().equals(code)) { //check otp code = enter code
            return "OTP invalid!";
        }
        User user = storeUser.remove(email); // take user
        userRepository.save(user); //save db when otp verify
        otpStore.remove(email, otp); // remove otp out map
        return "Authentication OTP successful ";


    }

    //Login
    public UserDTO loginByUsername(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user != null && bCryptPasswordEncoder.matches(password, user.getPassword())) {
            return userToDTO.toDTO(user);
        }
        //pass string encode to match pass hash
        return null;
    }

    public UserDTO loginByEmail(String email, String password) {
        User user = getUserByGmail(email);
        if (user == null) return null;
        if (!bCryptPasswordEncoder.matches(password, user.getPassword())) return null;

        return userToDTO.toDTO(user);

    }

    //getAllUser
    public List<UserDTO> getUser() {
        List<User> users = userRepository.findAll();
        return userToDTO.toDTOList(users);
    }

    //getUserByID
    public UserDTO getUserById(String userID) {
        User user = getUser(userID);
        return userToDTO.toDTO(user);
    }

    public User getUserByGmail(String email) {
        return userRepository.findByEmail(email);
    }


    protected User getUser(String userID) {
        return userRepository.findById(userID)
                .orElseThrow(() -> new RuntimeException("Parent not found"));
    }

    //update user by ID
    public UserDTO updateUser(String userId, User user) {
        User userExist = getUser(userId); //call fun getId to match user
        userExist.setFullName(user.getFullName());
        userExist.setPhone(user.getPhone());
        if(userExist.getEmail().isEmpty()){
            userExist.setEmail(user.getEmail());
        }
        userExist.setAddress(user.getAddress());

        User updateUser = userRepository.save(userExist);

        return userToDTO.toDTO(updateUser);
    }

    //Delete User
    public void deleteUser(String userId) {
        userRepository.deleteById(userId);
    }


    //change password
    public boolean changePassword(String userId, String oldPassword, String newPassword, String confirmPassword) {
        if (userId == null || oldPassword == null || newPassword == null || confirmPassword == null) { //check value not null
            throw new IllegalArgumentException("Input not null !");
        }
        User user = getUser(userId); // find user by id
        if (user == null) {
            throw new IllegalArgumentException("User not found !");
        }

        if (!new BCryptPasswordEncoder().matches(oldPassword, user.getPassword())) { // check pass user to match with pass input when encode
            throw new IllegalArgumentException("Old password incorrect");
        }

        if (!newPassword.equals(confirmPassword)) { //check new pass = confirm pass
            throw new IllegalArgumentException("New password and confirm password must same!");
        }
        user.setPassword(bCryptPasswordEncoder.encode(newPassword));  // update new pass
        userRepository.save(user); // update row
        return true;
    }

    //Check role user by userId
    public String checkRole(String user_id) {
        User user = getUser(user_id);
        return user.getRole().getRoleName();
    }

    //Get User by RoleID
    public List<UserDTO> getUserByRole(long role_id) {
        List<User> users = userRepository.findByRole_RoleId(role_id);
        return userToDTO.toDTOList(users);
    }

    public List<UserDTO> getUserByRoleName(String roleName) {
        List<User> users = userRepository.findByRole_RoleName(roleName);
        return userToDTO.toDTOList(users);
    }


}

