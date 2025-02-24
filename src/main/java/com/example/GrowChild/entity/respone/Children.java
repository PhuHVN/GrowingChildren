package com.example.GrowChild.entity.respone;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
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
    public String childrenName;

    @Min(value = 0,message = "Age greater than 0")
    @Max(value = 20)
    public int age;

    @Column(nullable = false)
    public String gender;

    public boolean isDelete = false;

    @ManyToOne
    @JoinColumn(name = "parent_id",nullable = false)
    public User parentId;

}
