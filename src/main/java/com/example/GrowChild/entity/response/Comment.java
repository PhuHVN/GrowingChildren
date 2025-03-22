package com.example.GrowChild.entity.response;


import com.example.GrowChild.entity.enumStatus.BlogStatus;
import com.example.GrowChild.entity.enumStatus.CommentStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long commentId;

    @NotBlank
    public String comment;

    public boolean isDelete = false;

    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    public User parentId;

    @ManyToOne
    @JoinColumn(name = "blog_id", nullable = false)
    public Blog blogId;

    public LocalDateTime date;

    @PrePersist
    protected void onCreate() {
        this.date = LocalDateTime.now();
    }

    @Enumerated(EnumType.STRING)
    private CommentStatus status;
}
