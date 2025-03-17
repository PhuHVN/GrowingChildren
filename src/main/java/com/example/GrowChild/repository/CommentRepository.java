package com.example.GrowChild.repository;

import com.example.GrowChild.entity.response.Blog;
import com.example.GrowChild.entity.response.Booking;
import com.example.GrowChild.entity.response.Comment;
import com.example.GrowChild.entity.response.Consulting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentByIsDeleteFalseAndBlogId(Blog blog);

}
