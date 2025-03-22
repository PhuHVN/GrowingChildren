package com.example.GrowChild.repository;

import com.example.GrowChild.entity.enumStatus.CommentStatus;
import com.example.GrowChild.entity.response.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentByIsDeleteFalseAndCommentId(Long CommentId);

    List<Comment> findByBlogIdAndParentIdAndStatus(Blog blogId, User parentId, CommentStatus status);


}
