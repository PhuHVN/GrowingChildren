package com.example.GrowChild.repository;


import com.example.GrowChild.entity.response.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User findByUsername(String username);

    User findByEmail(String email);

    List<User> findByRole_RoleNameAndIsDeleteFalse(String roleName);

    List<User> findByRole_RoleIdAndIsDeleteFalse(long role_id);

    @Query("SELECT u FROM User u JOIN u.children c WHERE c.childrenId = :childrenId")
    User findUserByChildrenId(@Param("childrenId") Long childrenId);

    boolean existsByEmail(String email);

    List<User> findByIsDeleteFalse();

}
