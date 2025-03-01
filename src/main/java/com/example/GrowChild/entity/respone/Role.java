package com.example.GrowChild.entity.respone;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    long roleId;

    @Column(name = "role_name", unique = true)
    @NotBlank()
    String roleName;

    @OneToMany(mappedBy = "role")
    @JsonManagedReference
    @ToString.Exclude
    List<User> users;


}
