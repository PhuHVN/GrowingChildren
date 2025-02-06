package com.example.GrowChild.entity;

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
    public List<User> users;
}
