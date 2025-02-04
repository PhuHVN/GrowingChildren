package com.example.GrowChild.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Setter;

@Setter
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

    @ManyToOne
    @JoinColumn(name = "role_id")
    @JsonIgnoreProperties({"users"})
    public Role role;

    @Column(nullable = true)
    public String gender;

    @Column(nullable = true)
    public String phone;

    @Min(0)
    @Max(5)
    public int rate;

}

