package com.example.GrowChild.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long role_id;
    public String roleName;

    @OneToMany(mappedBy = "role")
    public List<User> users;
}
