package com.example.GrowChild.service;

import com.example.GrowChild.dto.BlogDTO;
import com.example.GrowChild.dto.CommentDTO;
import com.example.GrowChild.entity.enumStatus.CommentStatus;
import com.example.GrowChild.entity.response.*;
import com.example.GrowChild.mapstruct.toDTO.CommentToDTO;
import com.example.GrowChild.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;

    @Autowired
    BlogService blogService;

    @Autowired
    CommentToDTO commentToDTO;

    @Autowired
    UserService userService;

    public Comment createComment(Comment comment, long blog_id, String parent_id) {
        if (comment == null || comment.getComment() == null || comment.getComment().isEmpty()) {
            throw new RuntimeException("Comment content cannot be empty");
        }

        Blog blog = blogService.getBlogById(blog_id);
        if (blog == null) {
            throw new RuntimeException("Blog not found");
        }

        User parent = userService.getUser(parent_id);
        if (parent == null || !parent.getRole().getRoleName().equals("Parent")) {
            throw new RuntimeException("Only parents can report blogs");
        }

        Comment newComment = Comment.builder()
                .blogId(blog)
                .parentId(parent)
                .comment(comment.getComment())
                .commentId(comment.getCommentId())
                .status(CommentStatus.COMPLETED) // Luôn đặt trạng thái là COMPLETED
                .build();

        return commentRepository.save(newComment);
    }

    public List<CommentDTO> getCommentByBlogId(long blogId, String parentId) {
        User parent = userService.getUser(parentId);
        if (parent == null || !parent.getRole().getRoleName().equals("Parent")) {
            throw new RuntimeException("Only parents can view comments");
        }

        Blog blog = blogService.getBlogById(blogId);
        if (blog == null) {
            throw new RuntimeException("Blog not found");
        }

        List<Comment> comments = commentRepository.findByBlogIdAndParentIdAndStatus(blog, parent, CommentStatus.COMPLETED);
        return comments.stream()
                .map(commentToDTO::toDTO)
                .collect(Collectors.toList());
    }
    private Comment getCommentByIsDeleteFalseAndCommentID(long comment_id) {
        return commentRepository.findCommentByIsDeleteFalseAndCommentId(comment_id);
    }

    public String deleteCommentByUser(long comment_id, String parent_id) {
        User parent = userService.getUser(parent_id);
        if (parent == null || !parent.getRole().getRoleName().equals("Parent")) { // find parent
            throw new RuntimeException("Parent not found");
        }
        Comment existComment = getCommentByIsDeleteFalseAndCommentID(comment_id);
        existComment.setDelete(true);
        commentRepository.save(existComment);
        return "Delete Successful!";
    }

    public CommentDTO getCommentById(long comment_id) {
        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new RuntimeException("Comment not found!"));
        return commentToDTO.toDTO(comment);
    }

    public Comment getCommentByCommentId(long comment_id) {
        return commentRepository.findById(comment_id).orElseThrow(() -> new RuntimeException("Comment not found!"));
    }

    public String updateCommentByUser(long comment_id, String parent_id, String newContent) {
        User parent = userService.getUser(parent_id);
        if (parent == null || !parent.getRole().getRoleName().equals("Parent")) {
            throw new RuntimeException("Only parents can update comments");
        }

        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getParentId().equals(parent)) {
            throw new RuntimeException("You can only update your own comments");
        }

        comment.setComment(newContent);
        commentRepository.save(comment);
        return "Comment updated successfully!";
    }

    public List<CommentDTO> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream()
                .map(commentToDTO::toDTO)
                .collect(Collectors.toList());
    }

    public String deleteCommentByAdmin(long commentId) {
        Comment existComment  = getCommentByCommentId(commentId);
        commentRepository.delete(existComment);
        return "Delete Successful!";
    }

    public String reportComment(long comment_id, String parent_id) {
        User parent = userService.getUser(parent_id);
        if (parent == null || !parent.getRole().getRoleName().equals("Parent")) {
            throw new RuntimeException("Only parents can report comments");
        }

        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (comment.getStatus() == CommentStatus.COMPLETED) {
            comment.setStatus(CommentStatus.PENDING);
            commentRepository.save(comment);
            return "Comment reported successfully and status changed to PENDING!";
        }

        return "Comment is already in PENDING status.";
    }
    public String reportCommentByAdmin(long comment_id, String admin_id) {
        User admin = userService.getUser(admin_id);
        if (admin == null || !admin.getRole().getRoleName().equals("Admin")) {
            throw new RuntimeException("Only Admin can report comments");
        }

        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (comment.getStatus() == CommentStatus.COMPLETED) {
            comment.setStatus(CommentStatus.PENDING);
            commentRepository.save(comment);
            return "Comment reported successfully and status changed to PENDING!";
        }

        return "Comment is already in PENDING status.";
    }
    public String approveComment(long comment_id, String admin_id) {
        User admin = userService.getUser(admin_id);
        if (admin == null || !admin.getRole().getRoleName().equals("Admin")) {
            throw new RuntimeException("Only admins can approve comments");
        }

        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (comment.getStatus() == CommentStatus.PENDING) {
            comment.setStatus(CommentStatus.COMPLETED);
            commentRepository.save(comment);
            return "Comment approved successfully!";
        }

        return "Only PENDING comments can be approved.";
    }

    public String rejectComment(long comment_id, String admin_id) {
        User admin = userService.getUser(admin_id);
        if (admin == null || !admin.getRole().getRoleName().equals("Admin")) {
            throw new RuntimeException("Only admins can reject comments");
        }

        Comment comment = commentRepository.findById(comment_id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (comment.getStatus() == CommentStatus.PENDING) {
            comment.setStatus(CommentStatus.CANCELLED);
            commentRepository.save(comment);
            return "Comment rejected successfully!";
        }

        return "Only PENDING comments can be rejected.";
    }



}
