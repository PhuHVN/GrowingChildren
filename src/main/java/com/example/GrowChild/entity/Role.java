package com.example.GrowChild.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    public long roleId;
    @Column(name = "role_name")
    public String roleName;

    @OneToMany(mappedBy = "role")
    @JsonBackReference
    private List<User> users;
}
