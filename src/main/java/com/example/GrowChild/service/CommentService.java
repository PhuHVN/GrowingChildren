package com.example.GrowChild.service;

import com.example.GrowChild.dto.CommentDTO;
import com.example.GrowChild.dto.ConsultingDTO;
import com.example.GrowChild.entity.request.CommentRequest;
import com.example.GrowChild.entity.response.Blog;
import com.example.GrowChild.entity.response.Booking;
import com.example.GrowChild.entity.response.Comment;
import com.example.GrowChild.entity.response.Consulting;
import com.example.GrowChild.mapstruct.toDTO.CommentToDTO;
import com.example.GrowChild.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BlogService blogService;

    @Autowired
    CommentToDTO commentToDTO;

    public Comment createComment(CommentRequest commentRequest, long blog_id){
        Blog blog = blogService.getBlogById(blog_id);
        if (blog == null){
            throw new RuntimeException("Blog not found");
        }
        Comment comment1 = Comment.builder()
                .comment(commentRequest.getComment())
                .date(LocalDateTime.now())
                .blogId(blog)
                .build();

        return commentRepository.save(comment1);
    }

    public List<CommentDTO> getCommentByBlogId(long blogId) {
        Blog blog = blogService.getBlogById(blogId);
        if (blog == null) {
            throw new RuntimeException("Blog not found!");
        }

        List<Comment> comments = commentRepository.findCommentByIsDeleteFalseAndBlogId(blog);
        if (comments.isEmpty()) {
            throw new RuntimeException("Comment not found for this blog!");
        }

        return commentToDTO.toDTOList(comments);
    }
}
