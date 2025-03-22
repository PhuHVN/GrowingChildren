package com.example.GrowChild.service;

import com.example.GrowChild.dto.BlogDTO;
import com.example.GrowChild.entity.enumStatus.BlogStatus;
import com.example.GrowChild.entity.response.Blog;
import com.example.GrowChild.entity.response.User;
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
                .status(BlogStatus.PENDING)
                .date(LocalDateTime.now())
                .build();

        return blogRepository.save(blog1);
    }

    public String approveBlog(Long blogId, String adminId) {
        // Kiểm tra xem blogId và adminId có bị null hoặc rỗng không
        if (blogId == null || adminId == null || adminId.trim().isEmpty()) {
            throw new RuntimeException("Blog ID and Admin ID must be provided");
        }

        // Kiểm tra admin có quyền duyệt không
        User admin = userService.getUser(adminId);
        if (admin == null || !admin.getRole().getRoleName().equals("Admin")) {
            throw new RuntimeException("Only admin can approve blogs");
        }

        // Kiểm tra blog có tồn tại không
        Blog existBlog = getBlogById(blogId);
        if (existBlog == null || !existBlog.getStatus().equals(BlogStatus.PENDING)) {
            throw new RuntimeException("Blog is not pending or not found");
        }

        // Cập nhật trạng thái blog
        existBlog.setStatus(BlogStatus.COMPLETED);
        blogRepository.save(existBlog);

        return "Blog approved successfully!";
    }



    public List<BlogDTO> getAllConfirmedBlogs() {
        List<Blog> blogs = blogRepository.findByStatus(BlogStatus.COMPLETED);
        return blogToDTO.toDTOList(blogs);
    }

    public List<BlogDTO> getAllBlogsForAdmin() {
        List<Blog> blogs = blogRepository.findAll();
        return blogToDTO.toDTOList(blogs);
    }

    public List<BlogDTO> getAll() {
        List<Blog> list = blogRepository.findBlogByIsDeleteFalse();
        return blogToDTO.toDTOList(list);
    }
    public String rejectBlog(long blogId, String adminId) {
        if (blogId == 0 || adminId == null || adminId.trim().isEmpty()) {
            throw new RuntimeException("Blog ID and Admin ID must be provided");
        }

        // Lấy thông tin Admin
        User admin = userService.getUser(adminId);
        if (admin == null || !admin.getRole().getRoleName().equals("Admin")) {
            throw new RuntimeException("Only admin can reject blogs");
        }

        // Lấy thông tin Blog
        Blog existBlog = getBlogById(blogId);
        if (existBlog == null) {
            throw new RuntimeException("Blog not found");
        }

        // Kiểm tra trạng thái Blog có phải PENDING không
        if (!BlogStatus.PENDING.equals(existBlog.getStatus())) {
            throw new RuntimeException("Only pending blogs can be rejected");
        }

        // Cập nhật trạng thái Blog thành REJECTED
        existBlog.setStatus(BlogStatus.CANCELLED);
        blogRepository.save(existBlog);

        return "Blog rejected successfully!";
    }

    public String checkBlog(long blogId, String parentId) {
        // Lấy thông tin Parent
        User parent = userService.getUser(parentId);
        if (parent == null || !parent.getRole().getRoleName().equals("Parent")) {
            throw new RuntimeException("Parents can report blogs");
        }

        // Lấy thông tin Blog
        Blog existBlog = getBlogById(blogId);
        if (existBlog == null) {
            throw new RuntimeException("Blog not found");
        }

        // Kiểm tra trạng thái Blog có phải COMPLETED không
        if (!BlogStatus.COMPLETED.equals(existBlog.getStatus())) {
            throw new RuntimeException("Only completed blogs can be reported");
        }

        // Cập nhật trạng thái Blog thành PENDING
        existBlog.setStatus(BlogStatus.PENDING);
        blogRepository.save(existBlog);

        return "Blog reported successfully and is now pending review!";
    }

    public String checkBlogByAdmin(long blogId, String adminId) {
        // Lấy thông tin Parent
        User parent = userService.getUser(adminId);
        if (parent == null || !parent.getRole().getRoleName().equals("Admin")) {
            throw new RuntimeException(" Admin can report blogs");
        }

        // Lấy thông tin Blog
        Blog existBlog = getBlogById(blogId);
        if (existBlog == null) {
            throw new RuntimeException("Blog not found");
        }

        // Kiểm tra trạng thái Blog có phải COMPLETED không
        if (!BlogStatus.COMPLETED.equals(existBlog.getStatus())) {
            throw new RuntimeException("Only completed blogs can be reported");
        }

        // Cập nhật trạng thái Blog thành PENDING
        existBlog.setStatus(BlogStatus.PENDING);
        blogRepository.save(existBlog);

        return "Blog reported successfully and is now pending review!";
    }
    public List<Blog> getAllBlog() {
        return blogRepository.findBlogByIsDeleteFalse();
    }

    public List<Blog> getAllBlog_Admin() {
        return blogRepository.findAll();
    }

    public List<BlogDTO> getAllBlogCompleted() {
        List<Blog> blogs = blogRepository.findByStatus(BlogStatus.COMPLETED);
        return blogToDTO.toDTOList(blogs);
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

    public List<BlogDTO> getBlogByUser(String userId) {
        User user = userService.getUser(userId);
        if (user == null || !user.getRole().getRoleName().equals("Parent")) {
            throw new RuntimeException("User not found or not authorized");
        }
        List<Blog> blogs = blogRepository.findByParentIdAndStatusAndIsDeleteFalse(user, BlogStatus.COMPLETED);
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
