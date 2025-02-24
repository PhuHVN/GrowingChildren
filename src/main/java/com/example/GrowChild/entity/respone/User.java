package com.example.GrowChild.entity.respone;


import com.example.GrowChild.entity.Blog;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String user_id;

    @Column(nullable = true,unique = true)
    public String username;

    @NotBlank(message = "password not blank!")
    public String password;

    @Nullable
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$",message = "Invalid Gmail address")
    public String email;

    @NotBlank(message = "Name is not blank!")
    @Pattern(regexp = "([A-Z a-z])\\w+",message ="Input must be range a - z")
    public String fullName;

    @Column(nullable = true)
    public String gender;

    @Nullable
    public String phone;

    @Min(value = 0,message =" rate must be greater 0")
    @Max(value = 5,message =" rate must be lower 5")
    public int rate;

    @Column(nullable = true)
    public String address;



    @ManyToOne
    @JoinColumn(name = "roleId")
    @JsonBackReference()
    @ToString.Exclude
    @JsonIgnore
    public Role role ;

    @OneToMany(mappedBy = "childrenId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Children> children;


    @OneToMany(mappedBy = "blogId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Blog> blog;

//    @OneToMany(mappedBy = "feedbackId", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<FeedBack> feedback;


    @OneToMany(mappedBy = "scheduleId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<ScheduleDoctor> schedule;


    private LocalDateTime createAt = LocalDateTime.now();

    private boolean isDelete = false; // 0 active - 1 delete

    @PrePersist
    protected void onCreate() {
        this.createAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ",\n Username='" + username + '\'' +
                ",\n email='" + email + '\'' +
                ",\n fullName='" + fullName + '\'' +
                ",\n phone='" + phone + '\'' +
                ",\n gender='" + gender + '\'' +
                ",\n roleName='" + (role != null ? role.getRoleName() : "null") + '\'' +
                ",\n isDelete=" + isDelete +
                '}';
    }



}

