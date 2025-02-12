package com.example.GrowChild.repository;

import com.example.GrowChild.entity.Children;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChildrenRepository extends JpaRepository<Children,Long> {

    List<Children> findChildrenByIsDeleteFalse();
    Children findChildrenByIsDeleteFalseAndChildrenId(long childrenId);
}
