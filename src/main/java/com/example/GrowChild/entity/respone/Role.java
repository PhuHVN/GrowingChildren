package com.example.GrowChild.entity.respone;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    public long roleId;

    @Column(name = "role_name",unique = true)
    @NotBlank()
    public String roleName;

    @OneToMany(mappedBy = "role")
    @JsonManagedReference
    @ToString.Exclude
    private List<User> users;


}
