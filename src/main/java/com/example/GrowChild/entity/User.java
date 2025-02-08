package com.example.GrowChild.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.Nullable;

@Setter
@Entity
@Data

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String user_id;

    @Column(nullable = true)
    //@Size(min = 3,message = "Username must be more 3 character!") - BUG - cant no do same time with nullable vs valid size
    public String username;

    @Column(nullable = false)
    public String password;

    @Nullable
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$",message = "Invalid Gmail address")
    public String email;

    @NotBlank(message = "Name is not blank!")
    @Pattern(regexp = "([A-Z a-z])\\w+",message ="Input must be range a - z")
    public String fullName;

    @ManyToOne
    @JoinColumn(name = "roleId")
    @JsonBackReference()
    private Role role;

    @Column(nullable = true)
    public String gender;

    @Nullable
    @Column(unique = true)
    public String phone;

    @Min(0)
    @Max(5)
    public int rate;


}

