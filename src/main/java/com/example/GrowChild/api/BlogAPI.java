package com.example.GrowChild.api;

import com.example.GrowChild.dto.BlogDTO;
import com.example.GrowChild.entity.Blog;
import com.example.GrowChild.service.BlogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("blog")
public class BlogAPI {

    @Autowired
    BlogService blogService;

    @PostMapping("createBlog/{user_id}")
    public ResponseEntity createBlog(@Valid @RequestBody Blog blog, @RequestParam String userId){
        if(!blogService.createBlog(blog,userId)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error create blog!");
        }
        return new ResponseEntity<>(blog,HttpStatus.CREATED);
    }

    @GetMapping("getAll")
    public List<BlogDTO> getAllBlog(){
        return blogService.getAll();
    }

    @GetMapping("getBlogById/{blog_id}")
    public BlogDTO getBlogById(@RequestParam long blog_id){
        return blogService.getBlogById(blog_id);
    }

    @PutMapping("updateBlog/{blog_id}")
    public BlogDTO updateBlogById(@RequestParam long blog_id,
                               @RequestBody Blog blog){
        return blogService.updateBlog(blog_id,blog);
    }

    @DeleteMapping("deleteBlog/{blog_id}")
    public String deleteBlog(@RequestParam long blog_id){
        return blogService.deleteBlog(blog_id);
    }

}
