package com.example.GrowChild.api;

import com.example.GrowChild.dto.CommentDTO;
import com.example.GrowChild.dto.ConsultingDTO;
import com.example.GrowChild.entity.request.CommentRequest;
import com.example.GrowChild.entity.request.ConsultingRequest;
import com.example.GrowChild.entity.response.Comment;
import com.example.GrowChild.entity.response.Consulting;
import com.example.GrowChild.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("commentAPI")
public class CommentAPI {
    @Autowired
    CommentService commentService;

    @PostMapping("createComment")
    public ResponseEntity createComment(@Valid @RequestBody CommentRequest commentRequest, @RequestParam long blogId) {
        Comment comment1 = commentService.createComment(commentRequest,blogId );
        return new ResponseEntity<>(comment1, HttpStatus.CREATED);
    }

    @GetMapping("getCommentByBlogId/{blog_id}")
    public ResponseEntity<List<CommentDTO>> getCommentByBlogId(@PathVariable("blog_id") long blogId) {
        List<CommentDTO> commentDTOS = commentService.getCommentByBlogId(blogId);
        return ResponseEntity.ok(commentDTOS);
    }
}
