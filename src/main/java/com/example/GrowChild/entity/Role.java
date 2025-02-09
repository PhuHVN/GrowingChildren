package com.example.GrowChild.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
    @JsonManagedReference
    private List<User> users;
}
