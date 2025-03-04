package com.example.GrowChild.repository;

import com.example.GrowChild.entity.response.Blog;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<Blog, Long> {
    List<Blog> findBlogByIsDeleteFalse();
    Blog findBlogByIsDeleteFalseAndBlogId(long BlogId);
}
