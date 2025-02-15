package com.example.GrowChild.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Children {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long childrenId;

    @NotBlank(message = "Name children not blank!")
    public String username;

    @Min(value = 0,message = "Age greater than 0")
    public int age;

    @Column(nullable = false)
    public String gender;

    public boolean isDelete = false;

    public String parentId;

}
