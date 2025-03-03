package com.example.GrowChild.entity.response;


import com.example.GrowChild.entity.Blog;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    String user_id;

    @Column(nullable = true, unique = true)
    String username;

    @NotBlank(message = "password not blank!")
    String password;

    @Nullable
    @Email(message = "Email is not valid")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@gmail\\.com$", message = "Invalid Gmail address")
    String email;

    @NotBlank(message = "Name is not blank!")
    @Pattern(regexp = "([A-Z a-z])\\w+", message = "Input must be range a - z")
    String fullName;

    @Column(nullable = true)
    String gender;

    @Nullable
    String phone;

    @Min(value = 0, message = " rate must be greater 0")
    @Max(value = 5, message = " rate must be lower 5")
    int rate;

    @Column(nullable = true)
    String address;

    LocalDateTime createAt = LocalDateTime.now();

    boolean isDelete = false; // 0 active - 1 delete

    @PrePersist
    void onCreate() {
        this.createAt = LocalDateTime.now();
    }


    @ManyToOne
    @JoinColumn(name = "roleId")
    @JsonBackReference()
    @ToString.Exclude
    @JsonIgnore
    Role role;

    @OneToMany(mappedBy = "childrenId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Children> children;

    @OneToMany(mappedBy = "blogId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Blog> blog;

//    @OneToMany(mappedBy = "feedbackId", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<FeedBack> feedback;

    @OneToMany(mappedBy = "bookId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<Booking> booking;

    @OneToMany(mappedBy = "scheduleId", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    List<ScheduleDoctor> schedule;

    @ManyToOne
    @JoinColumn(name = "membership_id", nullable = true)
    @JsonIgnore
    Membership membership;

    @Override
    public String toString() {
        return "User{" + "user_id='" + getUser_id() + '\'' + ",\n Username='" + getUsername() + '\'' + ",\n email='" + getEmail() + '\'' + ",\n fullName='" + getFullName() + '\'' + ",\n phone='" + getPhone() + '\'' + ",\n gender='" + getGender() + '\'' + ",\n roleName='" + (getRole() != null ? getRole().getRoleName() : "null") + '\'' + ",\n isDelete=" + isDelete() + '}';
    }


}

