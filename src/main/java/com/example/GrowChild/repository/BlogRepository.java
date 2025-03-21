package com.example.GrowChild.repository;

import com.example.GrowChild.entity.response.Blog;
import com.example.GrowChild.entity.response.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findBlogByIsDeleteFalse();

    List<Blog> findByParentIdAndIsDeleteFalse(User parent);

    List<Blog> findByHashtagContainingAndIsDeleteFalse(String hashtag);

    Blog findBlogByIsDeleteFalseAndBlogId(Long BlogId);
}
