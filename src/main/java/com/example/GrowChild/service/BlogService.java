package com.example.GrowChild.service;

import com.example.GrowChild.dto.BlogDTO;
import com.example.GrowChild.entity.response.Blog;
import com.example.GrowChild.entity.response.HealthRecord;
import com.example.GrowChild.entity.response.User;
import com.example.GrowChild.mapstruct.toDTO.BlogToDTO;
import com.example.GrowChild.repository.BlogRepository;
import org.hibernate.annotations.Parent;
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


    public Blog createBlog(Blog blog, String user_id) {
        User parent = userService.getUser(user_id);
        if (parent == null || !parent.getRole().getRoleName().equals("Parent")) { // find parent
            throw new RuntimeException("Parent not found");
        }
        Blog blog1 = Blog.builder()
                .parentId(parent)
                .blogId(blog.getBlogId())
                .title(blog.getTitle())
                .content(blog.getContent())
                .hashtag(blog.getHashtag())
                .date(LocalDateTime.now())
                .build();

        return blogRepository.save(blog1);
    }


    public List<BlogDTO> getAll() {
        List<Blog> list = blogRepository.findBlogByIsDeleteFalse();
        return blogToDTO.toDTOList(list);
    }

    public List<Blog> getAllBlog() {
        return blogRepository.findBlogByIsDeleteFalse();
    }

    public List<Blog> getAllBlog_Admin() {
        return blogRepository.findAll();
    }

    public List<BlogDTO> getBlogByUserId(String userId) {
        User user = userService.getUser(userId);
        if (user == null || !user.getRole().getRoleName().equals("Parent")) {
            throw new RuntimeException("User not found or not authorized");
        }
        List<Blog> blogs = blogRepository.findByParentIdAndIsDeleteFalse(user);
        return blogToDTO.toDTOList(blogs);
    }

    public BlogDTO getBlogDTOById(long blog_id) {
        Blog existBlog = getBlogByIsDeleteFalseAndBlogID(blog_id);
        if (existBlog == null) {
            throw new RuntimeException("Blog not found!");
        }
        return blogToDTO.toDTO(existBlog);
    }

    public List<BlogDTO> getBlogByHashTag(String hashtag) {
        List<Blog> blogs = blogRepository.findByHashtagContainingAndIsDeleteFalse(hashtag);
        return blogToDTO.toDTOList(blogs);
    }


    private Blog getBlogByIsDeleteFalseAndBlogID(long blog_id) {
        return blogRepository.findBlogByIsDeleteFalseAndBlogId(blog_id);
    }

    public BlogDTO updateBlog(long blog_id, Blog blog, String parent_id) {

        User parent = userService.getUser(parent_id);
        if (parent == null || !parent.getRole().getRoleName().equals("Parent")) { // find parent
            throw new RuntimeException("Parent not found");
        }
        Blog existBlog = getBlogByIsDeleteFalseAndBlogID(blog_id);

        existBlog = Blog.builder()
                .blogId(existBlog.getBlogId())
                .title(blog.getTitle())
                .hashtag(blog.getHashtag())
                .content(blog.getContent())
                .parentId(existBlog.getParentId())
                .date(LocalDateTime.now())
                .build();


        Blog updateBlog = blogRepository.save(existBlog);
        return blogToDTO.toDTO(updateBlog);
    }

    public String deleteBlog_User(long blog_id, String parent_id) {
        User parent = userService.getUser(parent_id);
        if (parent == null || !parent.getRole().getRoleName().equals("Parent")) { // find parent
            throw new RuntimeException("Parent not found");
        }
        Blog existBlog = getBlogByIsDeleteFalseAndBlogID(blog_id);
        existBlog.setDelete(true);
        blogRepository.save(existBlog);
        return "Delete Successful!";

    }

    public String deleteBlog_Admin(long blogId) {
        Blog existBlog  = getBlogById(blogId);
        blogRepository.delete(existBlog);
        return "Delete Successful!";
    }

    public Blog getBlogById(long blog_id) {
        return blogRepository.findById(blog_id).orElseThrow(() -> new RuntimeException("Blog not found!"));
    }

}
