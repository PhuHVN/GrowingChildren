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

    @GetMapping("getBlogsByUserId/{userId}")
    public ResponseEntity<List<BlogDTO>> getBlogsByUserId(@PathVariable String userId) {
        List<BlogDTO> blogs = blogService.getBlogByUserId(userId);
        return new ResponseEntity<>(blogs, HttpStatus.OK);
    }

    @GetMapping("getBlogById/{blog_id}")
    public BlogDTO getBlogById(@PathVariable long blog_id) {
        return blogService.getBlogDTOById(blog_id);
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
}
