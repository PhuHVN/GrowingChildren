package com.example.GrowChild.api;

import com.example.GrowChild.dto.CommentDTO;
import com.example.GrowChild.entity.response.Comment;
import com.example.GrowChild.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("commentAPI")
public class CommentAPI {
    @Autowired
    CommentService commentService;

    @PostMapping("create")
    public ResponseEntity<?> createComment(@RequestBody Comment comment,
                                           @RequestParam long blog_id,
                                           @RequestParam String parent_id) {
        try {
            Comment newComment = commentService.createComment(comment, blog_id, parent_id);
            return ResponseEntity.ok(newComment);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("getCommentByBlogId")
    public ResponseEntity<List<CommentDTO>> getCommentByBlogId(@RequestParam long blogId, @RequestParam String parentId) {
        List<CommentDTO> comments = commentService.getCommentByBlogId(blogId, parentId);
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("deleteByUser")
    public ResponseEntity<String> deleteComment(@RequestParam long commentId, @RequestParam String parentId) {
        String response = commentService.deleteCommentByUser(commentId, parentId);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/updateComment")
    public ResponseEntity<String> updateCommentByUser(@RequestParam long comment_id,
                                                      @RequestParam String parent_id,
                                                      @RequestParam String newContent) {
        return ResponseEntity.ok(commentService.updateCommentByUser(comment_id, parent_id, newContent));
    }

    @GetMapping("getAllComment")
    public ResponseEntity<List<CommentDTO>> getAllComments() {
        return ResponseEntity.ok(commentService.getAllComments());
    }
    @GetMapping("getCommentById")
    public ResponseEntity<CommentDTO> getCommentById(@RequestParam long comment_id) {
        CommentDTO commentDTO = commentService.getCommentById(comment_id);
        return ResponseEntity.ok(commentDTO);
    }
    @DeleteMapping("deleteByAdmin")
    public ResponseEntity<String> deleteCommentByAdmin(@RequestParam long commentId) {
        return ResponseEntity.ok(commentService.deleteCommentByAdmin(commentId));
    }

    @PostMapping("reportByUser")
    public ResponseEntity<String> reportComment(@RequestParam long comment_id, @RequestParam String parent_id) {
        return ResponseEntity.ok(commentService.reportComment(comment_id, parent_id));
    }

    @PostMapping("reportByAdmin")
    public ResponseEntity<String> reportCommentByAdmin(@RequestParam long comment_id, @RequestParam String admin_id) {
        return ResponseEntity.ok(commentService.reportCommentByAdmin(comment_id, admin_id));
    }

    @PostMapping("/approve")
    public ResponseEntity<String> approveComment(@RequestParam long comment_id, @RequestParam String admin_id) {
        return ResponseEntity.ok(commentService.approveComment(comment_id, admin_id));
    }

    @PostMapping("/reject")
    public ResponseEntity<String> rejectComment(@RequestParam long comment_id, @RequestParam String admin_id) {
        return ResponseEntity.ok(commentService.rejectComment(comment_id, admin_id));
    }
}
