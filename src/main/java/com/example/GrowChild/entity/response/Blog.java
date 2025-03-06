package com.example.GrowChild.entity.response;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

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

    public String fullName;
    public boolean isDelete = false;
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    public User parentId;
    private LocalDateTime date;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDateTime.now(); // Gán thời gian khi tạo blog
    }

}
