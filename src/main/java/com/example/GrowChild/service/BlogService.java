package com.example.GrowChild.service;

import com.example.GrowChild.dto.BlogDTO;
import com.example.GrowChild.entity.Blog;
import com.example.GrowChild.entity.Children;
import com.example.GrowChild.entity.User;
import com.example.GrowChild.mapstruct.BlogMapper;
import com.example.GrowChild.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlogService {

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    UserService userService;

    @Autowired
    BlogMapper blogMapper;

    public boolean createBlog(Blog blog, String userId){
        User user = userService.getUser(userId);
        if(user ==  null){
            return false;
        }
        blog.setParentId(user);
        blog.setDelete(false);
        blogRepository.save(blog);
        return true;
    }


    public List<BlogDTO> getAll(){
        List<Blog> list = blogRepository.findBlogByIsDeleteFalse();
        return blogMapper.toDTOList(list);
    }


    public BlogDTO getBlogById(long blog_id){
        Blog existBlog = getBlogByIsDeleteFalseAndBlogID(blog_id);
        if(existBlog == null){
            throw new RuntimeException("Blog not found!");
        }
        return blogMapper.toDTO(existBlog);
    }


    private Blog getBlogByIsDeleteFalseAndBlogID(long blog_id){
        return blogRepository.findBlogByIsDeleteFalseAndBlogId(blog_id);
    }



    public BlogDTO updateBlog(long blog_id, Blog blog){
        Blog existBlog = getBlogByIsDeleteFalseAndBlogID(blog_id);
//        existBlog = Blog.builder()
//                .title(existBlog.getTitle())
//                .description(blog.getDescription())
//                .content(blog.getContent())
//                .parentId(existBlog.getParentId())
//                .date(existBlog.getDate());

        existBlog.setTitle(blog.getTitle());
        existBlog.setDescription(blog.getDescription());
        existBlog.setContent(blog.getContent());
        existBlog.setParentId(blog.getParentId());
        existBlog.setDate(blog.getDate());

        Blog updateBlog = blogRepository.save(existBlog);
        return blogMapper.toDTO(updateBlog);
    }


    public String deleteBlog(long blog_id){
        Blog existBlog = getBlogByIsDeleteFalseAndBlogID(blog_id);
        existBlog.setDelete(true);
        blogRepository.save(existBlog);
        return "Delete Successful!";
    }

}
