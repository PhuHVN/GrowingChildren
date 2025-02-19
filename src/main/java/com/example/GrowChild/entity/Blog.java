package com.example.GrowChild.entity;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long blogId;

    @NotBlank
    public String title;

    @NotBlank
    public String description;

    @NotBlank
    public String content;

    public boolean isDelete = false;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    public User parentId;

}
