package com.example.GrowChild.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Entity
@Data

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String user_id;

    @Column(unique = true,nullable = true)
    //@Size(min = 3,message = "Username must be more 3 character!") - BUG - cant no do same time with nullable vs valid size
    public String username;

    @Column(nullable = true)
    public String password;

    @Column(nullable = true)
    public String googleId;

    @Column(nullable = true)
    public String email;

    @Pattern(regexp = "([A-Z a-z])\\w+",message ="Input must be range a - z")
    public String fullName;

    public int role_id;

    @Column(nullable = true)
    public String gender;

    @Column(nullable = true)
    public String phone;

    @Min(0)
    @Max(5)
    public int rate;

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }
}

