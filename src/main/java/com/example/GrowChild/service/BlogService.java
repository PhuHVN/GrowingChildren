package com.example.GrowChild.service;

import com.example.GrowChild.dto.BlogDTO;
import com.example.GrowChild.entity.response.Blog;

import com.example.GrowChild.mapstruct.toDTO.BlogToDTO;


import com.example.GrowChild.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserService userService;

    @Autowired
    BlogToDTO blogToDTO;

    public boolean createBlog(Blog blog, String parent_id){
        com.example.GrowChild.entity.response.User parent = userService.getUser(parent_id);
        if(parent == null || !parent.getRole().getRoleName().equals("Parent")){ // find parent
            throw new RuntimeException("Parent not found");
        }
        blog.setParentId(parent);
        blog.setDelete(false);
        blogRepository.save(blog);
        return true;
    }


    public List<BlogDTO> getAll(){
        List<Blog> list = blogRepository.findBlogByIsDeleteFalse();
        return blogToDTO.toDTOList(list);
    }


    public BlogDTO getBlogById(long blog_id){
        Blog existBlog = getBlogByIsDeleteFalseAndBlogID(blog_id);
        if(existBlog == null){
            throw new RuntimeException("Blog not found!");
        }
        return blogToDTO.toDTO(existBlog);
    }


    private Blog getBlogByIsDeleteFalseAndBlogID(long blog_id){
        return blogRepository.findBlogByIsDeleteFalseAndBlogId(blog_id);
    }



    public BlogDTO updateBlog(long blog_id, Blog blog){
        Blog existBlog = getBlogByIsDeleteFalseAndBlogID(blog_id);
        existBlog = Blog.builder()
                .blogId(existBlog.getBlogId())
                .title(blog.getTitle())
                .description(blog.getDescription())
                .content(blog.getContent())
                .parentId(existBlog.getParentId())
                .date(LocalDateTime.now())
                .build();


        Blog updateBlog = blogRepository.save(existBlog);
        return blogToDTO.toDTO(updateBlog);
    }


    public String deleteBlog(long blog_id){
        Blog existBlog = getBlogByIsDeleteFalseAndBlogID(blog_id);
        existBlog.setDelete(true);
        blogRepository.save(existBlog);
        return "Delete Successful!";
    }

}
