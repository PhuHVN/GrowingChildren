package com.example.GrowChild.api;

import com.example.GrowChild.dto.BlogDTO;
import com.example.GrowChild.entity.response.Blog;
import com.example.GrowChild.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("blogAPI")
public class BlogAPI {
    @Autowired
    BlogService blogService;

    @PostMapping("createBlog")
    public ResponseEntity createBlog(@Valid @RequestBody Blog blog, @RequestParam String userId) {
        Blog blog1 = blogService.createBlog(blog ,userId);
        return new ResponseEntity<>(blog1, HttpStatus.CREATED);
    }
    @GetMapping("blogs")
    public List<Blog> getAllRecord() {
        return blogService.getAllBlog();
    }

    @GetMapping("blogs-admin")
    public List<Blog> getAllRecord_Admin() {
        return blogService.getAllBlog_Admin();
    }

    @GetMapping("getAll")
    public List<BlogDTO> getAllBlog() {
        return blogService.getAll();
    }

    @GetMapping("getAllBlogCompleted")
    public List<BlogDTO> getAllBlogCompleted() {
        return blogService.getAllBlogCompleted();
    }

    @GetMapping("getBlogsByUserId/{userId}")
    public ResponseEntity<List<BlogDTO>> getBlogsByUserId(@PathVariable String userId) {
        List<BlogDTO> blogs = blogService.getBlogByUserId(userId);
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("getBlogById/{blog_id}")
    public BlogDTO getBlogById(@PathVariable long blog_id) {
        return blogService.getBlogDTOById(blog_id);
    }

    @GetMapping("getBlogsByHashTag")
    public ResponseEntity<List<BlogDTO>> getBlogsByHashTag(@RequestParam String hashtag) {
        List<BlogDTO> blogs = blogService.getBlogByHashTag(hashtag);
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @PutMapping("updateBlog")
    public BlogDTO updateBlogById(@RequestParam long blog_id,
                                  @RequestParam String parentId,
                                  @RequestBody Blog blog) {
        return blogService.updateBlog(blog_id, blog, parentId);
    }

    @DeleteMapping("deleteBlog")
    public String deleteBlog(@RequestParam long blog_id, @RequestParam String parentId) {
        return blogService.deleteBlog_User(blog_id, parentId );
    }

    @DeleteMapping("deleteBlogByAdmin")
    public String deleteBlogByAdmin(@RequestParam long blog_id) {
        return blogService.deleteBlog_Admin(blog_id);
    }

    @GetMapping("/user/{userId}/completed")
    public ResponseEntity<List<BlogDTO>> getCompletedBlogsByUser(@PathVariable String userId) {
        List<BlogDTO> blogs = blogService.getBlogByUser(userId);
        return ResponseEntity.ok(blogs);
    }
    
    @PutMapping("/approve/{blogId}")
    public ResponseEntity<String> approveBlog(
            @PathVariable Long blogId,
            @RequestParam String adminId) {

        if (blogId == null || adminId == null || adminId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Blog ID and Admin ID must be provided");
        }

        String response = blogService.approveBlog(blogId, adminId);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/check/{blogId}")
    public ResponseEntity<String> checkBlog(
            @PathVariable Long blogId,
            @RequestParam String parentId) {

        if (blogId == null || parentId == null || parentId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Blog ID and Parent ID must be provided");
        }

        String response = blogService.checkBlog(blogId, parentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/checkByAdmin/{blogId}")
    public ResponseEntity<String> checkBlogByAdmin(
            @PathVariable Long blogId,
            @RequestParam String adminId) {

        if (blogId == null || adminId == null || adminId.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Blog ID and Admin ID must be provided");
        }

        String response = blogService.checkBlogByAdmin(blogId, adminId);
        return ResponseEntity.ok(response);
    }



    @PutMapping("/reject")
    public ResponseEntity<String> rejectBlog(
            @RequestParam(required = true) Long blogId,
            @RequestParam(required = true) String adminId) {
        try {
            // Kiểm tra đầu vào có null hoặc rỗng không
            if (blogId == null || adminId == null || adminId.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Blog ID and Admin ID must be provided");
            }

            String response = blogService.rejectBlog(blogId, adminId);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


}
